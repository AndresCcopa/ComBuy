package com.example.raul.combuyappv20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.raul.combuyappv20.Usuario.LoginActivity;
import com.example.raul.combuyappv20.Usuario.RegisterActivity;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }

    public void nextActivityLog(View view) {
        Toast.makeText(this, "En mantenimiento ", Toast.LENGTH_LONG).show();
    }

    public void nextActivityRegister(View view) {
        Toast.makeText(this, "En mantenimiento ", Toast.LENGTH_LONG).show();
    }

    public void nextActivityUserInvite(View view) {
        Intent intent = new Intent(EmptyActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
