package life.pangs.communitydev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HolleWorld {

    @GetMapping("hello")
    public String hello(@RequestParam(name="name",defaultValue = "world") String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
