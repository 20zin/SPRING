package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //정적콘텐츠에서 우선순위를 먼저가진다 즉 톰캣은 컨트롤러로 먼저 매핑된것부터 찾는다
public class HelloController {
    @GetMapping("hello")//던져준 url에 hello를 찾음 get방식
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody//http의 바디부분을 내가 직접넣어주곘다 매우중요!!
    public String helloString(@RequestParam("name") String name){
        return "hello" + name;//string컨버터 통과
    }

    @GetMapping("hello-api")
    @ResponseBody//json반환(default) viewResolver대신에HttpMessageConverter가 동작
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();//객체로 던져줬네? ->json컨버터
        hello.setName(name);
        return hello;

    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
