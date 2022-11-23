package com.example.mydoctor.PrefManager;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public PrefManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyDoctor";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private String ID = "id";
    private String token = "token";
    private String IP = "ipadd";
    private String nama = "nama";
    private String email = "email";
    private String login = "login";
    private String DASHBOARD = "DASHBOARD";
    private String nama_role = "nama_role";
    private String perusahaan_user = "perusahaan_user";

    public String getNama_role() { return pref.getString(nama_role,""); }
    public void setNama_role(String id) {
        editor.putString(nama_role,id);
        editor.commit();
    }

    public boolean getLogin() {
        return pref.getBoolean(login, false);
    }

    public void setLogin(boolean x) {
        editor.putBoolean(login, x);
        editor.commit();
    }

    public String getID(){
        return pref.getString(ID,"");
    }
    public void setID(String id){
        editor.putString(ID,id);
        editor.commit();
    }
    public String getToken(){
        return pref.getString(token,"");
    }
    public void setToken(String id){
        editor.putString(token,id);
        editor.commit();
    }
    public String getNama(){
        return pref.getString(nama,"");
    }
    public String getEmail(){
        return pref.getString(email,"");
    }
    public void setNama(String id){
        editor.putString(nama,id);
        editor.commit();
    }
    public void setEmail(String id){
        editor.putString(email,id);
        editor.commit();
    }
    public String getIP(){
        return pref.getString(IP,"");
    }
    public void setIP(String id){
        editor.putString(IP,id);
        editor.commit();
    }
    public void hapusSession(){
        setLogin(false);
    }

    public void setIsFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }
    public boolean isFisrtTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

    public boolean getDashboard(){
        return pref.getBoolean(DASHBOARD,true);
    }

    public void setDashboard(boolean aktif){
        editor.putBoolean(DASHBOARD,aktif);
        editor.commit();
    }
}
