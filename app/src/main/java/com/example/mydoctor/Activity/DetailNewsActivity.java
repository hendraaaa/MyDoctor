package com.example.mydoctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mydoctor.Model.Berita;
import com.example.mydoctor.R;

public class DetailNewsActivity extends AppCompatActivity {
    public static final String EXTRA_NEWS = "extra_news";
    private Berita berita;
    private ImageView imageView;
    private TextView tvHeader,tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        initToolbar();
        imageView = findViewById(R.id.image);
        tvHeader = findViewById(R.id.headerNews);
        tvDesc = findViewById(R.id.descNews);
        berita = getIntent().getParcelableExtra(EXTRA_NEWS);

        tvHeader.setText(berita.getJudul());

        Glide
                .with(DetailNewsActivity.this)
                .load(berita.getImage())
                .into(imageView);
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News Good");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}