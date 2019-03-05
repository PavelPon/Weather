package com.example.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.Common.Common;
import com.example.weather.Model.WeatherResult;
import com.example.weather.Retrofit.IOpenWeatherMap;
import com.example.weather.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TodayWeatherFragment extends Fragment {

    ImageView imageViewWeather;
    TextView txv_city_name, txv_temperature, txv_descripton, txv_data_time, txv_wind,
            txv_pressure, txv_humidity, txv_sunrise, txv_sunset, txv_geo_coords;

    LinearLayout weather_panel;
    ProgressBar progressBarLoading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {

        if (instance == null) {
            instance = new TodayWeatherFragment();

        }
        return instance;
    }

    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);

        imageViewWeather = (ImageView) itemView.findViewById(R.id.img_weather);
        txv_city_name = (TextView) itemView.findViewById(R.id.text_view_city_name);
        txv_temperature = (TextView) itemView.findViewById(R.id.text_view_temperature);
        txv_descripton = (TextView) itemView.findViewById(R.id.text_view_description);
        txv_data_time = (TextView) itemView.findViewById(R.id.text_view_data_time);
        txv_wind = (TextView) itemView.findViewById(R.id.text_view_wind);
        txv_pressure = (TextView) itemView.findViewById(R.id.text_view_pressure);
        txv_humidity = (TextView) itemView.findViewById(R.id.text_view_humidity);
        txv_sunrise = (TextView) itemView.findViewById(R.id.text_view_sunrize);
        txv_sunset = (TextView) itemView.findViewById(R.id.text_view_sunset);
        txv_geo_coords = (TextView) itemView.findViewById(R.id.text_view_geo_coords);

        weather_panel = (LinearLayout) itemView.findViewById(R.id.weather_panel);

        progressBarLoading = (ProgressBar) itemView.findViewById(R.id.progressbar_today);

        Animation weatherPanelAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.animation_today);
        weather_panel.startAnimation(weatherPanelAnimation);
        getWeatherInformation();
        return itemView;

    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude())
                , Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                               @Override
                               public void accept(WeatherResult weatherResult) throws Exception {
//load image
                                   Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                           .append(weatherResult.getWeather().get(0).getIcon())
                                           .append(".png").toString()).into(imageViewWeather);

                                   txv_city_name.setText(weatherResult.getName());
                                   txv_descripton.setText(new StringBuilder("Weather in ").append(weatherResult.getName()).toString());
                                   txv_temperature.setText(new StringBuilder(
                                           String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                                   txv_data_time.setText(Common.convertUnixToData(weatherResult.getDt()));
                                   txv_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa").toString());
                                   txv_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" % ").toString());
                                   txv_sunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                                   txv_sunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));
                                   txv_geo_coords.setText(new StringBuilder(weatherResult.getCoord().toString()));
                                   txv_wind.setText(new StringBuilder("Speed: ").append(String.valueOf(weatherResult.getWind().getSpeed())).append("m/s").toString());

                                   weather_panel.setVisibility(View.VISIBLE);
                                   progressBarLoading.setVisibility(View.GONE);
                               }

                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                ));
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
