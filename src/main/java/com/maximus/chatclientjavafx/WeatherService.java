package com.maximus.chatclientjavafx;

import com.maximus.chatclientjavafx.controller.ProxyController;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    ProxyController proxy;

    public WeatherService(ProxyController proxy) {
        this.proxy = proxy;
    }

    public String getWeatherForecast() {
        return proxy.sayHello("Hello backend!");
    }
}
