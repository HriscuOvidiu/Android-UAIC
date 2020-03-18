package com.example.lab2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class ProductDetails extends AppCompatActivity {

    Bundle extras;
    TextView description;
    ImageView image;
    Button selectQuantity;

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

        selectQuantity = findViewById(R.id.select);
        selectQuantity.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                AlertDialog alertDialog = createNumberPickerDialog();
                alertDialog.show();
            }
        });
    }

    private AlertDialog createNumberPickerDialog() {
        NumberPicker np = new NumberPicker(this);
        np.setMinValue(0);
        np.setMaxValue(10);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(np);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
