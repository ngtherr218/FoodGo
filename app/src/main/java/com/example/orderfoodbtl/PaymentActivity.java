package com.example.orderfoodbtl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.orderfoodbtl.DBHelper.DBHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView subtotalValue, totalValue, totalValue2, submit;
    ImageButton back, changeLocation;
    Button goBack;
    TextView et_address;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Dialog loadingDialog;
    boolean flag = true;
    double latitude, longitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        subtotalValue = findViewById(R.id.subtotalValue);
        totalValue = findViewById(R.id.totalValue);
        totalValue2 = findViewById(R.id.totalValue2);
        back = findViewById(R.id.back);
        submit = findViewById(R.id.button_submit);
        et_address = findViewById(R.id.et_address);
        changeLocation = findViewById(R.id.location);

        // Khởi tạo LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Khởi tạo dialog loading
        createLoadingDialog();

        // Hiển thị dialog loading
        showLoadingDialog();

        CircleProgressBar circleProgressBar = loadingDialog.findViewById(R.id.line_progress);
        CountDownTimer countDownTimer = new CountDownTimer(60000, 15) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (circleProgressBar.getProgress() == 100) {
                    flag = false;
                }
                if (circleProgressBar.getProgress() == 0) {
                    flag = true;
                }
                if (flag) {
                    circleProgressBar.setProgress(circleProgressBar.getProgress() + 1);
                } else {
                    circleProgressBar.setProgress(circleProgressBar.getProgress() - 1);
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();

        Intent intent = getIntent();
        subtotalValue.setText(intent.getStringExtra("totalValue"));

        if (intent.hasExtra("newLongitude") && intent.hasExtra("newLatitude")) {
            // Nếu có tọa độ từ MapsActivity
            longitude = intent.getDoubleExtra("newLongitude", -1);
            latitude = intent.getDoubleExtra("newLatitude", -1);

            // Cập nhật địa chỉ từ tọa độ được gửi từ MapsActivity
            if (longitude != -1 && latitude != -1) {
                getAddressFromLocation(latitude, longitude);
            } else {
                Toast.makeText(this, "Invalid coordinates received from MapsActivity", Toast.LENGTH_SHORT).show();
                getCurrentLocation(); // Lấy vị trí hiện tại nếu tọa độ không hợp lệ
            }

        } else {
            // Nếu không có tọa độ, lấy vị trí hiện tại
            getCurrentLocation();
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper = new DBHelper(PaymentActivity.this);
                int userID = dbHelper.getUserId(PaymentActivity.this);
                String valueString = totalValue2.getText().toString().replace("$", "").trim();
                double totalValue;
                try {
                    totalValue = Double.parseDouble(valueString.replace(",", "."));
                } catch (NumberFormatException e) {
                    totalValue = 0.0; // Giá trị mặc định nếu có lỗi
                    e.printStackTrace();
                }
                dbHelper.addInvoice(userID, totalValue);
                int invoiceID = dbHelper.getInvoiceID(userID);
                if (invoiceID != -1) {
                    dbHelper.addInvoiceDetail(invoiceID, userID);
                    Toast.makeText(PaymentActivity.this, "add Invoice successful", Toast.LENGTH_SHORT).show();
                }

                Dialog dialog = new Dialog(PaymentActivity.this);
                dialog.setContentView(R.layout.dialog_sucess);
                dialog.setCancelable(false);
                // Áp dụng nền với bo góc
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);
                // Tùy chỉnh kích thước dialog
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                goBack = dialog.findViewById(R.id.goBack);
                goBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper = new DBHelper(PaymentActivity.this);
                        int userID = dbHelper.getUserId(PaymentActivity.this);
                        dbHelper.deleteAllCard(userID);
                        Intent intent1 = new Intent(PaymentActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        finishAffinity();
                    }
                });
            }
        });

        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PaymentActivity.this, MapsActivity.class);
                intent1.putExtra("longitude",longitude);
                intent1.putExtra("latitude",latitude);
                intent1.putExtra("totalValue",intent.getStringExtra("totalValue"));
                startActivity(intent1);
                finish();
            }
        });

    }

    private void getCurrentLocation(){
        // Kiểm tra quyền truy cập vị trí
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Nếu quyền đã được cấp, bắt đầu lấy vị trí
            startListeningLocation();
        } else {
            // Nếu quyền chưa được cấp, yêu cầu quyền
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void startListeningLocation() {
        try {
            // Khởi tạo LocationListener
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Khi có thay đổi vị trí, nhận được thông báo
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    getAddressFromLocation(latitude, longitude);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // Phương thức này không còn được sử dụng trong API mới, có thể bỏ qua
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    // Khi GPS hoặc mạng được bật
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    // Khi GPS hoặc mạng bị tắt
                }
            };

            // Cập nhật vị trí mỗi 10 giây hoặc khi di chuyển tối thiểu 10 mét
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, locationListener);
            }
        } catch (SecurityException e) {
            // Xử lý nếu không có quyền truy cập vị trí
            Toast.makeText(this, "No location access", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dừng việc theo dõi vị trí khi Activity bị hủy
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            // Lấy danh sách các địa chỉ từ tọa độ
            List<android.location.Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address address = addresses.get(0);

                // Lấy các thành phần của địa chỉ
                StringBuilder addressString = new StringBuilder();

                // Số nhà, số ngõ, đường
                String street = address.getThoroughfare();  // Đường
                String subStreet = address.getSubThoroughfare();  // Số nhà, số ngõ
                if (subStreet != null) {
                    addressString.append(subStreet).append(", ");
                }
                if (street != null) {
                    addressString.append(street).append(", ");
                }

                // Phường, xã, quận
                String subLocality = address.getSubLocality();  // Phường, xã
                if (subLocality != null) {
                    addressString.append(subLocality).append(", ");
                }

                String locality = address.getLocality();  // Quận, thành phố
                if (locality != null) {
                    addressString.append(locality).append(", ");
                }

                // Tỉnh, thành phố
                String adminArea = address.getAdminArea();  // Tỉnh, thành phố
                if (adminArea != null) {
                    addressString.append(adminArea).append(", ");
                }

                // Quốc gia
                String country = address.getCountryName();
                if (country != null) {
                    addressString.append(country);
                }

                // Cập nhật địa chỉ lên TextView
                et_address.setText(addressString.toString());
            } else {
                et_address.setText("Address not found");
            }

        } catch (IOException e) {
            e.printStackTrace();
            et_address.setText("Error getting location name");
        } finally {
            // Ẩn dialog loading khi hoàn tất
            hideLoadingDialog();
        }
    }

    private void createLoadingDialog() {
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.border_dialog);
    }

    private void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
