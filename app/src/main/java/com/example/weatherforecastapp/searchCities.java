package com.example.weatherforecastapp;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforecastapp.Model.weatherBased;
import com.example.weatherforecastapp.API.RetrofitClient;
import com.example.weatherforecastapp.API.WeatherApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchCities extends AppCompatActivity {
    private static final String API_KEY = "10a641a16d59f30f407c8e364764073c";
    private EditText validateCity;
    private TextView resultCity;
    private RecyclerView recycleSearchWeather;
    private weatherAdapter adapter;
    @Override
    protected void onCreate(Bundle safedState) {
        super.onCreate(safedState);
        setContentView(R.layout.activity_search_city);
        validateCity = findViewById(R.id.cityEditText);
        resultCity = findViewById(R.id.cityResultTextView);
        recycleSearchWeather = findViewById(R.id.searchWeatherRecyclerView);
        Button searchButton = findViewById(R.id.searchButton);
        recycleSearchWeather.setLayoutManager(new LinearLayoutManager(this));
        searchButton.setOnClickListener(v -> {
            String nameCity = validateCity.getText().toString().trim();
            if (!nameCity.isEmpty()) {
                loadWeatherOnCity(nameCity);
            } else {
                Toast.makeText(this, "Найдите интересуемый город", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadWeatherOnCity(String city) {
        WeatherApiService service = RetrofitClient.getInstance()
                .create(WeatherApiService.class);
        Call<weatherBased> request = service.getForecast(
                city, API_KEY, "metric", "ru");
        request.enqueue(new Callback<weatherBased>() {
            @Override
            public void onResponse(Call<weatherBased> call, Response<weatherBased> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherBased dataWeather = response.body();
                    resultCity.setText(dataWeather.getCity().getName());

                    adapter = new weatherAdapter(dataWeather.getList());
                    recycleSearchWeather.setAdapter(adapter);
                } else {
                    Toast.makeText(searchCities.this,
                            "Null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<weatherBased> call, Throwable throwable) {
                Toast.makeText(searchCities.this,
                        "Ошибка: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
