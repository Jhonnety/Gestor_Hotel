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

public class contratos extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button boton;
    EditText edtNumeroContrato,edtFecha,edtApartamento,edtUsuario;

    RequestQueue requestQueue;

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

        requestQueue=Volley.newRequestQueue(contratos.this);
        spinner.setOnItemSelectedListener(this);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=v.getId();
                String OperacionContrato=spinner.getSelectedItem().toString();
                String  numContrato=edtNumeroContrato.getText().toString();
                String  fechaContrato=edtFecha.getText().toString();
                String  numeroApt=edtApartamento.getText().toString();
                String  dueno=edtUsuario.getText().toString();
                if (id==R.id.boton1){
                    if (OperacionContrato.equals("Ingresar")){
                        crearContrato(numContrato,fechaContrato,numeroApt,dueno);
                    }
                    if(OperacionContrato.equals("Buscar")){
                        buscarContrato(numContrato);
                    }
                    if(OperacionContrato.equals("Borrar")){
                        eliminarContrato(numContrato);
                    }
                    if(OperacionContrato.equals("Editar")){
                        modificarContrato(numContrato,fechaContrato,numeroApt,dueno);
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
    private void LimpiarCampos(){
        edtNumeroContrato.setText("");
        edtFecha.setText("");
        edtApartamento.setText("");;
        edtUsuario.setText("");
    }

    private void crearContrato(final String numContrato,final String fecha,final String numApt,final String dueno){
        RequestQueue crear;
        String url="http://192.168.39.229:81/unidad_jhon/saveContrato.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(contratos.this,"Contrato Guardado!",Toast.LENGTH_SHORT).show();
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
                params.put(  "codContrato",numContrato);
                params.put(  "fechaVigencia",fecha);
                params.put( "numApt",numApt);
                params.put( "arriendatario",dueno);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



    private void buscarContrato(String numContrato){
        String url="http://192.168.39.229:81/unidad_jhon/fetchContrato.php?codContrato="+numContrato;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String fecha, numApt, dueno;
                        try {
                            fecha = response.getString("Fecha_vigencia");
                            numApt = response.getString("Num_apto");
                            dueno = response.getString("Arriendatario");
                            edtFecha.setText(fecha);
                            edtApartamento.setText(numApt);
                            edtUsuario.setText(dueno);
                            edtFecha.setVisibility(View.VISIBLE);
                            edtApartamento.setVisibility(View.VISIBLE);
                            edtUsuario.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(contratos.this,"Excepcion!",Toast.LENGTH_SHORT).show();
                            edtUsuario.setVisibility(View.VISIBLE);
                            edtUsuario.setText(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(contratos.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void eliminarContrato(final String numero){
        String url="http://192.168.39.229:81/unidad_jhon/deleteContrato.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(contratos.this,"Aparamento"+numero+" eliminado",Toast.LENGTH_SHORT).show();
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
                params.put(  "codContrato",numero);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void modificarContrato(final String numContrato,final String fecha,final String numApt,final String dueno){
        String url="http://192.168.39.229:81/unidad_jhon/updateContrato.php";
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(contratos.this,"Se ha modificado el contrato "+numContrato,Toast.LENGTH_SHORT).show();
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
                params.put(  "codContrato",numContrato);
                params.put(  "fechaVigencia",fecha);
                params.put( "numApt",numApt);
                params.put( "arriendatario",dueno);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}