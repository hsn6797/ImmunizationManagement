package com.example.immunizationmanagement.Model;


import android.content.ContentValues;
import android.content.Context;

import com.example.immunizationmanagement.DataBase.DataSource;
import com.example.immunizationmanagement.DataBase.ItemsTable;

import java.util.List;

public class BabyVaccine {

    private String id;
    private String b_id;
    private String v_id;
    private long issueDate;
    private Status status;

    public BabyVaccine() {
    }

    public BabyVaccine(String id, String b_id, String v_id, long dueDate, Status status) {
        this.id = id;
        this.b_id = b_id;
        this.v_id = v_id;
        this.issueDate = dueDate;
        this.status = status;
    }

    public BabyVaccine(String b_id, String v_id, long dueDate, Status status) {
        this.b_id = b_id;
        this.v_id = v_id;
        this.issueDate = dueDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "BabyVaccine{" +
                "id='" + id + '\'' +
                ", b_id='" + b_id + '\'' +
                ", v_id='" + v_id + '\'' +
                ", issueDate=" + issueDate +
                ", status=" + status +
                '}';
    }

    // To ContentValues method  use in database
    public ContentValues toValues() throws Exception {

        ContentValues values = new ContentValues();

        values.put(ItemsTable.COLUMN_BID_BV,this.getB_id());
        values.put(ItemsTable.COLUMN_VID_BV,this.getV_id());
        values.put(ItemsTable.COLUMN_ISSUE_DATE_BV,this.getIssueDate());
        values.put(ItemsTable.COLUMN_STATUS_BV,this.getStatus().toString());
        return values;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getV_id() {
        return v_id;
    }

    public void setV_id(String v_id) {
        this.v_id = v_id;
    }

    public long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(long issueDate) {
        this.issueDate = issueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    public static  void saveBabyVaccines(Context context, String b_id){

        DataSource ds = new DataSource(context);

        // Get baby from database
        Baby baby = ds.getBaby(b_id);

        // Get all vaccines from database
        List<Vaccine> vlist = ds.getAllVaccine();

        for (int i = 0; i < vlist.size(); i++) {

            Vaccine vaccine = vlist.get(i);
            long newDate = baby.addDaystoDob(vaccine.getDaysAfter());
            BabyVaccine bv = new BabyVaccine(
                    baby.getId(),
                    vaccine.getId(),
                    newDate,
                    Status.P
            );

            ds.addBabyVaccine(bv);
        }

    }





}
