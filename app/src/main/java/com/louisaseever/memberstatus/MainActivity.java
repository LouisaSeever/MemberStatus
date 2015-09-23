package com.louisaseever.memberstatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /*********************************************
     * Listens for user to click the Register button
     */
    // OnClickListener
    private View.OnClickListener mMemberListener = new View.OnClickListener() {
        public void onClick(View v) {
            TextView memberTextView = (TextView)v;
            String userName = memberTextView.getText().toString();

            //show Member data
            Context mainContext = getApplicationContext();
            Intent intent = new Intent(mainContext, MemberActivity.class);
            intent.putExtra(ApplicationFactory.ACTIVITY_DATA_USERNAME, userName);

 //           Bundle testBundle = intent.getExtras();
 //           String testValue = testBundle.getString(ACTIVITY_DATA_USERNAME);

            startActivityForResult(intent, ApplicationFactory.ACTIVITY_CODE_EDIT_MEMBER);

        }
    };


    MemberDataSource mMembersData;
    ArrayList<Member> mMembersList;

    private TableLayout mMembersTable;
    private EditText mNewNameTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationFactory factory = ApplicationFactory.getInstance();
        factory.setMainContext(this);

        mMembersData = factory.getMemberDataSource();
        mMembersList = mMembersData.getMembers();

        //populate table with existing members
        mMembersTable = (TableLayout) findViewById(R.id.mainMembersTable);
        populateMembersTable();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ApplicationFactory.ACTIVITY_CODE_REGISTER) : {
                if (resultCode == Activity.RESULT_OK) {
                    mMembersList = mMembersData.getMembers();
                    clearMembersTable();
                    populateMembersTable();
                }
                break;
            }
            case (ApplicationFactory.ACTIVITY_CODE_EDIT_MEMBER) : {
                if (resultCode == Activity.RESULT_OK) {
                    mMembersList = mMembersData.getMembers();
                    clearMembersTable();
                    populateMembersTable();
                }
                break;
            }
        }

    }

    @Override
    public void onStop(){
        Log.d(TAG,"onStop");

        //release database
       // ApplicationFactory.getInstance().close();

        super.onStop();
    }


    public void onClickRegister(View v){
        Log.d(TAG, "onClickRegister entered");

        //show register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, ApplicationFactory.ACTIVITY_CODE_REGISTER);
    }


    private void clearMembersTable(){
        int rowCount = mMembersTable.getChildCount();
        mMembersTable.removeAllViewsInLayout();
        //rowCount = mMembersTable.getChildCount();


        //for (int i=rowCount-1; i>=0; i++){
       //     mMembersTable.removeViewAt(i);
       // }
    }

    private void populateMembersTable() {
        //ArrayList<Member> kids = MainActivity.MembersData.getMembers();
        for (int i = 0; i < mMembersList.size(); i++) {
            String name = mMembersList.get(i).getUserName();
            addNameToTable(i, name);
        }
     }

    private void addNameToTable(int i, String name) {
        //add row to table
        TableRow nextRow = new TableRow(this);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        nextRow.setLayoutParams(rowLayout);

        //show Member name in text view in new row
        TextView nextText = new TextView(this);
        nextText.setText(name);
        nextText.setClickable(true);
        nextText.setOnClickListener(mMemberListener);
        nextRow.addView(nextText);

        mMembersTable.addView(nextRow, i);
    }



}