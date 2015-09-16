package com.example.databasedemo2;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class MainActivity extends Activity {

    private EditText editName;
	private EditText editAddress;
	private EditText editAge;
	private EditText editRecordId;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editName = (EditText) findViewById(R.id.editName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editAge = (EditText) findViewById(R.id.editAge);
        editRecordId = (EditText) findViewById(R.id.editRecordId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    		if (item.getTitle().equals("Insert")) {
    			insertData();
    		} else if (item.getTitle().equals("Select")) {
    			selectData();
    		} else if (item.getTitle().equals("Delete")) {
    			deleteData();
    		} else if (item.getTitle().equals("Update")) {
    			updateData();
    		}
    		return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Insert");
        menu.add("Select");
        menu.add("Delete");
        menu.add("Update");
        return true;
    }
    
    private void insertData() {
    		DBHelper helper = new DBHelper(this);
    		SQLiteDatabase db = helper.getWritableDatabase();
    		
    		ContentValues values = new ContentValues();
    		values.put("studentName", editName.getText().toString());
    		values.put("address", editAddress.getText().toString());
    		
    		int age = Integer.parseInt(editAge.getText().toString());
    		values.put("age", age);
    		
    		// value of rowid
    		long recordId = db.insert("Students", null, values);
    		
    		Toast.makeText(this, "Data inserted with recordId = " + recordId, Toast.LENGTH_SHORT).show();
    		
    		db.close();
    }
    
    private void selectData() {
    		// select studentId, studentName, address, age from students;
    	
    		DBHelper helper = new DBHelper(this);
    		SQLiteDatabase db = helper.getReadableDatabase();
    		
    		// String columns[] = new String[] {"studentId", "studentName", "address", "age"};
    		String columns[] = {"studentId", "studentName", "address", "age"};
    		
    		Cursor cursor = db.query("Students", columns, null, null, null, null, null);
    		
    		// there is some record availabe in the cursor 
    		if (!cursor.isAfterLast()) {
    			cursor.moveToFirst();
    			
    			while (!cursor.isAfterLast()) {
    				int studentId = cursor.getInt(0);
    				String studentName = cursor.getString(1);
    				String address = cursor.getString(2);
    				int studentAge = cursor.getInt(3);

    				Log.e("Student info", "Id:" + studentId + ", Name:" + studentName + ", address:" + address + ", age: " + studentAge);
    				
    				cursor.moveToNext();
    			}
    		}
    		
    		cursor.close();
    		
    		//   cursor with some records
    		//  cursor.isAfterLast() == false
    		// ---------------------------------------------------
    		// |  studentId  |   studentName  |  address  | age  |
    		// ---------------------------------------------------
    		// |     1       |  student1      |   pune    |  20  |
    		// ---------------------------------------------------
    		// |     2       |  student2      |   nashik  |  21  |
// last // ---------------------------------------------------
//===>  // |     3       |   student3     |   Mumbai  |   25 |
    		// ---------------------------------------------------
    		//                   after last row 
    		// ---------------------------------------------------
    		
    		
    		// cursor with empty table (no records)
    		// cursor.isAfterLast() == true
    		// ---------------------------------------------------
    		// |  studentId  |   studentName  |  address  | age  |
    		// ---------------------------------------------------
 // ==> //                   after last row 
        // ---------------------------------------------------
    		
    		
    		//   cursor with some records
    		//   cursor.moveToFirst();
    		// ---------------------------------------------------
    		// |  studentId  |   studentName  |  address  | age  |
    		// ---------------------------------------------------
 //===> // |     1       |  student1      |   pune    |  20  |
    		// ---------------------------------------------------
    		// |     2       |  student2      |   nashik  |  21  |
        // ---------------------------------------------------
        // |     3       |   student3     |   Mumbai  |   25 |
    		// ---------------------------------------------------
    		//                   after last row 
    		// ---------------------------------------------------

    		
    		db.close();
    }
    
    private void deleteData() {
    		DBHelper helper = new DBHelper(this);
    		SQLiteDatabase db = helper.getWritableDatabase();
    		
    		// delete from students where studentId = ? and studentName = ?;
    		// String whereArgs [] = new String[] {"3", "student3"};
    		
    		String whereArgs [] = new String[] {editRecordId.getText().toString()}; 
    		db.delete("students", "studentId = ?", whereArgs);
    		
    		db.close();
    }
    
    private void updateData() {
    		// update students set studentName = 'test' where studentId = 1
    	
    		DBHelper helper = new DBHelper(this);
    		SQLiteDatabase db = helper.getWritableDatabase();
    		
    		ContentValues values = new ContentValues();
    		values.put("studentName", "test");
    		
    		String whereArgs [] = new String[] {editRecordId.getText().toString()}; 
    		db.update("Students", values, "studentId = ?", whereArgs);
    		
    		db.close();
    }
    
}
