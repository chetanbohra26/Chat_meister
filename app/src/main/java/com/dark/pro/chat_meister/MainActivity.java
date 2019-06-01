package com.dark.pro.chat_meister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=(TextView)findViewById(R.id.txtmainhead);
        getFragmentManager().beginTransaction().replace(R.id.frag,new Login()).commit();
    }

    public void RegisterUser(View view) {
        getFragmentManager().beginTransaction().replace(R.id.frag,new Register()).commit();
        title.setText("Register");
    }

    public void forgot(View view) {
        Toast.makeText(this,"Register as a new user, all previous data would be lost..!",Toast.LENGTH_LONG).show();
    }
}
