package com.example.studentinformationmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.model.Certificate;

import org.w3c.dom.Text;

import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CertificateHolder> {

    private List<Certificate> certificateList;

    public CertificateAdapter(List<Certificate> certificateList) {
        this.certificateList = certificateList;
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
        holder.idText.setText(cer.getId());
        holder.titleText.setText(cer.getTitle());
        holder.descriptionText.setText(cer.getDescription());
        holder.dateText.setText(cer.getDescription());
    }

    @Override
    public int getItemCount() {
        return certificateList.size();
    }

    public class  CertificateHolder extends RecyclerView.ViewHolder {
        TextView idText, titleText, descriptionText, dateText;
        public CertificateHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.cert_id);
            titleText = itemView.findViewById(R.id.cert_title);
            descriptionText = itemView.findViewById(R.id.cert_description);
            dateText = itemView.findViewById(R.id.cert_date);
        }
    }
}
