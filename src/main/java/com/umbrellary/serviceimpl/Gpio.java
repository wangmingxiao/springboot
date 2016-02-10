package com.umbrellary.serviceimpl;

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import com.umbrellary.service.IGpio;
import com.umbrellary.utils.GpioUnit;
import com.umbrellary.utils.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;

import java.util.*;

@Service("Gpio")
public class Gpio implements IGpio {

    private static final Logger logger = LoggerFactory.getLogger(Gpio.class);


    @Override
    public Map<String, Map<String, String>> getSystemInfo() throws Exception {
        Map<String, Map<String, String>> group = new HashMap<>();

        Map<String, String> info1 = new HashMap<>();
        info1.put("Serial Number", SystemInfo.getSerial());
        info1.put("CPU Revision", SystemInfo.getCpuRevision());
        info1.put("CPU Architecture", SystemInfo.getCpuArchitecture());
        info1.put("CPU Part", SystemInfo.getCpuPart());
        info1.put("CPU Temperature", String.valueOf(SystemInfo.getCpuTemperature()));
        info1.put("CPU Core Voltage", String.valueOf(SystemInfo.getCpuVoltage()));
        info1.put("CPU Model Name", SystemInfo.getModelName());
        info1.put("Processor", SystemInfo.getProcessor());
        info1.put("Hardware Revision", SystemInfo.getRevision());
        info1.put("Is Hard Float ABI", String.valueOf(SystemInfo.isHardFloatAbi()));
        info1.put("Board Type", String.valueOf(SystemInfo.getBoardType()));
        group.put("HARDWARE INFO", info1);

        Map<String, String> info2 = new HashMap<>();
        info2.put("Total Memory", String.valueOf(SystemInfo.getMemoryTotal()));
        info2.put("Used Memory", String.valueOf(SystemInfo.getMemoryUsed()));
        info2.put("Free Memory", String.valueOf(SystemInfo.getMemoryFree()));
        info2.put("Shared Memory", String.valueOf(SystemInfo.getMemoryShared()));
        info2.put("Memory Buffers", String.valueOf(SystemInfo.getMemoryBuffers()));
        info2.put("Cached Memory", String.valueOf(SystemInfo.getMemoryCached()));
        info2.put("SDRAM_C Voltage", String.valueOf(SystemInfo.getMemoryVoltageSDRam_C()));
        info2.put("SDRAM_I Voltage", String.valueOf(SystemInfo.getMemoryVoltageSDRam_I()));
        info2.put("SDRAM_P Voltage", String.valueOf(SystemInfo.getMemoryVoltageSDRam_P()));
        group.put("MEMORY INFO", info2);

        Map<String, String> info3 = new HashMap<>();
        info3.put("OS Name", SystemInfo.getOsName());
        info3.put("OS Version", SystemInfo.getOsVersion());
        info3.put("OS Architecture", SystemInfo.getOsArch());
        info3.put("OS Firmware Date", SystemInfo.getOsFirmwareDate());
        group.put("OPERATING SYSTEM INFO", info3);

        Map<String, String> info4 = new HashMap<>();
        info4.put("Java Vendor", SystemInfo.getJavaVendor());
        info4.put("Java Vendor URL", SystemInfo.getJavaVendorUrl());
        info4.put("Java Version", SystemInfo.getJavaVersion());
        info4.put("Java VM", SystemInfo.getJavaVirtualMachine());
        info4.put("Java Runtime", SystemInfo.getJavaRuntime());
        group.put("JAVA ENVIRONMENT INFO", info4);

        Map<String, String> info5 = new HashMap<>();
        info5.put("Hostname", NetworkInfo.getHostname());
        for (String ipAddress : NetworkInfo.getIPAddresses())
            info5.put("IP Addresses", ipAddress);
        for (String fqdn : NetworkInfo.getFQDNs())
            info5.put("FQDN", fqdn);
        for (String nameserver : NetworkInfo.getNameservers())
            info5.put("Nameserver", nameserver);
        group.put("NETWORK INFO", info5);

        Map<String, String> info6 = new HashMap<>();
        info6.put("H264 Codec Enabled", String.valueOf(SystemInfo.getCodecH264Enabled()));
        info6.put("MPG2 Codec Enabled", String.valueOf(SystemInfo.getCodecMPG2Enabled()));
        info6.put("WVC1 Codec Enabled", String.valueOf(SystemInfo.getCodecWVC1Enabled()));
        group.put("CODEC INFO", info6);

        Map<String, String> info7 = new HashMap<>();
        info7.put("ARM Frequency", String.valueOf(SystemInfo.getClockFrequencyArm()));
        info7.put("CORE Frequency", String.valueOf(SystemInfo.getClockFrequencyCore()));
        info7.put("H264 Frequency", String.valueOf(SystemInfo.getClockFrequencyH264()));
        info7.put("ISP Frequency", String.valueOf(SystemInfo.getClockFrequencyISP()));
        info7.put("V3D Frequency", String.valueOf(SystemInfo.getClockFrequencyV3D()));
        info7.put("UART Frequency", String.valueOf(SystemInfo.getClockFrequencyUART()));
        info7.put("PWM Frequency", String.valueOf(SystemInfo.getClockFrequencyPWM()));
        info7.put("EMMC Frequency", String.valueOf(SystemInfo.getClockFrequencyEMMC()));
        info7.put("Pixel Frequency", String.valueOf(SystemInfo.getClockFrequencyPixel()));
        info7.put("VEC Frequency", String.valueOf(SystemInfo.getClockFrequencyVEC()));
        info7.put("HDMI Frequency", String.valueOf(SystemInfo.getClockFrequencyHDMI()));
        info7.put("DPI Frequency", String.valueOf(SystemInfo.getClockFrequencyDPI()));
        group.put("CLOCK INFO", info7);

        return group;
    }

    @Override
    public List<String> getGpioList() {
        List<String> gpiolist = new ArrayList<>();
        for (int i = 0; i < GpioUnit.getInstance().getGpioCount(); i++) {
            gpiolist.add("GPIO_" + String.format("%02d", i));
        }
        return gpiolist;
    }

    @Override
    public String cmdhandler(String cmd, String gpioid, String now, GpioPinDigitalMultipurpose gpio) {
        switch (cmd) {
            case "get":
                if (gpio != null) {
                    JsonHelper json = new JsonHelper().setReturnCode("0", "success");
                    json.addkeyvalue("mode", gpio.getMode().toString());
                    json.addkeyvalue("state", gpio.getState().toString());

                    return json.getJsonString();
                } else {
                    JsonHelper json = new JsonHelper().setReturnCode("-1", "出现错误");
                    return json.getJsonString();
                }

            case "setmode":
                if (gpio != null) {

                    if (now.equals("输入")) {
                        gpio.setMode(PinMode.DIGITAL_OUTPUT);
                    }
                    if (now.equals("输出")) {
                        gpio.setMode(PinMode.DIGITAL_INPUT);
                    }
                    if (now.equals("未知")) {
                        gpio.setMode(PinMode.DIGITAL_OUTPUT);
                    }

                    JsonHelper json = new JsonHelper().setReturnCode("0", "success");
                    json.addkeyvalue("mode", gpio.getMode().toString());
                    json.addkeyvalue("state", gpio.getState().toString());
                    return json.getJsonString();
                } else {
                    JsonHelper json = new JsonHelper().setReturnCode("-1", "出现错误");
                    return json.getJsonString();
                }

            case "setstate":
                if (gpio != null) {

                    if (now.equals("低电平")) {
                        gpio.high();
                    }
                    if (now.equals("高电平")) {
                        gpio.low();
                    }
                    if (now.equals("未知")) {
                        gpio.high();
                    }

                    JsonHelper json = new JsonHelper().setReturnCode("0", "success");
                    json.addkeyvalue("mode", gpio.getMode().toString());
                    json.addkeyvalue("state", gpio.getState().toString());
                    return json.getJsonString();
                } else {
                    JsonHelper json = new JsonHelper().setReturnCode("-1", "出现错误");
                    return json.getJsonString();
                }

            case "getall":

                Iterator iter = GpioUnit.getInstance().getGpioMap().entrySet().iterator();
                JsonHelper json = new JsonHelper().setReturnCode("0", "success");

                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String gpioname = (String) entry.getKey();
                    GpioPinDigitalMultipurpose pin = (GpioPinDigitalMultipurpose) entry.getValue();

                    if (pin != null) {
                        json.addcontent("gpioname", gpioname);
                        json.addcontent("mode", pin.getMode().toString());
                        json.addcontent("state", pin.getState().toString());
                        json.finishline();
                    } else {
                        json.addcontent("gpioname", gpioname);
                        json.addcontent("mode", "UNKNOW");
                        json.addcontent("state", "UNKNOW");
                        json.finishline();
                    }
                }
                json.finisharray("allgpio");
                return json.getJsonString();

            default:
                JsonHelper errorjson = new JsonHelper().setReturnCode("-1", "无法解析意图");
                return errorjson.getJsonString();
        }
    }
}