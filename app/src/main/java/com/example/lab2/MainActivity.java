package com.example.lab2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lab2.models.Product;

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
