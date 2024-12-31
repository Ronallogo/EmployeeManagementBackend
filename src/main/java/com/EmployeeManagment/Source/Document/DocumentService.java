package com.EmployeeManagment.Source.Document;

import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Message.Message;
import com.EmployeeManagment.Source.Message.MessageDTO;
import com.EmployeeManagment.Source.Message.ResponseMsgServer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository ;


    public void saveDocument(String extension ,  MultipartFile file , Message ms  ) throws IOException {
        this.documentRepository.save(Document.builder()
                        .extension(extension)

                        .content(file != null ? file.getBytes() : null)
                        .message(ms)
                .build());
    }

    public Optional<Document> getDocument(Long id){
        return this.documentRepository.findById(id);
    }

    public void deleteDocument(Long id){
        this.documentRepository.deleteById(id);
    }

    public Optional<Document> getByMessageId(Long id_ms){
        return this.documentRepository.findByMessage(id_ms);
    }


}
