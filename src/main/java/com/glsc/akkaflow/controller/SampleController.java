package com.glsc.akkaflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Skywalker on 2016/3/13.
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    @RequestMapping("index.html")
    public String index(Model model,
                        @RequestParam(value = "company", defaultValue = "国联证券") String company) {
        model.addAttribute("company", company);
        return "sample/index";
    }


}
