package com.example.orderfoodbtl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfoodbtl.DBHelper.DBHelper;

public class ProductDetailActivity extends AppCompatActivity {

    ImageButton back,minus,plus;
    Button order;
    TextView quantity,price,rating,productName;
    ImageView productImg;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String productname = intent.getStringExtra("product_name");
        int imgProductResId = intent.getIntExtra("img_product",R.drawable.pngwing_11); // Giá trị mặc định
        double productRating = intent.getDoubleExtra("product_rating", 0.0f);
        double productPrice = intent.getDoubleExtra("product_price", 0.0);
        back = findViewById(R.id.back);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        order = findViewById(R.id.order);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        rating = findViewById(R.id.rating);
        productName = findViewById(R.id.productName);
        productImg = findViewById(R.id.productImg);

        productName.setText(productname);
        productImg.setImageResource(imgProductResId);
        price.setText(String.format("$%.2f", productPrice));
        rating.setText(String.format("%.1f", productRating));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus();
                updatePrice(productPrice);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus();
                updatePrice(productPrice);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                int id = intent1.getIntExtra("product_id",1);
                int productQuantity = Integer.parseInt(quantity.getText().toString());
                Log.d("ID", String.valueOf(id));
                Log.d("quantity",quantity.getText().toString());
                dbHelper = new DBHelper(ProductDetailActivity.this);
                int userID = dbHelper.getUserId(ProductDetailActivity.this);
                if (userID != -1) {
                    dbHelper.addToCart(id,userID,productQuantity);
                    Toast.makeText(ProductDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ProductDetailActivity.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void minus(){
        int SL = Integer.parseInt(quantity.getText().toString());
        if(SL == 1){
            return;
        }
        else{
            SL -= 1;
        }
        quantity.setText(String.valueOf(SL));
    }
    public void plus(){
        int SL = Integer.parseInt(quantity.getText().toString());
        SL += 1;
        quantity.setText(String.valueOf(SL));
    }
    public void updatePrice(double productPrice){
        double Price = productPrice;
        int SL = Integer.parseInt(quantity.getText().toString());
        Price *= SL;
        price.setText(String.format("$%.2f", Price));
    }
}