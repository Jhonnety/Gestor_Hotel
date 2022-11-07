package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edtUsuario,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsuario = findViewById(R.id.edtUsuario);
    }

    public void Ingresar(View view)
    {
        if(edtPassword.getText().toString().equals("123")&&edtUsuario.getText().toString().equals("123")){
            Intent t = new Intent(this, Menu.class);
            startActivity(t);
        }

    }


}