package com.example.policy;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.policy.Note.AdapterNote;
import com.example.policy.Note.TextNote;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewImg;
    RecyclerView noteRec;
    String note;
    AdapterNote adapterNote;
    List<TextNote> notes;
    Adapter imgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerViewImg = findViewById(R.id.rec);
        notes = new ArrayList<>();
        adapterNote = new AdapterNote(notes);
        noteRec = findViewById(R.id.recText);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noteRec.setLayoutManager(linearLayoutManager);
        noteRec.setAdapter(adapterNote);
        checkPermissions();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewImg.setLayoutManager(gridLayoutManager);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapterNote.removeAt(viewHolder.getAdapterPosition());
            }
        });
        touchHelper.attachToRecyclerView(noteRec);
        createFolder();
        reinit();
    }


    public void checkPermissions(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return;
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA},1);
        }
    }

    public void createFolder(){
        File folder = new File(Environment.getExternalStorageDirectory()+File.separator+"Policy");
        boolean success = true;
        if (!folder.exists()){
            success = folder.mkdir();
        }
        if (success) {
            reinit();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenGalary(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            return;
        }
        Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,11);

    }

    public void openCamera(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[] {Manifest.permission.CAMERA},1);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }

    private void reinit(){
        String path = Environment.getExternalStorageDirectory().toString()+"/Policy";
        File directory = new File(path);
        File[] files = directory.listFiles();
        recyclerViewImg.setAdapter(new Adapter(files));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i("PERMISSION"," GRANTED");
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,bytes);
            File file = new File(Environment.getExternalStorageDirectory().toString()+"/Policy", System.currentTimeMillis()+".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes.toByteArray());
                fileOutputStream.close();
                reinit();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void NewNote(View view) {
        final EditText input = new EditText(this);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("New Note")
                .setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        note = input.getText().toString();
                        if (!note.isEmpty()){
                            TextNote txtNote = new TextNote(note);
                            notes.add(txtNote);
                        }
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        alertDialog.show();
    }

}
