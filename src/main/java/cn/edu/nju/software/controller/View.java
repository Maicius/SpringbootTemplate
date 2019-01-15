package cn.edu.nju.software.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "视图相关接口")
@Controller
public class View {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/createBpmn")
    public String createBpmn() {
        return "bpmn_model.html";
    }
}
