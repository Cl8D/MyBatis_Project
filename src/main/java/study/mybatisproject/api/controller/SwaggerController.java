package study.mybatisproject.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {
    @GetMapping("/api-docs")
    public String apiDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
