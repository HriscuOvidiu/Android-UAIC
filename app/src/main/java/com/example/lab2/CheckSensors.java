package com.example.lab2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.stream.Collectors;

public class CheckSensors extends AppCompatActivity implements SensorEventListener {

    ListView listView;
    TextView textView;
    Button button;

    LocationManager locationManager;
    LocationListener locationListener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sensors);

        listView = findViewById(R.id.list);
        textView = findViewById(R.id.location);
        button = findViewById(R.id.button);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String longitude = "" + location.getLongitude();
                String latitude = "" + location.getLatitude();

                String s = "Longitude: " + longitude + "\nLatitude: " + latitude;

                textView.setText(s);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                textView.setText("");
            }

            @Override
            public void onProviderDisabled(String provider) {
                textView.setText("Provider not available");
            }
        };

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> stringSensors = sensors.stream().map(s->s.getName()).collect(Collectors.toList());

        for(int i = 0; i < sensors.size(); i++) {
            sensorManager.registerListener(this, sensors.get(i), SensorManager.SENSOR_DELAY_NORMAL);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringSensors);
        listView.setAdapter(arrayAdapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 1);
            return;
        } else {
            listenToLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantedResults) {
        switch (requestCode){
            case 1:
                if (grantedResults.length > 0 && grantedResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listenToLocation();
                }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String text = "Name: " + event.sensor.getName() + "Values: " + arrToString(event.values);
        System.out.println(text);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void listenToLocation() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
            }
        });
    }

    private String arrToString(float[] values) {
        String f = "[";

        for (int i=0;i<values.length;i++) {
            if (i != 0) {
                f += ", ";
            }

            f+=values[i];
        }
        f+="]";

        return f;
    }
}
