package com.example.lab2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class ProductDetails extends AppCompatActivity {

    Bundle extras;
    TextView description;
    ImageView image;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);
        extras = getIntent().getExtras();

        getSupportActionBar().setTitle(extras.getString("name"));
        description.setText(extras.getString("description"));
        image.setImageResource(extras.getInt("img"));
    }
}
