package com.louisaseever.memberstatus;

/**
 * Created by LouisaSeever on 8/17/2015.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomPointsDialog extends DialogFragment {
    private final static String TAG="CustomPointsDialog";

    private Button mSaveButton;
    private TextView mPointsRequest;
    private EditText mPointsText;
    private OnSubmitListener mSubmitListener;

    private int mMinPoints = 0;
    private int mMaxPoints = 100;

    public interface OnSubmitListener {
        void getSubmittedPoints(int arg);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_custom_points);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Log.d(TAG, "show dialog");
        dialog.show();

        mSaveButton = (Button) dialog.findViewById(R.id.customPointsSaveButton);
        mPointsRequest = (TextView) dialog.findViewById(R.id.customPointsRequest);
        mPointsText = (EditText) dialog.findViewById(R.id.customPointsInput);

        //set request text
        String request = String.format("Enter points earned between %d and %d.", mMinPoints, mMaxPoints);
        mPointsRequest.setText(request);

        //define the save button's onClick listener
        Log.d(TAG, "setOnClickListener");
        mSaveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "saveButtonOnClickListener gets submittedPoints");
                String value = mPointsText.getText().toString();
                int points = Integer.valueOf(value);

                //verify submitted value is in range
                if((points < mMinPoints) || (points > mMaxPoints)){
                    mPointsRequest.setBackgroundColor(Color.argb(50,255,0,0));
                    mPointsText.setText(String.valueOf(mMinPoints));
                }
                else {
                    //send points back to listener
                    mSubmitListener.getSubmittedPoints(points);
                    //close up dlg
                    dismiss();
                }
            }
        });


        return dialog;
    }

    public void setPointsRange(int min, int max){
        mMinPoints = min;
        mMaxPoints = max;
    }

    public void setSubmitListener(OnSubmitListener listener){
        mSubmitListener = listener;
    }


}
