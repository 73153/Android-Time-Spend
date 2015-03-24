package com.ijoomer.tips.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase;

import com.ijoomer.tips.utilities.Utility;

public class DataBaseProvider
{ 
    private Context mContext;
    private SQLiteDatabase mDb; 
    private DataBaseHelper mDbHelper;



    public DataBaseProvider(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
        if(!mDbHelper.isDataBaseCreated()){
            try
            {
                mDbHelper.createDataBase();
            }
            catch (IOException mIOException)
            {
                throw new Error("UnableToCreateDatabase");
            }
        }


    }
    public DataBaseProvider open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();

    }

    public ArrayList<HashMap<String,String>> getSuite()
    {
        ArrayList<HashMap<String,String>> suiteList = new ArrayList<HashMap<String,String>>();
        try
        {
            mDb = mDbHelper.getReadableDatabase();
            String sql ="SELECT * from "+Utility.TABLE_SUITE;

            Cursor cursor = mDb.rawQuery(sql, null);
            if (cursor!=null)
            {
                while (cursor.moveToNext()){
                    HashMap<String,String> row=  new HashMap<String,String>();
                    row.put(Utility.ID,String.valueOf(cursor.getInt(cursor.getColumnIndex(Utility.ID))));
                    row.put(Utility.NAME,cursor.getString(cursor.getColumnIndex(Utility.NAME)));
                    suiteList.add(row);
                }
            }
            mDb.close();
            return suiteList;
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }

    public ArrayList<HashMap<String,String>> getDrivers(int suiteId)
    {
        ArrayList<HashMap<String,String>> suiteList = new ArrayList<HashMap<String,String>>();
        try
        {
            mDb = mDbHelper.getReadableDatabase();
            String sql ="SELECT * from "+Utility.TABLE_DRIVER+" where "+Utility.PARENTID +"="+suiteId;

            Cursor cursor = mDb.rawQuery(sql, null);
            if (cursor!=null)
            {
                while (cursor.moveToNext()){
                    HashMap<String,String> row=  new HashMap<String,String>();
                    row.put(Utility.ID,String.valueOf(cursor.getInt(cursor.getColumnIndex(Utility.ID))));
                    row.put(Utility.NAME,cursor.getString(cursor.getColumnIndex(Utility.NAME)));
                    suiteList.add(row);
                }
            }
            mDb.close();
            return suiteList;
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }

    public ArrayList<HashMap<String,String>> getTips(int suiteId)
    {
        ArrayList<HashMap<String,String>> suiteList = new ArrayList<HashMap<String,String>>();
        try
        {
            mDb = mDbHelper.getReadableDatabase();
            String sql ="SELECT * from "+Utility.TABLE_TIP+" where "+Utility.PARENTID +"="+suiteId;

            Cursor cursor = mDb.rawQuery(sql, null);
            if (cursor!=null)
            {
                while (cursor.moveToNext()){
                    HashMap<String,String> row=  new HashMap<String,String>();
                    row.put(Utility.ID,String.valueOf(cursor.getInt(cursor.getColumnIndex(Utility.ID))));
                    row.put(Utility.NAME,cursor.getString(cursor.getColumnIndex(Utility.NAME)));
                    row.put(Utility.NOTE,cursor.getString(cursor.getColumnIndex(Utility.NOTE)));
                    row.put(Utility.FAVOURITE, String.valueOf(cursor.getInt(cursor.getColumnIndex(Utility.FAVOURITE))));
                    row.put(Utility.QUESTIONONE,cursor.getString(cursor.getColumnIndex(Utility.QUESTIONONE)));
                    row.put(Utility.QUESTIONTWO,cursor.getString(cursor.getColumnIndex(Utility.QUESTIONTWO)));
                    row.put(Utility.QUESTIONTHREE,cursor.getString(cursor.getColumnIndex(Utility.QUESTIONTHREE)));
                    suiteList.add(row);
                }
            }
            mDb.close();
            return suiteList;
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }

    public HashMap<String,String> getRandomTips(int id)
    {
        HashMap<String,String> row = new HashMap<String,String>();
        try
        {
            mDb = mDbHelper.getReadableDatabase();
            String joinQuery = "SELECT a.id'tipid',a.name'tipname',a.note'tipnote',a.favourite'tipfavourite',a.questionOne'tipquestionone',a.questionTwo'tipquestiontwo',a.questionThree'tipquestionthree',b.id'driverid',b.name'drivername',c.id'suiteid',c.name'suitename' FROM "+Utility.TABLE_TIP+" a join "+Utility.TABLE_DRIVER+" b on b.id=a.parentId join "+Utility.TABLE_SUITE+" c on c.id=b.parentId where a.id="+id;

            Cursor cursor = mDb.rawQuery(joinQuery, null);
            if (cursor!=null)
            {
                while (cursor.moveToNext()){
                    row.put(Utility.TIPID, String.valueOf(cursor.getInt(cursor.getColumnIndex("tipid"))));
                    row.put(Utility.TIPNAME, cursor.getString(cursor.getColumnIndex("tipname")));
                    row.put(Utility.TIPNOTE, cursor.getString(cursor.getColumnIndex("tipnote")));
                    row.put(Utility.TIPFAVOURITE, String.valueOf(cursor.getInt(cursor.getColumnIndex("tipfavourite"))));
                    row.put(Utility.TIPQUESTIONONE, cursor.getString(cursor.getColumnIndex("tipquestionone")));
                    row.put(Utility.TIPQUESTIONTWO, cursor.getString(cursor.getColumnIndex("tipquestiontwo")));
                    row.put(Utility.TIPQUESTIONTHREE, cursor.getString(cursor.getColumnIndex("tipquestionthree")));
                    row.put(Utility.DRIVERID, String.valueOf(cursor.getInt(cursor.getColumnIndex("driverid"))));
                    row.put(Utility.DRIVERNAME, cursor.getString(cursor.getColumnIndex("drivername")));
                    row.put(Utility.SUITEID, String.valueOf(cursor.getInt(cursor.getColumnIndex("suiteid"))));
                    row.put(Utility.SUITENAME, cursor.getString(cursor.getColumnIndex("suitename")));
                }
            }

            mDb.close();
            return row;
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }

    public ArrayList<HashMap<String,String>> getFavourites()
    {
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        try
        {
            mDb = mDbHelper.getReadableDatabase();
            String joinQuery = "SELECT a.id'tipid',a.name'tipname',a.note'tipnote',a.favourite'tipfavourite',a.questionOne'tipquestionone',a.questionTwo'tipquestiontwo',a.questionThree'tipquestionthree',b.id'driverid',b.name'drivername',c.id'suiteid',c.name'suitename' FROM "+Utility.TABLE_TIP+" a join "+Utility.TABLE_DRIVER+" b on b.id=a.parentId join "+Utility.TABLE_SUITE+" c on c.id=b.parentId where a.favourite=1";

            Cursor cursor = mDb.rawQuery(joinQuery, null);
            if (cursor!=null)
            {
                while (cursor.moveToNext()){
                    HashMap<String,String> row = new HashMap<String,String>();
                    row.put(Utility.TIPID, String.valueOf(cursor.getInt(cursor.getColumnIndex("tipid"))));
                    row.put(Utility.TIPNAME, cursor.getString(cursor.getColumnIndex("tipname")));
                    row.put(Utility.TIPNOTE, cursor.getString(cursor.getColumnIndex("tipnote")));
                    row.put(Utility.TIPFAVOURITE, String.valueOf(cursor.getInt(cursor.getColumnIndex("tipfavourite"))));
                    row.put(Utility.TIPQUESTIONONE, cursor.getString(cursor.getColumnIndex("tipquestionone")));
                    row.put(Utility.TIPQUESTIONTWO, cursor.getString(cursor.getColumnIndex("tipquestiontwo")));
                    row.put(Utility.TIPQUESTIONTHREE, cursor.getString(cursor.getColumnIndex("tipquestionthree")));
                    row.put(Utility.DRIVERID, String.valueOf(cursor.getInt(cursor.getColumnIndex("driverid"))));
                    row.put(Utility.DRIVERNAME, cursor.getString(cursor.getColumnIndex("drivername")));
                    row.put(Utility.SUITEID, String.valueOf(cursor.getInt(cursor.getColumnIndex("suiteid"))));
                    row.put(Utility.SUITENAME, cursor.getString(cursor.getColumnIndex("suitename")));

                    data.add(row);
                }
            }

            mDb.close();
            return data;
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }

    public void updateFavourite(int id,int favourite)
    {
        try
        {
            mDb = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Utility.FAVOURITE, favourite);
            String where = Utility.ID + " = ? ";
            String [] value = { Integer.toString(id) };

            int count = mDb.update(Utility.TABLE_TIP, values, where , value);

            mDb.close();
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }
}

