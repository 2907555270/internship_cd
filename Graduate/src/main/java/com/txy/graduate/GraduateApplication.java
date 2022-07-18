package com.txy.graduate;

import com.txy.graduate.domain.dto.MenuDto;
import com.txy.graduate.domain.po.SysMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test")
@Slf4j
public class GraduateApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraduateApplication.class, args);
    }

    @GetMapping("/ser")
    public SysMenu test_ser(){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setName("hello");
        System.out.println(sysMenu);
        return sysMenu;
    }

    @GetMapping("/inc")
    public MenuDto test_inc(){
        MenuDto menuDTO = new MenuDto();
        menuDTO.setName("hello");
        return menuDTO;
    }

    @GetMapping("/debug")
    public String test_debug(){
        log.debug("hello...");
        return "hello debug";
    }
}
