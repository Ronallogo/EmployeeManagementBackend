package com.EmployeeManagment.Source.Employee.Controller;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Entity.EmployeeRequest;
import com.EmployeeManagment.Source.Employee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee_manager/employee")
@CrossOrigin("*")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService ;


    ////endpoint allowing to make an  employee registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeRequest employee){
         Employee  employeeAdded =  employeeService.create(employee);
        return  new ResponseEntity<Employee>( employeeAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one  employee
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
         Employee  employeeGot =  employeeService.get(id);
        return  new ResponseEntity<Employee>( employeeGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all  employees
    @GetMapping(value = "/all")
    public ResponseEntity<List<Employee>> allEmployee(){
        List<Employee> listEmployee =  employeeService.all() ;
        return new ResponseEntity<List<Employee>>(listEmployee , HttpStatus.OK);
    }


    /////endpoint allowing to update  one  employee by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Employee> editEmployee(@PathVariable Long id  , @RequestBody  EmployeeRequest employee){
         Employee  employeeEdited =  employeeService.edit(id ,  employee);
        return  new ResponseEntity<Employee>( employeeEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one  employee
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deleteEmployee(@PathVariable Long id){
        return  employeeService.delete(id);
    }


    /////endpoint allowing to do a research
    @GetMapping(value = "/search/{keyword}")
    public List<Employee> searchEmployee(@PathVariable String keyword){
        return  employeeService.search(keyword);
    }



}
