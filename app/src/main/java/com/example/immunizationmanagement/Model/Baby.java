package com.example.immunizationmanagement.Model;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import com.example.immunizationmanagement.Activities.AddBabyActivity;
import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.DataBase.ItemsTable;
import com.example.immunizationmanagement.Utills.Function;
import com.example.immunizationmanagement.Utills.ImageFactory;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Baby {

    private String id;
    private String name;
    private long dob;
    private Gender gender;
    private String imageName;

    private List<Vaccine> vaccines = null;

    public Baby(){

    }

    public Baby(String name, long dob, Gender gender, String imageName) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.imageName = imageName;
    }

    public Baby(String id, String name, long dob, Gender gender, String imageName) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Baby{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender=" + gender +
                ", imageName='" + imageName + '\'' +
                ", vaccines=" + vaccines +
                '}';
    }

    // To ContentValues method  use in database
    public ContentValues toValues() throws Exception {

        ContentValues values = new ContentValues();

        Field[] f =  this.getClass().getDeclaredFields();
        for (String attr : ItemsTable.ALL_COLUMNS_B) {
            if(!attr.equals(ItemsTable.COLUMN_ID_B)) {
                for (int j = 0; j < f.length; j++) {

                    if (attr.equals(f[j].getName()) && !f[j].equals(ItemsTable.COLUMN_ID_B)) {
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

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public long addDaystoDob(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getDob());
        cal.add(Calendar.MINUTE, 1440 * days);
        return cal.getTimeInMillis();
    }

    public void addBaby(Context context) {
        DataSource ds = new DataSource(context);

        long id = ds.addBaby(this);
        this.id = id+"";

        // Get all vaccines from database
        List<Vaccine> vlist = ds.getAllVaccine();

        for (int i = 0; i < vlist.size(); i++) {

            Vaccine vaccine = vlist.get(i);
            long newDate = this.addDaystoDob(vaccine.getDaysAfter());

            Status status = Status.P;

            //TODO- Comment 1
            //---------------------
            Date current = new Date();
            if(newDate < current.getTime()) status = Status.D;
            //-----------------------

            BabyVaccine bv = new BabyVaccine(
                    this.id,
                    vaccine.getId(),
                    newDate,
                    status
            );
            ds.addBabyVaccine(bv);
        }

        ds.closeConnection();
    }

    public void deleteBaby(Context context){

        DataSource ds = new DataSource(context);

        // Delete all BabyVaccines from database
        int rows = ds.deleteAllBabyVaccineByBID(this.id);

        // Delete baby from database
        int row = ds.DeleteBaby(this.id);

        // Delete baby image from phone storage
        ImageFactory.deleteImage(this.id);

        ds.closeConnection();

    }

    public void editBaby(Context context){

        DataSource ds = new DataSource(context);

        // Update baby in database
        int row = ds.editBaby(this);

        // Get all BabyVaccines from database
        List<BabyVaccine> bvlist = ds.getAllBabyVaccineByBID(this.id);

        for (int i = 0; i < bvlist.size(); i++) {
            BabyVaccine bv = bvlist.get(i);

            // 1- Get Vaccine from database
            Vaccine vaccine = ds.getVaccine(bv.getV_id());

            // 2- Calculate new issueDate
            long newDate = this.addDaystoDob(vaccine.getDaysAfter());
            bv.setIssueDate(newDate);

            // 3- Update issueDate in Database
            ds.editBabyVaccine(bv);

        }
        ds.closeConnection();

    }
}
