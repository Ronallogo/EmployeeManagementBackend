package com.EmployeeManagment.Source.Message;




import com.EmployeeManagment.Source.Message.outilSSE.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/message")
@CrossOrigin("*")
public class MessageController {
    @Autowired
    private MessageService messageService ;
    @Autowired
    private SseService sseService ;







    @PostMapping( value = "/send" ,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(
            @RequestPart("message") MessageDTO ms,
            @RequestPart(value="file" , required = false) MultipartFile file) throws IOException, InterruptedException {

        messageService.sendMessage(ms , file);
        sseService.sendMessageToAllClients("message envoyé");


        return  new ResponseEntity<>(HttpStatus.OK) ;
    }




    @GetMapping("/all/{email}")
    public List<MessageDTO1> getMessages(@PathVariable String email){
        return this.messageService.getMessages(email);
    }




    @DeleteMapping("/delete/{id_ms}/{nature_message}")
    public boolean delete(@PathVariable Long id_ms ,@PathVariable String nature_message){
        return this.messageService.delete(id_ms , nature_message);
    }





}



