package com.example.studentinformationmanagement.dialog;

import com.example.studentinformationmanagement.model.Certificate;

public interface CertificateDialogListener {
    void onCertificateAdded(Certificate certificate);
    void onCertificateUpdated(Certificate certificate, int position);
    void onCertificateDeleted(int position);
}
