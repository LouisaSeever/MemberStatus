package com.louisaseever.memberstatus;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by LouisaSeever on 8/13/2015.
 */
public class SQLAssignmentDataSource extends AssignmentDataSource {
    private static String TAG = "SQLAssignmentDataSource";
    private static String NEWLINE = "/n";

    private static CoderDojoDatabase _db;

    public SQLAssignmentDataSource(CoderDojoDatabase db){
           _db = db;
            //open database---------------------------------------------
            _db.getWritableDatabase();
    }

    @Override
    public void addAssignmentCategory(String category) throws Exception{
        //verify title is not empty
        if (category.isEmpty()){
            throw new Exception("Category title cannot be empty.");

        }

        _db.addCategory(category);
    }

    @Override
    public ArrayList<String> getAssignmentCategories() throws Exception{
       ArrayList<String> result = _db.getCategories();

        return result;
    }


    @Override
    public void addAssignment(String category, String description, int minPoints, int maxPoints) throws Exception {
        //verify description is not empty
        if (description.isEmpty()){
            throw new Exception("Description cannot be empty.");

        }

        _db.addAssignment(category, description, minPoints, maxPoints);

    }

    @Override
    public void addAssignment(Assignment assignment) throws Exception {
        //verify description is not empty
        if (assignment.getDescription().isEmpty()){
            throw new Exception("Description cannot be empty.");

        }
         _db.addAssignment(assignment);

    }

    @Override
    public void removeAssignment(int id) throws Exception {
        throw new UnsupportedOperationException("removeAssignment() not implemented");
    }

    @Override
    public void updateAssignment(int id, String category, String description, int minPoints, int maxPoints) throws Exception {
        //verify user name is not empty
        if (description.isEmpty()){
            throw new Exception("Description cannot be empty.");
        }
       _db.updateAssignment(id, category, description, minPoints, maxPoints);

    }

    @Override
    public ArrayList<Assignment> getAssignments() {
        ArrayList<Assignment> assignments = _db.getAssignments();
        return assignments;
    }

    @Override
    public Assignment getAssignment(int id) {
        Assignment found = _db.getAssignment(id);
        return found;
    }

    @Override
    public void clear() throws Exception {
        throw new UnsupportedOperationException("SQLAssignmentDataSource.clear() is not implemented");
    }
    @Override
    public void save() throws Exception{
        //do nothing.  sql will automatically save
    }

    @Override
    public void rebuild() throws Exception{
        _db.rebuild();
    }

    @Override
    public void load() throws Exception{
       super.load();
    }


 /*   @Override
    public String toString() {
        String output = "";

        ArrayList<Assignment> assignments = getAssignments();
        for (int i=0; i<assignments.size(); i++){
            String nextAssignment = assignments.get(i).toString();
            output += (nextAssignment + NEWLINE);
        }

        return output;
    }
*/
    @Override
    public void fromString(String input) throws Exception {
        throw new UnsupportedOperationException("SQLAssignmentDataSource.fromString() is not implemented");

    }
}
