package com.example.immunizationmanagement.Model;

import android.content.ContentValues;
import android.content.Context;

import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.DataBase.ItemsTable;

import java.lang.reflect.Field;
import java.util.List;

public class Vaccine {

    private String id;
    private String name;
    private int daysAfter;

    public Vaccine() {
    }

    public Vaccine(String id, String name, int daysAfter) {
        this.id = id;
        this.name = name;
        this.daysAfter = daysAfter;

    }

    public Vaccine(String name, int daysAfter) {
        this.name = name;
        this.daysAfter = daysAfter;
    }

    @Override
    public String toString() {
        return "Vaccine{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", daysAfter=" + daysAfter +
                '}';
    }

    // To ContentValues method  use in database
    public ContentValues toValues() throws Exception {

        ContentValues values = new ContentValues();

        Field[] f =  this.getClass().getDeclaredFields();
        for (String attr : ItemsTable.ALL_COLUMNS_V) {
            if(!attr.equals(ItemsTable.COLUMN_ID_V)) {
                for (int j = 0; j < f.length; j++) {

                    if (attr.equals(f[j].getName()) && !f[j].equals(ItemsTable.COLUMN_ID_V)) {
//                    Log.d("ffhh:", attr+" = "+f[j].getName()+" = "+ f[j].get(this).toString());
                        values.put(attr, f[j].get(this).toString());
                    }
                }
            }
        }
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysAfter() {
        return daysAfter;
    }

    public void setDaysAfter(int daysAfter) {
        this.daysAfter = daysAfter;
    }


    public void addVaccine(Context context) {
        DataSource ds = new DataSource(context);

        // Add Vaccine
        long id = ds.addVaccine(this);
        this.id = id+"";

        ds.closeConnection();
    }


    public void deleteVaccine(Context context){

        DataSource ds = new DataSource(context);

        // Delete all BabyVaccines from database
        int rows = ds.deleteAllBabyVaccineByVID(this.id);


        // Delete Vaccine from database
        int row = ds.DeleteVaccine(this.id);

        ds.closeConnection();


    }
    public void editVaccine(Context context){
        DataSource ds = new DataSource(context);



        // Update baby in database
        int row = ds.editVaccine(this);

        // Get all BabyVaccines from database
        List<BabyVaccine> bvlist = ds.getAllBabyVaccineByVID(this.id);

        for (int i = 0; i < bvlist.size(); i++) {
            BabyVaccine bv = bvlist.get(i);

            // 1- Get Baby from database
            Baby baby = ds.getBaby(bv.getB_id());

            // 2- Calculate new issueDate
            long newDate = baby.addDaystoDob(this.getDaysAfter());
            bv.setIssueDate(newDate);

            // 3- Update issueDate in Database
            ds.editBabyVaccine(bv);

        }
        ds.closeConnection();
    }
}
