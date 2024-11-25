package com.example.orderfoodbtl;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodbtl.Adapter.CartAdapter;
import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private DBHelper databaseHelper;
    private TextView subtotalValue, totalValue,deliveryValue;
    private Button checkoutButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        subtotalValue = view.findViewById(R.id.subtotalValue);
        totalValue = view.findViewById(R.id.totalValue);
        deliveryValue = view.findViewById(R.id.deliveryValue);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        databaseHelper = new DBHelper(getContext());
        int userID = databaseHelper.getUserId(getContext());
        recyclerView = view.findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Product> caList = databaseHelper.getAllCart(userID);

        cartAdapter = new CartAdapter(getContext(),caList,deliveryValue,subtotalValue,totalValue);
        recyclerView.setAdapter(cartAdapter);

        if(databaseHelper.getAllCart(userID).isEmpty()){
            deliveryValue.setText("$0.00");
        }

        double subtotal = databaseHelper.SubTotalPrice(userID);
        double delivery = Double.parseDouble(deliveryValue.getText().toString().replace("$",""));

        double total = subtotal + delivery;

        String formattedSubtotal = "$" + String.format("%.2f", subtotal);
        String formattedtotal = "$" + String.format("%.2f", total);

        subtotalValue.setText(formattedSubtotal);
        totalValue.setText(formattedtotal);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(databaseHelper.getAllCart(userID).isEmpty()){
                    Toast.makeText(requireContext(), "Nothing to buy", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                intent.putExtra("subtotalValue",subtotalValue.getText());
                intent.putExtra("totalValue",totalValue.getText());
                startActivity(intent);
            }
        });

        return view;
    }
}