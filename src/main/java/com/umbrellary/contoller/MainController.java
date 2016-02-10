package com.umbrellary.contoller;

import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.umbrellary.service.IGpio;
import com.umbrellary.utils.GpioUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    @Qualifier("Gpio")
    private IGpio iGpio;

    @RequestMapping(method = RequestMethod.GET, value = "main.pi")
    public String main(Model model) {

        try {
            model.addAttribute("info", iGpio.getSystemInfo());
            logger.info(String.valueOf(iGpio.getSystemInfo().size()));
            model.addAttribute("gpio", iGpio.getGpioList());
            logger.info(String.valueOf(iGpio.getGpioList().size()));
            return "index";

        } catch (Exception e) {
            model.addAttribute("error", e.toString());
            return "error";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "gpio.json")
    public String gpio(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String cmd = request.getParameter("cmd") == null ? "" : request.getParameter("cmd");
        String gpioid = request.getParameter("gpioid") == null ? "" : request.getParameter("gpioid");
        String now = request.getParameter("mode") == null ? "" : request.getParameter("mode");

        GpioPinDigitalMultipurpose gpio = GpioUnit.getInstance().getGpioPinDigitalMultipurpose(gpioid);

        out.print(iGpio.cmdhandler(cmd, gpioid, now, gpio));
        out.flush();
        out.close();

        return null;
    }

}
