package com.dark.pro.chat_meister;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv_navname,tv_navuname,tv_navtitle;
    RelativeLayout navfrag;
    String txtnavtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_navtitle=(TextView)findViewById(R.id.tv_nav_title);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nav_message_history);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Loading Messages...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        tv_navname=(TextView)hView.findViewById(R.id.nav_txtname);
        tv_navuname=(TextView)hView.findViewById(R.id.nav_txtid);
        Bundle bundle = getIntent().getExtras();
        tv_navname.setText(bundle.getString("name"));
        tv_navuname.setText(bundle.getString("id"));
        txtnavtitle="Welcome "+bundle.getString("name");
        tv_navtitle.setText(txtnavtitle);
        navfrag=(RelativeLayout)findViewById(R.id.nav_frag);
        getFragmentManager().beginTransaction().replace(R.id.nav_frag,new Home()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_dev) {
            navfrag.removeAllViews();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag,new About()).commit();
            tv_navtitle.setText("About us");
            tv_navtitle.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            navfrag.removeAllViews();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag,new Profile()).commit();
            tv_navtitle.setText("My profile");
            tv_navtitle.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_home) {
            navfrag.removeAllViews();
            getFragmentManager().beginTransaction().replace(R.id.nav_frag,new Home()).commit();
            tv_navtitle.setText(txtnavtitle);
            tv_navtitle.setVisibility(View.VISIBLE);
        }else if(id==R.id.nav_chat){
            navfrag.removeAllViews();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag,new Messages()).commit();
            tv_navtitle.setText("Select a chat");
            tv_navtitle.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void connect(View view) {
        navfrag.removeAllViews();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag,new MainActivityController()).commit();
        tv_navtitle.setVisibility(View.GONE);
    }

    public void viewMessages(View view) {
        navfrag.removeAllViews();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag,new Messages()).commit();
        tv_navtitle.setText("Select a chat");
        tv_navtitle.setVisibility(View.VISIBLE);
    }
}
