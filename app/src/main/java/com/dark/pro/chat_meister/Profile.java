package com.dark.pro.chat_meister;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Profile extends Fragment {
    TextView tvname,tvuname,tvcontact,tvaddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bd) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        tvname=(TextView)v.findViewById(R.id.profile_name);
        tvuname=(TextView)v.findViewById(R.id.profile_uname);
        tvcontact=(TextView)v.findViewById(R.id.profile_contact);
        tvaddress=(TextView)v.findViewById(R.id.profile_address);

        Bundle bundle = getActivity().getIntent().getExtras();
        tvname.setText("Name : "+bundle.getString("name"));
        tvuname.setText("Username : "+bundle.getString("id"));
        tvcontact.setText("Contact : "+bundle.getString("contact"));
        String addr=android.provider.Settings.Secure.getString(getActivity().getContentResolver(), "bluetooth_address");
        tvaddress.setText("Bluetooth Address :\n"+addr);
        return v;
    }
}
