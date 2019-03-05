package com.example.weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.Common.Common;
import com.example.weather.Model.FiveDayModel.WeatherForcastResult;
import com.example.weather.R;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

public class RecyclerViewForescastAdapter extends RecyclerView.Adapter<RecyclerViewForescastAdapter.MyViewHolder> {


    Context context;
    WeatherForcastResult weatherForcastResult;

    public RecyclerViewForescastAdapter(Context context, WeatherForcastResult weatherForcastResult) {
        this.context = context;
        this.weatherForcastResult = weatherForcastResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forescart, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        //Load image
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherForcastResult.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(myViewHolder.imageViewWeatherForecast);
        myViewHolder.txt_data_time.setText(new StringBuilder(Common.convertUnixToData(weatherForcastResult.list.get(position).dt)));
        myViewHolder.txt_description.setText(new StringBuilder(weatherForcastResult.list.get(position).weather.get(0).getDescription()));
        myViewHolder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherForcastResult.list.get(position).main.getTemp())).append("°C").toString());

    }

    @Override
    public int getItemCount() {
        return weatherForcastResult.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_data_time, txt_description, txt_temperature;
        ImageView imageViewWeatherForecast;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageViewWeatherForecast = (ImageView) itemView.findViewById(R.id.img_weather_forecast);
            txt_data_time = (TextView) itemView.findViewById(R.id.txt_data_time_forecast);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description_forecast);
            txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature_forecast);

        }
    }
}
