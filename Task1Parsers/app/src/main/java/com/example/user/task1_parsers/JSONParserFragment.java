package com.example.user.task1_parsers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParserFragment extends android.support.v4.app.Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.parser_layout,container,false);
        new Contact().execute();

        return view;
    }



    class Contact extends AsyncTask<Void,Void,Void>{

        String json="";

        public Contact() {
            super();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.androidhive.info/contacts/");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream s=connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(s));
                String line="";
                while((line=br.readLine())!=null){
                    json+=line+"\n";
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity().getApplicationContext(), "Its Being Loaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray contact=jsonObject.getJSONArray("contacts");

                AllContacts allContacts[]=new AllContacts[contact.length()];
                for(int i=0;i<contact.length();i++) {
                    JSONObject details = contact.getJSONObject(i);
                    allContacts[i]=new AllContacts();
                    allContacts[i].setName(details.getString("name"));
                    allContacts[i].setEmailId(details.getString("email"));
                    JSONObject phone=details.getJSONObject("phone");
                    allContacts[i].setMobile(phone.getString("mobile"));
                    RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
                    RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    MyAdapter myAdapter=new MyAdapter(allContacts);
                    recyclerView.setAdapter(myAdapter);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
