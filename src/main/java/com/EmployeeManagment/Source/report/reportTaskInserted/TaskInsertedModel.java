package com.EmployeeManagment.Source.report.reportTaskInserted;


import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
import com.EmployeeManagment.Source.Task_Inserted.Entity.TaskInserted;
import com.EmployeeManagment.Source.Task_Inserted.Repository.TaskInsertedRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Component
public class TaskInsertedModel {
    /*
     private Long id ;
    private Long position ;
    private Long task ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date_insertion ;
    private Integer gain_task_post ;


    */

    @Autowired
    private TaskInsertedRepository taskRepository ;
    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("POSTE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TACHE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("DATE D'INSERTION" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("GAIN DE TACHE POUR LE POSTE" , font));
        table.addCell(cell);




    }
    public void writeTableData(PdfPTable table){
        List<TaskInserted> list  = this.taskRepository.findAll() ;

        for(TaskInserted t: list){
            table.addCell(String.valueOf(t.getId())) ;
            table.addCell(t.getPosition().getPosition_name()) ;
            table.addCell(t.getTask().getTask_name()) ;
            table.addCell(String.valueOf(t.getDate_insertion())) ;
            table.addCell(String.valueOf(t.getGain_task_post())) ;
             ;


        }

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des taches insérées"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f,3.5f,3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }





}
