package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class chefu extends AppCompatActivity {

    List<chefuget> chefugets;
    private ArrayList<String> names = new ArrayList<>();
    Spinner spinDicks;
    RecyclerView recme;
    private static final String URL_category= "https://takangrestaurant.xyz/connect/categ.php";
    private static final String URL_chef= "https://takangrestaurant.xyz/connect/chef.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefu);

    spinDicks = findViewById(R.id.meatspin);
    recme = findViewById(R.id.chefs);
    recme.setLayoutManager(new LinearLayoutManager(this));
    chefugets = new ArrayList<>();

    chefuSpin();
    Cheftab();

    }
    private void Cheftab() {

        StringRequest stringRequest =new StringRequest(Request.Method.GET, URL_chef,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0 ; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int menu_id = jsonObject.getInt("menu_id");
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                int price =  jsonObject.getInt("price");
                                String image_path = jsonObject.getString("image_path");
                                String category_name = jsonObject.getString("categname");
                                int status = jsonObject.getInt("status");
                                chefuget chefuget =new chefuget(menu_id,name,description,price,image_path,category_name,status);
                                chefugets.add(chefuget);
                                final chefuadapter chefuadapter = new chefuadapter(chefu.this,chefugets);
                                recme.setAdapter(chefuadapter);
                                chefuadapter.notifyDataSetChanged();

                                spinDicks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        chefuadapter.getFilter().filter(spinDicks.getSelectedItem().toString());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });



                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(chefu.this, "Something Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(chefu.this, "Something Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);


    }
    private void chefuSpin() {
        StringRequest stringRequestes = new StringRequest(Request.Method.POST
                , URL_category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        names.add(jsonObject.getString("name"));
                    }

                    spinDicks.setAdapter(new ArrayAdapter<>
                            (chefu.this, android.R.layout.simple_spinner_dropdown_item, names));


                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue reqs = Volley.newRequestQueue(this);
        reqs.add(stringRequestes);



    }
}
