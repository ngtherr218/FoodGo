package com.example.orderfoodbtl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Product;
import com.example.orderfoodbtl.ProductDetailActivity;
import com.example.orderfoodbtl.R;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    private List<Product> productList;
    DBHelper dbHelper;

    public FavoriteAdapter(Context context, List<Product> productList) {
        dbHelper = new DBHelper(context);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameProduct.setText(product.getName());
        holder.nameCategory.setText(product.getCategory());
        holder.rateProduct.setText(String.valueOf(product.getRating()));
        holder.imgProduct.setImageResource(product.getImageResId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product_id", product.getId());
                intent.putExtra("product_name",product.getName());
                intent.putExtra("img_product",product.getImageResId());
                intent.putExtra("product_rating",product.getRating());
                intent.putExtra("product_price",product.getPrice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder{

        ImageView imgProduct;
        TextView nameProduct, nameCategory,rateProduct;
        ImageView next;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            nameCategory = itemView.findViewById(R.id.nameCategory);
            rateProduct = itemView.findViewById(R.id.rateProduct);
            next = itemView.findViewById(R.id.next);
        }
    }
}
