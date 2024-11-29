package com.example.orderfoodbtl;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.orderfoodbtl.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Marker currentMarker, shopMarker;
    private ImageButton btnSubmit;
    private LatLng location, shopLocation;
    private Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        String totalValue = intent.getStringExtra("totalValue");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, PaymentActivity.class);
                intent.putExtra("newLatitude", location.latitude);
                intent.putExtra("newLongitude", location.longitude);
                intent.putExtra("distance", calculateDistance(location.latitude, location.longitude, shopLocation.latitude,shopLocation.longitude));
                intent.putExtra("totalValue", totalValue);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        intent = getIntent();
        double longitude = intent.getDoubleExtra("longitude", -1);
        double latitude = intent.getDoubleExtra("latitude", -1);

        // Add a marker in your location and move the camera
        location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("You are here"));
        float zoomLevel = 16.0f; // Mức zoom, giá trị từ 2.0 (nhỏ nhất) đến 21.0 (lớn nhất)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

        MarkerOptions markerOptions = new MarkerOptions().position(location).title("Drag me!").draggable(true); // Cho phép kéo thả
        currentMarker = mMap.addMarker(markerOptions);

        shopLocation = new LatLng(21.00118127757313,105.8480665855491);
        shopMarker = mMap.addMarker(new MarkerOptions()
                .position(shopLocation).title("Shoppp").draggable(true));

        // Lắng nghe sự kiện kéo thả marker
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // Xử lý khi kết thúc kéo
                location = marker.getPosition();
                Toast.makeText(MapsActivity.this, "Marker dropped at: " + location.latitude + ", " + location.longitude, Toast.LENGTH_SHORT).show();

                // Xóa marker ban đầu
                if (currentMarker != null) {
                    currentMarker.remove();
                    currentMarker = null; // Tránh tham chiếu thừa
                }

                // Tạo marker mới tại vị trí thả
                currentMarker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title("New Location")
                        .draggable(true)); // Tiếp tục cho phép kéo thả nếu cần

                // Di chuyển camera đến vị trí mới (tùy chọn)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
            }
        });
    }

    private  double  calculateDistance(double lat1, double lon1, double lat2, double lon2){
        // Sử dụng đối tượng Location để tính khoảng cách giữa hai điểm
        Location location1 = new Location("");
        location1.setLatitude(lat1);
        location1.setLongitude(lon1);

        Location location2 = new Location("");
        location2.setLatitude(lat2);
        location2.setLongitude(lon2);

        // Tính khoảng cách giữa hai điểm theo đơn vị mét
        float distanceInMeters = location1.distanceTo(location2);

        // Chuyển đổi khoảng cách sang km
        return distanceInMeters / 1000;
    }
}