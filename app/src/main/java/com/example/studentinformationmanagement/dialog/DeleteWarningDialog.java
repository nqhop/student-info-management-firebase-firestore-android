package com.example.studentinformationmanagement.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DeleteWarningDialog {
    public static void showDeleteDialog(Context context, String itemName, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete " + itemName + "?");
        builder.setPositiveButton("Delete", positiveClickListener);
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
