package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
RelativeLayout apartamentos, contratos, torres, usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        apartamentos=findViewById(R.id.apartamentos);
        contratos=findViewById(R.id.contratos);
        torres=findViewById(R.id.torres);
        usuarios=findViewById(R.id.usuarios);
        apartamentos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent t = new Intent(getApplicationContext(), apartamento.class);
                startActivity(t);
            }

        });
        contratos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent t = new Intent(getApplicationContext(), contratos.class);
                startActivity(t);
            }

        });
        torres.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent t = new Intent(getApplicationContext(), com.example.hotel.torres.class);
                startActivity(t);
            }

        });
        usuarios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent t = new Intent(getApplicationContext(), usuarios.class);
                startActivity(t);
            }

        });

    }
}