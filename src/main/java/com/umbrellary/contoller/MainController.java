package com.umbrellary.contoller;

import com.umbrellary.service.ICacheService;
import com.umbrellary.service.IHomeService;
import com.umbrellary.service.IUserService;
import com.umbrellary.serviceimpl.UserService;
import com.umbrellary.utils.MethodCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @RequestMapping("api1")
    @ResponseBody
    public String api1(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        return name;
    }

    @RequestMapping("api2")
    public String api2(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("zruiname", name);
        Map m = new HashMap<>();
        m.put("key1", "va11111");
        m.put("key2", "15850164768");
        model.addAttribute("zrui", m);

        return "greeting";
    }

    @RequestMapping("api21")
    public ModelAndView api21(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        ModelAndView modelAndView = new ModelAndView("greeting");

        modelAndView.addObject("zruiname", name);
        Map m = new HashMap<>();
        m.put("key1", "va11111");
        m.put("key2", "15850164768");
        modelAndView.addObject("zrui", m);

        return modelAndView;
    }


    @RequestMapping(value = "api3/{day}")
    public String getForDay(@PathVariable String day, Model model) {
        model.addAttribute("zruiname", day);
        return "greeting";
    }

    @RequestMapping(value = "Login")
    public String Login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, Model model) {

        if (username == null || password == null) {
            model.addAttribute("nologin", "nologin");
        } else {
            model.addAttribute("islogin", "islogin");
        }

        return "login";
    }

    @RequestMapping("api4")
    @ResponseBody
    public String api4() {
        return "User succesfully created! (id = " + userService.setOne() + ")";
    }

    @RequestMapping("api5")
    @ResponseBody
    public String api5() {
        iHomeService.setOne("连云港", "18550200061");

        return "成功";
    }

    @RequestMapping("api6")
    @ResponseBody
    public String api6() {
        logger.debug("debug");
        logger.info("info");
        logger.error("error");
        logger.warn("warn");

        return "logger success";
    }

    @RequestMapping(value = "api7/{key}/{value}")
    @ResponseBody
    public String api7(@PathVariable String key, @PathVariable String value) {
        return iCacheService.stringCache(key, value);
    }

    @RequestMapping("api8")
    @ResponseBody
    @MethodCost
    public String api8() {
        return iCacheService.memsave("张瑞", "恍恍惚惚恍恍惚惚恍恍惚惚恍恍惚惚哈哈哈");
    }

    @RequestMapping("api9")
    @ResponseBody
    @MethodCost
    public String api9() {
        iCacheService.memdel("张瑞");
        return "删除成功";
    }

    @Autowired
    @Qualifier("cacheService")
    private ICacheService iCacheService;

    @Autowired
    @Qualifier("homeService")
    private IHomeService iHomeService;

    @Autowired
    @Qualifier("userService")
    private IUserService userService;

}
