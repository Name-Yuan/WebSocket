package com.source.controller;

import com.source.service.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {

    @Autowired
    private WebSocket socket;

    @GetMapping("/hello")
    public String hello(){
        socket.sendMessage("成功");
        return "OK";
    }

}
