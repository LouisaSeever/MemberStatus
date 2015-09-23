package com.louisaseever.memberstatus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberActivitiesActivity   extends AppCompatActivity
                                        implements  CustomPointsDialog.OnSubmitListener
{
    private static final String TAG = "MemberActivities";


 /*   // OnClickListener
    private class OnAssignmentSelectedListener implements OnItemSelectedListener   {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            //Toast.makeText(parent.getContext(),
            //        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
            //        Toast.LENGTH_SHORT).show();
            String selected = (String)parent.getItemAtPosition(pos);
            Assignment selectedAssignment = mAssignments.get(pos);


        }

        public void onNothingSelected(AdapterView<?> arg0) {
         //   // TODO Auto-generated method stub
        }

    };
*/

    private AssignmentDataSource mAssignmentDb;
    private MemberDataSource mMemberDb;
    private Member mMember; //member coming to this activity
    private ArrayList<CompletedAssignment> mCompletedAssignments;
    private ArrayList<String> mCategories;
    private ArrayList<Assignment> mAssignments;
    private HashMap<Integer,Assignment> mAssignmentLookup;
    private Intent mResult;

    private TableLayout mCompletedAssignmentsTable;
    private Spinner mAssignmentsSpinner;
    private ArrayAdapter<String> mAssignmentsAdapter;
//    private OnAssignmentSelectedListener mAssignmentSelectedListener;
    private TextView mPointsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_activities);

        //get views in this activity
        mCompletedAssignmentsTable = (TableLayout)findViewById(R.id.memberActivitiesTable);
        mAssignmentsSpinner = (Spinner)findViewById(R.id.memberActivitiesSpinner);
        mPointsText = (TextView)findViewById(R.id.memberActivitiesPoints);

        //get member from intent
        Intent thisIntent = getIntent();
        Bundle extras = thisIntent.getExtras();
        String userName = extras.getString(ApplicationFactory.ACTIVITY_DATA_USERNAME);
        Log.d(TAG, "OnCreate userName= "+ userName);

        //show user name in title
        this.setTitle( userName );

        try {
            mMemberDb = ApplicationFactory.getInstance().getMemberDataSource();
            mMember = mMemberDb.getMember(userName);
            Log.d(TAG, "OnCreate memberId "+ String.valueOf(mMember.getId()));

            //get list of all possible assignments
            Log.d(TAG, "get assignments");
            mAssignmentDb = ApplicationFactory.getInstance().getAssignmentDataSource();
            mAssignments = mAssignmentDb.getAssignments();
            ArrayList<String> descriptions = new ArrayList<String>();

            //populate assignments spinner and build lookup table
            populateAssignments(descriptions);

            //get all categories
            mCategories = mAssignmentDb.getAssignmentCategories();

            //get member's list of completed assignments
            Log.d(TAG, "get member's completed assignments");
            mCompletedAssignments = mMemberDb.getCompletedAssignments(mMember.getId());
            populateCompletedActivitiesTable();
        }
        catch(Exception ex){
            ApplicationUtilities.showErrorMessage(this, "Error loading assignments:  " + ex.getMessage());
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_member_activities, menu);
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
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ApplicationFactory.ACTIVITY_CODE_CUSTOM_ACTIVITY) : {
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG,"onActivityResult OK");
                    Bundle extras = data.getExtras();
                    int customPoints = extras.getInt(ApplicationFactory.ACTIVITY_DATA_CUSTOM_POINTS);
                    addCompletedAssignment(customPoints);
                }
                break;
            }
      }
    }


    public void onClickAdd(View view){
        Log.d(TAG, "onClickAdd");
        //get selected activity from spinner
        int i = mAssignmentsSpinner.getSelectedItemPosition();
        Assignment selected = mAssignments.get(i);

        //check for custom activity
        if (selected.getDescription().toLowerCase().contains("custom")) {
            setupCustomAssignment();
        }
        else{
            //verify hasn't already earned points for this
            if (assignmentIsComplete(selected)){
                ApplicationUtilities.showMessage(this, "Can not Add", "This assignment has already been completed.");
                return;
            }
        }

        //determine points for activity
        int max = selected.getMaxPoints();
        int min = selected.getMinPoints();
        int points = max;
        if(min!=max){
            //show dlg for user to assign points - value captured by listener getSubmittedPoints()
            getAssignedPoints(min, max);
        }
        else {
            //add the completed assignment to the table and db
            addCompletedAssignment(selected, points);
        }
    }

    private boolean assignmentIsComplete(Assignment assignment){
        boolean isComplete = false;

        int id = assignment.getId();
        for (int i=0; i<mCompletedAssignments.size(); i++){
            if (id == mCompletedAssignments.get(i).getAssignmentId()){
                isComplete = true;
                break;
            }
        }
        return isComplete;
    }

    private void addCompletedAssignment(int points) {
        //get selected activity from spinner
        int i = mAssignmentsSpinner.getSelectedItemPosition();
        Assignment selected = mAssignments.get(i);
        addCompletedAssignment(selected, points);
    }

    /************
     * Add the assignment tothe completedAssignment data ans show in table
     * @param selected
     * @param points
     */
    private void addCompletedAssignment(Assignment selected, int points) {
        Log.d(TAG, "AddCompletedAssignmane");
        CompletedAssignment completed = new CompletedAssignment(mMember.getId(), selected.getId(),points );
        mCompletedAssignments.add(completed);
        try {
            mMemberDb.addCompletedAssignmentToMember(mMember.getId(), selected.getId(), points);
        }
        catch(Exception ex){
            Log.d(TAG, "Exception adding completed Assignment:  " + ex.getMessage());
            ApplicationUtilities.showErrorMessage(this, "Exception adding completed Assignment:  " + ex.getMessage());
            return;
        }

        showCompletedAssignment(mCompletedAssignments.size()-1, points, selected.getDescription());
        int total = getTotalPoints();
        showTotal(total);
    }

    private void setupCustomAssignment(){
        Log.d(TAG,"setupCustomAssignment");
         //Intent intent = new Intent(this, AddCustomActivity.class);
         //startActivityForResult(intent, ApplicationFactory.ACTIVITY_CODE_CUSTOM_ACTIVITY);
    }

    private void getAssignedPoints(int min, int max){
        Log.d(TAG, "getAssignedPoints");
        CustomPointsDialog dlg = new CustomPointsDialog();
        dlg.setPointsRange(min, max);
        dlg.show(getFragmentManager(), "");

        dlg.setSubmitListener(this);
    }

    public void getSubmittedPoints(int points){
        Log.d(TAG,"getSubmittedPoints value=" + String.valueOf(points));

        //add the assignment to the members list
        int i = mAssignmentsSpinner.getSelectedItemPosition();
        Assignment selected = mAssignments.get(i);
        addCompletedAssignment(selected, points);
    }

    private int getTotalPoints(){
        Log.d(TAG, "getTotalPoints");
        int total = 0;
        for (int i=0; i<mCompletedAssignments.size(); i++){
            total += mCompletedAssignments.get(i).getAssignedPoints();
        }

        //update intent data
        Log.d(TAG, "update intent w total points "+ String.valueOf(total));
        mResult = new Intent();
        mResult.putExtra(ApplicationFactory.ACTIVITY_DATA_USERNAME, mMember.getUserName());
        mResult.putExtra(ApplicationFactory.ACTIVITY_DATA_TOTAL_POINTS, total);
        this.setResult(Activity.RESULT_OK, mResult);

        return total;
    }

    private void showTotal(int total){
        Log.d(TAG, "ShowTotal");
        mPointsText.setText(String.format("Total: %d", total));
    }

    private void populateAssignments(ArrayList<String> descriptions) {
        Log.d(TAG, "populateAssignments");
        //create lookup map
        mAssignmentLookup = new HashMap<Integer, Assignment>();

        Log.d(TAG, "populate assignments lookup map and spinner text");
        for (int i=0; i<mAssignments.size(); i++){
            Assignment next = mAssignments.get(i);
            descriptions.add(i, next.getDescription());
            mAssignmentLookup.put(new Integer(i), next);
        }
        mAssignmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descriptions);
        mAssignmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAssignmentsSpinner.setAdapter(mAssignmentsAdapter);
//        mAssignmentSelectedListener = new OnAssignmentSelectedListener();
//        mAssignmentsSpinner.setOnItemSelectedListener(mAssignmentSelectedListener);
    }

    private void populateCompletedActivitiesTable(){
        Log.d(TAG, "populateCompletedActivitiesTable");
        int total = 0;
        int count = mCompletedAssignments.size();
        for (int i=0; i<count; i++){
            //get the description and points for the completed assignment
            CompletedAssignment completed = mCompletedAssignments.get(i);
            int completedId = completed.getAssignmentId();
            Assignment assignment = mAssignmentLookup.get(new Integer(completedId));

            String description = assignment.getDescription();
            int points = completed.getAssignedPoints();
            total += points;

            //show them
            showCompletedAssignment(i, points, description);
        }
        showTotal(total);
    }

    private void showCompletedAssignment(int rowId, int points, String description ) {
        Log.d(TAG, "showCompletedAssignment for id=" + String.valueOf(rowId));
        //add row to table
        TableRow nextRow = new TableRow(this);
        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        nextRow.setLayoutParams(rowLayout);

        //show assignment description and points earned
        TextView pointText = new TextView(this);
        pointText.setText(String.valueOf(points));
        nextRow.addView(pointText);

        TextView descriptionText = new TextView(this);
        descriptionText.setPadding(10,0,0,0);
        descriptionText.setText(description);
        nextRow.addView(descriptionText);

        mCompletedAssignmentsTable.addView(nextRow, rowId);
    }



}
