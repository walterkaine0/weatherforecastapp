package com.example.weatherforecastapp;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforecastapp.Model.weatherBased;
import com.example.weatherforecastapp.API.RetrofitClient;
import com.example.weatherforecastapp.API.WeatherApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "10a641a16d59f30f407c8e364764073c";
    private static final int CODE_PERMISSION = 100;
    private TextView cityText;
    private RecyclerView recycleWeather;
    private FusedLocationProviderClient locationClient;
    private weatherAdapter adapter;
    @Override
    protected void onCreate(Bundle safedState) {
        super.onCreate(safedState);
        setContentView(R.layout.activity_main);
        cityText = findViewById(R.id.cityNameTextView);
        recycleWeather = findViewById(R.id.weatherRecyclerView);
        Button buttonSearch = findViewById(R.id.searchCityButton);
        recycleWeather.setLayoutManager(new LinearLayoutManager(this));
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        buttonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, searchCities.class);
            startActivity(intent);
        });
        requestPermission();
    }
    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    CODE_PERMISSION);
        } else {
            getSelfLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int codeRequest, @NonNull String[] permissions, @NonNull int[] result) {
        super.onRequestPermissionsResult(codeRequest, permissions, result);
        if (codeRequest == CODE_PERMISSION) {
            if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                getSelfLocation();
            } else {
                Toast.makeText(this, "Не удается получить разрешение", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void getSelfLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.getLastLocation().addOnSuccessListener(this, selflocation -> {
                if (selflocation != null) {
                    loadWeather(selflocation.getLatitude(),
                            selflocation.getLongitude());
                } else {
                    Toast.makeText(this, "Не удалось получить доступ к информации о погоде",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void loadWeather(double latitude, double longitude) {
        WeatherApiService service = RetrofitClient.getInstance()
                .create(WeatherApiService.class);
        Call<weatherBased> request = service.getForecastByCoordinates(
                latitude, longitude, API_KEY, "metric", "ru");
        request.enqueue(new Callback<weatherBased>() {
            @Override
            public void onResponse(Call<weatherBased> call, Response<weatherBased> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherBased dataWeather = response.body();
                    cityText.setText(dataWeather.getCity().getName());
                    adapter = new weatherAdapter(dataWeather.getList());
                    recycleWeather.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка: ",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<weatherBased> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Ошибка: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
