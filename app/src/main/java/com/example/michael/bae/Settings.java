package com.example.michael.bae;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;


public class Settings extends ActionBarActivity {
    ListView lv;
    ArrayAdapter<CharSequence> adapter;
    SimpleAdapter sa;
    ArrayList<HashMap<String,String>> list = new ArrayList<>();

    final static private String FIRE_BASE_URL = "https://b-a-e.firebaseio.com/";
    Firebase myBaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myBaseRef = new Firebase(FIRE_BASE_URL);

        HashMap<String,String> item;
        for (int i = 0; i < settingsList.length; i++){
            item = new HashMap<>();
            item.put("line1", settingsList[i][0]);
            item.put("line2", settingsList[i][1]);
            list.add(item);
        }

        sa = new SimpleAdapter(Settings.this, list, android.R.layout.two_line_list_item,
                new String[]{"line1", "line2"}, new int[] {android.R.id.text1,
        android.R.id.text2});
        lv = (ListView) findViewById(R.id.listView);
        adapter = ArrayAdapter.createFromResource(Settings.this, R.array.Settings, android.R.layout.simple_list_item_1);
        lv.setAdapter(sa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){

                }else if(position == 1) {

                }else if(position == 2) {

                }else if(position == 3) {
                    myBaseRef.unauth();
                    startActivity(new Intent(Settings.this, LoginActivity.class));
                }else if(position == 4) {
                    view.setEnabled(false);
                }
            }
        });
    }

    private String[][] settingsList = {
            {"App", ""},
            {"Notifications", ""},
            {"Calender", ""},
            {"Log Out", ""},
            {"About", "Version 7.0.3(Android 4.4.4)"}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
