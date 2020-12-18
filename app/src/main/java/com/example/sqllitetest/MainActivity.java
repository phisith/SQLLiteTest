package com.example.sqllitetest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    DatePickerDialog datePickerDialog;
    EditText edit_Tracking_number, edit_Reference_number, edit_ProvinceId, edit_DistrictId, edit_VillageId, edit_search, edit_Tracker_Date
            ,edit_SubmitDate;
    Button btm_insertdata;
    Button btm_getAlldata;
    Button btm_updatedata;
    Button btm_deletedata;
    Button btm_go;
    Button btm_datepicker;
    Button btm_datepicker2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        edit_Tracking_number = (EditText)findViewById(R.id.edit_Tracking_number);
        edit_Reference_number = (EditText)findViewById(R.id.edit_Reference_number);
        edit_ProvinceId = (EditText)findViewById(R.id.edit_ProvinceId);
        edit_DistrictId = (EditText)findViewById(R.id.edit_DistrictId);
        edit_VillageId = (EditText)findViewById(R.id.edit_VillageId);
        edit_search = (EditText)findViewById(R.id.edit_search);
        edit_Tracker_Date = (EditText)findViewById(R.id.edit_Tracker_Date);
        edit_SubmitDate = (EditText)findViewById(R.id.edit_SubmitDate);
        btm_insertdata = (Button)findViewById(R.id.btm_insertdata);
        btm_getAlldata = (Button)findViewById(R.id.btm_getAlldata);
        btm_updatedata = (Button)findViewById(R.id.btm_updatedata);
        btm_deletedata = (Button)findViewById(R.id.btm_deletedata);
        btm_go = (Button)findViewById(R.id.btm_go);
        btm_datepicker = (Button)findViewById(R.id.datepicker);
        btm_datepicker2 = (Button) findViewById(R.id.datepicker2);
        Search();
        AddData();
        SelectALL();
        Search();
        UpdateData();
        DeleteData();

        btm_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edit_Tracker_Date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btm_datepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edit_SubmitDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    public void DeleteData(){
        btm_deletedata.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = databaseHelper.deleteData(edit_Tracking_number.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void UpdateData(){
        btm_updatedata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = databaseHelper.updateData(edit_Tracking_number.getText().toString(),
                                edit_Reference_number.getText().toString(), edit_ProvinceId.getText().toString(),
                                edit_DistrictId.getText().toString(), edit_VillageId.getText().toString(),
                                edit_Tracker_Date.getText().toString(), edit_SubmitDate.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void AddData(){
        btm_insertdata.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = databaseHelper.insertData(edit_Tracking_number.getText().toString(),
                                edit_Reference_number.getText().toString(), edit_ProvinceId.getText().toString(),
                                edit_DistrictId.getText().toString(), edit_VillageId.getText().toString(),
                                edit_Tracker_Date.getText().toString(), edit_SubmitDate.getText().toString());

                        if(isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void SelectALL(){
        btm_getAlldata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = databaseHelper.SelectAllData();
                        if (res.getCount() == 0){
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Tracking number: "+ res.getString(1)+"\n");
                            buffer.append("Reference number: "+ res.getString(2)+"\n");
                            buffer.append("Province: "+ res.getString(3)+"\n");
                            buffer.append("District: "+ res.getString(4)+"\n");
                            buffer.append("Village: "+ res.getString(5)+"\n");
                            buffer.append("Tracker date: "+ res.getString(6)+"\n");
                            buffer.append("SubmitDate: "+ res.getString(7)+"\n\n");
                        }

                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void Search(){
        btm_go.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = databaseHelper.search(edit_search.getText().toString());
                        if (edit_search.equals("")){
                            Toast.makeText(MainActivity.this, "Data not found", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (res.moveToNext()){
                            edit_Tracking_number.append(res.getString(1));
                            edit_Reference_number.append(res.getString(2));
                            edit_ProvinceId.append(res.getString(3));
                            edit_DistrictId.append(res.getString(4));
                            edit_VillageId.append(res.getString(5));
                            edit_Tracker_Date.append(res.getString(6));
                            edit_SubmitDate.append(res.getString(7));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Data not found", Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );

    }
    effffl






}
