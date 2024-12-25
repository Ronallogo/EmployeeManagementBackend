package com.EmployeeManagment.Source.Message;


import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Message.ChatRoom.ChatRoomService;
import com.EmployeeManagment.Source.Message.withSocketIo.SocketIOService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private MessageRepository repository;
    @Autowired
    private EmployeeRepository employeeRepository ;


    @Autowired
    private  ChatRoomService chatRoomService;



    public void sendMessage(MessageDTO1 ms  ,byte[] file ) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        var chatId =  chatRoomService.getChatRoomId(ms.getSender() , ms.getReceiver() , false);


        var e_sender = this.employeeRepository.findByEmail(ms.getSender())
            .orElseThrow(()-> new EmployeeNotFoundException("Owner not found"));

        var e_received = this.employeeRepository.findByEmail(ms.getReceiver())
            .orElseThrow(()-> new EmployeeNotFoundException("Recipient not found"));
        this.repository.save( Message.
            builder()
            .owner(e_sender)
            .file(file != null  ? file: null)
            .type(file != null ?   "JOIN" : "MESSAGE")
            .nature("sent_message")
            .chatId(chatId.get())
            .content(ms.getContent()).recipient(e_received.getEmail()).timestamp(now)
            .build());







    }


    public List<MessageDTO1> getMessages(String email){


        var e = this.employeeRepository.findByEmail(email).orElseThrow(()->new EmployeeNotFoundException("Employee not found"));
        return this.repository.findByReceiverOrderByTimestampAsc(e.getId())
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

      /*  if(nature.equals("received_message")){
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
        }*/
        return false ;


    }









}