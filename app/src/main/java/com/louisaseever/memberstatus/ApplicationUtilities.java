package com.louisaseever.memberstatus;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by LouisaSeever on 8/11/2015.
 */

public class ApplicationUtilities {

    /***
     * showErrorMessage shows a dialog to the user with the specified message
     * @param context: owning context
     * @param message:  message to be displayed
     */
    public static void showErrorMessage(Context context, String message) {
        showMessage(context, "Error Encountered", message);
    }

    public static void showMessage(Context context, String title, String message){
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(context);
        dlgBuilder.setTitle(title);
        dlgBuilder.setMessage(message);
        dlgBuilder.setCancelable(true);
//	    dlgBuilder.setPositiveButton("Yes",
//	            new DialogInterface.OnClickListener() {
//	        public void onClick(DialogInterface dialog, int id) {
//	            dialog.cancel();
//	        }
//	    });
//	    dlgBuilder.setNegativeButton("No",
//	            new DialogInterface.OnClickListener() {
//	        public void onClick(DialogInterface dialog, int id) {
//	            dialog.cancel();
//	        }
//	    });

        AlertDialog alert = dlgBuilder.create();
        alert.show();
    }


}


