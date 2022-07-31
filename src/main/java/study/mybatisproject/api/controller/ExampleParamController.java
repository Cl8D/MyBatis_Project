package study.mybatisproject.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.mybatisproject.api.controller.dto.ExampleParam;
import study.mybatisproject.api.controller.dto.ExampleUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 파라미터 받는 방법
@Controller
@RequestMapping("/example/param")
@Slf4j
public class ExampleParamController {

    /**
     * @RequestParam 이용하기
     */
    // ?id=test&code=1234
    // view는 GetMapping 경로를 따라간다고 생각해주면 편할 것 같다.
    @GetMapping("/example1")
    // @RequestParam은 value를 설정해줘도 되는데, 설정 안 하면 그냥 변수명이 value의 값이 된다.
    public void example1(@RequestParam(value = "id") String id,
                         @RequestParam String code,
                         Model model) {
        // 이런 식으로 model을 통해 view에 출력이 가능하다.
        model.addAttribute("id", id);
        model.addAttribute("code", code);
    }

    /**
     * Map을 이용해서 받기
     */
    @GetMapping("/example2")
    public void example2(@RequestParam Map<String, Object> paramMap,
                         Model model) {
        // 혹은 map을 통해서도 접근이 가능하다. 변수 여러 개면 차라리 map으로 관리해도 편할 듯!
        // paramMap = {id=test, code=1234, hi=ee}
        log.info("paramMap = {}", paramMap);
        model.addAttribute("paramMap", paramMap);
    }

    /**
     * 클래스(DTO)를 이용해서 받기
     */
    @GetMapping("/example3")
    // 클래스로 받을 때는 @RequestParam을 없애야 한다. (내부 필드로 자동 매핑되는 게 안 되는 듯?)
    public void example3(ExampleParam exampleParam,
                         Model model) {
        // exampleParam = ExampleParam(id=test, code=1234)
        log.info("exampleParam = {}", exampleParam);
        model.addAttribute("exampleParam", exampleParam);
    }

    /**
     * PathVariable 이용하기
     */
    // /example4/test/1234
    @GetMapping("/example4/{id}/{code}")
    public String example4(@PathVariable String id,
                           @PathVariable String code,
                           Model model) {
        model.addAttribute("id", id);
        model.addAttribute("code", code);

        // pathVariable을 사용할 때는 return으로 뷰 네임을 넘겨줘야 잘 작동한다.
        // 이유에 대해서 생각해봤는데, viewName을 리턴하지 않으면 동작하는 경로가 /WEB-INF/views/example/param/example4/test/123.jsp 이거인 거 보니까...
        // viewResolver는 RequestMapping을 통해 설정한 경로로 동작한다...라고 생각하면 될 것 같다!

        // 물론 위의 예제에서 model을 사용해도 뷰네임 리턴해줘도 된다.
        return "/example/param/example4";
    }

    /**
     * String[] 배열 받기
     */
    // ?ids=12&ids=34&ids=56
    @GetMapping("/example5")
    public String example5(@RequestParam String[] ids,
                           Model model) {
        model.addAttribute("ids", ids);
        return "/example/param/example5";
    }

    /**
     * JSON을 받는 방법
     */
    @GetMapping("/example6/form")
    public void form() {
    }


    @PostMapping("/example6/saveData")
    // 사실 여기서 바로 하는 것보다는, 보통 클래스 단위에 @RestController를 많이 붙이는 것 같다 (개인적인 경험으로는?)
    @ResponseBody
    public Map<String, Object> example6(
            // Map 대신에 DTO를 보통 많이 받으니까, DTO로 설정
//            @RequestBody Map<String, Object> map
            @RequestBody ExampleUserRequest exampleUserRequest
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", true);
        // 이런 식으로 map에 jsp에서 전송해준 데이터가 담긴다.
        // map: {user={name=jenny, age=22, address=Korea}}
//        log.info("map: {}", map);

        // map 대신 DTO를 사용해도 잘 되는 걸 볼 수 있다.
        // ExampleUserRequest(user=ExampleUser(name=jenny, age=22, address=Korea))
        log.info("examUserReq : {}", exampleUserRequest);
        return resultMap; // 여기 resulr가 성공했을 때 jsp에게 넘겨주는 데이터. success의 param으로 들어간다.
    }
}
