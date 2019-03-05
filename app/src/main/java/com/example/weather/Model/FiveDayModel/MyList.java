package com.example.weather.Model.FiveDayModel;
import com.example.weather.Model.Clouds;
import com.example.weather.Model.Main;
import com.example.weather.Model.Sys;
import com.example.weather.Model.Weather;
import com.example.weather.Model.Wind;
import java.util.List;

public class MyList {
    public int dt;
    public Main main;
    public List<Weather> weather;
    public Clouds clouds;
    public Wind wind;
    public Rain rain;
    public Sys sys;
    public String dt_txt;
}
