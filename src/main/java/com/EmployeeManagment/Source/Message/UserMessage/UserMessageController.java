package com.EmployeeManagment.Source.Message.UserMessage;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserMessageController {

    private final UserMessageService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserMessage addUser(
            @Payload UserMessage user
    ) {
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserMessage disconnectUser(
            @Payload UserMessage user
    ) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserMessage>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }


}
