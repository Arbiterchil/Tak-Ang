package com.example.tak_ang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context ;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";

    public static  final String USER_ID = "user_id";
    public static  final String FULLNAME= "fullname";
    public static  final String USERNAME="username";
    public static  final String EMAIL = "email";
    public static  final String ADDRESS = "address";
    public static  final String GENDER = "gender";
//    public static  final String IMAGE = "image_path";
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void CreateSession(String user_id,String fullname,String username,String email,
                              String address,String gender){
    editor.putBoolean(LOGIN,true);
    editor.putString(USER_ID,user_id);
    editor.putString(FULLNAME,fullname);
    editor.putString(USERNAME,username);
    editor.putString(EMAIL,email);
    editor.putString(ADDRESS,address);
    editor.putString(GENDER,gender);
    editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void CheckLogin(){
        if (!this.isLogin()){
                Intent i =new Intent(context,MainActivity.class);
                context.startActivity(i);
                ((menustart)context).finish();
        }
    }

    public HashMap<String,String >getUserDeatils(){
        HashMap<String,String> user= new HashMap<>();
        user.put(USER_ID,sharedPreferences.getString(USER_ID,null));
        user.put(FULLNAME,sharedPreferences.getString(FULLNAME,null));
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(ADDRESS,sharedPreferences.getString(ADDRESS,null));
        user.put(GENDER,sharedPreferences.getString(GENDER,null));
        return user;


    }

public void logout(){
editor.clear();
editor.commit();
    Intent i =new Intent(context,MainActivity.class);
    context.startActivity(i);
    ((menustart)context).finish();
}


}
