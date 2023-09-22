package com.example.assessment2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private List<String> mediaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Assuming 3 columns in your grid.
        photoAdapter = new PhotoAdapter(mediaList);
        recyclerView.setAdapter(photoAdapter);

        if (arePermissionsGranted()) {
            new LoadPhotosTask().execute();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
        }
    }

    private boolean arePermissionsGranted() {
        return (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean allPermissionsGranted = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "Permission " + permissions[i] + " not granted.");
                    allPermissionsGranted = false;
                }
            }

            if (allPermissionsGranted) {
                new LoadPhotosTask().execute();
            }
        }
    }





    private class LoadPhotosTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> tempMediaList = new ArrayList<>();
            Cursor cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID},
                    null,
                    null,
                    MediaStore.Images.Media.DATE_ADDED + " DESC"
            );

            if (cursor != null) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    Uri contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.toString(id));
                    tempMediaList.add(contentUri.toString());
                }
                cursor.close();
            }
            return tempMediaList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            mediaList.clear();
            mediaList.addAll(result);
            photoAdapter.notifyDataSetChanged();
        }
    }
}