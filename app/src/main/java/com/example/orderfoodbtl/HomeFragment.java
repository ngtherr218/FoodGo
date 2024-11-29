package com.example.orderfoodbtl;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfoodbtl.Adapter.ProductAdapter;
import com.example.orderfoodbtl.DBHelper.DBHelper;
import com.example.orderfoodbtl.Model.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ImageView tvLoc;
    private EditText txtSearch;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DBHelper databaseHelper;
    private TextView all, fastFood, beverages,desserts ,appetizers,  vegetarianDishes, mainDishes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        all = view.findViewById(R.id.all);
        mainDishes = view.findViewById(R.id.mainDishes);
        vegetarianDishes = view.findViewById(R.id.vegetarianDishes);
        desserts = view.findViewById(R.id.desserts);
        fastFood = view.findViewById(R.id.fastFood);
        appetizers = view.findViewById(R.id.appetizers);
        beverages = view.findViewById(R.id.beverages);
        tvLoc = view.findViewById(R.id.tvLoc);
        txtSearch = view.findViewById(R.id.txtSearch);

        databaseHelper = new DBHelper(getContext());

        GradientDrawable border = new GradientDrawable();
        border.setShape(GradientDrawable.RECTANGLE); // Hình dạng (hình chữ nhật)
        border.setStroke(5, 0xFF000000); // Độ dày viền (5px) và màu viền (đen)
        border.setCornerRadius(20);
        all.setBackground(border);

        if(databaseHelper.getAllProducts().isEmpty()){
            databaseHelper.addProduct(new Product("Fast Food",R.drawable.khoai_tay_chien, "French fries", 4.9,10));
            databaseHelper.addProduct(new Product("Main Dishes",R.drawable.pho_bo, "Noodle Soup", 4.4,15));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.com_tam,"Broken rice",5,20));
            databaseHelper.addProduct(new Product("Fast Food", R.drawable.ga_ran,"Fried chicken", 4.3,18));
            databaseHelper.addProduct(new Product("Vegetarian Dishes", R.drawable.dau_phu,"Fried tofu", 4.2,10));
            databaseHelper.addProduct(new Product("Vegetarian Dishes", R.drawable.rau_cu,"Stir-fried vegetables", 4,15));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.nuoc_ngot,"Soft drink",4.6,12));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.bia,"Beer", 4.8,10));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.sinh_to,"Strawberry smoothie", 4.1,10));
            databaseHelper.addProduct(new Product("Fast Food", R.drawable.bap_rang_bo, "Popcorn", 4.5,12));
            databaseHelper.addProduct(new Product("Fast Food",R.drawable.nem_chua_ran, "Fried spring rolls", 4.9,10));
            databaseHelper.addProduct(new Product("Main Dishes",R.drawable.lau_ga, "Chicken hotpot", 4.4,30));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.muc_xao,"Fried squid",5,20));
            databaseHelper.addProduct(new Product("Appetizers", R.drawable.salad,"Salad", 4.3,18));
            databaseHelper.addProduct(new Product("Vegetarian Dishes", R.drawable.banh_chay,"Vegetarian cakes", 4.2,10));
            databaseHelper.addProduct(new Product("Vegetarian Dishes", R.drawable.sup_nam,"Mushroom soup", 4,15));
            databaseHelper.addProduct(new Product("Desserts", R.drawable.pudding,"Pudding",4.6,12));
            databaseHelper.addProduct(new Product("Desserts", R.drawable.kem,"Ice cream", 4.8,10));
            databaseHelper.addProduct(new Product("Desserts", R.drawable.che,"Sweet soup", 4.1,10));
            databaseHelper.addProduct(new Product("Fast Food", R.drawable.pngwing_7, "Chicken Burger", 4.5,12));
            databaseHelper.addProduct(new Product("Main Dishes",R.drawable.steak, "Steak", 4.4,15));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.pasta,"Pasta ",5,20));
            databaseHelper.addProduct(new Product("Main Dishes",R.drawable.ca_hoi, "Grilled Salmon", 4.4,15));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.lasagna,"Lasagna",5,20));
            databaseHelper.addProduct(new Product("Main Dishes",R.drawable.kim_chi, "Kimchi Stew ", 4.4,15));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.pung_bao,"Kung Pao Chicken",5,20));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.ca_kho_to,"Caramelized Fish",5,20));
            databaseHelper.addProduct(new Product("Main Dishes",R.drawable.beef_stew, "Beef Stew", 4.4,15));
            databaseHelper.addProduct(new Product("Main Dishes", R.drawable.pizza,"Pizza ",5,20));
            databaseHelper.addProduct(new Product("Appetizers", R.drawable.tom_coctail,"Shrimp Cocktail ", 4.3,18));
            databaseHelper.addProduct(new Product("Appetizers", R.drawable.trung_nhoi,"Deviled Eggs ", 4.3,18));
            databaseHelper.addProduct(new Product("Appetizers", R.drawable.banh_bot_loc,"Tapioca Dumplings", 4.3,18));
            databaseHelper.addProduct(new Product("Appetizers", R.drawable.pho_mai,"Cheese Platter", 4.3,18));
            databaseHelper.addProduct(new Product("Appetizers", R.drawable.moza,"Mozzarella Sticks", 4.3,18));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.capuchino,"Capuchino",4.6,12));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.whiskey,"Whiskey", 4.8,10));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.ma,"Margarita ", 4.1,10));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.pina,"Pina Colada",4.6,12));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.mango_juice,"Mango Juice", 4.8,10));
            databaseHelper.addProduct(new Product("Beverages", R.drawable.mocha_jiuce,"Iced Mocha", 4.1,10));
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 0, true));

        // Lấy danh sách sản phẩm từ SQLite
        List<Product> productList = databaseHelper.getAllProducts();

        // Thiết lập Adapter cho RecyclerView
        productAdapter = new ProductAdapter(getContext(),productList);
        recyclerView.setAdapter(productAdapter);

        tvLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByName(txtSearch.getText().toString());
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getAllProducts();
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        vegetarianDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByCategory("Vegetarian Dishes");
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });


        mainDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByCategory("Main Dishes");
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        appetizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByCategory("Appetizers");
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByCategory("Desserts");
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByCategory("Beverages");
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        fastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> productList = databaseHelper.getProductsByCategory("Fast Food");
                productAdapter = new ProductAdapter(getContext(),productList);
                recyclerView.setAdapter(productAdapter);
            }
        });

        return view;
    }


}