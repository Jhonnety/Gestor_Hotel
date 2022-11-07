package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class apartamento extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button boton;
    EditText edtNumeroApartamento,edtTorre,edtCCDueño,edtNumHabitaciones,edtPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartamento);
        boton=(Button)findViewById(R.id.boton1);

        edtNumeroApartamento=findViewById(R.id.edtNumeroApartamento);
        edtTorre=findViewById(R.id.edtTorre);
        edtCCDueño=findViewById(R.id.edtCCDueño);
        edtNumHabitaciones =findViewById(R.id.edtNumHabitaciones);
        edtPrecio=findViewById(R.id.edtPrecio);

        Spinner spinner= findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
        if(text.equals("Buscar"))
        {
            edtTorre.setVisibility(View.INVISIBLE);
            edtCCDueño.setVisibility(View.INVISIBLE);
            edtNumHabitaciones.setVisibility(View.INVISIBLE);
            edtPrecio.setVisibility(View.INVISIBLE);
            boton.setText(text);
        }
        else if(text.equals("Ingresar"))
        {
            edtTorre.setVisibility(View.VISIBLE);
            edtCCDueño.setVisibility(View.VISIBLE);
            edtNumHabitaciones.setVisibility(View.VISIBLE);
            edtPrecio.setVisibility(View.VISIBLE);
            boton.setText(text);
        }
        else if(text.equals("Editar"))
        {
            edtTorre.setVisibility(View.VISIBLE);
            edtCCDueño.setVisibility(View.VISIBLE);
            edtNumHabitaciones.setVisibility(View.VISIBLE);
            edtPrecio.setVisibility(View.VISIBLE);
            boton.setText(text);
        }
        else//Borrar
        {
            edtTorre.setVisibility(View.INVISIBLE);
            edtCCDueño.setVisibility(View.INVISIBLE);
            edtNumHabitaciones.setVisibility(View.INVISIBLE);
            edtPrecio.setVisibility(View.INVISIBLE);
            boton.setText(text);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onClick(view v){
        int id= R.
    }
}