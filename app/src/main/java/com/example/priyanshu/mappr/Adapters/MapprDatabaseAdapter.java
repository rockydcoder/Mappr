package com.example.priyanshu.mappr.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.priyanshu.mappr.Data.Message;

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
        return id;
    }

    public long insertTeacher(String name, int userid) {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.USERID, userid);
        contentValues.put(MapprDatabaseHelper.NAME, name);
        long id = db.insert(MapprDatabaseHelper.ALL_TEACHERS_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertParent(String name, int userid) {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.USERID, userid);
        contentValues.put(MapprDatabaseHelper.NAME, name);
        long id = db.insert(MapprDatabaseHelper.ALL_PARENTS_TABLE_NAME, null, contentValues);
        return id;
    }

//    public long insertGroup(String name) {
//        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MapprDatabaseHelper.NAME, name);
//        long id = db.insert(MapprDatabaseHelper.TABLE_NAME, null, contentValues);
//        return id;
//    }

//    public ArrayList<String> getGroups() {
//        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
//        String[] columns = {MapprDatabaseHelper.UID, MapprDatabaseHelper.NAME};
//        Cursor cursor = db.query(MapprDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
//        ArrayList<String> groups = new ArrayList<>();
//        while(cursor.moveToNext()) {
//            String group = cursor.getString(1);
//            groups.add(group);
//        }
//
//        return  groups;
//    }

    static class MapprDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "mappr_database";
//        private static final String TABLE_NAME = "GROUPS_TABLE";
        private static final String ALL_STUDENTS_TABLE_NAME = "ALL_STUDENTS_TABLE";
        private static final String ALL_TEACHERS_TABLE_NAME = "ALL_TEACHERS_TABLE";
        private static final String ALL_PARENTS_TABLE_NAME = "ALL_PARENTS_TABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String USERID = "user_id";
        private static final String NAME = "name";
        private static final String CREATE_STUDENT_TABLE = "CREATE TABLE "+ALL_STUDENTS_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INT, "+NAME+" VARCHAR(255));";
        private static final String CREATE_TEACHER_TABLE = "CREATE TABLE "+ALL_TEACHERS_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INT, "+NAME+" VARCHAR(255));";
        private static final String CREATE_PARENT_TABLE = "CREATE TABLE "+ALL_PARENTS_TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERID+" INT, "+NAME+" VARCHAR(255));";
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
                db.execSQL(DROP_STUDENT_TABLE);
                db.execSQL(DROP_TEACHER_TABLE);
                db.execSQL(DROP_PARENT_TABLE);
                db.execSQL(CREATE_STUDENT_TABLE);
                db.execSQL(CREATE_TEACHER_TABLE);
                db.execSQL(CREATE_PARENT_TABLE);
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
