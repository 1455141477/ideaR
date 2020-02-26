package cn.itcast.web;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


@Slf4j
@RestController
@RequestMapping("hello")
public class HelloController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User hello(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

   /* @RequestMapping("1")
    public String test() {
        return "hello";
    }*/
}
