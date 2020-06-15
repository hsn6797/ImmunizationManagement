package com.example.immunizationmanagement.DataBase;

public class ItemsTable {

    public static final String TABLE_Name_B = "Baby";
    public static final String TABLE_Name_BV = "BabyVaccine";
    public static final String TABLE_Name_V = "Vaccine";

    // Baby Table in Database
    public static final String COLUMN_ID_B = "id";
    public static final String COLUMN_NAME_B = "name";
    public static final String COLUMN_DOB_B = "dob";
    public static final String COLUMN_GENDER_B = "gender";
    public static final String COLUMN_IMAGE_NAME_B = "imageName";


    public static final String SQL_CREATE_B =
            "Create Table " + TABLE_Name_B + "(" +
                    COLUMN_ID_B + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_B + " TEXT, " +
                    COLUMN_DOB_B + "   LONG, " +
                    COLUMN_GENDER_B + "   TEXT, " +
                    COLUMN_IMAGE_NAME_B + "   TEXT " + ");";


    public static final String SQL_DELETE_B =
            "DROP TABLE " + TABLE_Name_B;


    public static final String[] ALL_COLUMNS_B =
            {COLUMN_ID_B, COLUMN_NAME_B, COLUMN_DOB_B, COLUMN_GENDER_B, COLUMN_IMAGE_NAME_B};


    // Vaccine Table in Database
    public static final String COLUMN_ID_V = "id";
    public static final String COLUMN_NAME_V = "name";
    public static final String COLUMN_DAYS_AFTER_V = "daysAfter";

    public static final String SQL_CREATE_V =
            "Create Table " + TABLE_Name_V + "(" +
                    COLUMN_ID_V + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_V + " TEXT, " +
                    COLUMN_DAYS_AFTER_V + "   INTEGER " + ");";


    public static final String SQL_DELETE_V =
            "DROP TABLE " + TABLE_Name_V;


    public static final String[] ALL_COLUMNS_V =
            {COLUMN_ID_V, COLUMN_NAME_V, COLUMN_DAYS_AFTER_V};


    // BabyVaccine Table in Database
    public static final String COLUMN_ID_BV = "id";
    public static final String COLUMN_BID_BV = "b_id";
    public static final String COLUMN_VID_BV = "v_id";
    public static final String COLUMN_ISSUE_DATE_BV = "issueDate";
    public static final String COLUMN_STATUS_BV = "status";


    public static final String SQL_CREATE_BV =
            "Create Table " + TABLE_Name_BV + "(" +
                    COLUMN_ID_BV + " INTEGER PRIMARY KEY," +
                    COLUMN_BID_BV + " INTEGER, " +
                    COLUMN_VID_BV + " INTEGER, " +
                    COLUMN_ISSUE_DATE_BV + " LONG, " +
                    COLUMN_STATUS_BV + "   TEXT " + ");";


    public static final String SQL_DELETE_BV =
            "DROP TABLE " + TABLE_Name_BV;


    public static final String[] ALL_COLUMNS_BV =
            {COLUMN_ID_BV, COLUMN_BID_BV, COLUMN_VID_BV, COLUMN_ISSUE_DATE_BV,COLUMN_STATUS_BV};

}
