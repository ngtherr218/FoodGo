package com.example.orderfoodbtl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.InvoiceDetailActivity;
import com.example.orderfoodbtl.Model.Invoice;
import com.example.orderfoodbtl.R;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private Context context;
    private List<Invoice> ListInvoice;
    DBHelper dbHelper;

    public InvoiceAdapter(Context context, List<Invoice> listInvoice) {
        this.context = context;
        ListInvoice = listInvoice;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_item, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice invoice = ListInvoice.get(position);
        holder.Valueinvoice_id.setText(String.valueOf(invoice.getId()));
        holder.Name.setText(invoice.getUserName());
        holder.Valueinvoice_date.setText(invoice.getDate());
        holder.Valueinvoice_total_amount.setText(String.valueOf(invoice.getTotal_amount()));
        holder.Valueinvoice_Status.setText(invoice.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InvoiceDetailActivity.class);
                intent.putExtra("invoice_id", String.valueOf(invoice.getId())); // Đảm bảo giá trị không phải là null
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListInvoice.size();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder{
        TextView Valueinvoice_id,Name,Valueinvoice_date,Valueinvoice_total_amount,Valueinvoice_Status;
        public InvoiceViewHolder(@NonNull View itemView) {
        super(itemView);
        Valueinvoice_id= itemView.findViewById(R.id.Valueinvoice_id);
        Name= itemView.findViewById(R.id.Name);
        Valueinvoice_date =  itemView.findViewById(R.id.Valueinvoice_date);
        Valueinvoice_total_amount= itemView.findViewById(R.id.Valueinvoice_total_amount);
        Valueinvoice_Status= itemView.findViewById(R.id.Valueinvoice_Status);
    }
}
}
