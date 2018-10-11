package com.example.user.filedownload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Download1().execute();

                } else {
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            }
        } else {
            // Permission has already been granted

            new Download1().execute();

        }
    }

//
//    private boolean hasReadPermissions() {
//        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//    }
//
//    private boolean hasWritePermissions() {
//        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable())
           requestAppPermissions();
        else
            Toast.makeText(this, "Internet Not Working", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int j=0; j<files.length; j++) {
                if((files[j].getName()).contains(".pdf")|| (files[j].getName()).contains(".mp3")|| (files[j].getName()).contains(".zip")|| (files[j].getName()).contains(".doc")|| (files[j].getName()).contains(".mp4"))
                MyFiles.add(files[j].getName());
            }
        }

        return MyFiles;
    }


    public String getFileName(String url){
        String fileName = url.substring(url.lastIndexOf('/') + 1);
//        Log.i("maybeagain",fileName);
        return fileName;

    }
    class Download1 extends AsyncTask<Void,Void,Void> {
        File outputFile=null;
        String url1="http://androhub.com/demo/demo.pdf";
        String url2="http://androhub.com/demo/demo.mp3";
        String url3="http://androhub.com/demo/demo.doc";
        String url4="http://androhub.com/demo/demo.zip";
        String url5="http://androhub.com/demo/demo.mp4";
        String allUrl[]=new String[]{url1,url2,url3,url4,url5};
        int i=0;
        boolean doesNotFileExists[]=new boolean[]{true,true,true,true,true};
        String fname[]=new String[]{"","","","",""};
        @Override
        protected Void doInBackground(Void... voids) {
            for(;i<allUrl.length;i++) {
                try {
                    URL url = new URL(allUrl[i]);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();


                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Toast.makeText(MainActivity.this, "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                    int length = connection.getContentLength()/100;
                    Log.i("Length Of "+fname[i],length+"");
//                    Log.i("FileDoesNotExist", " maybe " + doesNotFileExists[i]);
                    if (fileDoesNotExist(fname[i])) {
                        if (isExternalStorageWritable()) {
//                            Log.i("Downloaded File: " ,fname[i]);
                            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/" + fname[i]);
//                            Log.i("File Name=",fname[i]);
                            InputStream is = connection.getInputStream();

                            byte[] buffer = new byte[4096];//Set buffer type
                            int len1 = 0;//init length
                            int perc=0;
                            while ((len1 = is.read(buffer)) != -1) {
//                                Log.i("Length Of File",len1+"");

                                perc+=len1/100;
                                if(length!=0)
                                Log.i("% Of "+fname[i],(perc*100)/length+"");
                                fos.write(buffer, 0, len1);//Write new file
                            }

                            //Close all connection after doing task
                            fos.close();
                            is.close();
                            connection.disconnect();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            return null;
        }

        private boolean fileDoesNotExist(String fileName) {
            File f = new File(Environment.getExternalStorageDirectory().getPath());

            File[] files = f.listFiles();
            if (files.length == 0) {
                doesNotFileExists[i] = true;
            }
            else {
                for (int j=0; j<files.length; j++) {
                    if((files[j].getName()).contains(fileName)) {
//                        Log.i("Nishant "+fileName,files[j].getName());
//                        doesNotFileExists[i] = false;
                        return false;
                    }
                }
            }
            return doesNotFileExists[i];
//            doesNotFileExists[i]=true;
        }
        protected void onPreExecute() {
            super.onPreExecute();
            for (int j = 0;j<allUrl.length;j++){
                    fname[j] = getFileName(allUrl[j]);

//                    Log.i("PreExecutex`",fname[j]+""+doesNotFileExists[j]);
                    if (!fileDoesNotExist(fname[j]))
                        Toast.makeText(MainActivity.this, "File Already Exists", Toast.LENGTH_SHORT).show();

        }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for(int k=0;k<allUrl.length;k++) {
                if (fileDoesNotExist(fname[k]))
                    Toast.makeText(MainActivity.this, fname[k], Toast.LENGTH_SHORT).show();
            }
            ListView lv;
            final ArrayList<String> FilesInFolder = GetFiles(Environment.getExternalStorageDirectory().getPath());
            lv = (ListView)findViewById(R.id.list);

            lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, FilesInFolder));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if((FilesInFolder.get(i).substring(FilesInFolder.get(i).lastIndexOf('.'))).equals(".pdf")) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/"+FilesInFolder.get(i)));
                            intent.setType("application/pdf");
                                startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    if((FilesInFolder.get(i).substring(FilesInFolder.get(i).lastIndexOf('.'))).equals(".doc")){
                        try {
//                            Log.i("Doc", FilesInFolder.get(i));
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_VIEW);
                            String type = "application/msword";
                            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/" + FilesInFolder.get(i))), type);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    else
                        if((FilesInFolder.get(i).substring(FilesInFolder.get(i).lastIndexOf('.'))).equals(".mp3")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + FilesInFolder.get(i)));
                                intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + FilesInFolder.get(i)), "audio/mp3");
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        if((FilesInFolder.get(i).substring(FilesInFolder.get(i).lastIndexOf('.'))).equals(".mp4")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + FilesInFolder.get(i)));
                                intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/" + FilesInFolder.get(i)), "video/mp4");
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }


            });

        }

        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }
//        public boolean isExternalStorageReadable() {
//            String state = Environment.getExternalStorageState();
//            if (Environment.MEDIA_MOUNTED.equals(state) ||
//                    Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//                return true;
//            }
//            return false;
//        }


    }
}
