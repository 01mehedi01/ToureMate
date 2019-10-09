package com.example.user.touremate.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.user.touremate.DataBase.DataBaseHelper.USER_LOGINID;

/**
 * Created by User on 3/6/2018.
 */

public class DataBaseSource {

    private DataBaseHelper dh;
    private SQLiteDatabase sqLiteDatabase;
    private  Context context;
    public  double GetAmount = 0.0 ;
    public int Id;
    private SharedPreferences sharedPreferencesOne;
    private  SharedPreferences.Editor editorOne;
    private  int createtoureUserID;

    public DataBaseSource(Context context) {
        this.context = context;
        dh = new DataBaseHelper(context);
    }
    public void Open(){
        sqLiteDatabase = dh.getWritableDatabase();
    }
    public void Close(){
        sqLiteDatabase.close();
    }

    public  boolean UpdateInfo(EventProjoClass projoClass){

        this.Open();

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EVENT_NAME,projoClass.getEventName());
        values.put(DataBaseHelper.EVENT_RETURN,projoClass.getReturnDate());
        values.put(DataBaseHelper.EVENT_START,projoClass.getStartDate());
        values.put(DataBaseHelper.EVENT_BUDGET,projoClass.getEstimateBudget());
      //  Toast.makeText(context, "hello \t"+projoClass.getAutoincrementEvent(), Toast.LENGTH_SHORT).show();
       int updateRow =  sqLiteDatabase.update(DataBaseHelper.EVENT,values,
                DataBaseHelper.EVENT_ID+"="+ projoClass.getAutoincrementEvent(),null);
        this.Close();

        if(updateRow > 0){
            return true;
        }else {
            return false;
        }

    }
    public  boolean UpdateInfoBudget(EventProjoClass projoClass){

        this.Open();

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EVENT_BUDGET,projoClass.getEstimateBudget());

        int updateRow =  sqLiteDatabase.update(DataBaseHelper.EVENT,values,
                DataBaseHelper.EVENT_ID +"="+ projoClass.getAutoincrementEvent(),null);
        this.Close();

        if(updateRow > 0){
            return true;
        }else {
            return false;
        }

    }
    public  boolean InsertEventInfo(EventProjoClass projoClass){

        this.Open();


        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EVENT_NAME,projoClass.getEventName());
        values.put(DataBaseHelper.EVENT_RETURN,projoClass.getReturnDate());
        values.put(DataBaseHelper.EVENT_BUDGET,projoClass.getEstimateBudget());
        values.put(DataBaseHelper.EVENT_START,projoClass.getStartDate());
        values.put(USER_LOGINID,projoClass.getUserLohinID());

        long inserted = sqLiteDatabase.insert(DataBaseHelper.EVENT,null,values);
        this.Close();

        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }
   public ArrayList<EventProjoClass>getAllInfo(int U_id){

       this.Open();
       ArrayList<EventProjoClass>projoClasses = new ArrayList<>();

       String selection = USER_LOGINID + "="+U_id;
       Cursor cursor = sqLiteDatabase.query(DataBaseHelper.EVENT,null,selection,null,null,null,null);

       if(cursor != null && cursor.getCount() > 0){
           cursor.moveToFirst();
           do{
               int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.EVENT_ID));
               String eventName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_NAME));
               String StartDate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_START));
               String ReturnDate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_RETURN));
               double  Budgert = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.EVENT_BUDGET));


               EventProjoClass projoClass = new EventProjoClass(id,eventName,StartDate,Budgert,ReturnDate);
               projoClasses.add(projoClass);

           }while (cursor.moveToNext());
       }
       cursor.close();
       this.Close();
       return projoClasses;
   }
    public ArrayList<EventProjoClass>getAllInfoByID(int IDD){

        this.Open();
        ArrayList<EventProjoClass>projoClasses = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.EVENT,null,DataBaseHelper.EVENT_ID+"="+IDD,null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.EVENT_ID));
                String eventName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_NAME));
                String StartDate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_START));
                String ReturnDate = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_RETURN));
                double  Budgert = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.EVENT_BUDGET));


                EventProjoClass projoClass = new EventProjoClass(id,eventName,StartDate,Budgert,ReturnDate);
                projoClasses.add(projoClass);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();
        return projoClasses;
    }
   public  EventProjoClass UpadateInfoById(int id){

       this.Open();
       EventProjoClass projoClass = null;
       ArrayList<EventProjoClass>projoClasses = new ArrayList<>();
       Cursor cursor = sqLiteDatabase.query(DataBaseHelper.EVENT,null,DataBaseHelper.EVENT_ID+"= "+id,null,null,null,null);

       if(cursor != null && cursor.getCount() > 0){
           cursor.moveToFirst();

               int Id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.EVENT_ID));
               String Name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_NAME));
               String eventStart = cursor.getString(cursor.getColumnIndex(DataBaseHelper.EVENT_START));


               projoClass = new EventProjoClass();

       }
       this.Close();
       return projoClass;
   }
   public  boolean DeleteInfo(int infoId){


       this.Open();
       int deteleRow = sqLiteDatabase.delete(DataBaseHelper.EVENT,DataBaseHelper.EVENT_ID+"="+infoId,null);

       this.Close();

       if(deteleRow > 0)
         {
             return  true;
         }else {
             return false;
         }
   }

   /////////////////////////////////////////////////////////////// Deatils Table ////////////////////////////

    public  boolean DELeteInfoDetails(int infoId){

        this.Open();
        int deteleRow = sqLiteDatabase.delete(DataBaseHelper.COST_TABLE,DataBaseHelper.COSTID +"="+infoId,null);

        this.Close();

        if(deteleRow>0)
        {
            return  true;
        }else {
            return false;
        }
    }
    public  boolean DELeteInfoDetailsBYForeignKey(int infoId){

        this.Open();
        int deteleRow = sqLiteDatabase.delete(DataBaseHelper.COST_TABLE,DataBaseHelper.COST_FOREIGNBY_CREATE_TOUR +"="+infoId,null);

        this.Close();

        if(deteleRow >0)
        {
            return  true;
        }else {
            return false;
        }
    }
  /*  public  boolean DELeteBYFOrieignKEY(int infoId){

        this.Open();
        int deteleRow = sqLiteDatabase.delete(DataBaseHelper.DETAILS_TABLE,DataBaseHelper.INFOFOREINFID+"="+infoId,null);
        this.Close();

        if(deteleRow>0)
        {
            return  true;
        }else {
            return false;
        }
    }*/

    public boolean InsertCost(CostProjoClass costProjoClass){
        this.Open();

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.COST_NAME,costProjoClass.getCostName());
        values.put(DataBaseHelper.COST_DATE,costProjoClass.getCosteTime());
        values.put(DataBaseHelper.COST_AMOUNT,costProjoClass.getCostAmount());
        values.put(DataBaseHelper.COST_FOREIGNBY_CREATE_TOUR,costProjoClass.getAutoincrementByTourPlan());
        values.put(DataBaseHelper.COST_DETAILS,costProjoClass.getCostDetails());


        long inserted = sqLiteDatabase.insert(DataBaseHelper.COST_TABLE,null,values);
        this.Close();

        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }

    public  ArrayList<CostProjoClass> getallInfoDetails(int IId){

        this.Open();
        ArrayList<CostProjoClass> costProjoClasses = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.COST_TABLE,null,DataBaseHelper.COST_FOREIGNBY_CREATE_TOUR+"= "+IId,
                null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                 int Id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COSTID));
                 int autoByCreate = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COST_FOREIGNBY_CREATE_TOUR));
                String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COST_NAME));
                String date = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COST_DATE));
                String details = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COST_DETAILS));
                double amount = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COST_AMOUNT));

                GetAmount = GetAmount + amount;


                CostProjoClass projoClass = new CostProjoClass(Id,autoByCreate,name,amount,details,date);
                costProjoClasses.add(projoClass);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();
        return costProjoClasses;
    }

    //////////////////////////////////////////   Login //////////////////////////////////////////
    public boolean InsertLoginValue(LoginPojoClass loginPojoClass){

        this.Open();

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.USERNAME,loginPojoClass.getUserName());
        values.put(DataBaseHelper.USERPASSWORD,loginPojoClass.getUserPassword());
        values.put(DataBaseHelper.USERPHONENUMBER,loginPojoClass.getUserPhoneNumber());


        long inserted = sqLiteDatabase.insert(DataBaseHelper.LOGIN_TABLE,null,values);
        this.Close();

        if(inserted > 0){
            return true;
        }else {
            return false;
        }
    }




    public boolean MatchUserANDPassword(String user,String pass){

        this.Open();
        boolean good = false;
        ArrayList<LoginPojoClass> projoClasses = new ArrayList<>();

       // String[] projection = {DataBaseHelper.USERNAME ,DataBaseHelper.USERPASSWORD};
        String  selection = DataBaseHelper.USERNAME +" =?"+ " AND " +  DataBaseHelper.USERPASSWORD +" =?" ;
        String[] selectionargs = {user,pass};

        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.LOGIN_TABLE
                ,null
                , selection
                ,selectionargs
                ,null
                ,null
                ,null);


        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{

                String username = cursor.getString(cursor.getColumnIndex(DataBaseHelper.USERNAME));
                String passwo = cursor.getString(cursor.getColumnIndex(DataBaseHelper.USERPASSWORD));

                if(user.equals(username) && pass.equals(passwo)){

                     Id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LOGIN_ID));

                    good = true;
                    break;
                }
                else {
                    good = false;
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();
        return good;
    }
    public  ArrayList<LoginPojoClass> ShowUserAndPassByPhoneNumber(String phone){

        this.Open();
        boolean good = false;
        String username;
        String pass;
        ArrayList<LoginPojoClass> projoClasses = new ArrayList<>();

        String[] projection = {DataBaseHelper.USERPHONENUMBER};
        String  selection = DataBaseHelper.USERPHONENUMBER +" =?";
        String[] selectionargs = {phone};

        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.LOGIN_TABLE
                ,null
                ,selection
                ,selectionargs
                ,null
                ,null
                ,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{

                username = cursor.getString(cursor.getColumnIndex(DataBaseHelper.USERNAME));
                pass =    cursor.getString(cursor.getColumnIndex(DataBaseHelper.USERPASSWORD));

                    LoginPojoClass loginPojoClass = new LoginPojoClass(username,pass);
                    projoClasses.add(loginPojoClass);



            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();

        return projoClasses;
    }

  /*  public  ArrayList<LoginPojoClass> getailllogin(){

        this.Open();
        ArrayList<LoginPojoClass> projoClasses = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.LOGIN_TABLE,null,null,
                null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                int Id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LOGIN_ID));
                String user = cursor.getString(cursor.getColumnIndex(DataBaseHelper.USERNAME));
                String pass = cursor.getString(cursor.getColumnIndex(DataBaseHelper.PASSWORD));

                LoginPojoClass projoClass = new LoginPojoClass(Id,user,pass);
                projoClasses.add(projoClass);

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();
        return projoClasses;*/
    }


    ///////////////////////////////////    Progress bAr///////////////////////////////////
   /* public Double ProgressBar(int IId){

        this.Open();
        ArrayList<ProjoClDetailsTest> projoClasses = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.DETAILS_TABLE,null,DataBaseHelper.INFOFOREINFID+"= "+IId,
                null,null,null,null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{

                double salary = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.SALARY));


               SalaryPro += salary;

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();
        return SalaryPro;
    }*/


