package com.example.assessment2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.fullImageView);

        Intent intent = getIntent();
        if (intent != null) {
            String imageUri = intent.getStringExtra("imageUri");
            imageView.setImageURI(Uri.parse(imageUri));
        }
    }
}