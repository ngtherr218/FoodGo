package com.example.orderfoodbtl;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfoodbtl.DBHelper.DBHelper;

public class SignInActivity extends AppCompatActivity {
    EditText et_nameSI,et_passSI;
    Button bt_signin;
    TextView tv_signup;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        dbHelper = new DBHelper(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_nameSI = findViewById(R.id.et_nameSI);
        et_passSI = findViewById(R.id.et_passSI);
        bt_signin = findViewById(R.id.bt_signin);
        tv_signup = findViewById(R.id.tv_signUp);

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, Signup.class);
                startActivity(intent);
            }
        });
        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInUser();
            }
        });
    }
    private void SignInUser() {
        String name = et_nameSI.getText().toString().trim();
        String password = et_passSI.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }


        // Check if email already exists
        if (dbHelper.checkAccountExists(name, password)) {
            int userID = dbHelper.getUserIdByUserName(name);
            String email = dbHelper.getEmailByUserId(userID);
            dbHelper.saveUserInfo(this,userID,email,name);
            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "User or password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}