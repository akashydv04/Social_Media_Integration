package com.example.cms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cms.database.DatabaseHelper;
import com.example.cms.model.EmployeeModel;

import java.util.ArrayList;

import static com.example.cms.database.DatabaseHelper.TABLE_NAME;

public class DoTransact extends AppCompatActivity {

    TextView id_txt, transferTo;
    EditText emp_name, emp_email, emp_phone, available_credit, transfer_credit;
    DatabaseHelper databaseHelper;
    Spinner emp_list;
    ArrayList<EmployeeModel> list_emp;
    SQLiteDatabase db;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_transact);

        id_txt = findViewById(R.id.emp_id);
        emp_name = findViewById(R.id.emp_name);
        emp_email = findViewById(R.id.emp_email);
        emp_phone = findViewById(R.id.emp_phone);
        available_credit = findViewById(R.id.available_credit);
        emp_list = findViewById(R.id.empl_id);
        transferTo = findViewById(R.id.transfer_to);
        transfer_credit = findViewById(R.id.trans_credit);

        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        id_txt.setText(id);
        list_emp = new ArrayList<>();

        getAllDetails();
        loadSpinner();

        emp_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                db = databaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where id="+id,null);
                if (cursor.moveToFirst()){
                    String name = cursor.getString(1);
                 