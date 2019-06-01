package com.dark.pro.chat_meister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;

public class Register extends Fragment implements View.OnClickListener {
    EditText uname,name,contact,pass,pass2;
    Button reg;
    String txtuname,txtname,txtcontact,txtpass,txtpass2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bd) {
        View v=inflater.inflate(R.layout.fragment_register,container,false);
        Toast.makeText(getActivity(),"Caution: Registering will remove any previous data...",Toast.LENGTH_SHORT).show();
        uname=(EditText)v.findViewById(R.id.signup_uname);
        name=(EditText)v.findViewById(R.id.signup_name);
        pass=(EditText)v.findViewById(R.id.signup_pass);
        pass2=(EditText)v.findViewById(R.id.signup_pass2);
        contact=(EditText)v.findViewById(R.id.signup_contact);
        reg=(Button)v.findViewById(R.id.signup_frag_reg);
        reg.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        txtuname=uname.getText().toString().trim();
        txtname=name.getText().toString().trim();
        txtcontact=contact.getText().toString().trim();
        txtpass=pass.getText().toString().trim();
        txtpass2=pass2.getText().toString();
        if(txtuname.length()<7){
            Toast.makeText(getActivity(),"Username should contain atleast 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtname.equals("")){
            Toast.makeText(getActivity(),"Name cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtcontact.equals("")||txtcontact.length()<8){
            Toast.makeText(getActivity(),"Invalid contact no.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtpass.length()<6){
            Toast.makeText(getActivity(),"Password too short...",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!txtpass.equals(txtpass2)){
            Toast.makeText(getActivity(),"Passwords not matching",Toast.LENGTH_SHORT).show();
            return;
        }
        deletePrev();

        SharedPreferences sp= getActivity().getSharedPreferences("active_login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("id", txtuname);
        editor.putString("name", txtname);
        editor.putString("contact", txtcontact);
        editor.putString("pass",md5(txtpass));
        editor.apply();

        Toast.makeText(getActivity(),"Welcome "+txtname,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(),HomeScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", txtname);
        bundle.putString("id", txtuname);
        bundle.putString("contact",txtcontact);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().finish();
    }
    public void deletePrev(){
        try{
            SharedPreferences prefs=getActivity().getSharedPreferences("active_login",MODE_WORLD_READABLE);
            prefs.edit().remove("active_login");
            prefs.edit().clear();
            prefs.edit().commit();

            new MsgHelper(getActivity()).delete_all();
            new FriendHelper(getActivity()).delete_all();
        }catch(Exception e){

        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
