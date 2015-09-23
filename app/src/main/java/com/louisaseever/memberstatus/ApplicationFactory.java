package com.louisaseever.memberstatus;

import android.content.Context;
import android.util.Log;

/**
 * Created by LouisaSeever on 8/11/2015.
 */
public class ApplicationFactory {
    private final String TAG = "ApplicationFactory";


    public static final int ACTIVITY_CODE_REGISTER = 1;
    public static final int ACTIVITY_CODE_EDIT_MEMBER = 2;
    public static final int ACTIVITY_CODE_MEMBER_ACTIVITIES = 3;
    public static final int ACTIVITY_CODE_CUSTOM_ACTIVITY = 4;

    public static final String ACTIVITY_DATA_USERNAME = "UserName";
    public static final String ACTIVITY_DATA_TOTAL_POINTS = "TotalPoints";
    public static final String ACTIVITY_DATA_CUSTOM_POINTS = "CustomPoints";

    private static MemberDataSource mMemberData;
    private static AssignmentDataSource mAssignmentData;
    private static Context mMainContext;
    private static CoderDojoDatabase mSqlDatabase;


    private static ApplicationFactory ourInstance = new ApplicationFactory();

    public static ApplicationFactory getInstance() {
        return ourInstance;
    }

    private ApplicationFactory() {
    }

//    public Context getMainContext(){
//        return mMainContext;
//    }


    public void setMainContext(Context context){
        mMainContext = context;
    }

    public boolean isTest(){
        return true;
    }

    public MemberDataSource getMemberDataSource(){
        Log.d(TAG,"getMemberDataSource");
        if (mMemberData == null){
            //load any existing data
            try {
                if (mSqlDatabase == null) {
                    mSqlDatabase = new CoderDojoDatabase(mMainContext);
                    if (isTest()) {
                        mSqlDatabase.rebuild();
                    }
                }

                mMemberData = new SQLMemberDataSource(mSqlDatabase);
                if (isTest()){
                    mMemberData.load();
                }
                //mMemberData = new InMemoryKidsDataSource();
                //_memberData = new XmlKidsDataSource();
             }
            catch(Exception ex){
                Log.d(TAG, "Cannot load  memberDataSource: " + ex.getMessage());
            }
        }
        return mMemberData;
    }

    public AssignmentDataSource getAssignmentDataSource(){
        Log.d(TAG, "getAssignmentDataSource");
        if (mAssignmentData == null){
            //load any existing data
            try {
                if (mSqlDatabase == null){
                    mSqlDatabase = new CoderDojoDatabase(mMainContext);
                    if (isTest()) {
                        mSqlDatabase.rebuild();
                     }
                }
                mAssignmentData = new SQLAssignmentDataSource(mSqlDatabase);
                if (isTest()){
                    mAssignmentData.load();
                }
            }
            catch(Exception ex){
                Log.d(TAG, "Cannot load Assignments data source: " + ex.getMessage());
            }
        }
        return mAssignmentData;

    }



    public void close(){
        Log.d(TAG, "close");
        mMemberData = null;
        mAssignmentData = null;
        mSqlDatabase.close();
    }



}
