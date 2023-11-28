package com.example.studentinformationmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.dialog.CertificateDialogListener;
import com.example.studentinformationmanagement.model.Certificate;

import org.w3c.dom.Text;

import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CertificateHolder> {

    private List<Certificate> certificateList;

    public ItemClickListener clickListener;
    public CertificateDialogListener listener;

    public void setClickListener(ItemClickListener myListener) {
       this.clickListener = myListener;
    }

    public CertificateAdapter(List<Certificate> certificateList, CertificateDialogListener listener) {
        this.certificateList = certificateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CertificateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.certificate, parent,false);
        return new CertificateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificateHolder holder, int position) {
        Certificate cer = certificateList.get(position);
//        holder.idText.setText(cer.getId());
        holder.titleText.setText(cer.getTitle());
        holder.descriptionText.setText(cer.getDescription());
        holder.dateText.setText(cer.getDate());
        holder.deleteBtn.setOnClickListener(v -> {
            listener.onCertificateDeleted(position);
        });
    }

    @Override
    public int getItemCount() {
        return certificateList.size();
    }

    public class  CertificateHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {
        TextView idText, titleText, descriptionText, dateText;
        Button deleteBtn;
        public CertificateHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.delete);
            titleText = itemView.findViewById(R.id.cert_title);
            descriptionText = itemView.findViewById(R.id.cert_description);
            dateText = itemView.findViewById(R.id.cert_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
