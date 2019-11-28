package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText user , pass ;
    TextView sign;
    Button logs;
    SessionManager sessionManager;
    private static String URL_LOG = "https://takangrestaurant.xyz/connect/login.php";
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager =  new SessionManager(this);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        sign = findViewById(R.id.regis);
        logs = findViewById(R.id.logins);
        saveLoginCheckBox =findViewById(R.id.rem);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            user.setText(loginPreferences.getString("username", ""));
            pass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,register.class));
            }
        });

        logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = user.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (!username.isEmpty()||!password.isEmpty()){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(user.getWindowToken(), 0);

                    username = user.getText().toString();
                    password = pass.getText().toString();

                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();


                }
                    Login(username,password);
                    }else{

                    user.setError("Please put Username");
                    pass.setError("Please put The indicated Password");
                }

            }
        });




    }

    private void Login(final String username,final String password){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In......");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success =jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i =0 ;i<jsonArray.length();i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String user_id = object.getString("user_id").trim();
                                    String fullnames = object.getString("fullname").trim();
                                    String usernames = object.getString("username").trim();
                                    String emails = object.getString("email").trim();
                                    String addresss = object.getString("address").trim();
                                    String genders = object.getString("gender").trim();

                                    sessionManager.CreateSession(user_id, fullnames
                                            , usernames, emails, addresss, genders);

                                    Toast.makeText(MainActivity.this,
                                            "Welcome \n" +
                                                    fullnames, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent
                                            (MainActivity.this, menustart.class);

                                    startActivity(intent);
                                    finish();


                                }
                            }


                        }catch (JSONException e){
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}
