package ss.database;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText Rollno,Name;
	SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Rollno = findViewById(R.id.roll);
		Name = findViewById(R.id.name);

		db = openOrCreateDatabase("dbName", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS TableName(rollno VARCHAR,name VARCHAR);");
	}
	public void onInsert(View view) {
		if (Rollno.getText().toString().trim().length() == 0 ||
			Name.getText().toString().trim().length() == 0) {
			showMessage("Error", "Please enter all values");
		} else {
			db.execSQL(
				"INSERT INTO TableName VALUES(" 
				+ "'" + Rollno.getText() + "',"
				+ "'" + Name.getText()   + "'" 
				+ ")" //;
			);
			showMessage("Success", "Record added");
			clearText();
		}
	}
	public void onDelete(View view) {
		if (Rollno.getText().toString().trim().length() == 0) {
			showMessage("Error", "Please enter Rollno");
		} else {
			Cursor c=db.rawQuery("SELECT * FROM TableName WHERE rollno='" + Rollno.getText() + "'", null);
			if (c.moveToFirst()) {
				db.execSQL("DELETE FROM TableName WHERE rollno='" + Rollno.getText() + "'");
				showMessage("Success", "Record Deleted");
			} else {
				showMessage("Error", "Invalid Rollno");
			}
			clearText();
		}
	}
	public void onUpdate(View view) {
		if (Rollno.getText().toString().trim().length() == 0) {
			showMessage("Error", "Please enter Rollno");
		} else {
			Cursor c=db.rawQuery("SELECT * FROM TableName WHERE rollno='" + Rollno.getText() + "'", null);
			if (c.moveToFirst()) {
				db.execSQL("UPDATE TableName SET name='" + Name.getText() + "' WHERE rollno='" + Rollno.getText() + "'");
				showMessage("Success", "Record Modified");
			} else {
				showMessage("Error", "Invalid Rollno");
			}
			clearText();
		}
	}
	public void onShow(View view) {
		if (Rollno.getText().toString().trim().length() == 0) {
			showMessage("Error", "Please enter Rollno");
		} else {
			Cursor c=db.rawQuery("SELECT * FROM TableName WHERE rollno='" + Rollno.getText() + "'", null);
			if (c.moveToFirst()) {
				Name.setText(c.getString(1));
			} else {
				showMessage("Error", "Invalid Rollno");
				clearText();
			}
		}
	}
	public void onShowAll(View view) {
		Cursor c=db.rawQuery("SELECT * FROM TableName", null);
		if (c.getCount() == 0) {
			showMessage("Error", "No records found");
		} else {
			StringBuffer buffer=new StringBuffer();
			while (c.moveToNext()) {
				buffer.append("Rollno: " + c.getString(0) + "\n");
				buffer.append("Name: " + c.getString(1) + "\n\n");
			}
			showMessage("Details", buffer.toString());
		}
	}
	public void showMessage(String title, String message) {
		new Builder(this).setCancelable(true).setTitle(title).setMessage(message).show();
	}
	public void clearText() {
		Rollno.setText("");
		Name.setText("");
		Rollno.requestFocus();
	}
}
