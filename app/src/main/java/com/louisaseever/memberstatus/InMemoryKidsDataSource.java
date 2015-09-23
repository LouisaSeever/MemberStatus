package com.louisaseever.memberstatus;

import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class InMemoryKidsDataSource extends MemberDataSource {
    private final String TAG = "InMemoryKidsDataSource";
    private final String delimiter = ", ";
    private final String defaultFileName = "Kids";

    private ArrayList<Member> _kidsArray;

    public InMemoryKidsDataSource(){
        _kidsArray = new ArrayList<Member>();

    }

    @Override
    public void addMember(Member newMember) throws Exception{
        //verify name unique
        Member found = getMember(newMember.getUserName());
        if (found != null){
            throw new Exception("User name is not unique");
        }
        _kidsArray.add(newMember);


    }

    @Override
    public void updateMember(String name, Member updatedMember) throws Exception{
        Member found = getMember(updatedMember.getUserName());
        if (found == null){
            throw new Exception("User does not exist.");
        }
        int i = _kidsArray.indexOf(found);
        _kidsArray.set(i,updatedMember);
    }


    @Override
    public void removeMember(String userName) throws Exception{
        for (int i=0; i<_kidsArray.size(); i++){
            if (_kidsArray.get(i).getUserName().equalsIgnoreCase(userName)){
                _kidsArray.remove(i);
            }
        }
    }

    @Override
    public ArrayList<Member> getMembers() {
        ArrayList<Member> kids  = new ArrayList<Member>();
        for (int i=0; i<_kidsArray.size(); i++){
            kids.add(_kidsArray.get(i));
        }

        return kids;
    }

    @Override
    public Member getMember(String name) {
        for (int i=0; i<_kidsArray.size(); i++){
            if (_kidsArray.get(i).getUserName().equalsIgnoreCase(name)){
                return 	_kidsArray.get(i);
            }
        }
        // not found
        return null;
    }


    public void addCompletedAssignmentToMember(int memberId, int assignmentId, int points) throws Exception{
        throw new UnsupportedOperationException("addCompletedAssignmentToMember() not implemented");
    }

    public ArrayList<CompletedAssignment> getCompletedAssignments(int memberId) throws Exception{
        ArrayList<CompletedAssignment> completed = new ArrayList<CompletedAssignment>();

        return completed;
    }


    public void load() throws Exception{
        //verify storage card exits
        if (!DataSourceUtilities.isExternalStorageWritable()){
            throw new Exception("Storage card must be inserted befor saving.");
        }

        //get path to file
        File kidsFile = DataSourceUtilities.getDataStorageFile(defaultFileName);
        String path = kidsFile.getAbsolutePath();

        //read comma separated string
        if (kidsFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String kidsString = reader.readLine();
            reader.close();

            //populate this data source from the string
            this.fromString(kidsString);
        }
    }

    public void clear() throws Exception{
        //verify storage card exits
        if (!DataSourceUtilities.isExternalStorageWritable()){
            throw new Exception("Storage card must be inserted befor saving.");
        }

        //remove file
        File kidsFile = DataSourceUtilities.getDataStorageFile(defaultFileName);
        kidsFile.delete();

        //reload to create new empty file;
        this.load();
    }


    public void save() throws Exception{
        //verify storage card exits
        if (!DataSourceUtilities.isExternalStorageWritable()){
            throw new Exception("Storage card must be inserted befor saving.");
        }

        //get path to file
        File kidsFile = DataSourceUtilities.getDataStorageFile(defaultFileName);

        //create comma separated string
        String kidsString = toString();

        //write file
        FileWriter writer = new FileWriter(kidsFile);
        writer.write(kidsString);
        writer.close();
    }

    public void rebuild() throws Exception{
        //does nothing
    }
/*
    public String toString()
    {
        Log.d(TAG, "writeObject");
        String output = "";
        for (int i=0; i<_kidsArray.size(); i++){
            String nextKid = _kidsArray.get(i).toString();
            output = output +  nextKid + "/n";  //separate members by new line
        }
        return output;
    }
*/
    /*****
     *     populates contents from a comma separated strings.  One line per member
     */
   public void fromString(String input) throws Exception
    {
        Log.d(TAG, "fromString");
        _kidsArray = new ArrayList<Member>();

        int nextStart = 0;
        for (int i=0; nextStart<input.length(); i++){
            //get name from comma separated list
            int nextEnd = input.indexOf(delimiter,nextStart);
            if (nextEnd>nextStart) {
                String nextEntry = input.substring(nextStart, nextEnd);

                //add kid to array
                Member nextKid = new Member("lastName", "firstName", nextEntry, 0);
                _kidsArray.add(nextKid);
            }

            //increment to next start
            nextStart = nextEnd+2;
        }


    }

}
