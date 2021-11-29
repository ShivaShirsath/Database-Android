package ss.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBH extends SQLiteOpenHelper {

	private static final String	
		DB_NAME 	= "db",
		TABLE_NAME	= "Table",
		ID_COL		= "id",
		NAME_COL	= "name";
	
	private static final int DB_VERSION	= 0;

	public DBH(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
			"CREATE TABLE " + TABLE_NAME + " ("
				   + ID_COL + " " + getType((Object)ID_COL) + " PRIMARY KEY AUTOINCREMENT, "
				   + NAME_COL + " " + getType((Object)NAME_COL)
			+ ")"
		);
	}
	
	public void addNewCourse(String courseName, String courseDuration, String courseDescription, String courseTracks) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME_COL, courseName);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public static String getType(Object obj){
		return obj.getClass().getName().contains("String") 
			? "TEXT"
			: obj.getClass().getName().substring(obj.getClass().getName().lastIndexOf(".")).toUpperCase();
	}
}

