package com.example.officersclub.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQlite.db";
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN = "name";
    public static final String STAFFNO_COLUMN = "staff_no";
    public static final String BILL_TABLE_NAME = "bill";
    public static final String BILL_NO_COLUMN = "bill_no";
    public static final String DATE_COLUMN = "bill_date";
    public static final String TIME_COLUMN = "bill_time";
    public static final String AMOUNT_COLUMN = "bill_amount";


    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " +USER_TABLE_NAME  +
                        "("+STAFFNO_COLUMN+" integer primary key,"+USER_COLUMN +" text)");
        db.execSQL(
                "create table " +BILL_TABLE_NAME  +
                        "("+BILL_NO_COLUMN+" integer primary key,"+DATE_COLUMN +" text,"+TIME_COLUMN + " text,"+AMOUNT_COLUMN+" real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+BILL_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertBillDetails (Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BILL_NO_COLUMN, bill.getBillNo());
        contentValues.put(DATE_COLUMN,bill.getDate() );
        contentValues.put(TIME_COLUMN,bill.getDate() );
        contentValues.put(AMOUNT_COLUMN,bill.getDate() );
        db.insert(BILL_TABLE_NAME, null, contentValues);
        return true;
    }

    public long insertUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN, user.getName());
        contentValues.put(STAFFNO_COLUMN, "12");
        db.execSQL("delete from "+ USER_TABLE_NAME);
        long retVal=db.insert(USER_TABLE_NAME, null, contentValues);
        return retVal;
    }


    public int getTotalBills(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BILL_TABLE_NAME);
        return numRows;
    }




    public ArrayList<User> getAllUsers() {
        ArrayList<User> array_list = new ArrayList<User>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USER_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            User user = new User(res.getString(res.getColumnIndex(USER_COLUMN)),new Integer(res.getString(res.getColumnIndex(STAFFNO_COLUMN))));
            array_list.add(user);
            res.moveToNext();
        }
        return array_list;
    }
    public ArrayList<Bill> getAllBills() {
        ArrayList<Bill> arrayList = new ArrayList<Bill>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+BILL_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Bill  bill=new Bill();

            bill.setAmount(new Double(res.getString(res.getColumnIndex(AMOUNT_COLUMN))));
            bill.setBillNo(new Integer(res.getString(res.getColumnIndex(BILL_NO_COLUMN))));
            bill.setDate(res.getString(res.getColumnIndex(DATE_COLUMN)));
            bill.setTime(res.getString(res.getColumnIndex(TIME_COLUMN)));
            bill.notifyAll();
            arrayList.add(bill);
            res.moveToNext();
        }
        return arrayList;
    }
    public ArrayList<Bill> getBillsByMonth(String date) {
        ArrayList<Bill> arrayList = new ArrayList<Bill>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+BILL_TABLE_NAME+" where "+DATE_COLUMN+"="+date, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Bill  bill=new Bill();

            bill.setAmount(new Double(res.getString(res.getColumnIndex(AMOUNT_COLUMN))));
            bill.setBillNo(new Integer(res.getString(res.getColumnIndex(BILL_NO_COLUMN))));
            bill.setDate(res.getString(res.getColumnIndex(DATE_COLUMN)));
            bill.setTime(res.getString(res.getColumnIndex(TIME_COLUMN)));
            arrayList.add(bill);
            res.moveToNext();
        }
        return arrayList;
    }
    public ArrayList<Bill> getBillsBy(String date) {
        ArrayList<Bill> arrayList = new ArrayList<Bill>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+BILL_TABLE_NAME+" where "+DATE_COLUMN+"="+date, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Bill  bill=new Bill();

            bill.setAmount(new Double(res.getString(res.getColumnIndex(AMOUNT_COLUMN))));
            bill.setBillNo(new Integer(res.getString(res.getColumnIndex(BILL_NO_COLUMN))));
            bill.setDate(res.getString(res.getColumnIndex(DATE_COLUMN)));
            bill.setTime(res.getString(res.getColumnIndex(TIME_COLUMN)));
            arrayList.add(bill);
            res.moveToNext();
        }
        return arrayList;
    }

}
