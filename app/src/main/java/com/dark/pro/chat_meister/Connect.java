package com.dark.pro.chat_meister;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class Connect extends Fragment {
    BluetoothAdapter bluetoothAdapter;
    ListView list_discoverable,list_paired;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_connect, container, false);
        list_paired=(ListView)view.findViewById(R.id.list_paired_dev);
        list_discoverable=(ListView)view.findViewById(R.id.list_disc_dev);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getActivity(), "Bluetooth is not available!", Toast.LENGTH_SHORT).show();
        }
        if(bluetoothAdapter.isEnabled()){
            Toast.makeText(getActivity(),"Bluetooth already enabled..!",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent onBluetooth=new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(onBluetooth,111);
        }
        bluetoothAdapter.startDiscovery();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        List<String> s = new ArrayList<String>();
        for(BluetoothDevice bt : pairedDevices)
            s.add(bt.getName());
        list_paired.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, s));
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111&&requestCode==RESULT_OK){
                Toast.makeText(getActivity(), "Bluetooth turned on...!", Toast.LENGTH_SHORT).show();
        }
    }

}
