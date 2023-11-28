package com.example.studentinformationmanagement.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.studentinformationmanagement.MainActivity;
import com.example.studentinformationmanagement.R;
import com.example.studentinformationmanagement.activity.FormActivity;
import com.example.studentinformationmanagement.model.Certificate;

import java.util.ArrayList;


public class CertificateDialog extends DialogFragment {
    View dialogView;

    Certificate certificate;
    boolean isUpdated = false;
    int position;


    private CertificateDialogListener listener;

    public CertificateDialog(CertificateDialogListener certificateDialogListener, Certificate certificate){
        this.listener = certificateDialogListener;
        this.certificate = certificate;
    }

    public CertificateDialog( CertificateDialogListener certificateDialogListener, Certificate certificate, int position) {
        this(certificateDialogListener, certificate);
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.certication_dialog, null);

        EditText title = dialogView.findViewById(R.id.title);
        EditText description = dialogView.findViewById(R.id.description);
        EditText date = dialogView.findViewById(R.id.date);



        if(certificate != null) {
            title.setText(certificate.getTitle());
            description.setText(certificate.getDescription());
            date.setText(certificate.getDate());
            isUpdated = true;
        }


        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                      Certificate certificate = new Certificate("01", title.getText().toString(),
                               description.getText().toString(),
                               date.getText().toString()
                              );

                        if (isUpdated) {
                            listener.onCertificateUpdated(certificate, position);
                        } else {
                            listener.onCertificateAdded(certificate);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel the dialog.
                        CertificateDialog.this.getDialog().cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    public void saveCert() {

    }
}
