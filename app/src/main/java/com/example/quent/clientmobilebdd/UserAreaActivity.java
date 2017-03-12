package com.example.quent.clientmobilebdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.quent.clientmobilebdd.R.id.btnDeconnexion;
import static com.example.quent.clientmobilebdd.R.id.listView_User;
import static com.example.quent.clientmobilebdd.R.id.tvUser;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        ((TextView) findViewById(tvUser)).setText("Bonjour "+intent.getStringExtra("name")+",");

        final Button bDeconnexion = (Button) findViewById(btnDeconnexion);

        bDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final HashMap<String, String> users = new HashMap<String, String>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    AlertDialog.Builder builder;
                    JSONArray jsonResponse = new JSONArray(response);

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject jsonobject = jsonResponse.getJSONObject(i);
                        users.put(jsonobject.getString("login_etudiants"), jsonobject.getString("nom_personnes")+ " "+ jsonobject.getString("prenom_personnes"));
                    }

                    ListView ListViewUser = (ListView) findViewById(listView_User);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserAreaActivity.this, android.R.layout.simple_list_item_1, users.values().toArray(new String[users.values().size()]));
                    ListViewUser.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BddRequest loginRequest = new BddRequest(responseListener, "getUsers");
        RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
        queue.add(loginRequest);

        ((ListView) findViewById(listView_User)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            AlertDialog.Builder builder;
                            JSONObject jsonResponse = new JSONObject(response);
                            int result = jsonResponse.getInt("etat");
                            if (result == 1){
                                Toast.makeText(getApplicationContext(), "Le mot de passe à été réinitialisé", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Une erreur est survenue", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                String nom = "";

                for(Map.Entry<String, String> entry : users.entrySet()) {
                    if (entry.getValue().equals(((TextView) view).getText())){
                        nom = entry.getKey();
                    }
                }

                BddRequest Request = new BddRequest(responseListener, "resetMdp/"+nom);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(Request);
            }
        });
    }


}
