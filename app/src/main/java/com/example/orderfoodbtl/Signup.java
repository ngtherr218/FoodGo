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

public class Signup extends AppCompatActivity {
    EditText et_name,et_pass,et_email;
    Button bt_signup;
    TextView tv_signin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        dbHelper = new DBHelper(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        et_email = findViewById(R.id.et_email);
        bt_signup = findViewById(R.id.bt_signup);
        tv_signin = findViewById(R.id.tv_signin);
        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        bt_signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registerUser();
        }
    });
    }
    private void registerUser() {
        if (dbHelper == null) {
            Toast.makeText(this, "Database Helper not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = et_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_pass.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(dbHelper.checkUserExists(name)){
            Toast.makeText(this, "User name is exist", Toast.LENGTH_SHORT).show();
            return;
        }
       dbHelper.addUser(name, email,password);

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Signup.this, SignInActivity.class);
        startActivity(intent);
    }
}