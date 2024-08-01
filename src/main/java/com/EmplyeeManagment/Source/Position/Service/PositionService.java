package com.EmplyeeManagment.Source.Position.Service;


import com.EmplyeeManagment.Source.Position.Entity.Position;
import com.EmplyeeManagment.Source.Position.Exception.PositionNotFoundException;
import com.EmplyeeManagment.Source.Position.Repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
            positionRepository.deleteById(id);
            return true ;
        }
        else throw  new PositionNotFoundException(" this position  does not exist is maybe already  deleted !!");
    }
    /*return false*/

}
