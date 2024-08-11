package com.EmployeeManagment.Source.Pay_Stub.Controller;


import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStubRequest;
import com.EmployeeManagment.Source.Pay_Stub.Service.PayStubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee_manager/payStub")
@CrossOrigin("*")
public class PayStubController {

    /////variable that represent the service in this endpoint class
    @Autowired
    private PayStubService PayStubService ;

    ////endpoint allowing to make a PayStub registration
    @RequestMapping(value = "/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PayStub> savePayStub(@RequestBody PayStubRequest PayStubRequest){
        PayStub PayStubAdded = PayStubService.create(PayStubRequest);
        return  new ResponseEntity<PayStub>(PayStubAdded, HttpStatus.CREATED);
    }

    ///endpoint allowing to get one PayStubInserted
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<PayStub> getPayStub(@PathVariable Long id){
        PayStub PayStubGot = PayStubService.get(id);
        return  new ResponseEntity<PayStub>(PayStubGot , HttpStatus.FOUND);
    }


    /////endpoint allowing to get all PayStub
    @GetMapping(value = "/all")
    public ResponseEntity<List<PayStub>> allPayStub(){
        List<PayStub> listPayStub = PayStubService.all() ;
        return new ResponseEntity<List<PayStub>>(listPayStub , HttpStatus.OK);
    }


    /////endpoint allowing to update  one PayStub by id
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<PayStub> editPayStub(@PathVariable Long id  , @RequestBody PayStubRequest PayStubRequest){
        PayStub PayStubEdited = PayStubService.edit(id , PayStubRequest);
        return  new ResponseEntity<PayStub>(PayStubEdited , HttpStatus.OK);
    }


    ///////endpoint allowing to delete one PayStub
    @DeleteMapping(value = "/delete/{id}")
    public  boolean deletePayStub(@PathVariable Long id){
        return PayStubService.delete(id);
    }

}
