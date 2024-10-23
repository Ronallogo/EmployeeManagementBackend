package com.EmployeeManagment.Source.Notification.service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Entity.NotificationRequest;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ServiceNotification {


    @Autowired
    private NotificationRepository repositoryNotification ;
    @Autowired
    private EmployeeRepository employeeRepository ;



    public List<Notification> allNotificationForOne(Long employee){
        Employee e = employeeRepository.findById(employee)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));
        return  repositoryNotification.findByEmployee(employee);
    }

    public List<Notification> search (String type ,   Long employee){
        Employee e = employeeRepository.findById(employee)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));
        return  repositoryNotification.researchNotification(type ,employee);
    }


    public Notification create(NotificationRequest n){
        Employee e = employeeRepository.findById(n.getEmployee())
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));
       return repositoryNotification.save(new Notification( n.getMessage() , e , n.getDate() , false , n.getType()  ));
    }

    public void changeState(Long id_notification){
        Notification n = repositoryNotification.findById(id_notification)
                .orElseThrow(()->  new RuntimeException("notification not found!!"));
        n.setViews(true);
        repositoryNotification.save(n);
    }

    public boolean deleteNotification(Long id){
        if(repositoryNotification.existsById(id)){
            this.repositoryNotification.deleteById(id);
            return true ;
        }
        else{
            throw  new RuntimeException("notification not found!!!");
        }
    }

    public List<Notification> resetNotification(List<Notification>notificationList){

      return   this.repositoryNotification.saveAll(notificationList);

    }

}
