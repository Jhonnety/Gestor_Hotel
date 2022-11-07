package com.example.hotel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class apartamento extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button boton;
    EditText edtNumeroApartamento,edtTorre,edtCCDueño,edtNumHabitaciones,edtPrecio;
    Spinner spinner;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartamento);
        boton=(Button)findViewById(R.id.boton1);
        edtNumeroApartamento = findViewById(R.id.edtNumeroApartamento);
        edtTorre=findViewById(R.id.edtTorre);
        edtCCDueño=findViewById(R.id.edtCCDueño);
        edtNumHabitaciones =findViewById(R.id.edtNumHabitaciones);
        edtPrecio=findViewById(R.id.edtPrecio);

        spinner= findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        requestQueue=Volley.newRequestQueue(apartamento.this);
        spinner.setOnItemSelectedListener(this);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=v.getId();
                String OperacionApt=spinner.getSelectedItem().toString();
                String  numApt=edtNumeroApartamento.getText().toString();
                String  torreApt=edtTorre.getText().toString();
                String  duenoApt=edtCCDueño.getText().toString();
                String  cantHabitaciones=edtNumHabitaciones.getText().toString();
                String  PrecioApt=edtPrecio.getText().toString();
                if (id==R.id.boton1){
                    if (OperacionApt.equals("Ingresar")){
                        crearApartamento(numApt,torreApt,duenoApt,cantHabitaciones,PrecioApt);
                    }
                    if(OperacionApt.equals("Buscar")){
                        buscarApartamento(numApt);
                    }
                    if(OperacionApt.equals("Borrar")){
                        eliminarApartamento(numApt);
                    }
                    if(OperacionApt.equals("Editar")){
                        modificarApartamento(numApt,torreApt,duenoApt,cantHabitaciones,PrecioApt);
                    }
                }
            }
        });

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
    private void LimpiarCampos(){
        edtTorre.setText("");
        edtCCDueño.setText("");
        edtNumHabitaciones.setText("");;
        edtPrecio.setText("");
        edtNumeroApartamento.setText("");
    }
    private void crearApartamento(final String numero,final String torre,final String dueno,final String canthabitaciones,final String precio){
        RequestQueue crear;
        crear=Volley.newRequestQueue(apartamento.this);
        String url="http://192.168.39.229:81/unidad_jhon/saveApt.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(apartamento.this,"Guardado exitosamente!",Toast.LENGTH_SHORT).show();
                        LimpiarCampos();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put(  "numero",numero);
                params.put(  "torre",torre);
                params.put( "dueno",dueno);
                params.put( "canthabitaciones",canthabitaciones);
                params.put( "precio",precio);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void buscarApartamento(String numero){
        String url="http://192.168.39.229:81/unidad_jhon/fetchApt.php?numero="+numero;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String torre, dueno, habitaciones, precio;
                        try {
                            torre = response.getString("Torre");
                            dueno = response.getString("Dueño");
                            habitaciones = response.getString("Num_habitaciones");
                            precio = response.getString("Precio");
                            edtTorre.setText(torre);
                            edtCCDueño.setText(dueno);
                            edtNumHabitaciones.setText(habitaciones);
                            edtPrecio.setText(precio);
                            edtTorre.setVisibility(View.VISIBLE);
                            edtCCDueño.setVisibility(View.VISIBLE);
                            edtNumHabitaciones.setVisibility(View.VISIBLE);
                            edtPrecio.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            Toast.makeText(apartamento.this, "Excepcion", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(apartamento.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

        );
        requestQueue.add(request);
        }

    private void eliminarApartamento(final String numero){
        String url="http://192.168.39.229:81/unidad_jhon/deleteApt.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(apartamento.this,"Aparamento"+numero+" eliminado",Toast.LENGTH_SHORT).show();
                        LimpiarCampos();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put(  "numero",numero);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void modificarApartamento(final String numero,final String torre,final String dueno,final String canthabitaciones,final String precio){
        String url="http://192.168.39.229:81/unidad_jhon/updateApt.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(apartamento.this,"Se ha modificado el apartamento "+numero,Toast.LENGTH_SHORT).show();
                        LimpiarCampos();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put(  "numero",numero);
                params.put(  "torre",torre);
                params.put( "dueno",dueno);
                params.put( "canthabitaciones",canthabitaciones);
                params.put( "precio",precio);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    }
