package com.example.lab2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab2.models.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private List<Product> productList;
    public ListView listView;
    public int myInt;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            this.myInt = savedInstanceState.getInt("myInt");
            Log.d("myInt", String.valueOf(this.myInt));
        }

        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Grocery Store");

        productList = this.getProducts();
        listView = (ListView) findViewById(R.id.listView);

        List<String> namesList = null;
        namesList = productList.stream().map(p -> p.getName()).collect(Collectors.toList());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, namesList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);

                Product currentProduct = productList.get(position);

                intent.putExtra("name", currentProduct.getName());
                intent.putExtra("description", currentProduct.getDescription());
                intent.putExtra("img", currentProduct.getImg());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("On start called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("On resume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("On pause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("On stop called");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("On restart called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("On destroy called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact:
                visitContactPage();
                break;
            case R.id.preferences:
                visitPreferencePage();
                break;
            case R.id.write:
                writeFile("myFile", "myBody");
                break;
            case R.id.read:
                readFile("myFile");
                break;
        }

        return true;
    }

    private void visitContactPage() {
        Intent intent = new Intent(this, Contact.class);

        startActivity(intent);
    }

    private void visitPreferencePage() {
        Intent intent = new Intent(this, Preferences.class);

        startActivity(intent);
    }

    private void writeFile(String filename, String body) {
        try{
            File ffile = new File(this.getFilesDir(), filename);
            FileWriter writer = new FileWriter(ffile);
            writer.append(body);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "File written to disk successfully", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readFile(String filename) {
        try {
            FileInputStream fis = this.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            return;
        } catch (UnsupportedEncodingException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        this.myInt = 1;
        savedInstanceState.putInt("myInt", this.myInt);
    }

    private List<Product> getProducts()
    {
        List<Product> list = new ArrayList<Product>();
        list.add(new Product("Apple", R.drawable.apple, "A nice apple"));
        list.add(new Product("Banana", R.drawable.banana, "A nice banana"));
        list.add(new Product("Kiwi", R.drawable.kiwi, "A nice kiwi"));
        list.add(new Product("Watermelon", R.drawable.watermelon, "A nice watermelon"));
        list.add(new Product("Strawberry", R.drawable.strawberry, "A nice strawberry"));

        return list;
    }
}
