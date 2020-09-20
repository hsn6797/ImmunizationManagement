package com.example.immunizationmanagement.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.immunizationmanagement.Model.Baby;
import com.example.immunizationmanagement.Model.BabyVaccine;
import com.example.immunizationmanagement.Model.Vaccine;
import com.example.immunizationmanagement.Utills.Function;

import java.util.ArrayList;
import java.util.List;

public class DataSource {


    private Context context;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.context = context;
        mDbHelper = new DBHelper(context);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void openConnection(){
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void closeConnection(){
        mDbHelper.close();
    }


    // Baby CRUD Methods
    public long addBaby(Baby item){
        ContentValues values = null;
        try {
            values = item.toValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDatabase.insert(ItemsTable.TABLE_Name_B,null,values);
    }
    public List<Baby> getAllBabies(){
        List<Baby> dataItems = null;
        try {
//            mDatabase = mDbHelper.getReadableDatabase();

            dataItems = new ArrayList<>();
            Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_B,ItemsTable.ALL_COLUMNS_B,
                    null,null,null,null,null);
            while(cursor.moveToNext()){
                Baby item = new Baby(
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_B)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME_B)),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_DOB_B)),
                        Function.checkGender(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_GENDER_B))),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE_NAME_B))
                        );
                dataItems.add(item);
            }
        } catch (Exception e) {
            return null;
        }
        return dataItems;
    }
    public Baby getBaby(String id){
        Baby item = null;

        Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_B,ItemsTable.ALL_COLUMNS_B,
                ItemsTable.COLUMN_ID_B +"=?",new String[]{id},null,null,null,null);

        if(cursor != null && cursor.moveToFirst()){
            item = new Baby(
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_B)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME_B)),
                    cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_DOB_B)),
                    Function.checkGender(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_GENDER_B))),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_IMAGE_NAME_B))
            );
        }

        return item;
    }
    public int DeleteBaby(String id){
        return mDatabase.delete(ItemsTable.TABLE_Name_B,ItemsTable.COLUMN_ID_B+"=?",new String[]{id});
    }
    public int editBaby(Baby item){
        ContentValues values = null;
        try {
            values = item.toValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return mDatabase.update(ItemsTable.TABLE_Name_B,values,ItemsTable.COLUMN_ID_B + "=?",
                new String[]{item.getId()});
    }


    // Vaccine CRUD Methods
    public long addVaccine(Vaccine item){
        ContentValues values = null;
        try {
            values = item.toValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDatabase.insert(ItemsTable.TABLE_Name_V,null,values);

    }
    public List<Vaccine> getAllVaccine(){
        List<Vaccine> dataItems = null;
        try {
//            mDatabase = mDbHelper.getReadableDatabase();

            dataItems = new ArrayList<>();
            Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_V,ItemsTable.ALL_COLUMNS_V,
                    null,null,null,null,null);
            while(cursor.moveToNext()){
                Vaccine item = new Vaccine(
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_V)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME_V)),
                        cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_DAYS_AFTER_V))
                );
                dataItems.add(item);
            }
        } catch (Exception e) {
            return null;
        }
        return dataItems;
    }
    public Vaccine getVaccine(String id){
        Vaccine item = null;

        Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_V,ItemsTable.ALL_COLUMNS_V,
                ItemsTable.COLUMN_ID_V +"=?",new String[]{id},null,null,null,null);

        if(cursor != null && cursor.moveToFirst()){
            item = new Vaccine(
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_V)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME_V)),
                    cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_DAYS_AFTER_V))
            );
        }

        return item;
    }
    public int DeleteVaccine(String id){
        return mDatabase.delete(ItemsTable.TABLE_Name_V,ItemsTable.COLUMN_ID_V+"=?",new String[]{id});
    }
    public int editVaccine(Vaccine item){
        ContentValues values = null;
        try {
            values = item.toValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDatabase.update(ItemsTable.TABLE_Name_V,values,ItemsTable.COLUMN_ID_V + "=?",
                new String[]{item.getId()});
    }


    // BabyVaccine CRUD Methods
    public long addBabyVaccine(BabyVaccine item){
        ContentValues values = new ContentValues();
        try {
            values.put(ItemsTable.COLUMN_BID_BV,item.getB_id());
            values.put(ItemsTable.COLUMN_VID_BV,item.getV_id());
            values.put(ItemsTable.COLUMN_ISSUE_DATE_BV,item.getIssueDate());
            values.put(ItemsTable.COLUMN_STATUS_BV,item.getStatus().toString());
            values.put(ItemsTable.COLUMN_SNOOZAT_BV,item.getSnoozAt());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDatabase.insert(ItemsTable.TABLE_Name_BV,null,values);

    }
    public List<BabyVaccine> getAllBabyVaccineByBID(String b_id){
        List<BabyVaccine> dataItems = null;
        try {
//            mDatabase = mDbHelper.getReadableDatabase();

            dataItems = new ArrayList<>();
            Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_BV,ItemsTable.ALL_COLUMNS_BV,
                    ItemsTable.COLUMN_BID_BV +"=?",new String[]{b_id},null,null,null);
            while(cursor.moveToNext()){
                BabyVaccine item = new BabyVaccine(
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_BV)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_BID_BV)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_VID_BV)),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_ISSUE_DATE_BV)),
                        Function.checkStatus(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_STATUS_BV))),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_SNOOZAT_BV))
                        );
                dataItems.add(item);
            }
        } catch (Exception e) {
            return null;
        }
        return dataItems;
    }
    public List<BabyVaccine> getAllBabyVaccineByVID(String v_id){
        List<BabyVaccine> dataItems = null;
        try {
//            mDatabase = mDbHelper.getReadableDatabase();

            dataItems = new ArrayList<>();
            Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_BV,ItemsTable.ALL_COLUMNS_BV,
                    ItemsTable.COLUMN_VID_BV +"=?",new String[]{v_id},null,null,null);
            while(cursor.moveToNext()){
                BabyVaccine item = new BabyVaccine(
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_BV)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_BID_BV)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_VID_BV)),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_ISSUE_DATE_BV)),
                        Function.checkStatus(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_STATUS_BV))),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_SNOOZAT_BV))

                );
                dataItems.add(item);
            }
        } catch (Exception e) {
            return null;
        }
        return dataItems;
    }

    public List<BabyVaccine> getAllBabyVaccine(){
        List<BabyVaccine> dataItems = null;
        try {
//            mDatabase = mDbHelper.getReadableDatabase();

            dataItems = new ArrayList<>();
            Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_BV,ItemsTable.ALL_COLUMNS_BV,
                    null,null,null,null,null);
            while(cursor.moveToNext()){
                BabyVaccine item = new BabyVaccine(
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_BV)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_BID_BV)),
                        cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_VID_BV)),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_ISSUE_DATE_BV)),
                        Function.checkStatus(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_STATUS_BV))),
                        cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_SNOOZAT_BV))

                );
                dataItems.add(item);
            }
        } catch (Exception e) {
            return null;
        }
        return dataItems;
    }


    public BabyVaccine getBabyVaccine(String id){
        BabyVaccine item = null;

        Cursor cursor = mDatabase.query(ItemsTable.TABLE_Name_BV,ItemsTable.ALL_COLUMNS_BV,
                ItemsTable.COLUMN_ID_BV +"=?",new String[]{id},null,null,null,null);

        if(cursor != null && cursor.moveToFirst()){
             item = new BabyVaccine(
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID_BV)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_BID_BV)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_VID_BV)),
                    cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_ISSUE_DATE_BV)),
                    Function.checkStatus(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_STATUS_BV))),
                    cursor.getLong(cursor.getColumnIndex(ItemsTable.COLUMN_SNOOZAT_BV))

             );
        }

        return item;
    }


    public int deleteAllBabyVaccineByBID(String b_id){
        return mDatabase.delete(ItemsTable.TABLE_Name_BV,ItemsTable.COLUMN_BID_BV+"=?",new String[]{b_id});
    }
    public int deleteAllBabyVaccineByVID(String v_id){
        return mDatabase.delete(ItemsTable.TABLE_Name_BV,ItemsTable.COLUMN_VID_BV+"=?",new String[]{v_id});
    }
    public int deleteBabyVaccine(String id){
        return mDatabase.delete(ItemsTable.TABLE_Name_BV,ItemsTable.COLUMN_ID_BV+"=?",new String[]{id});
    }

    public int editBabyVaccine(BabyVaccine item){
        ContentValues values = null;
        try {
            values = item.toValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDatabase.update(ItemsTable.TABLE_Name_BV,values,ItemsTable.COLUMN_ID_BV + "=?",
                new String[]{item.getId()});
    }


}
