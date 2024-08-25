package com.EmployeeManagment.Source.Employee.Service;


import com.EmployeeManagment.Source.Employee.Entity.EmployeeRequest;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Position.Repository.PositionRepository;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


///// class service for employee
@Service
@Transactional
public class EmployeeService {
    ///variable represents repository of employee
    @Autowired
    private EmployeeRepository employeeRepository ;
    @Autowired
    private PositionRepository positionRepository ;


    ////function for create an employee
    public Employee create(EmployeeRequest employee){
        ///check if the position exist
            Position p  =   positionRepository.findById(employee.getPosition())
                    .orElseThrow(()-> new PositionNotFoundException("position not found to make employee registration !!")) ;

            ////make the employee registration
            return  employeeRepository.save(new Employee(
                    employee.getName(),
                    employee.getSurname() ,
                    employee.getEmail(),
                    employee.getAddress() ,
                    employee.getBirthday() ,
                    employee.getPhone(),
                    p
            ));
    }


    //////function to get an employee
    public Employee get(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("this employee does not exist !!"));

    }

    ///function  to get all employee
    public List<Employee> all (){
        return employeeRepository.findAll() ;
    }

    ////function to update an employee
    public Employee edit(Long id ,   EmployeeRequest employee){
        ///check if the position exist in case where  position is updated
        Position p  =   positionRepository.findById(employee.getPosition())
                .orElseThrow(()-> new PositionNotFoundException("position not found to make employee registration !!")) ;

        Employee e = employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("this employee do not exist"));


        ////make the employee update
        return  employeeRepository.save(new Employee(
                id ,
                employee.getName(),
                employee.getSurname() ,
                employee.getEmail(),
                employee.getAddress() ,
                employee.getBirthday() ,
                employee.getPhone(),
                p
        ));
    }

    ///////function to delete an employee
    public boolean delete (Long id){
        //// check if this employee exist
         if(employeeRepository.existsById(id)){
            ///try to delete otherwise throw the error
            try{
                employeeRepository.deleteById(id);
                return  true ;
            }
            catch (Exception e ){
                throw  new RuntimeException("make sure that the employee is not in relationship with any entities !!");
            }
         }
         else{
            throw new EmployeeNotFoundException("this employee does not exist or maybe already deleted !!!");
         }




    }

    ////function to make research on one employee
    public  List<Employee> search(String keyword){
        return employeeRepository.researchEmployee(keyword);
    }

    public Employee getByEmail(String email){
        return this.employeeRepository.findByEmail(email)
                         .orElseThrow(()->new EmployeeNotFoundException("employee not found !!!"));
    }


}

