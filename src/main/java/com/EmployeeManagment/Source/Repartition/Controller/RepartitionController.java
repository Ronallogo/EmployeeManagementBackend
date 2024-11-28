package com.EmployeeManagment.Source.Repartition.Controller;


import com.EmployeeManagment.Source.Repartition.Entities.RepartitionDTO;
import com.EmployeeManagment.Source.Repartition.Entities.RepartitionDTO2;
import com.EmployeeManagment.Source.Repartition.Entities.RepartitionDTO3;
import com.EmployeeManagment.Source.Repartition.Entities.RepartitionRequest;
import com.EmployeeManagment.Source.Repartition.Service.RepartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth/employee_manager/repartition")
@CrossOrigin("*")
public class RepartitionController {

    @Autowired
    private RepartitionService repartitionService ;

    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RepartitionDTO> saveTask(@RequestBody RepartitionRequest request){
        RepartitionDTO response = repartitionService.create(request);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/searchOne/{taskScheduled_id}")
    public ResponseEntity<List<RepartitionDTO2>> getTask(@PathVariable Long taskScheduled_id){
        List<RepartitionDTO2> dto2List = repartitionService.searchForOneTask(taskScheduled_id);
        return  new ResponseEntity<>(dto2List , HttpStatus.FOUND);
    }
    @GetMapping(value = "/searchMany/{keyword}")
    public ResponseEntity<List<RepartitionDTO3>> getManyTask(@PathVariable String keyword){
        List<RepartitionDTO3> dto2List = repartitionService.globalResearch(keyword);
        return  new ResponseEntity<>(dto2List , HttpStatus.OK);
    }
    @GetMapping(value = "/searchForOneEmployee/{id}")
    public ResponseEntity<List<RepartitionDTO3>> getForOneEmployee(@PathVariable Long id){
        List<RepartitionDTO3> dto2List = repartitionService.researchForOneEmployee(id);
        return  new ResponseEntity<>(dto2List , HttpStatus.OK);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<List<RepartitionDTO3>> getAllTask(){
        List<RepartitionDTO3> dto2List = repartitionService.allRepartition();
        return  new ResponseEntity<>(dto2List , HttpStatus.OK);
    }


    @PutMapping(value = "/edit")
    public ResponseEntity<RepartitionDTO> edit(@RequestBody RepartitionRequest request){
            RepartitionDTO response = this.repartitionService.edit(request);
            return  new ResponseEntity<>(response , HttpStatus.OK);

    }

    @DeleteMapping(value = "/delete/{taskScheduled_id}")
    public ResponseEntity<?> deleteRepartition(@PathVariable Long taskScheduled_id){
        boolean response = this.repartitionService.deleteRepartitionForTaskScheduled(taskScheduled_id);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }
    @DeleteMapping(value = "/dropEmployee/{id}")
    public ResponseEntity<?>  dropEmployeeForRepartition(@PathVariable Long id ){
        boolean response = this.repartitionService.dropEmployeeForRepartition(id );
        return new ResponseEntity<>(response , HttpStatus.OK);
    }




}
