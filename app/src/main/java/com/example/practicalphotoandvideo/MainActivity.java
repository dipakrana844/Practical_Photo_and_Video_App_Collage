package com.example.practicalphotoandvideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button img, video, play;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.btn_1);
        video = findViewById(R.id.btn_2);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File directory = new File(getExternalMediaDirs()[0].getAbsolutePath() +
                        File.separator+getResources().getString(R.string.app_name));
                if(!directory.exists()){
                    directory.mkdirs();
                }
                file = new File(directory, System.currentTimeMillis() + ".jpg");
                Uri uri = FileProvider.getUriForFile(MainActivity.this,getPackageName() + ".provider",file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent, 1000);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                File directory = new File(getExternalMediaDirs()[0].getAbsolutePath()+
                        File.separator+getResources().getString(R.string.app_name));
                if (!directory.exists()){
                    directory.mkdirs();
                }
                file = new File(directory,System.currentTimeMillis()+".mp4");

                Uri uri = FileProvider.getUriForFile(MainActivity.this,getPackageName()+".provider",file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent,2000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Image Captured", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);
        }else if(requestCode == 2000 && resultCode == RESULT_OK){
            Toast.makeText(this, "video Captured", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);
        }
    }
}
