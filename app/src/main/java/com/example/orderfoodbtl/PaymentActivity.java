package com.example.orderfoodbtl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfoodbtl.DBHelper.DBHelper;

public class PaymentActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView subtotalValue, totalValue, totalValue2, submit;
    ImageButton back;
    Button goBack;
    EditText et_address;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        subtotalValue = findViewById(R.id.subtotalValue);
        totalValue = findViewById(R.id.totalValue);
        totalValue2 = findViewById(R.id.totalValue2);
        back = findViewById(R.id.back);
        submit = findViewById(R.id.button_submit);
        et_address = findViewById(R.id.et_address);

        Intent intent = getIntent();
        subtotalValue.setText(intent.getStringExtra("subtotalValue"));
        totalValue.setText(intent.getStringExtra("totalValue"));
        totalValue2.setText(intent.getStringExtra("totalValue"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_address.getText().toString().isEmpty()){
                    Toast.makeText(PaymentActivity.this, "Enter the address to send to.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog = new Dialog(PaymentActivity.this);
                dialog.setContentView(R.layout.dialog_sucess);
                dialog.setCancelable(false);
                // Áp dụng nền với bo góc
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);
                // Tùy chỉnh kích thước dialog
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                goBack = dialog.findViewById(R.id.goBack);
                goBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper = new DBHelper(PaymentActivity.this);
                        int userID = dbHelper.getUserId(PaymentActivity.this);
                        dbHelper.deleteAllCard(userID);
                        Intent intent1 = new Intent(PaymentActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        finishAffinity();
                    }
                });
            }
        });
    }
}
