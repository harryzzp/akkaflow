package com.glsc.akkaflow.controller;

import com.glsc.akkaflow.beans.Task;
import com.glsc.akkaflow.dao.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Skywalker on 2016/3/10.
 */
@Controller
public class DemoController {

    @Autowired
    private TaskDAO taskDAO;

    @RequestMapping("/greeting.html")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        Task task = new Task("Hi", 10000);
        long number = taskDAO.createTask(task);
        System.out.println("~~~~" + number);
        model.addAttribute("number", number);
        return "greeting";
    }
}
