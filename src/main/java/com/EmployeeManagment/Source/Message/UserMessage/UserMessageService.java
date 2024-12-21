package com.EmployeeManagment.Source.Message.UserMessage;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMessageService {
    private final UserMessageRepository repository;

    public void saveUser(UserMessage user) {
        user.setStatus("ONLINE");
        repository.save(user);
    }

    public void disconnect(UserMessage user) {
        var storedUser = repository.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus("OFFLINE");
            repository.save(storedUser);
        }
    }

    public List<UserMessage> findConnectedUsers() {
        return repository.findAllByStatus("ONLINE");
    }
}
