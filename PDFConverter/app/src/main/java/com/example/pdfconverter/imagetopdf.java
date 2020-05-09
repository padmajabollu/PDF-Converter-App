package com.example.pdfconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class imagetopdf extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE_CAMERA = 1;
    private int STORAGE_PERMISSION_CODE_GALLERY = 2;
    private int STORAGE_PERMISSION_CODE_WRITE = 3;
    String  ImageSavePathInDevice=null;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    Random random;
    File imageFile;
    String mFilePath;
    EditText mTextEt;
    EditText sign;
    EditText decription;
    byte[] bytes;
    Button mSaveBtn;
    Bitmap bitmap=null;
    boolean boolean_save;
    int flag;
    private ImageView imageView;
    private Button camera, gallery;

    //keep track of camera capture intent
    static final int CAMERA_CAPTURE = 1;
    //keep track of cropping intent
    final int PIC_CROP_CAMERA = 3;
    final int PIC_CROP_GALLERY = 4;
    String mypath="";
    //keep track of gallery intent
    final int PICK_IMAGE_REQUEST = 2;
    //captured picture uri
    private Uri uri;
    public Bitmap thePic;
    boolean signature = false, pdfname = false;
    int lower;
    int upper;

    int space;
    int length;
    int i;
    char c;
    String l_name1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagetopdf);

        getSupportActionBar().setTitle("Image to PDF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE_GALLERY);

        imageView = (ImageView) findViewById(R.id.imgview);
        camera = (Button) findViewById(R.id.camera);
        gallery = (Button) findViewById(R.id.gallary);
        mSaveBtn = (Button) findViewById(R.id.convert);
        mTextEt = (EditText) findViewById(R.id.pdfname);
        sign = (EditText) findViewById(R.id.signature);
        decription = (EditText) findViewById(R.id.discription);

        random=new Random();
        try {
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE_GALLERY);
                    checkPermission(Manifest.permission.CAMERA,STORAGE_PERMISSION_CODE_CAMERA);

                    ImageSavePathInDevice =CreateRandomAudioFileName(5) + "Image.jpg";
                    flag=PIC_CROP_CAMERA;
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    imageFile=new File(file,ImageSavePathInDevice);
                    uri= FileProvider.getUriForFile(getApplicationContext(),
                            getApplicationContext().getPackageName() + ".provider"
                            ,imageFile);
                    //Uri uri=Uri.fromFile(imageFile);
                    takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,  uri );
                    try {
                        startActivityForResult(takePictureIntent, 1);
                    }
                    catch (SecurityException e)
                    {
                        e.printStackTrace();
                        /*ImageSavePathInDevice =CreateRandomAudioFileName(5) + "Image.jpg";
                        flag=PIC_CROP_CAMERA;
                        Intent takePictureIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        imageFile=new File(file1,ImageSavePathInDevice);
                        uri= FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider"
                                ,imageFile);
                        //Uri uri=Uri.fromFile(imageFile);
                        takePictureIntent1.putExtra( MediaStore.EXTRA_OUTPUT,  uri );
                        try {
                            startActivityForResult(takePictureIntent1,1);
                        }
                        catch (SecurityException e1)
                        {
                            e1.printStackTrace();
                        }
                        */

                    }

                }
            });
            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE_GALLERY);
                    flag=PIC_CROP_GALLERY;
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    try {
                        startActivityForResult(intent, 2);

                    }
                    catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(getApplicationContext(),"There is no File Explorer Clients Installed.",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            mSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lower = 0;
                    space = 0;
                    upper = 0;
                    l_name1 = sign.getText().toString();
                    length = sign.getText().toString().length();

                    i = 0;
                    while (i < length) {
                        c = l_name1.charAt(i);
                        i++;
                        if (c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k' || c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v' || c == 'w' || c == 'x' || c == 'y' || c == 'z') {
                            lower = lower + 1;
                        }
                        if (c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F' || c == 'G' || c == 'H' || c == 'I' || c == 'J' || c == 'K' || c == 'L' || c == 'M' || c == 'N' || c == 'O' || c == 'P' || c == 'Q' || c == 'R' || c == 'S' || c == 'T' || c == 'U' || c == 'V' || c == 'W' || c == 'X' || c == 'Y' || c == 'Z') {
                            upper = upper + 1;
                        }
                        if (Character.isWhitespace(c)) {
                            space = space + 1;
                        }
                    }
                    if (length >= 4 && (lower + upper + space) == length) {
                        signature = true;

                    } else {
                        sign.setError("Type Signature");
                    }

                    if (mTextEt.getText().toString().trim().equalsIgnoreCase("")) {
                        mTextEt.setError("Type Pdf Name");
                    } else {
                        pdfname = true;
                    }

                    if (signature && pdfname)
                    {
                        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE_WRITE);
                        savePdf();
                    }
                    else
                    {
                        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE_WRITE);

                    }

                }

            });

        } catch (Exception e) {
            Toast.makeText(imagetopdf.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


        public void savePdf() {

                String signature1 = "\n\nFrom : " + sign.getText().toString();
            com.itextpdf.text.Document mDoc = new com.itextpdf.text.Document();

            mFilePath = Environment.getExternalStorageDirectory() + "/" + "PDF FILES";
            File fpath = new File(mFilePath);
            if (!fpath.exists()) {
                fpath.mkdirs();
            }

            try {

                String description1 = "\n " + decription.getText().toString();
                try {

                    mFilePath = Environment.getExternalStorageDirectory() + "/" + "PDF FILES" + "/" + mTextEt.getText().toString() +
                            ".pdf";

                    PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));

                    mDoc.open();

                    mDoc.addAuthor("Padmaja Bollu");

                    //Font ftitle= FontFactory.getFont(FontFactory.TIMES_BOLD);
                    Font ftitle = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
                    com.itextpdf.text.Paragraph title = new com.itextpdf.text.Paragraph(description1, ftitle);
                    title.setAlignment(Paragraph.ALIGN_CENTER);
                    //title.add(t);
                    Font fcontent = FontFactory.getFont(FontFactory.TIMES_BOLD, 13);

                    com.itextpdf.text.Paragraph content = new com.itextpdf.text.Paragraph(signature1, fcontent);
                    content.setAlignment(Paragraph.ALIGN_LEFT);

                    Image image = Image.getInstance(bytes);

                    float documentWidth = mDoc.getPageSize().getWidth() - mDoc.leftMargin() - mDoc.rightMargin();
                    float documentHeight = mDoc.getPageSize().getHeight() - mDoc.topMargin() - mDoc.bottomMargin();
                    image.scaleToFit(documentWidth, documentHeight);
                    mDoc.add(image);

                    mDoc.add(title);
                    mDoc.add(content);

                    Toast.makeText(imagetopdf.this, mTextEt.getText().toString() + ".pdf\nis saved to \n" + mFilePath, Toast.LENGTH_LONG).show();

                    mDoc.close();
                    Intent first = new Intent(imagetopdf.this, MainActivity.class);
                    startActivity(first);
                    finish();

                    boolean_save = true;

                } catch (Exception e) {
                    Toast.makeText(imagetopdf.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }

            } catch (Exception e) {
                Toast.makeText(imagetopdf.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            Intent intent_main = new Intent(imagetopdf.this, MainActivity.class);
            startActivity(intent_main);
            finish();
        }

        else if (id == R.id.shareapp) {
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
        else if(id==android.R.id.home)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    public void checkPermission(String permission,int requestCode)
    {
        if(ContextCompat.checkSelfPermission(imagetopdf.this,permission)==PackageManager.PERMISSION_DENIED)
        {

            ActivityCompat.requestPermissions(imagetopdf.this,new String[]{permission},requestCode);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==STORAGE_PERMISSION_CODE_CAMERA)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"Camera Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Camera Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==STORAGE_PERMISSION_CODE_GALLERY)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"Storage Read Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Storage Read Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==STORAGE_PERMISSION_CODE_WRITE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"Storage Write Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Storage Write Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==2 && resultCode== Activity.RESULT_OK)
        {
            uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();

            /*
             Bitmap bitmap1 = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            float degrees = 90;//rotation degree
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            //imageView.setRotation(imageView.getRotation()+90);
            //Toast.makeText(image_to_pdf.this,geturl,Toast.LENGTH_SHORT).show();
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            imageView.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();

             */
        }
        else if (requestCode==1 && resultCode== Activity.RESULT_OK)
        {
            Bitmap bitmap1 = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                 //bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            float degrees = 90;//rotation degree
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            //imageView.setRotation(imageView.getRotation()+90);
            //Toast.makeText(image_to_pdf.this,geturl,Toast.LENGTH_SHORT).show();
            try {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                bytes=byteArrayOutputStream.toByteArray();
                Toast.makeText(getApplicationContext(),"Image is saved at "+imageFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();


            }
            catch (NullPointerException ne)
            {
                ne.printStackTrace();
                Toast.makeText(getApplicationContext(),"Capture once again !!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }


        }

    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}