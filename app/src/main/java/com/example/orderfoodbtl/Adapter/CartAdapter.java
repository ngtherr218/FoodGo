package com.example.orderfoodbtl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Product;
import com.example.orderfoodbtl.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Product> cartList;
    DBHelper dbHelper;
    private TextView subtotalValue, totalValue;

    public CartAdapter( Context context,List<Product> cartList, TextView totalValue) {
        this.cartList = cartList;
        this.context = context;
        this.totalValue = totalValue;
    }

    public void updateCartItems(List<Product> newcartList) {
        this.cartList = newcartList;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Product cartItem = cartList.get(position);
        holder.itemName.setText(cartItem.getName());
        holder.itemDetails.setText(cartItem.getCategory());
        holder.itemPrice.setText(String.valueOf(cartItem.getTotalPrice()));
        holder.itemImage.setImageResource(cartItem.getImageResId());
        holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));
        dbHelper = new DBHelper(context);
        int userID = dbHelper.getUserId(context);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID != -1) {
                    dbHelper.deleteformCart(cartItem.getId(),userID);
                    Toast.makeText(context, "Removed to cart!", Toast.LENGTH_SHORT).show();
                    refreshCart();
                }
                else{
                    Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID != -1) {
                    dbHelper.IncreaseQuantity(cartItem.getId(),userID,cartItem.getQuantity());
                    Toast.makeText(context, "Increase quantity!", Toast.LENGTH_SHORT).show();
                    refreshCart();
                }
                else{
                    Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItem.getQuantity() == 1){
                    return;
                }else{
                    if(userID != -1) {
                        dbHelper.DecreaseQuantity(cartItem.getId(), userID, cartItem.getQuantity());
                        Toast.makeText(context, "Decrease quantity!", Toast.LENGTH_SHORT).show();
                        refreshCart();
                    }
                    else{
                        Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void refreshCart() {

        dbHelper = new DBHelper(context);
        int userID = dbHelper.getUserId(context);
        List<Product> updatedCartList = dbHelper.getAllCart(userID);
        updateCartItems(updatedCartList);
        notifyDataSetChanged();

        // Nếu có các thành phần khác như tổng giá, hãy cập nhật chúng ở đây
        double subtotal = dbHelper.SubTotalPrice(userID);
        double total = subtotal;

        String formattedtotal = "$" + String.format("%.2f", total);
        totalValue.setText(formattedtotal);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemName, itemDetails, itemPrice,itemQuantity;
        ImageButton decreaseQuantity,increaseQuantity,deleteButton;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemDetails = itemView.findViewById(R.id.itemDetails);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
