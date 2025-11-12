package com.example.weatherforecastapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherforecastapp.Model.weatherDetail;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class weatherAdapter extends RecyclerView.Adapter<weatherAdapter.weatherHolder> {
    private List<weatherDetail> weatherList;
    public weatherAdapter(List<weatherDetail> weatherList) {
        this.weatherList = weatherList;
    }
    @NonNull
    @Override
    public weatherHolder onCreateViewHolder(@NonNull ViewGroup root, int typeView) {
        View view = LayoutInflater.from(root.getContext())
                .inflate(R.layout.item_weather, root, false);
        return new weatherHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull weatherHolder holder, int position) {
        weatherDetail element = weatherList.get(position);
        String normalizeData = formatData(element.getDtTxt());
        holder.dataText.setText(normalizeData);

        holder.tempText.setText(String.format("%.1f°C", element.getMain().getTemp()));

        if (element.getWeather() != null && !element.getWeather().isEmpty()) {
            holder.describeText.setText(element.getWeather().get(0).getDescription());
        }
    }
    @Override
    public int getItemCount() {
        return weatherList != null ? weatherList.size() : 0;
    }
    private String formatData(String dtTxt) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("ru"));
            SimpleDateFormat outputFormat = new SimpleDateFormat("d MMMM, HH:mm", new Locale("ru"));
            Date data = inputFormat.parse(dtTxt);
            return outputFormat.format(data);
        } catch (Exception e) {
            return dtTxt;
        }
    }
    static class weatherHolder extends RecyclerView.ViewHolder {
        TextView dataText;
        TextView tempText;
        TextView describeText;
        public weatherHolder(@NonNull View vid) {
            super(vid);
            dataText = vid.findViewById(R.id.dateTextView);
            tempText = vid.findViewById(R.id.tempTextView);
            describeText = vid.findViewById(R.id.descriptionTextView);
        }
    }
}

