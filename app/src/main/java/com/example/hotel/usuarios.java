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

public class usuarios extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button boton;
    EditText edtNumeroCedula,edtNombres,edtApellidos,edtNumeroTelefono,edtTipopersona;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        boton=(Button)findViewById(R.id.boton1);
        edtNumeroCedula = findViewById(R.id.edtNumeroCedula);
        edtNombres=findViewById(R.id.edtNombres);
        edtApellidos=findViewById(R.id.edtApellidos);
        edtNumeroTelefono =findViewById(R.id.edtNumeroTelefono);
        edtTipopersona=findViewById(R.id.edtTipopersona);

        Spinner spinner= findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        requestQueue=Volley.newRequestQueue(usuarios.this);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=v.getId();
                String OperacionUsuario=spinner.getSelectedItem().toString();
                String  cedula =edtNumeroCedula.getText().toString();
                String  nombre=edtNombres.getText().toString();
                String  apellido=edtApellidos.getText().toString();
                String  telefono=edtNumeroTelefono.getText().toString();
                String  tipo=edtTipopersona.getText().toString();
                if (id==R.id.boton1){
                    if (OperacionUsuario.equals("Ingresar")){
                        crearPersona(cedula,nombre,apellido,telefono,tipo);
                    }
                    if(OperacionUsuario.equals("Buscar")){
                        buscarPersona(cedula);
                    }
                    if(OperacionUsuario.equals("Borrar")){
                        eliminarPersona(cedula);
                    }
                    if(OperacionUsuario.equals("Editar")){
                        modificarPersona(cedula,nombre,apellido,telefono,tipo);
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
            edtNombres.setVisibility(View.INVISIBLE);
            edtApellidos.setVisibility(View.INVISIBLE);
            edtNumeroTelefono.setVisibility(View.INVISIBLE);
            edtTipopersona.setVisibility(View.INVISIBLE);
            boton.setText(text);
        }
        else if(text.equals("Ingresar"))
        {
            edtNombres.setVisibility(View.VISIBLE);
            edtApellidos.setVisibility(View.VISIBLE);
            edtNumeroTelefono.setVisibility(View.VISIBLE);
            edtTipopersona.setVisibility(View.VISIBLE);
            boton.setText(text);
        }
        else if(text.equals("Editar"))
        {
            edtNombres.setVisibility(View.VISIBLE);
            edtApellidos.setVisibility(View.VISIBLE);
            edtNumeroTelefono.setVisibility(View.VISIBLE);
            edtTipopersona.setVisibility(View.VISIBLE);
            boton.setText(text);
        }
        else//Borrar
        {
            edtNombres.setVisibility(View.INVISIBLE);
            edtApellidos.setVisibility(View.INVISIBLE);
            edtNumeroTelefono.setVisibility(View.INVISIBLE);
            edtTipopersona.setVisibility(View.INVISIBLE);
            boton.setText(text);
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void LimpiarCampos(){
        edtNumeroCedula.setText("");
        edtNombres.setText("");
        edtApellidos.setText("");;
        edtNumeroTelefono.setText("");
        edtTipopersona.setText("");
    }
    private void crearPersona(final String cedula,final String nombre,final String apellido,final String telefono,final String tipo) {
        RequestQueue crear;
        String url = "http://192.168.39.229:81/unidad_jhon/savePersona.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(usuarios.this, "Persona " + cedula + " Guardada", Toast.LENGTH_SHORT).show();
                        LimpiarCampos();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cedula", cedula);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("telefono", telefono);
                params.put("tipo", tipo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void buscarPersona(String cedula){
        String url="http://192.168.39.229:81/unidad_jhon/fetchPersona.php?cedula="+cedula;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       String cedula,  nombre,  apellido,  telefono,  tipo;
                       try{
                           cedula = response.getString("Cedula");
                           nombre = response.getString("Nombre");
                           apellido = response.getString("Apellido");
                           telefono = response.getString("telefono");
                           tipo = response.getString("Tipo");
                           edtNombres.setText(nombre);
                           edtApellidos.setText(apellido);
                           edtNumeroTelefono.setText(telefono);
                           edtTipopersona.setText(tipo);
                           edtNombres.setVisibility(View.VISIBLE);
                           edtApellidos.setVisibility(View.VISIBLE);
                           edtNumeroTelefono.setVisibility(View.VISIBLE);
                           edtTipopersona.setVisibility(View.VISIBLE);
                       }
                       catch(JSONException e){
                           e.printStackTrace();;
                       }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }

    private void eliminarPersona(final String cedula){
        String url="http://192.168.39.229:81/unidad_jhon/deletePersona.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(usuarios.this,"Usuario "+cedula+" eliminado",Toast.LENGTH_SHORT).show();
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
                params.put(  "cedula",cedula);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void modificarPersona(final String cedula,final String nombre,final String apellido,final String telefono,final String tipo){
        String url="http://192.168.39.229:81/unidad_jhon/updatePersona.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(usuarios.this,"Se ha modificado la persona "+cedula,Toast.LENGTH_SHORT).show();
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
                params.put("cedula", cedula);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("telefono", telefono);
                params.put("tipo", tipo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }








}


