/**
 * Gretar Ævarsson
 * gretar80@gmail.com
 * © 2016
 */

package com.example.s198586_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{

    // headers
    private static final String TABELLNAVN = "Kontakter";
    private static final String KEY_ID = "_ID";       // INT PK
    private static final String KEY_NAME = "Navn";    // TEXT
    private static final String KEY_TLF = "Tlf";      // TEXT
    private static final String KEY_DAG = "Dag";      // INT
    private static final String KEY_MANED = "Maned";  // INT
    private static final String KEY_AR = "Ar";        // INT
    private static final String KEY_SMS = "Sms";      // INT

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAVN = "DB_Kontakter";

    // konstruktors
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAVN, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String lagTabell = "CREATE TABLE " + TABELLNAVN + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                 KEY_NAME + " TEXT, " + KEY_TLF + " TEXT, " + KEY_DAG + " INTEGER, " +
                 KEY_MANED + " INTEGER, " +  KEY_AR + " INTEGER, " + KEY_SMS + " INTEGER" + ")";
        db.execSQL(lagTabell);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS " + TABELLNAVN);
        }
        catch (Exception e){
            Log.d("DATABASE","onUpgrade feil "+ e.toString());
        }
    }

    // legg til kontakt
    public boolean leggTilKontakt(Kontakt kontakt){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();     // nøkkel-verdi par lagres her

            values.put(KEY_NAME, kontakt.getNavn());
            values.put(KEY_TLF, kontakt.getTelefonnr());
            values.put(KEY_DAG, kontakt.getDag());
            values.put(KEY_MANED, kontakt.getManed());
            values.put(KEY_AR, kontakt.getAr());
            values.put(KEY_SMS, kontakt.isSendSMS() ? 1 : 0);

            db.insert(TABELLNAVN, null, values);

            db.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // finn alle kontakter
    public List<Kontakt> finnAlleKontakter(){
        List<Kontakt> kontaktList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABELLNAVN;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Kontakt kontakt = new Kontakt();
                    kontakt.set_ID(Integer.parseInt(cursor.getString(0)));
                    kontakt.setNavn(cursor.getString(1));
                    kontakt.setTelefonnr(cursor.getString(2));
                    kontakt.setDag(Integer.parseInt(cursor.getString(3)));
                    kontakt.setManed(Integer.parseInt(cursor.getString(4)));
                    kontakt.setAr(Integer.parseInt(cursor.getString(5)));
                    kontakt.setSendSMS(Integer.parseInt(cursor.getString(6)) == 0 ? false : true);
                    kontaktList.add(kontakt);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kontaktList;
        }
        catch (Exception e){
            Log.d("DATABASE","Feil "+ e.toString());
            return null;
        }
    }

    // oppdatere kontakt
    public int oppdaterKontakt(Kontakt kontakt){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, kontakt.getNavn());
            values.put(KEY_TLF, kontakt.getTelefonnr());
            values.put(KEY_DAG, kontakt.getDag());
            values.put(KEY_MANED, kontakt.getManed());
            values.put(KEY_AR, kontakt.getAr());
            if (kontakt.isSendSMS())
                values.put(KEY_SMS, 1);
            else
                values.put(KEY_SMS, 0);

            int endret = db.update(TABELLNAVN, values, KEY_ID + "=?", new String[]{String.valueOf(kontakt.get_ID())});
            db.close();
            return endret;
        }
        catch (Exception e){
            Log.d("DATABASE","Feil "+ e.toString());
            return -1;
        }
    }

    // slette kontakt
    public void slettKontakt(Kontakt kontakt){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABELLNAVN, KEY_ID + "=?", new String[]{String.valueOf(kontakt.get_ID())});
            db.close();
        }
        catch (Exception e){
            Log.d("DATABASE","Feil "+ e.toString());
        }
    }

    // finn kontakt
    public Kontakt finnKontakt(int id){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TABELLNAVN, new String[]{KEY_ID, KEY_NAME, KEY_TLF, KEY_DAG, KEY_MANED, KEY_AR, KEY_SMS}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            Kontakt kontakt = new Kontakt(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)) == 0 ? false : true);
            cursor.close();
            db.close();
            return kontakt;
        }
        catch (Exception e){
            Log.d("DATABASE","Feil "+ e.toString());
            return null;
        }
    }

    public List<Kontakt> hentAlleSomHarBursdag(String maned, String dag){
        List<Kontakt> kontaktList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELLNAVN + " WHERE " + KEY_MANED + " = " + maned + " AND " + KEY_DAG + " = " + dag;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {
                    Kontakt kontakt = new Kontakt();
                    kontakt.set_ID(Integer.parseInt(cursor.getString(0)));
                    kontakt.setNavn(cursor.getString(1));
                    kontakt.setTelefonnr(cursor.getString(2));
                    kontakt.setDag(Integer.parseInt(cursor.getString(3)));
                    kontakt.setManed(Integer.parseInt(cursor.getString(4)));
                    kontakt.setAr(Integer.parseInt(cursor.getString(5)));
                    kontakt.setSendSMS(Integer.parseInt(cursor.getString(6)) == 0 ? false : true);
                    kontaktList.add(kontakt);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return kontaktList;
        }
        catch (Exception e){
            Log.d("DATABASE","Feil "+ e.getMessage());
            return null;
        }
    }
}
