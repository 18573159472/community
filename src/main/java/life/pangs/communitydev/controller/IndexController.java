package life.pangs.communitydev.controller;

import life.pangs.communitydev.mapper.UserMapper;
import life.pangs.communitydev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String index2Hello(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>0){
            for(Cookie c : cookies){
                if("token".equals(c.getName())){
                    String token = c.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }


        return "index";
    }
}
