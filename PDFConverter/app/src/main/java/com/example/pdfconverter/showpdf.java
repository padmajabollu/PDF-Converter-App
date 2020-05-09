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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class showpdf extends AppCompatActivity {
    ListView show_list;
    ArrayAdapter list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpdf);

        getSupportActionBar().setTitle("PDF Files");
        Toast.makeText(showpdf.this,"Click to Open PDF File !!!",Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    Toast.makeText(showpdf.this,path1,Toast.LENGTH_LONG).show();


                    if (myFile.exists()) {

                        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/PDF FILES/" +PaperName+".pdf");  // -> filename
                        //Uri path = Uri.fromFile(pdfFile);
                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);

                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP   |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri path= FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider"
                                ,pdfFile);

                        pdfIntent.setDataAndType(path, "application/pdf");
                        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        try {
                            startActivity(pdfIntent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(showpdf.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(showpdf.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
        catch (Exception e)
        {
            Toast.makeText(showpdf.this,"Error !!!",Toast.LENGTH_SHORT).show();
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
            Intent intent_main=new Intent(getApplicationContext(), MainActivity.class);
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