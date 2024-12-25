package com.EmployeeManagment.Source.Message.withSocketIo;

import com.EmployeeManagment.Source.Message.MessageDTO;
import com.EmployeeManagment.Source.Message.MessageDTO1;
import com.EmployeeManagment.Source.Message.MessageService;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocketIOService {
    private final SocketIOServer server;
    @Autowired
    private MessageService service ;
    public SocketIOService(SocketIOServer server) {
        this.server = server;
    }
    @PostConstruct
    public void startServer() { server.start(); }
    @PreDestroy
    public void stopServer() { server.stop(); }
    @OnConnect
    public void onConnect() { System.out.println("Client connecté"); }
    @OnDisconnect
    public void onDisconnect() { System.out.println("Client déconnecté"); }
    @OnEvent("userMessage")
    public void onUserMessage(SocketIOClient client, String message) throws JsonProcessingException {
        System.out.println("Message reçu: " + message);
            ObjectMapper objectMapper = new ObjectMapper();
            MessageDTO1 ms = objectMapper.readValue(message, MessageDTO1.class);
        try {
            // Désérialiser le JSON reçu
            // Décoder le fichier s'il est présent
            if (ms.getFile() != null) {
                byte[] fileBytes = java.util.Base64.getDecoder().decode(ms.getFile());
                this.service.sendMessage(ms , fileBytes);
            }
            // Diffuser à tous les clients
            server.getBroadcastOperations().sendEvent("newMessage", ms);

        } catch (Exception e) {
            e.printStackTrace();
        }
        server.getBroadcastOperations().sendEvent("userMessage", ms);
    }
}