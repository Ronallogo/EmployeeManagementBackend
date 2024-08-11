package com.EmployeeManagment.Source.TimeOff.Entity;


import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  TimeOffRequest {

    private Long id ;
    private LocalDate beginning ;
    private LocalDate end ;
    private String type ;
    private boolean status ;
    private Long employee ;


    ////// constructor personalized for TimeOffRequest creation
    public TimeOffRequest(
            LocalDate beginning ,
            LocalDate end ,
            String type ,
            boolean status ,
            Long employee
    ){
        this.setBeginning(beginning);
        this.setEmployee(employee);
        this.setEnd(end);
        this.setType(type);
        this.setStatus(status);

    }


    /////// function to check the deviation between the date
    public boolean periodTimeOffCheck(LocalDate beginning , LocalDate end){
            //// if beginning year is not greater than end year
          if(!( beginning.getYear() > end.getYear())){
              ////check the deviation between the months
              return beginning.getMonthValue() > end.getMonthValue();
          }
          else {
            return true ;
          }
    }







}
