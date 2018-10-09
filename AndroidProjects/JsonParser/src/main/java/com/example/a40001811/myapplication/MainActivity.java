package com.example.a40001811.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Contacts().execute();
            }
        });
    }






    class Contacts extends AsyncTask<Void, Void, Void>{
        String response="";
        String name,email,mobNo;

        @Override
        protected Void doInBackground(Void... voids) {


            String url="https://api.androidhive.info/contacts/";

            try {
                URL urll=new URL(url);
                HttpURLConnection connection= (HttpURLConnection) urll.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream=new BufferedInputStream(connection.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line=br.readLine())!=null) {
                    response += line + "\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Script Is Being Downloaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(response!=null) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray contacts = jsonObject.getJSONArray("contacts");
                    AllContacts allContacts[]=new AllContacts[contacts.length()];
                    for(int i=0;i<contacts.length();i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        name = c.getString("name");
                        email = c.getString("email");
                        JSONObject m = c.getJSONObject("phone");
                        mobNo = m.getString("mobile");
                        allContacts[i]=new AllContacts();
                        allContacts[i].setName(name);
                        allContacts[i].setEmail(email);
                        allContacts[i].setMobNo(mobNo);
                        Toast.makeText(MainActivity.this, allContacts[i].getName()+""+allContacts[i].getMobNo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}