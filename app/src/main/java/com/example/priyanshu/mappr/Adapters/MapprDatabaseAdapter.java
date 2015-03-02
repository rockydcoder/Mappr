package com.example.priyanshu.mappr.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.priyanshu.mappr.Data.Message;

import java.util.ArrayList;

/**
 * Created by rocky on 27/2/15.
 */
public class MapprDatabaseAdapter {

    MapprDatabaseHelper mapprDatabaseHelper;

    public MapprDatabaseAdapter(Context context) {
        mapprDatabaseHelper = new MapprDatabaseHelper(context);
    }

    public long insertGroup(String name) {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MapprDatabaseHelper.NAME, name);
        long id = db.insert(MapprDatabaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public ArrayList<String> getGroups() {
        SQLiteDatabase db = mapprDatabaseHelper.getWritableDatabase();
        String[] columns = {MapprDatabaseHelper.UID, MapprDatabaseHelper.NAME};
        Cursor cursor = db.query(MapprDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<String> groups = new ArrayList<>();
        while(cursor.moveToNext()) {
            String group = cursor.getString(1);
            groups.add(group);
        }

        return  groups;
    }

    static class MapprDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "mappr_database";
        private static final String TABLE_NAME = "GROUPS_TABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "name";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
        private Context context;


        public MapprDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (android.database.SQLException e) {
                Message.message(context, "" + e);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (android.database.SQLException e) {
                Message.message(context, "" + e);
            }

        }
    }


}
