package com.example.priyanshu.mappr.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Switch;

import com.example.priyanshu.mappr.Data.Message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rocky on 27/2/15.
 */
public class MapprDatabaseAdapter {

    MapprDatabaseHelper mapprDatabaseHelper;

    public MapprDatabaseAdapter(Context context) {
        mapprDatabaseHelper = new MapprDatabaseHelper(context);
    }

    public long insertStudent(String name, int userid) {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.USERID, userid);
        contentValues.put(MapprDatabaseHelper.NAME, name);
        long id = db.insert(MapprDatabaseHelper.ALL_STUDENTS_TABLE_NAME, null, contentValues);
        return userid;
    }

    public long insertTeacher(String name, int userid) {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.USERID, userid);
        contentValues.put(MapprDatabaseHelper.NAME, name);
        long id = db.insert(MapprDatabaseHelper.ALL_TEACHERS_TABLE_NAME, null, contentValues);
        return userid;
    }

    public long insertParent(String name, int userid) {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.USERID, userid);
        contentValues.put(MapprDatabaseHelper.NAME, name);
        long id = db.insert(MapprDatabaseHelper.ALL_PARENTS_TABLE_NAME, null, contentValues);
        return userid;
    }

    public long insertPost(Integer post){
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.POSTID,post);
        return db.insert(MapprDatabaseHelper.POST_TABLE_NAME,null,contentValues);
    }

//    public long insertGroup(String name) {
//        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MapprDatabaseHelper.NAME, name);
//        long id = db.insert(MapprDatabaseHelper.TABLE_NAME, null, contentValues);
//        return id;
//    }

    public ArrayList<String> getGroups() {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        String[] columns = {MapprDatabaseHelper.UID, MapprDatabaseHelper.NAME};
        Cursor cursor = db.query(MapprDatabaseHelper.DATABASE_NAME, columns, null, null, null, null, null);
        ArrayList<String> groups = new ArrayList<>();
        while(cursor.moveToNext()) {
            String group = cursor.getString(1);
            groups.add(group);
        }

        return  groups;
    }

    public String getUserName(String tableName, Integer userId){

        SQLiteDatabase db = mapprDatabaseHelper.getReadableDatabase();
        switch(tableName){
            case "student":
                tableName=MapprDatabaseHelper.ALL_STUDENTS_TABLE_NAME;
                break;
            case "teacher":
                tableName=MapprDatabaseHelper.ALL_TEACHERS_TABLE_NAME;
                break;
            case "parent":
                tableName=MapprDatabaseHelper.ALL_PARENTS_TABLE_NAME;
                break;
            default:
                return tableName+" "+userId;
        }
        Log.d("table",tableName);

        String select = MapprDatabaseHelper.USERID +"='"+userId+"'";
        String[] projection = {MapprDatabaseHelper.UID, MapprDatabaseHelper.USERID, MapprDatabaseHelper.NAME};
        Cursor cursor = db.query(
                tableName,                                // The table to query
                projection,                               // The columns to return
                select,                                   // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );
        String name;
        if(cursor.getCount()<=0)
            name="";
        else {
            cursor.moveToFirst();
            name = cursor.getString(2);
        }
//        Log.d("cursor",cursor.getInt(0)+"");
        /*while(cursor.moveToNext()){
            if(cursor.getInt(cursor.getColumnIndex(MapprDatabaseHelper.USERID))==userId);
              name=cursor.getString(cursor.getColumnIndex(MapprDatabaseHelper.NAME));
        }*/
        Log.d("name=",name);
        return name;
    }
    public ArrayList<Integer> getPostIds(){
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        String[] columns = {MapprDatabaseHelper.UID,MapprDatabaseHelper.POSTID};
        Cursor cursor = db.query(MapprDatabaseHelper.POST_TABLE_NAME,columns,null,null,null,null,null);
        ArrayList<Integer> postIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            int postId = cursor.getInt(1);
            postIds.add(postId);
        }
        return postIds;
    }
    public ArrayList<Integer> getUserIds(){
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        String[] columns = {MapprDatabaseHelper.UID,MapprDatabaseHelper.USERID,MapprDatabaseHelper.NAME};
        Cursor cursor = db.query(MapprDatabaseHelper.ALL_STUDENTS_TABLE_NAME,columns,null,null,null,null,null);
        ArrayList<Integer> userIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            int userId = cursor.getInt(1);
            userIds.add(userId);
        }
        return userIds;
    }
    public HashMap getUserNames(){
        SQLiteDatabase db = mapprDatabaseHelper.getReadableDatabase();
        String[] columns = {MapprDatabaseHelper.UID,MapprDatabaseHelper.USERID,MapprDatabaseHelper.NAME};
        HashMap<String,HashMap<Integer,String>> users=new HashMap<>();
        Cursor cursor = db.query(MapprDatabaseHelper.ALL_STUDENTS_TABLE_NAME,columns,null,null,null,null,null);
        HashMap<Integer,String> student_users=new HashMap<>();
        int userId=0;
        String userName="";
        while(cursor.moveToNext()) {
            userId = cursor.getInt(1);
            userName = cursor.getString(2);
            student_users.put(userId,userName);
        }
        Log.d("getUserNames","added to students table:"+userId+" "+userName);
        users.put("student",student_users);

        Cursor cursor1 = db.query(MapprDatabaseHelper.ALL_TEACHERS_TABLE_NAME,columns,null,null,null,null,null);
        HashMap<Integer,String> teacher_users=new HashMap<>();
        while(cursor1.moveToNext()) {
            userId = cursor1.getInt(1);
            userName = cursor1.getString(2);
            teacher_users.put(userId,userName);
        }
        Log.d("getUserNames","added to teachers table:"+userId+" "+userName);
        users.put("teacher",teacher_users);

        Cursor cursor2 = db.query(MapprDatabaseHelper.ALL_PARENTS_TABLE_NAME,columns,null,null,null,null,null);
        HashMap<Integer,String> parent_users=new HashMap<>();
        while(cursor2.moveToNext()) {
            userId = cursor2.getInt(1);
            userName = cursor2.getString(2);
            parent_users.put(userId,userName);
        }
        Log.d("getUserNames","added to parents table:"+userId+" "+userName);
        users.put("parent",parent_users);
        return users;
    }
    static class MapprDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "mappr_database";
//        private static final String TABLE_NAME = "GROUPS_TABLE";
        private static final String ALL_STUDENTS_TABLE_NAME = "ALL_STUDENTS_TABLE";
        private static final String ALL_TEACHERS_TABLE_NAME = "ALL_TEACHERS_TABLE";
        private static final String ALL_PARENTS_TABLE_NAME = "ALL_PARENTS_TABLE";
        private static final String POST_TABLE_NAME = "POST_ID_TABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String USERID = "user_id";
        private static final String POSTID="post_id";
        private static final String NAME = "name";
        private static final String CREATE_STUDENT_TABLE = "CREATE TABLE "+ALL_STUDENTS_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INT, "+NAME+" VARCHAR(255));";
        private static final String CREATE_TEACHER_TABLE = "CREATE TABLE "+ALL_TEACHERS_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INT, "+NAME+" VARCHAR(255));";
        private static final String CREATE_PARENT_TABLE = "CREATE TABLE "+ALL_PARENTS_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INT, "+NAME+" VARCHAR(255));";
        private static final String CREATE_POST_TABLE = "CREATE TABLE "+POST_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+POSTID+" INT);";
        private static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " +ALL_STUDENTS_TABLE_NAME;
        private static final String DROP_TEACHER_TABLE = "DROP TABLE IF EXISTS " +ALL_TEACHERS_TABLE_NAME;
        private static final String DROP_PARENT_TABLE = "DROP TABLE IF EXISTS " +ALL_PARENTS_TABLE_NAME;
        private Context context;


        public MapprDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
              /*  db.execSQL(DROP_STUDENT_TABLE);
                db.execSQL(DROP_TEACHER_TABLE);
                db.execSQL(DROP_PARENT_TABLE);*/
                db.execSQL(CREATE_STUDENT_TABLE);
                db.execSQL(CREATE_TEACHER_TABLE);
                db.execSQL(CREATE_PARENT_TABLE);
                db.execSQL(CREATE_POST_TABLE);
            } catch (android.database.SQLException e) {
                Message.message(context, "" + e);
                Log.d("Database", e + "");
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_STUDENT_TABLE);
                onCreate(db);
            } catch (android.database.SQLException e) {
                Message.message(context, "" + e);
            }

        }
    }


}
