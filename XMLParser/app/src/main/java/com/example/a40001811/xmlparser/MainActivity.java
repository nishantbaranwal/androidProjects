package com.example.a40001811.xmlparser;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Pizza().execute();
            }
        });
    }






    class Pizza extends AsyncTask<Void,Void,Void>{
        String str="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "XML File is being Loaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            InputStream is = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("item");
                AllPizza allPizza[]=new AllPizza[nList.getLength()];
                for (int i=0; i<nList.getLength(); i++) {

                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        allPizza[i]=new AllPizza();
                        Element element2 = (Element) node;
                        allPizza[i].setId(getValue("id", element2));
                        allPizza[i].setName(getValue("name", element2));
                        allPizza[i].setCost(getValue("cost", element2));
                        allPizza[i].setDescription(getValue("description", element2));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        private String getValue(String tag, Element element) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url=new URL("https://api.androidhive.info/pizza/?format=xml");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream=new BufferedInputStream(connection.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                while((line=br.readLine())!=null){
                    str+=line+"\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
