package com.news.getbeer;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;


import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BAJAJ on 10-06-2017.
 */

public class MainActivity  extends AppCompatActivity {

    private TextView resultTextView;

    ArrayList<GetBeer> arraylist = new ArrayList<GetBeer>();
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;

    String APILink = "https://api.punkapi.com/v2/beers?per_page=50";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);
        new NewAsyncTask().execute();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager sManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                sManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        return true;
    }


    public class NewAsyncTask extends AsyncTask<Void, Void,Void> {

        String APILink = "https://api.punkapi.com/v2/beers?per_page=50";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            String result = "";
            result = HttpConnect.getDataFromApi(APILink);
            System.out.println("result "+result);

            JSONObject jsonObject = null;
            if(!result.equals("Exception")) {
                try {
                    //For Offline DATA saved
                    ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(), "") + "offline.srl"));
                    out.writeObject(result);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    //get Offline DATA
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(getCacheDir(), "") + "offline.srl")));
                   result = (String)in.readObject();
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
//
            }
            try {

                JSONArray jArr = new JSONArray(result);

                for (int i = 0; i < jArr.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonObject = jArr.getJSONObject(i);
                    // Retrieve JSON Objects
                    map.put("name", jsonObject.getString("name"));
                    map.put("description", jsonObject.getString("description"));
                    map.put("imageurl", jsonObject.getString("image_url"));
                    map.put("firstbrewed", jsonObject.getString("first_brewed"));
                    GetBeer gb =new GetBeer(jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image_url"),jsonObject.getString("first_brewed"));

                    arraylist.add(gb);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void args) {
            listview = (ListView) findViewById(R.id.listview);
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
        }

    }
}

