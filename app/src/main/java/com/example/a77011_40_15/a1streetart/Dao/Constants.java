package com.example.a77011_40_15.a1streetart.Dao;

public class Constants {
    public static final int VERSION = 2;
    public static final String NOM_BDD = "afpastreetart";
    public static final String TABLE_ARTICLES = "photos";
    public static final String COL_ID = "ID";
    public static final int NUM_COL_ID = 0;
    public static final String COL_NAME = "NAME";
    public static final int NUM_COL_NAME = 1;
    public static final String COL_DESC = "DESCRIPTION";
    public static final int NUM_COL_DESC = 2;
    public static final String COL_URI = "URI";
    public static final int NUM_COL_URI = 3;
    public static final String COL_LATITUDE = "LATITUDE";
    public static final int NUM_COL_LATITUDE = 4;
    public static final String COL_LONGITUDE = "LONGITUDE";
    public static final int NUM_COL_LONGITUDE = 5;
    public static final String COL_DATE = "DATE";
    public static final int NUM_COL_DATE = 6;
    final String NOT_NULL = "NOT NULL";

    public static final String CREATE_BDD = "CREATE TABLE "
            + TABLE_ARTICLES + " ("+
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_NAME + " TEXT NOT NULL, " +
            COL_URI + " TEXT NOT NULL, " +
            COL_DESC + " TEXT NOT NULL)";
/*
  COL_URI + " TEXT NOT NULL, " +
            COL_LATITUDE + " REAL NOT NULL, " +
            COL_LONGITUDE + " REAL NOT NULL, " +
            COL_DATE + " INTEGER NOT NULL" +
            */
}
