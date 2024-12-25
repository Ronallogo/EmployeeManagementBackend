package com.EmployeeManagment.Source.Message.withSocketIo;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
public class SocketIOConfig {
        @Bean
        public SocketIOServer socketIOServer() {
            Configuration config = new Configuration();
            config.setHostname("localhost");
            config.setPort(9092)
            ; return new SocketIOServer(config);
        }
}
