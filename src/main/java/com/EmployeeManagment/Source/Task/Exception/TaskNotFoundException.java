package com.EmployeeManagment.Source.Task.Exception;

/////class to manage TaskNotFoundError
public class TaskNotFoundException  extends  RuntimeException{
    public TaskNotFoundException(String message){ super(message);}
}
