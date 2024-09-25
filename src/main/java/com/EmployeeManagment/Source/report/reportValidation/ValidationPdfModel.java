package com.EmployeeManagment.Source.report.reportValidation;


import com.EmployeeManagment.Source.Task_Scheduled.Entity.TaskScheduled;
import com.EmployeeManagment.Source.Task_Scheduled.Repository.TaskScheduledRepository;
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
public class ValidationPdfModel {

    @Autowired
    private TaskScheduledRepository taskScheduledRepository ;
    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);


        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("tache" , font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("date de début" , font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("date de fin" , font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("employé" , font));
        table.addCell(cell);




    }
    public void writeTableData(PdfPTable table){
        List<TaskScheduled> list  = this.taskScheduledRepository.findAll() ;

        for(TaskScheduled task : list){
            table.addCell(String.valueOf(task.getId())) ;
            table.addCell(task.getTaskInserted().getTask().getTask_name()) ;
            table.addCell(task.getBeginning().toString()) ;
            table.addCell(task.getEnd().toString()) ;
            table.addCell(task.getEmployee().getName() + " "+ task.getEmployee().getSurname());


        }

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des taches validé"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(5);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f , 3.5f , 3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }

}
