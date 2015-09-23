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

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void onClickSave(View view){
        Log.d(TAG, "onClickSave");

        //get info about member
        EditText lastNameInput = (EditText)findViewById(R.id.registerLastName);
        EditText firstNameInput = (EditText)findViewById(R.id.registerFirstName);
        EditText userNameInput = (EditText)findViewById(R.id.registerUserName);
        EditText ageInput = (EditText)findViewById(R.id.registerAge);
        String ageText = ageInput.getText().toString();
        int age = 0;
        try {
            age = Integer.valueOf(ageText);
        }
        catch(Exception ex){
            ApplicationUtilities.showErrorMessage(this, "Please enter a valid number for your age.");
            return;
        }

        Member newMember = new Member(lastNameInput.getText().toString().trim(),
                                    firstNameInput.getText().toString().trim(),
                                    userNameInput.getText().toString().trim(),
                                    age);

        //add new member to database
        try {
            MemberDataSource dataSource = ApplicationFactory.getInstance().getMemberDataSource();
            dataSource.addMember(newMember);
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
}
