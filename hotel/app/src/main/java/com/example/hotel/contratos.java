package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class contratos extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button boton;
    EditText edtNumeroContrato,edtFecha,edtApartamento,edtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratos);

        boton=(Button)findViewById(R.id.boton1);
        Spinner spinner= findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        edtNumeroContrato=findViewById(R.id.edtNumeroContrato);
        edtFecha=findViewById(R.id.edtFecha);
        edtApartamento=findViewById(R.id.edtApartamento);
        edtUsuario =findViewById(R.id.edtUsuario);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
        if(text.equals("Buscar"))
        {
            edtFecha.setVisibility(View.INVISIBLE);
            edtApartamento.setVisibility(View.INVISIBLE);
            edtUsuario.setVisibility(View.INVISIBLE);
            boton.setText(text);
        }
        else if(text.equals("Ingresar"))
        {
            edtFecha.setVisibility(View.VISIBLE);
            edtApartamento.setVisibility(View.VISIBLE);
            edtUsuario.setVisibility(View.VISIBLE);
            boton.setText(text);
        }
        else if(text.equals("Editar"))
        {
            edtFecha.setVisibility(View.VISIBLE);
            edtApartamento.setVisibility(View.VISIBLE);
            edtUsuario.setVisibility(View.VISIBLE);
            boton.setText(text);
            boton.setText(text);
        }
        else//Borrar
        {
            edtFecha.setVisibility(View.INVISIBLE);
            edtApartamento.setVisibility(View.INVISIBLE);
            edtUsuario.setVisibility(View.INVISIBLE);
            boton.setText(text);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}