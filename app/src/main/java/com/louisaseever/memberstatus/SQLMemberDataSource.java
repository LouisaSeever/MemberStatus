package com.louisaseever.memberstatus;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LouisaSeever on 8/12/2015.
 */
public class SQLMemberDataSource extends MemberDataSource{
    private static String TAG = "SQLMemberDataSource";
    private static String NEWLINE = "/n";

    private static CoderDojoDatabase _db;

    public SQLMemberDataSource(CoderDojoDatabase db){
        _db = db;
        //open database---------------------------------------------
        _db.getWritableDatabase();
     }

    @Override
    public void addMember(Member newMember) throws Exception {
        //verify user name is not empty
        if (newMember.getUserName().isEmpty()){
            throw new Exception("User name cannot be empty.  Please specify a user name.");
        }
        //verify user name unique
        Member found = _db.getMemberByUserName(newMember.getUserName());
        if (found != null){
            throw new Exception("User name is not unique.");
        }
        _db.addMember(newMember);
    }

    @Override
    public void removeMember(String name) throws Exception {
        throw new UnsupportedOperationException("removeMember() not implemented");
    }

    @Override
    public void updateMember(String name, Member updatedMember) throws Exception{
        //verify user name is not empty
        if (name.isEmpty()){
            throw new Exception("User name cannot be empty.  Please specify a user name.");
        }

        //verify new/different user name is unique
        if (!name.equalsIgnoreCase(updatedMember.getUserName())) {
            boolean found = _db.MemberExists(updatedMember.getUserName());
            if (found) {
                throw new Exception("Username already used. Choose another");
            }
        }

        _db.updateMember(updatedMember);
    }


    @Override
    public ArrayList<Member> getMembers() {
        ArrayList<Member> members = _db.getMembers();
        return members;
    }

    @Override
    public Member getMember(String userName) {
        Member found = _db.getMemberByUserName(userName);
        return found;
    }

    private boolean MemberExists(String userName){
        boolean found = _db.MemberExists(userName);
        return found;
    }


    public void addCompletedAssignmentToMember(int memberId, int assignmentId, int points) throws Exception{
        Log.d(TAG, "addCompletedAssignmentToMember: " + String.valueOf(memberId));
        _db.addCompletedAssignment(memberId, assignmentId, points);

    }

    public ArrayList<CompletedAssignment> getCompletedAssignments(int memberId) throws Exception{
        Log.d(TAG, "getCompletedAssignments for " + String.valueOf(memberId));
        ArrayList<CompletedAssignment> completed = new ArrayList<CompletedAssignment>();
        completed = _db.getCompletedAssignmentsForMember(memberId);

        return completed;
    }

    @Override
    public void rebuild() throws Exception{
        _db.rebuild();
    }

    @Override
    public void clear() throws Exception {
        _db.clear();
    }

    @Override
    public void load() throws Exception {
        //nothing to do
    }

    @Override
    public void save() throws Exception {
        //nothing to do
    }

    /*
    @Override
    public String toString() {
        Log.d(TAG, "toString");
        String output = "";

        ArrayList<Member> members = getMembers();
        for (int i=0; i<members.size(); i++){
            String nextKid = members.get(i).toString();
            output = output +  nextKid + NEWLINE;
        }
        //Log.d(TAG, "end toString=" + output);

        return output;
    }
*/
    @Override
    public void fromString(String input) throws Exception {
        throw new UnsupportedOperationException("fromString() not implemented");
    }
}
