package com.EmployeeManagment.Source.Message;


import com.EmployeeManagment.Source.Document.DocumentService;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Message.ChatRoom.ChatRoomService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private DocumentService documentService ;
    @Autowired
    private  ChatRoomService chatRoomService;



    public void sendMessage(MessageDTO ms  ,MultipartFile file  ) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        var chatId =  chatRoomService.getChatRoomId(ms.getSender() , ms.getReceiver());


        var e_sender = this.employeeRepository.findByEmail(ms.getSender())
            .orElseThrow(()-> new EmployeeNotFoundException("Owner not found"));

        var e_received = this.employeeRepository.findByEmail(ms.getReceiver())
            .orElseThrow(()-> new EmployeeNotFoundException("Recipient not found"));




       var message =  this.repository.save( Message.
            builder()
            .owner(e_sender)
            .type(file != null ?   "JOIN" : "MESSAGE")
            .nature("sent_message")
            .chatId(chatId.get())
           .delete_for_recipient(false)
           .delete_for_sender(false)

            .content(ms.getContent()).recipient(e_received.getEmail()).timestamp(now)
            .build());

       this.documentService.saveDocument(ms.getExtension() , file , message);

    }


    public List<MessageDTO1> refreshMessages(String email) throws InterruptedException   {
        Thread.sleep(9000L);


        var e = this.employeeRepository.findByEmail(email).orElseThrow(()->new EmployeeNotFoundException("Employee not found"));
        return this.repository.findByReceiverOrderByIdDesc(e.getEmail())
                .stream()
                .map(tuple -> {
                    try {
                        var fileContent = this.documentService.getByMessageId((Long)tuple.get("id")).get().getContent() ;
                         var  blob = fileContent == null ?  null : convert(fileContent);
                        String nameFile = this.documentService.getByMessageId((Long)tuple.get("id")).get().getFile_name() ;
                        var sender = this.employeeRepository.findById((Long) tuple.get("owner")).get().getEmail() ;
                        var message  = MessageDTO1.builder()
                                .id((Long)tuple.get("id"))
                                .content((String) tuple.get("content"))
                                .sender(sender)
                                .type((String) tuple.get("type"))
                                .receiver( tuple.get("recipient").toString())
                                .name(nameFile)
                                .date(tuple.get("timestamp").toString())
                                .nature((String) tuple.get("nature"))
                                .delete_for_sender((boolean) tuple.get("delete_for_sender"))
                                .delete_for_recipient((boolean) tuple.get("delete_for_recipient"))
                               .build();


                        if(fileContent != null) message.setFile(blob);
                        return  message ;
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }) .collect(Collectors.toList());


    }
    public List<MessageDTO1> getMessages(String email) throws InterruptedException {


        Thread.sleep(1000L);
        var e = this.employeeRepository.findByEmail(email).orElseThrow(()->new EmployeeNotFoundException("Employee not found"));
        return this.repository.findByReceiverOrderByIdDesc(e.getEmail())
                .stream()
                .map(tuple -> {
                    try {
                        var fileContent = this.documentService.getByMessageId((Long)tuple.get("id")).get().getContent() ;
                        String nameFile = this.documentService.getByMessageId((Long)tuple.get("id")).get().getFile_name() ;
                        var  blob = fileContent == null ?  null : convert(fileContent);
                        var sender = this.employeeRepository.findById((Long) tuple.get("owner")).get().getEmail() ;
                        var message =  MessageDTO1.builder()
                                .id((Long)tuple.get("id"))
                                .content((String) tuple.get("content"))
                                .sender(sender)
                                .type((String) tuple.get("type"))
                                .receiver( tuple.get("recipient").toString())
                                .name(nameFile)
                                .date(tuple.get("timestamp").toString())
                                .nature((String) tuple.get("nature"))
                                .delete_for_sender((boolean) tuple.get("delete_for_sender"))
                                .delete_for_recipient((boolean) tuple.get("delete_for_recipient"))
                                .build();

                        if(fileContent != null) message.setFile(blob);
                        return  message ;

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }) .collect(Collectors.toList());


    }


    public boolean delete(Long id_ms , String email  ){

        var message = this.repository.findById(id_ms)
                .orElseThrow(()-> new RuntimeException("message not exist"));

        if(checkRecipient(message , email)){
                message.setDelete_for_recipient(true);
                if(message.isDelete_for_sender()){
                    this.repository.deleteById(id_ms);
                }else {
                    this.repository.save(message);
                }
        }else{
            message.setDelete_for_sender(true);
            if(message.isDelete_for_recipient()){
                this.repository.deleteById(id_ms);
            }else if(checkSender(message , email)) {
                this.repository.save(message);
            }
        }
        return true ;


    }



    public boolean checkRecipient(Message ms ,    String email){
            return Objects.equals(ms.getRecipient(), email);
    }


    public boolean checkSender(Message ms , String email){
        return Objects.equals(ms.getOwner().getEmail(), email);
    }

    public static Blob convert(byte[] file) throws SQLException {
        return new SerialBlob(file);
    }





  //  public SseEmitter reponse









}
