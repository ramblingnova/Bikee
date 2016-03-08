package com.example.tacademy.bikee.common;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tacademy.bikee.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class SmallMapActivity extends AppCompatActivity {
    GoogleMap gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_map);

        gm = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.small_map)).getMap();
//        captureScreen();
    }

    public void captureScreen() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                OutputStream fout = null;
                String filePath = "/sdcard/1.png";
                try {
                    fout = openFileOutput(filePath,
                            MODE_WORLD_READABLE);
                    // Write the string to the file
                    snapshot.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                    fout.flush();
                    fout.close();
                } catch (FileNotFoundException e) {
                    Log.d("ImageCapture", "FileNotFoundException");
                    Log.d("ImageCapture", e.getMessage());
                    filePath = "";
                } catch (IOException e) {
                    Log.d("ImageCapture", "IOException");
                    Log.d("ImageCapture", e.getMessage());
                    filePath = "";
                }
                openShareImageDialog(filePath);
            }
        };
        gm.snapshot(callback);
    }

    public void openShareImageDialog(String filePath) {
        File file = this.getFileStreamPath(filePath);

        if (!filePath.equals("")) {
            final ContentValues values = new ContentValues(2);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            final Uri contentUriFile = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
            startActivity(Intent.createChooser(intent, "Share Image"));
        } else {
        }
    }
}