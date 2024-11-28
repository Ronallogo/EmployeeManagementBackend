package com.EmployeeManagment.Source.Message;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/auth/employee_manager/message")
@CrossOrigin("*")
public class MessageController {


    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Message  sendMessage(
            @Payload Message msg
    ){
        return msg ;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public Message addUser(
            @Payload Message msg,
            SimpMessageHeaderAccessor headerAccessor
    ){
        headerAccessor.getSessionAttributes()
                .put("username" , msg.getSender());

        return msg ;
    }
}
