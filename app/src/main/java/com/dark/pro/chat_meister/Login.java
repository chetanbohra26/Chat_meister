package com.dark.pro.chat_meister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;


public class Login extends Fragment implements View.OnClickListener {
    String txtid,txtpass,txtcontact,txtname;
    EditText edtuname,edtpass;
    Button login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bd) {
        View v=inflater.inflate(R.layout.fragment_login, container, false);

        SharedPreferences sp=getActivity().getSharedPreferences("active_login",Context.MODE_PRIVATE);
        txtid=sp.getString("id","");
        txtname=sp.getString("name","");
        txtcontact=sp.getString("contact","");
        txtpass=sp.getString("pass","");
        edtuname=(EditText)v.findViewById(R.id.login_uname);
        edtpass=(EditText)v.findViewById(R.id.login_pass);
        login=(Button)v.findViewById(R.id.login_frag_btn);
        login.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        String checkuname,checkpass;
        checkuname=edtuname.getText().toString().trim();
        checkpass=edtpass.getText().toString().trim();
        if(checkuname.equals("")) {
            Toast.makeText(getActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(checkpass.equals("")) {
            Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(checkuname.equals(txtid)&&md5(checkpass).equals(txtpass)){
            Toast.makeText(getActivity(),"Welcome "+txtname,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(),HomeScreen.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", txtname);
            bundle.putString("id", txtid);
            bundle.putString("contact",txtcontact);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().finish();
        }
        else{
            Toast.makeText(getActivity(),"Invalid data",Toast.LENGTH_SHORT).show();
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
