package com.EmplyeeManagment.Source.Task_Inserted.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TaskInsertedRequest {

    private Long id ;
    private Long position ;
    private Long task ;
    private Integer gain_task_post ;

    TaskInsertedRequest(Long position , Long task , Integer gain_task_post){
            this.setTask(task);
            this.setPosition(position);
            this.setGain_task_post(gain_task_post);
    }



}
