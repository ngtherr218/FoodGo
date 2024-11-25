package com.example.orderfoodbtl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Product;
import com.example.orderfoodbtl.ProductDetailActivity;
import com.example.orderfoodbtl.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    DBHelper dbHelper;
    public ProductAdapter(Context context, List<Product> productList) {
        dbHelper = new DBHelper(context);
        this.context = context;
        this.productList = productList;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductCategory.setText(product.getCategory());
        holder.tvRating.setText(String.valueOf(product.getRating()));
        holder.imgProduct.setImageResource(product.getImageResId());

        holder.ivFavorite.setOnClickListener(v ->{
            boolean isFavorite = dbHelper.getFavoriteById(product.getId(), dbHelper.getUserId(context));

            if(!isFavorite){
                int userID = dbHelper.getUserId(context);
                if(userID != -1) {
                    dbHelper.addToFavorite(product.getId(),userID);
                    holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_25);
                    Toast.makeText(context, "Added to Favorites!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                int userID = dbHelper.getUserId(context);
                int id = dbHelper.getIdFavoriteByPIUI(product.getId(),userID);
                if (id != -1) { // Kiểm tra nếu ID hợp lệ
                    dbHelper.deleteFavorite(String.valueOf(id)); // Xoá mục yêu thích
                    Toast.makeText(context, product.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
                    holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
                } else {
                    Toast.makeText(context, "Unable to remove " + product.getName() + " from favorites", Toast.LENGTH_SHORT).show();
                }
             }
        });
        holder.add.setOnClickListener(v -> {
            int userID = dbHelper.getUserId(context);
            if(userID != -1) {
                dbHelper.addToCart(product.getId(),userID,1);
                Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product_id", product.getId());
            intent.putExtra("product_name",product.getName());
            intent.putExtra("img_product",product.getImageResId());
            intent.putExtra("product_rating",product.getRating());
            intent.putExtra("product_price",product.getPrice());
            context.startActivity(intent);
        });
        if(dbHelper.getFavoriteById(product.getId(), dbHelper.getUserId(context))){
            holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_25);
        }else{
            holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, ivFavorite,add;
        TextView tvProductName, tvProductCategory, tvRating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            ivFavorite = itemView.findViewById(R.id.tvFavorite);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            tvRating = itemView.findViewById(R.id.tvRating);
            add = itemView.findViewById(R.id.add);
        }
    }
}
