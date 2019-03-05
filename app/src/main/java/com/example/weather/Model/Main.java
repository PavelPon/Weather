package com.example.weather.Model;

public class Main {

    public Main() {
    }

    private float temp;
    private float pressure;
    private int humidity;
    private float tempMin;
    private float tempMax;
    private float seaLevel;
    private float grndLevel;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
    }

    public float getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(float grndLevel) {
        this.grndLevel = grndLevel;
    }
}
