package com.example.raul.combuyappv20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.raul.combuyappv20.Usuario.LoginActivity;
import com.example.raul.combuyappv20.Usuario.RegisterActivity;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }

    public void nextActivityLog(View view) {
        Intent intent = new Intent(EmptyActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void nextActivityRegister(View view) {
        Intent intent = new Intent(EmptyActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void nextActivityUserInvite(View view) {
        Intent intent = new Intent(EmptyActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
