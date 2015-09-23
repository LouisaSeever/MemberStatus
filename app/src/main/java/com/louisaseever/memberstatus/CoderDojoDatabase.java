package com.louisaseever.memberstatus;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;


/**
 * Created by LouisaSeever on 8/11/2015.
 */
public class CoderDojoDatabase extends SQLiteOpenHelper{
        private static String TAG="CoderDojoDatabase";


        private static String _databaseName = "DojoData";
        private static int _version = 1;
        private static SQLiteDatabase _db; //the open database

    /**
     * constructor required for open helper
     */
    public CoderDojoDatabase(Context context){
        super(context, _databaseName, null, _version);
    }

    public CoderDojoDatabase(
            Context context,
            String name,
            CursorFactory factory,
            int version
    )
    {
        super(context, name, factory, version);
    }


    /**
     * onCreate required for open helper.  Creates a new database if one does not yet exist
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(TAG, "onCreate");
        createAllTables(db);

        _db = db;

    }

    /**
     * onUpgrade required for open helper.  Updates a database if the stored data version does not match expected version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        dropAllTables(db);
        onCreate(db);
    }

    //Clear all data from tables and repopulate all constant data
    public void clear() {
        clearMemberData();
    }


    /**
     * onDowngrade used by open helper.  Updates a database if the stored data version does not match expected version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(TAG, "Downgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        dropAllTables(db);
        onCreate(db);
    }

    /**
     * onOpen used by open helper.  Called when a database is opened.
     */
    @Override
    public void onOpen(SQLiteDatabase db){
        Log.w(TAG, "opening database");
         super.onOpen(db);

        _db = db;

    }


    /***
     * Rebuild the database:  drop all tables and recreate
     */
    public void rebuild(){
        verifyOpen();
        dropAllTables(_db);
        createAllTables(_db);
    }

    private void createAllTables(SQLiteDatabase db) {
        createCategoryTable(db);
        createAssignmentTable(db);
        createMemberTable(db);
        createCompletedAssignmentTable(db);
    }

    private void dropAllTables(SQLiteDatabase db) {
        Log.d(TAG, "dropAllTables");

        db.execSQL("DROP TABLE IF EXISTS CompletedAssignment");
        db.execSQL("DROP TABLE IF EXISTS Member");
        db.execSQL("DROP TABLE IF EXISTS Assignment");
        db.execSQL("DROP TABLE IF EXISTS Category");
    }

    //******************************
    //createMemberData
    //  db:  SQLLiteDatabase to persist member data
    //creates the Member table
    //******************************
    private void createMemberTable(SQLiteDatabase db){
        Log.d(TAG, "createMemberTable");

        try {
            String dbCreateSql =
                    "create table Member("
                            + " _id integer primary key autoincrement,"
                            + " LastName text not null,"
                            + " FirstName text not null,"
                            + " UserName text not null,"
                            + " Age integer"
                            + ")";
            Log.d(TAG, dbCreateSql);

            db.execSQL(dbCreateSql);
        }
        catch(Exception ex){
            Log.d(TAG,ex.getMessage());
        }
    }

    private void clearMemberData(){
        String dbSql = "delete from Member";
        Log.d(TAG, dbSql);
        _db.execSQL(dbSql);
    }

    //***************
    // addMember
    //      lastName
    //      firstName
    //      userName
    //      age
    //Adds a new member to the database
    //***************
    public void addMember(
            String lastName,
            String firstName,
            String userName,
            int age)
    {
        //wait for open
        verifyOpen();

        ContentValues member = new ContentValues();
        member.put("LastName", lastName);
        member.put("FirstName", firstName);
        member.put("UserName", userName);
        member.put("Age", age);

        _db.insert("Member", null, member);

    }

    /*****
     * addMember:  Add a row to the database with data for the specified Member
     * @param member:  Member to be added
     */
    public void addMember(Member member)
    {
        addMember(member.getLastName(),
                member.getFirstName(),
                member.getUserName(),
                member.getAge());

    }
    //***************
    // updateMember
    //      id
    //      lastName
    //      firstName
    //      userName
    //      age
    //Updates an existing member in the database
    //***************
    public void updateMember(
            int id,
            String lastName,
            String firstName,
            String userName,
            int age) {
        //wait for open
        verifyOpen();

        ContentValues member = new ContentValues();
        member.put("LastName", lastName);
        member.put("FirstName", firstName);
        member.put("UserName", userName);
        member.put("Age", age);

        String where = "_id=?";

        _db.update("Member", member, where, new String[]{String.valueOf(id)});
    }

        /*****
       * updateMember:  update the member with new values
       * @param member:  Member to be added
       */
    public void updateMember(Member member)
    {
        //not valid id => add him
        if (member.getId() < 0){
            addMember(member);
        }
        updateMember(member.getId(),
                member.getLastName(),
                member.getFirstName(),
                member.getUserName(),
                member.getAge());
    }

    /***************
    * getMembers():  return all Members
    *  @return  an ArrayList<Members> of all the active members
    */
    public ArrayList<Member> getMembers(){
            verifyOpen();

        ArrayList<Member> result = new ArrayList<Member>();
        Cursor queryResult = _db.rawQuery("Select _id, LastName, FirstName, UserName, Age from Member", null);

        while (queryResult.moveToNext()){
            int id = queryResult.getInt(0);
            String lastname = queryResult.getString(1);
            String firstname = queryResult.getString(2);
            String username = queryResult.getString(3);
            int age = queryResult.getInt(4);
            Member next = new Member(id, lastname, firstname, username, age);
            result.add(next);
        }

        queryResult.close();
        return result;
    }


    /*******
     * get the member with the specified user name.
     * @param userName:
     * @return ArrayList of members
     */
    public Member getMemberByUserName(String userName){
        verifyOpen();
        Member result = null;
        Cursor queryResult = _db.rawQuery("Select _id, LastName, FirstName, UserName, Age from Member where UserName=?", new String[]{userName});
        while (queryResult.moveToNext()){
            int id = queryResult.getInt(0);
            String lastname = queryResult.getString(1);
            String firstname = queryResult.getString(2);
            String username = queryResult.getString(3);
            int age = queryResult.getInt(4);
            result = new Member(id, lastname, firstname, username, age);
        }

        queryResult.close();
        return result;
    }

    /*****
     * MemberExists determines if a member with the specified userName already exists 
     * @param userName:  member's user name.  Sould be unique identifier
     * @return true if and only if a member is found in the database with the specified username.
     */
    public boolean MemberExists(String userName){
        verifyOpen();
        Cursor queryResult = _db.rawQuery("Select _id  from Member where UserName=?", new String[]{userName});
        int count = queryResult.getCount();

        queryResult.close();
        return (count>0);
    }



    /**************************************************************************************************
     *              CATEGORY
     *************************************************************************************************/

    //******************************
    //createCategoryTable
    //  db:  SQLLiteDatabase to persist assignment category data
    //creates the Category table
    //******************************
    private void createCategoryTable(SQLiteDatabase db){
        Log.d(TAG, "createCategoryTable");

        try {
            String dbCreateSql =
                    "create table Category("
                            + " _id integer primary key autoincrement,"
                            + " Category text not null"
                            + ")";
            Log.d(TAG, dbCreateSql);

            db.execSQL(dbCreateSql);
        }
        catch(Exception ex){
            Log.d(TAG,ex.getMessage());
        }
    }

    private void clearCategoryData(){
        String dbSql = "delete from Category";
        Log.d(TAG, dbSql);
        _db.execSQL(dbSql);
    }


    /***
     * addAssignment adds a new Assignment to the database
     * @param category title

     */
    public void addCategory(String category) {
        //wait for open
        verifyOpen();

        ContentValues values = new ContentValues();
        values.put("Category", category);

        _db.insert("Category", null, values);

    }


    /*****
     * getCategories() returns all categories in the database in an ArrayList
     * @return all assignments
     */
    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<String>();
        verifyOpen();
        Cursor queryResult = _db.rawQuery("Select _id, Category from Category", null);
        while (queryResult.moveToNext()){
            int id = queryResult.getInt(0);
            String next = queryResult.getString(1);
            categories.add(next);
        }

        queryResult.close();
        return categories;
    }

    /***
     * get the ID of the specified category
     * @param category:  string describing category
     * @return: the requested id
     */
    public int getCategoryId(String category) {
        int result = -1;
        verifyOpen();
        Cursor queryResult = _db.rawQuery("Select _id  from Category where Category=?", new String[]{category});
        while (queryResult.moveToNext()){
            result = queryResult.getInt(0);
        }

        queryResult.close();
        return result;
    }




    /**************************************************************************************************
 *              ASSIGNMENT
 *************************************************************************************************/

    //******************************
    //createAssignmentData
    //  db:  SQLLiteDatabase to persist member data
    //creates the Assignment table
    //******************************
    private void createAssignmentTable(SQLiteDatabase db){
        Log.d(TAG, "createAssignmentData");

        try {
            String dbCreateSql =
                    "create table Assignment("
                            + " _id integer primary key autoincrement,"
                            + " CategoryId integer not null,"
                            + " Description text not null,"
                            + " MinPoints integer not null,"
                            + " MaxPoints integer text not null,"
                            + " FOREIGN KEY(CategoryId) REFERENCES Category(_id)"
                            + ")";
            Log.d(TAG, dbCreateSql);

            db.execSQL(dbCreateSql);
        }
        catch(Exception ex){
            Log.d(TAG,ex.getMessage());
        }
    }

    private void clearAssignmentData(){
        String dbSql = "delete from Assignment";
        Log.d(TAG, dbSql);
        _db.execSQL(dbSql);
    }


    /***
     * addAssignment adds a new Assignment to the database
     * @param description
     * @param minPoints
     * @param maxPoints
     */
    public void addAssignment(String category, String description, int minPoints, int maxPoints) {
        //wait for open
        verifyOpen();

        //get category id
        int id = getCategoryId(category);

        ContentValues values = new ContentValues();
        values.put("CategoryId", id);
        values.put("Description", description);
        values.put("MinPoints", minPoints);
        values.put("MaxPoints", maxPoints);

        _db.insert("Assignment", null, values);

    }

    /*****
     * addAssignment adds an assignment to the database
     * @param assignment:  the assignment to be added
     */
    public void addAssignment(Assignment assignment) {
        addAssignment(assignment.getCategory(), assignment.getDescription(), assignment.getMinPoints(), assignment.getMaxPoints());
    }

    /****
     * updateAssignment updates the values of an existing Assignment
     * @param id:  unique integer identifier
     * @param category:  assignment category
     * @param description:  description of assignment
     * @param minPoints:  minimum points to be assigned for completing assignment
     * @param maxPoints:  maximum points to be assigned for completing assignment
     */
    public void updateAssignment(int id, String category, String description, int minPoints, int maxPoints) {
        //not valid id => add id
        if (id < 0){
            Assignment added = new Assignment(category, description, minPoints, maxPoints);
            addAssignment(added);
        }

        //wait for db to open
        verifyOpen();

        //get category id
        int categoryId = getCategoryId(category);

        //set up values
        ContentValues values = new ContentValues();
        values.put("CategoryId", categoryId);
        values.put("Description", description);
        values.put("MinPoints", minPoints);
        values.put("MaxPoints", maxPoints);

        String where = "_id=?";

        _db.update("Assignment", values, where, new String[]{String.valueOf(id)});
    }

    /*****
     * getassignments() returns all assignments in the database in an ArrayList
     * @return all assignments
     */
    public ArrayList<Assignment> getAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        verifyOpen();

        Cursor queryResult = _db.rawQuery(
                        "Select Assignment._id, "
                        + " Category.Category,"
                        + " Assignment.Description,"
                        + " Assignment.MinPoints, Assignment.MaxPoints "
                        + " from Assignment"
                        + " INNER JOIN Category on Assignment.CategoryId = Category._id"
                        , null);

        while (queryResult.moveToNext()){
            int id = queryResult.getInt(0);
            String category = queryResult.getString(1);
            String description = queryResult.getString(2);
            int minPoints= queryResult.getInt(3);
            int maxpoints = queryResult.getInt(4);
            Assignment next = new Assignment(id,category, description, minPoints, maxpoints);
            assignments.add(next);
        }
         queryResult.close();
        return assignments;
    }

    /***
     * getAssignment() gets the assignment with the specified id
     * @param id: unique identifier
     * @return: the requested Assignment
     */
    public Assignment getAssignment(int id) {
        Assignment result = null;
        verifyOpen();
        Cursor queryResult = _db.rawQuery(
                "Select Assignment._id, "
                        + " Category.Category,"
                        + " Assignment.Description,"
                        + " Assignment.MinPoints, Assignment.MaxPoints "
                        + " from Assignment"
                        + " INNER JOIN Category on Assignment.CategoryId = Category._id"
                        , new String[]{String.valueOf(id)});
        while (queryResult.moveToNext()){
            String category = queryResult.getString(1);
            String description = queryResult.getString(2);
            int minPoints= queryResult.getInt(3);
            int maxpoints = queryResult.getInt(4);
            result = new Assignment(id, category, description, minPoints, maxpoints);
        }

        queryResult.close();
        return result;
    }





    /**************************************************************************************************
     *              COMPLETEDASSIGNMENT
     *************************************************************************************************/


//******************************
//createCompletedAssignmentData
//  db:  SQLLiteDatabase to persist completed assignment data
//creates the CompletedAssignment table
//******************************
    private void createCompletedAssignmentTable(SQLiteDatabase db){
        Log.d(TAG, "createCompletedAssignmentData");

        try {
            String dbCreateSql =
                    "create table CompletedAssignment("
                            + " MemberId integer not null,"
                            + " AssignmentId integer not null,"
                            + " Points integer not null,"
                            + " FOREIGN KEY(MemberId) REFERENCES Member(_id),"
                            + " FOREIGN KEY(AssignmentId) REFERENCES Assignment(_id)"
                           + ")";
            Log.d(TAG, dbCreateSql);

            db.execSQL(dbCreateSql);
        }
        catch(Exception ex){
            Log.d(TAG,ex.getMessage());
        }
        Log.d(TAG, "CompletedAssignment table created");

    }

    private void clearCompletedAssignmentData(){
        String dbSql = "delete from CompletedAssignment";
        Log.d(TAG, dbSql);
        _db.execSQL(dbSql);
    }


    /***
     * addCompletedAssignment adds a new CompletedAssignment to the database
     * @param memberId: id of member who completed the assignment
     * @param assignmentId:  id of assignment
     */
    public void addCompletedAssignment(int memberId, int assignmentId, int points) {
        //wait for open
        verifyOpen();

        ContentValues values = new ContentValues();
        values.put("MemberId", memberId);
        values.put("AssignmentId", assignmentId);
        values.put("Points", points);

        _db.insert("CompletedAssignment", null, values);

    }

    /*****
     * addCompletedAssignment adds an Assignmentassignment to the database
     * @param member:  member who completed the assignment
     * @param assignment:  the assignment to be added
     * @param points: points earned for completion
     */
    public void addCompletedAssignment(Member member, Assignment assignment, int points) {
        addCompletedAssignment(member.getId(), assignment.getId(), points);
    }

    public void deleteCompletedAssignment(int memberId, int assignmentId){
        String deleteSQL = String.format("delete from  CompletedAssignment where MemberId=? and AssignmentId=?",
                                            new String[]{String.valueOf(memberId), String.valueOf(assignmentId)});

        _db.execSQL(deleteSQL);

    }


    /*****
     * getCompletedAssignmentsForMember() returns all assignments in the database in an ArrayList
     * @param memberId:  id of member whose completed assignments are requested
     * @return all assignments completed by specified member
     */
    public ArrayList<CompletedAssignment> getCompletedAssignmentsForMember(int memberId) {
        ArrayList<CompletedAssignment> assignments = new ArrayList<CompletedAssignment>();
        verifyOpen();
        Cursor queryResult = _db.rawQuery("Select AssignmentId, Points"
                                        + " from CompletedAssignment"
                                        + " where MemberId=?", new String[]{String.valueOf(memberId)});

        while (queryResult.moveToNext()){
            int assignmentId = queryResult.getInt(0);
            int points= queryResult.getInt(1);
            CompletedAssignment next = new CompletedAssignment(memberId, assignmentId, points);
            assignments.add(next);
        }

        queryResult.close();
        return assignments;
    }


    /***********************************************************************************************
     *          UTILITIES
     **********************************************************************************************/

    //Make sure database has been opened
    private void verifyOpen() {
        if (_db == null){
            getWritableDatabase();
        }
    }

}
