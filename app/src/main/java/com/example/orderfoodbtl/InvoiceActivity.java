package com.example.orderfoodbtl;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodbtl.Adapter.InvoiceAdapter;
import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Invoice;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InvoiceAdapter invoiceAdapter;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.RecycleViewInvoice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        int userID = dbHelper.getUserId(this);
        List<Invoice> invoiceList = dbHelper.getAllInvoice(userID);

        invoiceAdapter = new InvoiceAdapter(this,invoiceList);
        recyclerView.setAdapter(invoiceAdapter);
    }
}