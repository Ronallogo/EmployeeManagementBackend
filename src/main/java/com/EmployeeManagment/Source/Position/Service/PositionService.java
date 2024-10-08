package com.EmployeeManagment.Source.Position.Service;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmployeeManagment.Source.Position.Repository.PositionRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


////class position service with all position logics
@Service
@Transactional
public class PositionService {



    /////variable that represents the PositionRepository
    @Autowired
    private PositionRepository  positionRepository ;


    ////function for position creation
    public Position create(Position position){
        return  positionRepository.save(position);
    }


    /////function for getting all position
    public List<Position> all(){
        return positionRepository.findAll() ;
    }


    //////function for position updating
    public Position edit(Long id  ,  Position position){
        position.setId(id);
        return  positionRepository.save(position);
    }



    /////function for getting position

    public Position get(Long id){
        return positionRepository.findById(id)
                .orElseThrow(()-> new PositionNotFoundException("position  is not found !!"));
    }

    /////function for searching position
    public List<Position> search(String keyword){
        return positionRepository.researchByName(keyword);
    }



    /////function for deleting position
    public boolean delete(Long id){
        if(positionRepository.existsById(id)){
           try{
               positionRepository.deleteById(id);
               return true ;
           }catch (Exception e){
                throw  new RuntimeException("make sure that any task is inserted with this position");
           }
        }
        else throw  new PositionNotFoundException(" this position  does not exist is maybe already  deleted !!");
    }
    /*return false*/



}



