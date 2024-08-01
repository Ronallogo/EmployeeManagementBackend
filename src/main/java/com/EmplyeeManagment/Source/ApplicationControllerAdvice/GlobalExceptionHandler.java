package com.EmplyeeManagment.Source.ApplicationControllerAdvice;


import com.EmplyeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmplyeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmplyeeManagment.Source.Task.Exception.TaskNotFoundException;
import com.EmplyeeManagment.Source.Task_Inserted.Exception.TaskInsertedNotFoundException;
import com.EmplyeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmplyeeManagment.Source.Task_Scheduled.Exception.TaskScheduledNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


// Controller Advice for handle all Exception
@ControllerAdvice
public class GlobalExceptionHandler {
    /////endpoint exception for employee not found
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFoundException(EmployeeNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    /////endpoint exception for position not found
    @ExceptionHandler(PositionNotFoundException.class)
    public ResponseEntity<?> handlePositionNotFoundException(PositionNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    ////endpoint exception for task not found
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }



    ////endpoint exception for task_inserted not found
    @ExceptionHandler(TaskInsertedNotFoundException.class)
    public ResponseEntity<?> handleTaskInsertedNotFoundException(TaskInsertedNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    ////endpoint exception for task_scheduled not found
    @ExceptionHandler(TaskScheduledNotFoundException.class)
    public ResponseEntity<?> handleTaskScheduledNotFoundException(TaskScheduledNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    ////endpoint exception for others exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
