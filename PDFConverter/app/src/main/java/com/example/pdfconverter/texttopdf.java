package com.example.pdfconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class texttopdf extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 1;

    public static final int STORAGE_CODES = 102;
    private static final int request_code = 6384;
    String mFilePath;
    EditText title1, content1, sign, pdfname1;
    Button texttopdf1;
    boolean signature=false,pdfname=false,flag=true,title_b=false,content_b=false;
    int lower;
    int upper;

    int space;
    int length;
    int i;
    char c;
    String title,sign1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texttopdf);

        getSupportActionBar().setTitle("Text to PDF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title1 = (EditText) findViewById(R.id.title);
        content1 = (EditText) findViewById(R.id.contet);
        sign = (EditText) findViewById(R.id.signature);
        pdfname1 = (EditText) findViewById(R.id.pdfname);
        texttopdf1 = (Button) findViewById(R.id.convert);


        try {

            texttopdf1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //while(true)
                    {
                        lower=0;
                        space=0;
                        upper=0;
                        title=title1.getText().toString();
                        length=title1.getText().toString().length();

                        i=0;
                        while(i<length)
                        {
                            c=title.charAt(i);
                            i++;
                            if(c=='a' || c=='b' || c=='c' || c=='d' || c=='e' || c=='f' || c=='g' || c=='h' || c=='i' || c=='j' || c=='k' || c=='l' || c=='m' || c=='n' || c=='o' || c=='p' || c=='q' || c=='r' || c=='s' || c=='t' || c=='u' || c=='v' || c=='w' || c=='x' || c=='y' || c=='z')
                            {
                                lower=lower+1;
                            }
                            if(c=='A' || c=='B' || c=='C' || c=='D' || c=='E' || c=='F' || c=='G' || c=='H' || c=='I' || c=='J' || c=='K' || c=='L' || c=='M' || c=='N' || c=='O' || c=='P' || c=='Q' || c=='R' || c=='S' || c=='T' || c=='U' || c=='V' || c=='W' || c=='X' || c=='Y' || c=='Z')
                            {
                                upper=upper+1;
                            }
                            if(c==' ')
                            {
                                space=space+1;
                            }

                        }
                        if(length>=4 && (lower+upper+space)==length)
                        {
                            title_b=true;
                            //                      break;
                        }
                        else
                        {
                            title1.setError("\nType Title");
                        }
                    }

                    // while(true)
                    {
                        lower=0;
                        space=0;
                        upper=0;
                        sign1=sign.getText().toString();
                        length=sign.getText().toString().length();

                        i=0;
                        while(i<length)
                        {
                            c=sign1.charAt(i);
                            i++;
                            if(c=='a' || c=='b' || c=='c' || c=='d' || c=='e' || c=='f' || c=='g' || c=='h' || c=='i' || c=='j' || c=='k' || c=='l' || c=='m' || c=='n' || c=='o' || c=='p' || c=='q' || c=='r' || c=='s' || c=='t' || c=='u' || c=='v' || c=='w' || c=='x' || c=='y' || c=='z')
                            {
                                lower=lower+1;
                            }
                            if(c=='A' || c=='B' || c=='C' || c=='D' || c=='E' || c=='F' || c=='G' || c=='H' || c=='I' || c=='J' || c=='K' || c=='L' || c=='M' || c=='N' || c=='O' || c=='P' || c=='Q' || c=='R' || c=='S' || c=='T' || c=='U' || c=='V' || c=='W' || c=='X' || c=='Y' || c=='Z')
                            {
                                upper=upper+1;
                            }
                            if(c==' ')
                            {
                                space=space+1;
                            }

                        }
                        if(length>=4 && (lower+upper+space)==length)
                        {
                            signature=true;
                            //       break;
                        }
                        else
                        {
                            sign.setError("\nType Signature");
                        }
                    }
                    if(pdfname1.getText().toString().trim().equalsIgnoreCase(""))
                    {
                        pdfname1.setError("Type Pdf Name");
                    }
                    else
                    {
                        pdfname=true;
                    }

                    if(content1.getText().toString().trim().equalsIgnoreCase(""))
                    {
                        content1.setError("Type Content");
                    }
                    else
                    {
                        content_b=true;
                    }
                    if (title_b && content_b && signature && pdfname) {
                        if (ContextCompat.checkSelfPermission(texttopdf.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            //Toast.makeText(text_to_pdf.this, "You have already granted this permission!",
                            //      Toast.LENGTH_SHORT).show();
                            savePdf();
                        } else {
                            requestStoragePermission();

                        }

                    }
                }


            });

        } catch (Exception e) {
            Toast.makeText(texttopdf.this, "Error !!!", Toast.LENGTH_SHORT).show();
        }


    }

    public void savePdf() {
        com.itextpdf.text.Document mDoc = new com.itextpdf.text.Document();

        mFilePath = Environment.getExternalStorageDirectory() + File.separator + "PDF FILES";

        File fpath = new File(mFilePath);
        if (!fpath.exists()) {
            fpath.mkdirs();
        }

        try {
            mFilePath = Environment.getExternalStorageDirectory() + "/" + "PDF FILES" + "/" + pdfname1.getText().toString() +
                    ".pdf";



            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));


            mDoc.open();

            String t = title1.getText().toString() + "\n\n";
            String c = content1.getText().toString() + "\n\n";
            String s = "From : " + sign.getText().toString() + "\n";

            mDoc.addAuthor("Padmaja Bollu");
            Font ftitle= FontFactory.getFont(FontFactory.TIMES_BOLD,16);
            com.itextpdf.text.Paragraph title = new com.itextpdf.text.Paragraph(t,ftitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);

            Font fcontent=FontFactory.getFont(FontFactory.TIMES,14);

            com.itextpdf.text.Paragraph content = new com.itextpdf.text.Paragraph(c,fcontent);
            content.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            //content.add(c);
            Font fsignature=FontFactory.getFont(FontFactory.TIMES_BOLD,13);

            com.itextpdf.text.Paragraph signature = new com.itextpdf.text.Paragraph(s,fsignature);
            signature.setAlignment(Paragraph.ALIGN_LEFT);
            //signature.add(s);
            mDoc.add(title);
            mDoc.add(content);
            mDoc.add(signature);

            Toast.makeText(texttopdf.this, pdfname1.getText().toString() + ".pdf\nis saved to \n" + mFilePath, Toast.LENGTH_LONG).show();

            mDoc.close();
            Intent first=new Intent(texttopdf.this,MainActivity.class);
            startActivity(first);
            finish();


        } catch (Exception e) {
            //Toast.makeText(texttopdf.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            Intent intent_main = new Intent(texttopdf.this, MainActivity.class);
            startActivity(intent_main);
            finish();
        }

        if (id == R.id.shareapp) {
            Intent share = new Intent(Intent.ACTION_SEND);
            String text = "https://play.google.com/store/apps/details?id=com.pdfconverter&hl=en";
            share.putExtra(Intent.EXTRA_TEXT, text);
            share.setType("text/plain");

            startActivity(Intent.createChooser(share, "Share with"));


        }
        else if (id == R.id.exit) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else if (id == R.id.home) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id==android.R.id.home)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();

        }



        return super.onOptionsItemSelected(item);
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("The permission is needed to access Camera and for Read and Write the Data")

                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(texttopdf.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                savePdf();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}