package com.EmployeeManagment.Source.TimeOff.Entity;


import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  TimeOffRequest {

    private Long id ;
    private Date beginning ;
    private Date end ;
    private String type ;
    private boolean status ;
    private Long timeOffApply ;


    ////// constructor personalized for TimeOffRequest creation
    public TimeOffRequest(
            Date beginning ,
            Date end ,
            String type ,
            boolean status ,

            Long timeOffApply
    ){
        this.setBeginning(beginning);

        this.setEnd(end);
        this.setType(type);
        this.setStatus(status);
        this.setTimeOffApply(timeOffApply);

    }


    /////// function to check the deviation between the date
    public boolean periodTimeOffCheck(Date beginning , Date end){
            //// if beginning year is not greater than end year
          if(!( beginning.getYear() > end.getYear())){
              ////check the deviation between the months
              return beginning.getMonth() > end.getMonth();
          }
          else {
            return true ;
          }
    }







}
