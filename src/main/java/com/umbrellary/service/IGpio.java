package com.umbrellary.service;

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;

import java.util.List;
import java.util.Map;

public interface IGpio {

    Map<String, Map<String, String>> getSystemInfo() throws Exception;

    List<String> getGpioList();

    String cmdhandler(String cmd, String gpioid, String now, GpioPinDigitalMultipurpose gpio);
}
