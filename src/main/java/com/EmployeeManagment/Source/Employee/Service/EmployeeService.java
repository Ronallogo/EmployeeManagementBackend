package com.EmployeeManagment.Source.Employee.Service;


import com.EmployeeManagment.Source.Employee.Entity.EmployeeRequest;
import com.EmployeeManagment.Source.Employee.Exception.DuplicactedEmployeeException;
import com.EmployeeManagment.Source.Employee.Exception.EmployeeNotFoundException;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Notification.Entity.Notification;
import com.EmployeeManagment.Source.Notification.Repository.NotificationRepository;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Position.Repository.PositionRepository;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmployeeManagment.Source.Security.entities.User;
import com.EmployeeManagment.Source.Security.entities.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


///// class service for employee
@Service
@Transactional
public class EmployeeService {
    ///variable represents repository of employee
    @Autowired
    private EmployeeRepository employeeRepository ;
    @Autowired
    private PositionRepository positionRepository ;
    @Autowired
    private NotificationRepository notificationRepository ;



    @Autowired
    private UserRepository userRepository ;


    ////function for create an employee
    public Employee create(EmployeeRequest employee , MultipartFile file) throws IOException {
    ///check if the position exist
        Position p  =   positionRepository.findById(employee.getPosition())
                .orElseThrow(()-> new PositionNotFoundException("position not found to make employee registration !!")) ;

        User user = userRepository.findByEmail(employee.getEmail())
                .orElseThrow(()-> new RuntimeException(" user not found "));

        if(this.checkUnity(employee)) throw new DuplicactedEmployeeException("employee already exist ");
        
        ////make the employee registration



        return  employeeRepository.save(new Employee(
                employee.getName(),
                employee.getSurname() ,
                employee.getEmail(),
                employee.getAddress() ,
                employee.getBirthday() ,
                employee.getPhone(),
                p ,
                user  ,
                file.getBytes()

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
    public Employee edit(Long id ,   EmployeeRequest employee , MultipartFile file) throws IOException {

        System.out.print(employee + "******------******" + id);
        ///check if the position exist in case where  position is updated
        Position p  =   positionRepository.findById(employee.getPosition())
                .orElseThrow(()-> new PositionNotFoundException("position not found to make employee registration !!")) ;

        Employee e = employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("this employee do not exist"));

        User user = userRepository.findByEmail(employee.getEmail())
                .orElseThrow(()-> new RuntimeException(" user not found "));

        user.setFirstname(employee.getName());
        user.setLastname(employee.getSurname());
        userRepository.save(user);
        employee.setPhoto(file.getBytes());
        ////make the employee update
        return  employeeRepository.save(new Employee(
                id ,
                employee.getName(),
                employee.getSurname() ,
                employee.getEmail(),
                employee.getAddress() ,
                employee.getBirthday() ,
                employee.getPhone(),
                p ,
                user ,
               employee.getPhoto()
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
    public Optional<Employee> searchById(Long id){
        return employeeRepository.findById(id);
    }

    public Employee getByEmail(String email){
        return this.employeeRepository.findByEmail(email)
                         .orElseThrow(()->new EmployeeNotFoundException("employee not found !!!"));
    }


    public boolean checkUnity(EmployeeRequest e){
        List<Employee> list = this.employeeRepository.findAll() ;

        for(Employee emp : list){
             if(
                     Objects.equals(emp.getName() , e.getName()) &&
                     Objects.equals(emp.getSurname() , e.getSurname()) &&
                     Objects.equals(emp.getBirthday() , e.getBirthday()) &&
                     Objects.equals(emp.getEmail() , e.getEmail())

             ){
                return false;
             }
        }
        return false ;
    }


    public Employee editWithOutPhoto(Long id, EmployeeRequest employee ,MultipartFile file ) throws IOException {
        System.out.print(employee + "******------******" + id);
        ///check if the position exist in case where  position is updated
        Position p  =   positionRepository.findById(employee.getPosition())
                .orElseThrow(()-> new PositionNotFoundException("position not found to make employee registration !!")) ;

        Employee e = employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException("this employee do not exist"));

        User user = userRepository.findByEmail(employee.getEmail())
                .orElseThrow(()-> new RuntimeException(" user not found "));


        ////make the employee update
        return  employeeRepository.save(new Employee(
                id ,
                employee.getName(),
                employee.getSurname() ,
                employee.getEmail(),
                employee.getAddress() ,
                employee.getBirthday() ,
                employee.getPhone(),
                p ,
                user ,
                file.getBytes()


        ));

    }
}

