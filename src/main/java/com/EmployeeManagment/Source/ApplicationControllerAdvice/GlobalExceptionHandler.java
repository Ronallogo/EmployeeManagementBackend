package com.EmployeeManagment.Source.ApplicationControllerAdvice;


import com.EmployeeManagment.Source.Absences.Exception.AbsenceNotFoundException;
import com.EmployeeManagment.Source.Content.Exception.ContentNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Exception.PayStubNotFoundException;
import com.EmployeeManagment.Source.Task.Exception.TaskNotFoundException;
import com.EmployeeManagment.Source.Task_Inserted.Exception.TaskInsertedNotFoundException;
import com.EmployeeManagment.Source.Task_Scheduled.Exception.TaskScheduledNotFoundException;
import com.EmployeeManagment.Source.TimeOff.Exception.TimeOffNotFoundException;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
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



    ////endpoint exception for payStub not found
    @ExceptionHandler(PayStubNotFoundException.class)
    public ResponseEntity<?> handlePayStubNotFoundException(PayStubNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }



    ////endpoint exception for content not found
    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<?> handleContentNotFoundException(ContentNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    ////endpoint exception for timeoff not found
    @ExceptionHandler(TimeOffNotFoundException.class)
    public ResponseEntity<?> handleTimeOffNotFoundException(TimeOffNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }



    ////endpoint exception for absences not found
    @ExceptionHandler(AbsenceNotFoundException.class)
    public ResponseEntity<?> handleAbsenceNotFoundException(AbsenceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    /////endpoint for entity not found
    @ExceptionHandler(EntityNotFoundException.class )
    public ResponseEntity<?> handleEntityNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //////endpoints for unique taskInserted




    ////endpoint exception for others exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
