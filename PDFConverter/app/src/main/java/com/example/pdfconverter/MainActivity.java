package com.example.pdfconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    String PathHolder;
    Button btn_image;
    Button btn_text;
    Button btn_files;
    Button btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_files=(Button) findViewById(R.id.showpdf);
        btn_image=(Button) findViewById(R.id.imgtopdf);
        btn_text=(Button) findViewById(R.id.texttopdf);
        btn_share=(Button) findViewById(R.id.sharepdf);

        try {
            btn_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_text=new Intent(MainActivity.this,texttopdf.class);
                    startActivity(intent_text);
                    finish();

                }
            });

            btn_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_image=new Intent(MainActivity.this,imagetopdf.class);
                    startActivity(intent_image);
                    finish();
                    //MainActivity.this.finish();
                }
            });

            btn_files.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_files=new Intent(MainActivity.this,showpdf.class);
                    startActivity(intent_files);
                    finish();
                    //MainActivity.this.finish();
                }
            });

            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_share=new Intent(MainActivity.this,sharepdf.class);
                    startActivity(intent_share);
                    finish();
                    //MainActivity.this.finish();
                }
            });


        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Error !!!",Toast.LENGTH_SHORT).show();
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


        if (id == R.id.shareapp) {
            Intent share=new Intent(Intent.ACTION_SEND);
            String text = "https://play.google.com/store/apps/details?id=com.pdfconverter&hl=en";
            share.putExtra(Intent.EXTRA_TEXT, text);
            share.setType("text/plain");

            startActivity(Intent.createChooser(share,"Share with"));


        }
        else if (id == R.id.exit) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            finish();
        }
        else if (id == R.id.home) {
            Intent intent=new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id==android.R.id.home)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Do you want to Exit ?")
                    .setTitle("Exit")
                    .setIcon(R.drawable.logo)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }




        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit ?")
                .setTitle("Exit")
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}