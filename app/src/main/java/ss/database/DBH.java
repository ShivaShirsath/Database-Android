package ss.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBH extends SQLiteOpenHelper {

	private static final String[]
		DB 	    = {"dbName"},
		TABLE	= {"TableName"},
		COLUMN	= {"id", "name"};
	
	private static final int DB_VERSION	= 0;

	public DBH(Context context) {
		super(context, DB[0], null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
			"CREATE TABLE " + TABLE[0] + " ("
				   + COLUMN[0] + " " + getType((Object)COLUMN[0]) + " PRIMARY KEY AUTOINCREMENT, "
				   + COLUMN[1] + " " + getType((Object)COLUMN[1])
			+ ")"
		);
	}
	
	public void addNewRecord(String[] values) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		for(int i=0; i < values.length; i++){
			contentValues.put(COLUMN[i], values[i]);
		}
		db.insert(TABLE[0], null, contentValues);
		db.close();
	}
	public void deleteRecordHavingID(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE[0],COLUMN[0]+ " = ?", new String[]{"" + id});
		db.close();
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE[0]);
		onCreate(db);
	}
	
	public static String getType(Object obj){
		return obj.getClass().getName().contains("String") 
			? "TEXT"
			: obj.getClass().getName().substring(obj.getClass().getName().lastIndexOf(".")).toUpperCase();
	}
}

