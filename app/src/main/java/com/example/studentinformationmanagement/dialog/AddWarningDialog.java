package com.example.studentinformationmanagement.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AddWarningDialog {
    public static void showAddDialog(Context context, String itemName, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input fields are not valid");
        builder.setMessage("Enter input fields and correct date format (dd/mm/yy)");
        builder.setPositiveButton("Confirm", positiveClickListener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
