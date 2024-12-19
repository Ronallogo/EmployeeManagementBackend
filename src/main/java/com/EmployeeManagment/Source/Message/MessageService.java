package com.EmployeeManagment.Source.Message;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageSendedRepository repository_send ;
    @Autowired
    private MessageReceivedRepository repository_receive;
    @Autowired
    private EmployeeRepository employeeRepository ;

    private  SimpMessagingTemplate messagingTemplate;


    @Autowired
    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public void send(String destination, MessageDTO message) {
        messagingTemplate.convertAndSend(destination, message);
    }

    public MessageDTO sendMessage(MessageDTO ms  ,MultipartFile file ) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        
        var e_sender = this.employeeRepository.findByEmail(ms.getSender())
                .orElseThrow(()-> new EmployeeNotFoundException("Sender not found"));

        var e_received = this.employeeRepository.findByEmail(ms.getReceiver())
                .orElseThrow(()-> new EmployeeNotFoundException("Received not found"));
        this.repository_send.save( MessageSended.
                builder()
                .sender(e_sender)
                .file(file != null  ? file.getBytes() : null)
                .type(file != null ?   "JOIN" : "MESSAGE")
                .nature("sent_message")
                .content(ms.getContent()).receiver(e_received.getEmail()).timestamp(now)
                .build());

        var messageReceived =    this.repository_receive.save(MessageReceived.builder()
                        .content(ms.getContent())
                        .sender(e_sender.getEmail())
                        .nature("received_message")
                        .receiver(e_received)
                        .file(file != null  ? file.getBytes() : null)
                    .type(file !=  null?   "JOIN" : "MESSAGE").timestamp(now)
                .build());


        return MessageDTO.builder()
                .id(messageReceived.getId())
                .receiver(e_received.getEmail())
                .sender(e_sender.getEmail())
                .content(ms.getContent())
                .nature("sent_message")
                .build();

    }


    public List<MessageDTO1> getMessages(String email){


        var e = this.employeeRepository.findByEmail(email).orElseThrow(()->new EmployeeNotFoundException("Employee not found"));
        return this.repository_receive.findByReceiverOrderByTimestampAsc(e.getId())
                .stream()
                .map(tuple -> MessageDTO1.builder()
                        .id((Long)tuple.get("id"))
                        .content((String) tuple.get("content"))
                        .sender((String) tuple.get("sender"))
                        .type((String) tuple.get("type"))
                        .receiver((String) tuple.get("receiver"))
                        .file((byte[]) tuple.get("file"))
                        .nature((String) tuple.get("nature"))

                       .build()) .collect(Collectors.toList());

    }

    public boolean delete(Long id_ms , String nature){

        if(nature.equals("received_message")){
            if(!this.repository_receive.existsById(id_ms) )  throw new RuntimeException("Doesn't exist");
            this.repository_receive.deleteById(id_ms);
            return true ;

        }
        else if(nature.equals("sent_message")){
            if(!this.repository_send.existsById(id_ms) )  throw new RuntimeException("Doesn't exist");
            this.repository_send.deleteById(id_ms);
            return true ;
        }else{
            throw new RuntimeException("Wrong message nature");
        }


    }









}
