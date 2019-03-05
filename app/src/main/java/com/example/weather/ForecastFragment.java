package com.example.weather;


import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.Adapter.RecyclerViewForescastAdapter;
import com.example.weather.Common.Common;
import com.example.weather.Model.FiveDayModel.WeatherForcastResult;
import com.example.weather.Retrofit.IOpenWeatherMap;
import com.example.weather.Retrofit.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    TextView txt_city_name, txt_geo_coords;
    RecyclerView recyclerViewForecast;
    static ForecastFragment instace;

    public static ForecastFragment getInstance() {
        if (instace == null)
            instace = new ForecastFragment();
        return instace;

    }


    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city);
        txt_geo_coords = (TextView) itemView.findViewById(R.id.txt_geo_coords);

        recyclerViewForecast = (RecyclerView) itemView.findViewById(R.id.rv_five_days);
        recyclerViewForecast.setHasFixedSize(true);
        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getForecastWeatherInformation();


        return itemView;
    }

    private void getForecastWeatherInformation() {

        compositeDisposable.add(mService.getWeatherForcastByLatLng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForcastResult>() {
                    @Override
                    public void accept(WeatherForcastResult weatherForcastResult) throws Exception {
                        displayForecastWeather(weatherForcastResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ERROR", "" + throwable.getMessage());
                    }
                })

        );
    }

    private void displayForecastWeather(WeatherForcastResult weatherForcastResult) {
        txt_city_name.setText(new StringBuilder(weatherForcastResult.city.name));
        txt_geo_coords.setText(new StringBuilder(weatherForcastResult.city.coord.toString()));


        RecyclerViewForescastAdapter adapter = new RecyclerViewForescastAdapter(getContext(), weatherForcastResult);
        recyclerViewForecast.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

}
