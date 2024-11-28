package com.example.orderfoodbtl;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodbtl.Adapter.InvoiceDetailAdapter;
import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Product;

import java.util.List;

public class InvoiceDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InvoiceDetailAdapter invoiceDetailAdapter;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        String invoiceId = intent.getStringExtra("invoice_id");
        int invoiceID = Integer.parseInt(invoiceId);
        recyclerView = findViewById(R.id.RvDetailInvoice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        List<Product> InvoiceDetailList = dbHelper.getInvoiceDetail(invoiceID);

        invoiceDetailAdapter = new InvoiceDetailAdapter(this,InvoiceDetailList);
        recyclerView.setAdapter(invoiceDetailAdapter);

    }
}