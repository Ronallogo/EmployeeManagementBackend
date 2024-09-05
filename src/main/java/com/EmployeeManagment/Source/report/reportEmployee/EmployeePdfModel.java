package com.EmployeeManagment.Source.report.reportEmployee;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Repository.ContentRepository;
import com.EmployeeManagment.Source.Employee.Entity.Employee;
import com.EmployeeManagment.Source.Employee.Repository.EmployeeRepository;
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
public class EmployeePdfModel {
    @Autowired
    private EmployeeRepository employeeRepository ;
    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NOM" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PRENOM" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("DATE DE NAISSANCE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("EMAIL" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ADRESSE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PHONE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("POSTE" , font));
        table.addCell(cell);



    }
    public void writeTableData(PdfPTable table){
        List<Employee> list  = this.employeeRepository.findAll() ;

        for(Employee e: list){
            table.addCell(String.valueOf(e.getId())) ;
            table.addCell(e.getName()) ;
            table.addCell(e.getSurname()) ;
            table.addCell(String.valueOf(e.getBirthday())) ;
            table.addCell(e.getEmail()) ;
            table.addCell(String.valueOf(e.getAddress())) ;
            table.addCell(e.getPhone()) ;
            table.addCell(e.getPosition().getPosition_name()) ;


        }

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des employés"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(8);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f,3.5f,3.5f,3.5f,3.5f,3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }






}
