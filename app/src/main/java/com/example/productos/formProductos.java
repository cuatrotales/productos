package com.example.productos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class formProductos extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText jetId, jetNombre, jetValor;
    CheckBox jcbActivo;
    String Id, Nombre, Valor, Url="http://192.168.20.48:80/WebServices/";
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
    public void Select(){
        Id = jetId.getText().toString();
        if (Id.isEmpty()){
            Toast.makeText(this, "¿Sin el ID? Ni con el viento de la rosa de guadalupe...", Toast.LENGTH_SHORT).show();
            jetId.requestFocus();
        }else{
            Url+="select.php?id="+Id;
            jrq = new JsonObjectRequest(Request.Method.GET, Url, null, this, this);
            rq.add(jrq);
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
}