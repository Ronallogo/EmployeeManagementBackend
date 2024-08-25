package com.EmployeeManagment.Source.Task_Inserted.Exception;

public class DuplicateTaskInsertedException extends RuntimeException{
    public DuplicateTaskInsertedException(String message){
            super(message);
    }
}
