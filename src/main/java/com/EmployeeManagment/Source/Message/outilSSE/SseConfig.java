package com.EmployeeManagment.Source.Message.outilSSE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@Configuration
public class SseConfig { @Bean public SseEmitter sseEmitter() { return new SseEmitter(); } }
