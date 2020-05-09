package com.example.pdfconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class sharepdf extends AppCompatActivity {

    ListView show_list;
    ArrayAdapter list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharepdf);

        getSupportActionBar().setTitle("Share PDF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(sharepdf.this,"Click to Share PDF File !!!",Toast.LENGTH_SHORT).show();

        show_list=(ListView) findViewById(R.id.show);

        try
        {

            File path =new File(Environment.getExternalStorageDirectory()+"/PDF FILES/");
            Log.d("Files", "Path: " + path);
            File directory = new File(path.toString());
            File[] files=directory.listFiles();
            int length = 0;
            for (File file : path.listFiles())
            {
                if(file.isFile())
                {
                    length+=1;
                }

            }

            final String[] names = new String[length];
            for (int i = 0; i < length; i++) {
                String[] parts=(files[i].getName()).split("\\.");
                names[i]=parts[0];
                //Toast.makeText(show_pdf_files.this,parts[0],Toast.LENGTH_SHORT).show();
                //names[i] = files[i].getName();
            }


            ArrayList<String> array_list=new ArrayList<String>();
            array_list.addAll(Arrays.asList(names));
            list_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array_list);

            show_list.setAdapter(list_adapter);

            show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String PaperName = names[i];
                    String path1=Environment.getExternalStorageDirectory()+"/PDF FILES/"+PaperName+".pdf";
                    File myFile = new File(path1);
                    Toast.makeText(sharepdf.this,path1,Toast.LENGTH_LONG).show();

                    if (myFile.exists()) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);

                        Uri path= FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider"
                                ,myFile);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,path);
                        shareIntent.setType("application/pdf");

                        try {
                            startActivity(shareIntent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(sharepdf.this, "No Application available to Share PDF", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(sharepdf.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
        catch (Exception e)
        {
            Toast.makeText(sharepdf.this,"Error !!!",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent_main=new Intent(sharepdf.this, MainActivity.class);
            startActivity(intent_main);
            finish();
        }

        else if (id == R.id.shareapp) {
            Intent share=new Intent(Intent.ACTION_SEND);
            String text = "https://play.google.com/store/apps/details?id=com.pdfconverter&hl=en";
            share.putExtra(Intent.EXTRA_TEXT, text);
            share.setType("text/plain");

            startActivity(Intent.createChooser(share,"Share with"));


        }
        else if (id == R.id.exit) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else if(id==android.R.id.home)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}