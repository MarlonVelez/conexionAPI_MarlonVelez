package com.example.conexionapi_marlonvelez;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.conexionapi_marlonvelez.modelo.Users;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaUsers;
    ArrayAdapter arrayAdapter;
    ArrayList <String> users= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaUsers= findViewById(R.id.listUsers);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
        listaUsers.setAdapter(arrayAdapter);
        datosList();
    }

    public void datosList(){
        String URL="https://jsonplaceholder.typicode.com/users";
        JsonArrayRequest usersJSON= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    parseJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(usersJSON);
    }

    public void parseJSON(JSONArray myJSON) throws JSONException {
        for (int i =0; i<myJSON.length(); i++){

            JSONObject jsonObject= null;

            Users user= new Users();

            jsonObject= myJSON.getJSONObject(i);

            user.setId(jsonObject.getLong("id"));
            user.setName(jsonObject.getString("name"));
            user.setUsername(jsonObject.getString("username"));
            user.setEmail(jsonObject.getString("email"));
            users.add(user.getName());
        }
        arrayAdapter.notifyDataSetChanged();
    }
}