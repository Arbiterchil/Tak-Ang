package com.example.tak_ang;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class menustart extends AppCompatActivity {

    Button menu,reser,logut,reservation,ordes,waiter;
    CircleImageView circleImageView,cheffu;
    TextView fullname,email;
    SessionManager sessionManager;
    private static String URL_READ = "https://takangrestaurant.xyz/connect/read.php";
    String getId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menustart);

        sessionManager = new SessionManager(this);
        sessionManager.CheckLogin();
        cheffu = findViewById(R.id.chef);
        menu = findViewById(R.id.menu);
        waiter= findViewById(R.id.waiter);
        reser  =findViewById(R.id.reser);
        logut = findViewById(R.id.logout);
        circleImageView = findViewById(R.id.images);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        reservation = findViewById(R.id.reservation);
        HashMap<String, String > user = sessionManager.getUserDeatils();
        getId = user.get(sessionManager.USER_ID);


        ordes = findViewById(R.id.chefu);
        ordes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menustart.this,aorde.class));
            }
        });

        cheffu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menustart.this,chefu.class));
            }
        });


        logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(menustart.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure? Want to Logout?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.logout();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        waiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menustart.this,waiterside.class));
            }
        });




    }
    public void getUserDetails(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Preparing......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    progressDialog.dismiss();
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success =jsonObject.getString("success");
                        JSONArray jsonArray =jsonObject.getJSONArray("read");
                        if (success.equals("1")){
                            for (int i= 0; i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String fullnames = object.getString("fullname").trim();
                                String emails = object.getString("email").trim();
                                int position = object.getInt("position");
                                final String usernameYE = object.getString("username").trim();
                                String imagess = object.getString("image_path");

                                String posit = Integer.toString(position);
                                switch (posit) {
                                    case "2":
                                        cheffu.setVisibility(View.GONE);
                                        ordes.setVisibility(View.GONE);
                                        waiter.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        menu.setVisibility(View.GONE);
                                        reser.setVisibility(View.GONE);
                                        reservation.setVisibility(View.GONE);
                                        waiter.setVisibility(View.GONE);
                                        String path = "https://takangrestaurant.xyz/images/"+imagess;
                                        Picasso.get().load(path).into(circleImageView);
                                        break;
                                    case "4":
                                        reser.setVisibility(View.GONE);
                                        reservation.setVisibility(View.GONE);
                                        cheffu.setVisibility(View.GONE);
                                        String paths = "https://takangrestaurant.xyz/images/"+imagess;
                                        Picasso.get().load(paths).into(circleImageView);
                                        break;
                                }
                                fullname.setText(fullnames);
                                email.setText(emails);


                                final String getID = object.getString("user_id").trim();
                                        menu.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(menustart.this, usernameYE, Toast.LENGTH_SHORT).show();
                                                Intent user = new Intent(menustart.this,reserlist.class);
                                                user.putExtra("user_id",getID);
                                                user.putExtra("useme",usernameYE);
                                                startActivity(user);
                                            }
                                        });

                                reser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent user = new Intent(menustart.this,tableres.class);
                                        user.putExtra("user_id",getID);
                                        startActivity(user);
                                    }
                                });

                                reservation.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent user1 = new Intent(menustart.this,myreser.class);
                                        user1.putExtra("user_id",getID);
                                        startActivity(user1);
                                    }
                                });


                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(menustart.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();

                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(menustart.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > params = new HashMap<>();
                params.put("user_id",getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//

        Toast.makeText(this, "Please Click Logout if you want to exit.", Toast.LENGTH_SHORT).show();
    }
}
