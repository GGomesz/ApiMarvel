package com.example.marvelzinha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1 ;
    private static final String DATABASE_NAME = "MV_BD";
    private static final String DICTONARY_TABLE = "Character";
    private static final String DEF_ID = "id";
    private static final String DESCRIPTION = "description";

    private static final String[] COLUNAS = {DEF_ID, DICTONARY_TABLE, DATABASE_NAME};

    public DataBase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE Character ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "description TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Character");
        this.onCreate(sqLiteDatabase);

    }

    public void addData(Model model) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, model.getDescription());
        sqLiteDatabase.insert(DICTONARY_TABLE, null, values);
        sqLiteDatabase.close();
    }

    public List<Model> getEveryone(){
        List<Model> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + DICTONARY_TABLE ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int customerID = cursor.getInt(0);
                String customerDef =  cursor.getString(1);

                Model newCustomer = new Model(customerID, customerDef);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
