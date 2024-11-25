package com.example.orderfoodbtl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfoodbtl.DBHelper.DBHelper;

public class ChangeInformationActivity extends AppCompatActivity {

    Button change;
    DBHelper dbHelper;
    EditText edName, edPass, edEmail;
    ImageView goback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor);

        edName = findViewById(R.id.nameUser);
        edEmail = findViewById(R.id.emailUser);
        edPass = findViewById(R.id.passUser);
        change = findViewById(R.id.change);
        goback = findViewById(R.id.goBack);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        int id = intent.getIntExtra("id",-1);

        edName.setText(name);
        edEmail.setText(email);

        dbHelper = new DBHelper(this);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra dữ liệu nhập
                if (edName.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edPass.getText().toString().isEmpty()) {
                    Toast.makeText(ChangeInformationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gọi hàm cập nhật
                long result = dbHelper.updateUser(
                        id,
                        edName.getText().toString(),
                        edEmail.getText().toString(),
                        edPass.getText().toString()
                );

                // Kiểm tra kết quả cập nhật
                if (result > 0) {
                    Toast.makeText(ChangeInformationActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeInformationActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(ChangeInformationActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
