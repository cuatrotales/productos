package com.example.productos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class formProductos extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText jetId, jetNombre, jetValor;
    CheckBox jcbActivo;
    String Id,
            Nombre,
            Valor,
            Url="http://192.168.20.48:80/webServices/";
    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_productos);
        jetId=findViewById(R.id.etId);
        jetNombre=findViewById(R.id.etNombre);
        jetValor=findViewById(R.id.etValor);
        jcbActivo=findViewById(R.id.cbActivo);

        rq = Volley.newRequestQueue(this);
    }
    boolean buscarId(){
        Url+="select.php?id="+Id;
        jrq = new JsonObjectRequest(Request.Method.GET, Url, null, this, this);
        rq.add(jrq);
        return true;
    }
    public void select(){
        Id = jetId.getText().toString();
        if (Id.isEmpty()){
            Toast.makeText(this, "¿Sin el ID? Ni con el viento de la rosa de guadalupe...", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            buscarId();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Producto registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try{
            jsonObject = jsonArray.getJSONObject(0);
            jetId.setText(jsonObject.optString("id"));
            jetNombre.setText(jsonObject.optString("nombre"));
            jetValor.setText(jsonObject.optString("valor"));
            if (jsonObject.optString("activo").equals("si")){
                jcbActivo.setChecked(true);
            }else{
                jcbActivo.setChecked(false);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "El producto no se encuentra en nuestra base de datos :c", Toast.LENGTH_SHORT).show();
    }

    public void insert(View v){
        Id=jetId.getText().toString();
        Nombre=jetNombre.getText().toString();
        Valor=jetValor.getText().toString();
        if (Id.isEmpty() || Nombre.isEmpty() || Valor.isEmpty()){
            Toast.makeText(this, "¿Eso vacio? Don chimbilin...", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            if (buscarId()){
                Url+="update.php";
            }else{
                Url+="insert.php";
            }
            StringRequest postRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    limpiarCampos(v);
                    Toast.makeText(getApplicationContext(), "¿Que pongo?", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Nose que poner", Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", jetId.getText().toString().trim());
                    params.put("nombre", jetNombre.getText().toString().trim());
                    params.put("valor", jetValor.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add((postRequest));
        }
    }
    public void  delete(View v){
        Id=jetId.getText().toString();
        if (Id.isEmpty()){
            Toast.makeText(this, "Debe tener el id, ahuevado", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            Url+="delete.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    limpiarCampos(v);
                    Toast.makeText(getApplicationContext(), "¿Si se elimino? OBVIO BOBIS!", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Nada, intente de nuevo o algo...", Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", jetId.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }
    public void sell(View v){
        Id=jetId.getText().toString();
        if (Id.isEmpty()){
            Toast.makeText(getApplicationContext(), "Mijoooo... El ID ._.", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            Url+="sell.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    limpiarCampos(v);
                    Toast.makeText(getApplicationContext(), "Melo, Ya lo vendió", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "No, viejo. Eso no funcionó", Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id",jetId.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }
    public void limpiarCampos(View v){
        jetId.setText("");
        jetNombre.setText("");
        jetValor.setText("");
        jcbActivo.setChecked(false);
        jetId.requestFocus();
    }
}