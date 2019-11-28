package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText fullname,username,password,conpassword,email,address;
    Spinner  genders,customer;
    Button   register;
    TextView cons;
    private static String URL_REGIST = "https://takangrestaurant.xyz/connect/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cons = findViewById(R.id.confirma);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        conpassword = findViewById(R.id.conpassword);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        genders = findViewById(R.id.genders);
        customer = findViewById(R.id.customer);
        register =findViewById(R.id.register);
        gender();
        posit();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registersusers();
            }
        });
    }
    private void posit() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Customer");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customer.setAdapter(arrayAdapter);
    }
    private void gender() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("MALE");
        arrayList.add("FEMALE");
        arrayList.add("GENDER EQUALITY");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genders.setAdapter(arrayAdapter);


        genders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              if (genders.getSelectedItem().toString().equals("MALE")){
                  cons.setText("0");
              }else if (genders.getSelectedItem().toString().equals("FEMALE")){
                  cons.setText("1");
              }else if(genders.getSelectedItem().toString().equals("GENDER EQUALITY")){
                  cons.setText("2");
              }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void registersusers() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Data......");
        progressDialog.show();

        final String fullnames =this.fullname.getText().toString().trim();
        final String usernames = this.username.getText().toString().trim();
        final String emails = this.email.getText().toString().trim();
        final String passwords = this.password.getText().toString().trim();
        final String addresss = this.address.getText().toString().trim();
        final String image_paths = "profile.jpeg";
        final String gens = this.genders.getSelectedItem().toString().trim();

        final String conpasswords = conpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (fullnames.isEmpty() || usernames.isEmpty() || emails.isEmpty() ||
                                addresss.isEmpty() || conpasswords.isEmpty() || passwords.isEmpty()){
                            progressDialog.dismiss();
                            fullname.requestFocus();
                            username.requestFocus();
                            email.requestFocus();
                            address.requestFocus();
                            password.requestFocus();
                            conpassword.requestFocus();
                            Toast.makeText(register.this, "Please Fill up all data needed", Toast.LENGTH_SHORT).show();
                            if (!passwords.equals(conpasswords)){
                                progressDialog.dismiss();
                                Toast.makeText(register.this, "Password not match please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success =jsonObject.getString("success");

                                if (success.equals("1")){
                                    progressDialog.dismiss();
                                    Toast.makeText(register.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(register.this,MainActivity.class));
                                    finish();
                                }
                            }catch (JSONException e){
                                progressDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(register.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(register.this, "Error " +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String > params = new HashMap<>();
                params.put("fullname",fullnames);
                params.put("username",usernames);
                params.put("email",emails);
                params.put("password",passwords);
                params.put("gender",cons.getText().toString());
                params.put("address",addresss);
                params.put("image_path",image_paths);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
