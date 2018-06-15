package com.example.raul.combuyappv20.Usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.raul.combuyappv20.EmptyActivity;
import com.example.raul.combuyappv20.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etfirstname, etlastname, etdni, etemail, etuser, etpassword;
    private Button btncreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etfirstname = findViewById(R.id.et_firstname_register);
        etlastname = findViewById(R.id.et_lastname_register);
        etdni = findViewById(R.id.et_dni_register);
        etemail = findViewById(R.id.et_email_register);
        etuser = findViewById(R.id.et_user_register);
        etpassword = findViewById(R.id.et_password_register);

    }

    public void nextActivityLog(View view) {


        Intent intent = new Intent(RegisterActivity.this,EmptyActivity.class);
        startActivity(intent);
    }
}
