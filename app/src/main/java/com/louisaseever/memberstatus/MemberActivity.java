package com.louisaseever.memberstatus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MemberActivity extends AppCompatActivity {
    private static final String TAG = "MemberActivity";

    private String mUserName;
    private Member mMember;

    private EditText mLastNameInput;
    private EditText mFirstNameInput;
    private EditText mUserNameInput;
    private EditText mAgeInput;
    private TextView mPointsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        //get text inputs
        mLastNameInput = (EditText)findViewById(R.id.memberLastName);
        mFirstNameInput = (EditText)findViewById(R.id.memberFirstName);
        mUserNameInput = (EditText)findViewById(R.id.memberUserName);
        mAgeInput = (EditText)findViewById(R.id.memberAge);
        mPointsText = (TextView)findViewById(R.id.memberPoints);

        //get requested user name
        Intent thisIntent = getIntent();
        Bundle extras = thisIntent.getExtras();
        String key = ApplicationFactory.ACTIVITY_DATA_USERNAME;
        Log.d(TAG, "key=" + key);
        mUserName = extras.getString(key);
        Log.d(TAG, "mUserName" + mUserName);

        //get the requested user
        MemberDataSource db = ApplicationFactory.getInstance().getMemberDataSource();
        mMember = db.getMember(mUserName);
        Log.d(TAG, "userId=" + String.valueOf(mMember.getId()));

        //populate member information
        populateMember();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_member, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult result=" + String.valueOf(resultCode));
        Log.d(TAG, "requestCode=" + String.valueOf(requestCode));
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ApplicationFactory.ACTIVITY_CODE_MEMBER_ACTIVITIES) : {
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG,"onActivityResult OK");
                    Bundle extras = data.getExtras();
                    Log.d(TAG, "Extras size=" + String.valueOf(extras.size()));
                    int totalPoints = extras.getInt(ApplicationFactory.ACTIVITY_DATA_TOTAL_POINTS);
                    Log.d(TAG, "totalPoints=" + String.valueOf(totalPoints));
                    showMemberPoints(totalPoints);
                }
                break;
            }

        }

    }

    public void onClickViewActivities(View view){
        Log.d(TAG, "onClickViewActivities");
        Intent intent = new Intent(this, MemberActivitiesActivity.class);
        intent.putExtra(ApplicationFactory.ACTIVITY_DATA_USERNAME, mUserName);
        startActivityForResult(intent, ApplicationFactory.ACTIVITY_CODE_MEMBER_ACTIVITIES);
    }

    public void onClickSave(View view){
        Log.d(TAG, "onClickSave");

        //get updated values
        String ageText = mAgeInput.getText().toString();
        int age = 0;
        try {
            age = Integer.valueOf(ageText);
        }
        catch(Exception ex){
            ApplicationUtilities.showErrorMessage(this, "Please enter a valid number for your age.");
            return;
        }
        //username changed => verify new name is unique
        MemberDataSource dataSource = ApplicationFactory.getInstance().getMemberDataSource();
        String updatedUserName = mUserNameInput.getText().toString().trim();
        if (!updatedUserName.equalsIgnoreCase(mUserName)) {
            Member otherMember = dataSource.getMember(updatedUserName);
            if (otherMember != null) {
                //user name already used => issue error
                ApplicationUtilities.showErrorMessage(this, updatedUserName + " is already used by another member.  Please choose a different user name.");
                return;
           }
        }

        //create updatedMember
        Member updatedMember = new Member(mMember.getId(),
                mLastNameInput.getText().toString().trim(),
                mFirstNameInput.getText().toString().trim(),
                updatedUserName,
                age);

        //update member in database
        try {
             dataSource.updateMember(mUserName, updatedMember);
        }
        catch(Exception ex){
            ApplicationUtilities.showErrorMessage(this, "Error encountered in saving.  " + ex.getMessage());
            return;
        }

        //return to calling activity
         Intent result=new Intent();
        // Set The Result in Intent
        setResult(Activity.RESULT_OK);
        // finish The activity
        finish();
    }

    private void populateMember(){
        mLastNameInput.setText(mMember.getLastName());
        mFirstNameInput.setText(mMember.getFirstName());
        mUserNameInput.setText(mMember.getUserName());
        mAgeInput.setText(String.valueOf(mMember.getAge()));
        //get total points from db
        showMemberPoints(0);

    }

    private void showMemberPoints(int points){
         mPointsText.setText(String.valueOf(points));

    }
}
