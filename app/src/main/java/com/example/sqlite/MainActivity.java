package com.example.sqlite;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText name, contact, email;
    Button insert, update, delete, view;

    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        contact= findViewById(R.id.contact);
        email=findViewById(R.id.email);

        insert=findViewById(R.id.btnInsert);
        update=findViewById(R.id.btnUpdate);
        delete= findViewById(R.id.btnDelete);
        view= findViewById(R.id.btnView);


        DB= new DBHelper(this);

        String regex1 = "^[0-9]{10}";
        Pattern p1 = Pattern.compile(regex1);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String emailTXT = email.getText().toString();

                Matcher m1 = p1.matcher(contactTXT);



                if(nameTXT.isEmpty()){
                    name.setError("Field can't be empty");
                    name.requestFocus();
                    return;
                }
                if (contactTXT.isEmpty()) {
                    contact.setError("Field can't be empty");
                    contact.requestFocus();
                    return;
                }
                if(!m1.matches()) {
                    contact.setError("Enter valid Contact number");
                    contact.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(emailTXT).matches()){
                    email.setError("Enter valid Email");
                    email.requestFocus();
                    return;
                }


                if (emailTXT.isEmpty()) {
                    email.setError("Field can't be empty");
                    email.requestFocus();
                    return;
                }

                Boolean checkinsertdata=DB.intertuserdata(emailTXT,nameTXT,contactTXT);
                if (checkinsertdata==true){
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    Toast.makeText(MainActivity.this,"New Entry Insert",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"No Data Insert",Toast.LENGTH_SHORT).show();

                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String emailTXT = email.getText().toString();

                Matcher m1 = p1.matcher(contactTXT);

                if(nameTXT.isEmpty()){
                    name.setError("Field can't be empty");
                    name.requestFocus();
                    return;
                }
                if (contactTXT.isEmpty()) {
                    contact.setError("Field can't be empty");
                    contact.requestFocus();
                    return;
                }
                if(!m1.matches()) {
                    contact.setError("Enter valid Contact number");
                    contact.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(emailTXT).matches()){
                    email.setError("Enter valid Email");
                    email.requestFocus();
                    return;
                }


                if (emailTXT.isEmpty()) {
                    email.setError("Field can't be empty");
                    email.requestFocus();
                    return;
                }

                Boolean checkupdatedata=DB.updateuserdata(emailTXT,nameTXT,contactTXT);
                if (checkupdatedata==true){
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    Toast.makeText(MainActivity.this,"Data updated",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data not updated",Toast.LENGTH_SHORT).show();

                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTXT = email.getText().toString();

                Boolean checkdeletedata=DB.deleteuserdata(emailTXT);
                if (checkdeletedata==true){
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_SHORT).show();

                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Email\t\t: "+res.getString(0)+"\n");
                    buffer.append("Name\t\t: "+res.getString(1)+"\n");
                    buffer.append("Contact\t: "+res.getString(2)+"\n\n");


                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });


    }
}