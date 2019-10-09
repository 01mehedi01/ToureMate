package com.example.user.touremate.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 3/6/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
   // PRAGMA foreign_keys = ON;
   // <sqlite> PRAGMA foreign_keys = ON;
    public static final String DataBaseName = "Data";
    public static final int DataBaseVersion = 11;
/////////////////////////////////////////////////// 1st Table //////////////////////////////////////////

    public static final String EVENT = "Info";
    public static final String EVENT_ID = "_eventid";
    public static final String EVENT_NAME = "event";
    public static final String EVENT_START = "start";
    public static final String EVENT_RETURN = "return";
    public static final String EVENT_BUDGET = "budget";
    public static final String USER_LOGINID = "u_id";

    ///////////////////////////////////  2nd Table ////////////////////////////////////////////////////

    public  static final String COST_TABLE = "forign";
    public  static final String COSTID = "_costid";
    public  static final String COST_NAME = "name";
    public  static final String COST_AMOUNT = "amount";
    public  static final String COST_DATE  = "date";
    public  static final String COST_DETAILS  = "details";
    public  static final String COST_FOREIGNBY_CREATE_TOUR  = "cretetour";

    ///////////////////////////////3 rd TAble //////////////////////////////////

    public  static final String LOGIN_TABLE = "login";
    public  static final String LOGIN_ID = "_loginid";
    public  static final String USERNAME = "username";
    public  static final String USERPHONENUMBER = "userphone";
    public  static final String USERPASSWORD = "password";


    public static final String CREATE_TABLE_LOGIN_TABLE = " create table " +LOGIN_TABLE + " ( "+
            LOGIN_ID + " integer primary key, "+
            USERNAME +" text, "+
            USERPASSWORD + " text, "+
            USERPHONENUMBER + " text );" ;

    public static final String CREATE_TABLE_EVENT = " create table " +EVENT + " ( "+
            EVENT_ID + " integer primary key, "+
            EVENT_NAME +" text, "+
            EVENT_START + " text ,"+
            EVENT_RETURN + " text ,"+
            EVENT_BUDGET + " real ,"+
            USER_LOGINID + " integer  ,"
            + " FOREIGN KEY("+USER_LOGINID +") REFERENCES "+LOGIN_TABLE+"("+LOGIN_ID+"));";

    public static final String CREATE_TABLE_COST_TABLE = " create table " +COST_TABLE + " ( "+
            COSTID + " integer primary key, "+
            COST_NAME +" text, "+
            COST_DATE + " text ,"+
            COST_DETAILS + " text ,"+
            COST_AMOUNT + " real  ,"  +
            COST_FOREIGNBY_CREATE_TOUR + " integer ,"
            + " FOREIGN KEY("+COST_FOREIGNBY_CREATE_TOUR +") REFERENCES "+EVENT+"("+EVENT_ID+"));";

    public DataBaseHelper(Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_COST_TABLE);
        db.execSQL(CREATE_TABLE_LOGIN_TABLE);

    }


    @Override
    public void onOpen(SQLiteDatabase db) {

        super.onOpen(db);
       // db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL(" drop table if exists "+COST_TABLE);
      db.execSQL(" drop table if exists "+EVENT);
      db.execSQL(" drop table if exists "+LOGIN_TABLE);
        onCreate(db);
    }
}
