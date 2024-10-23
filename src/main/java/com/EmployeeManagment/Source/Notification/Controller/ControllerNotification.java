package com.EmployeeManagment.Source.Notification.Controller;


import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Entity.NotificationRequest;
import com.EmployeeManagment.Source.Notification.service.ServiceNotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/notification")
@CrossOrigin("*")
public class ControllerNotification {

    @Autowired
    ServiceNotification notificationService ;
    /////endpoint allowing to get all  notifications
    @GetMapping(value = "/allForOne/{id_employee}")
    public ResponseEntity<List<Notification>> allNotification(@PathVariable Long id_employee){
        List<Notification> listNotification =  notificationService.allNotificationForOne(id_employee) ;
        return new ResponseEntity<List<Notification>>(listNotification , HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Notification> allNotification( @RequestBody NotificationRequest n){
        Notification notification =  notificationService.create(n) ;
        return new ResponseEntity<Notification>(notification , HttpStatus.OK);
    }


    /////endpoint allowing to update  one  notification by id
    @PutMapping(value = "/changeState/{id}")
    public ResponseEntity<?> editNotification(@PathVariable Long id)  {
         notificationService.changeState(id);
        return  new ResponseEntity<>(HttpStatus.OK);
    }



    ///////endpoint allowing to delete one  notification
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteNotification(@PathVariable Long id){
        return  notificationService.deleteNotification(id);
    }


    /////endpoint allowing to do a research
    @GetMapping(value = "/search/{keyword}")
    public List<Notification> searchNotification(@PathVariable String keyword , @RequestBody Long id_employee){
        return  notificationService.search(keyword , id_employee);
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<?> resetData(@RequestBody List<Notification> n){
        return new ResponseEntity<>( this.notificationService.resetNotification(n) , HttpStatus.OK);
    }


}
