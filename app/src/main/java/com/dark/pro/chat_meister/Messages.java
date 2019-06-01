package com.dark.pro.chat_meister;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

//import static com.dark.pro.chat_meister.R.id.parent;


public class Messages extends Fragment {
    ListView msg_list;
    Spinner sp;
    ArrayAdapter adapter,friend;
    ArrayList fdata,mdata;
    String AESkey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_messages, container, false);
        msg_list=(ListView)view.findViewById(R.id.msg_list);
        sp=(Spinner)view.findViewById(R.id.friend_spinner);
        fdata=new ArrayList();
        mdata=new ArrayList();
        friend=new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,fdata);
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,mdata);
        friends();
        sp.setAdapter(friend);
        msg_list.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                search(item);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AESkey="F3BB4F9A6356E93817A045A4A76E9CAD";
        return view;
    }

    void search(String str){
        adapter.clear();
        MsgHelper msg=new MsgHelper(getActivity());
        Cursor cr=msg.selectdata(str);
        cr.moveToFirst();

        if(cr.getCount()>0){
            do{
                try {
                    mdata.add(AESCrypt.decrypt(AESkey, cr.getString(0)));
                }catch (Exception e){
                    //in case of AES Failure
                    Toast.makeText(getActivity(),"Error in message retrieving...",Toast.LENGTH_SHORT).show();
                }
            }while(cr.moveToNext());
        }
    }

    void friends(){
        friend.clear();
        FriendHelper f=new FriendHelper(getActivity());
        Cursor cr=f.selectdata();
        cr.moveToFirst();
        if(cr.getCount()>0){
            do{
                fdata.add(cr.getString(0));
            }while(cr.moveToNext());
        }
    }
}
