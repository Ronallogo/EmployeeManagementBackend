package com.EmployeeManagment.Source.report.reportAbsence;


import com.EmployeeManagment.Source.Absences.Entity.Absence;
import com.EmployeeManagment.Source.Absences.Repository.AbsenceRepository;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOff;
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
public class AbsencePdfModel {

    @Autowired
    private AbsenceRepository absenceRepository ;


    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("JOUR DE L'ABSENCE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("RAISON DE L'ABSENCE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("EMPLOYÉ" , font));
        table.addCell(cell);







    }



    public void writeTableData(PdfPTable table) {
        List<Absence> list = this.absenceRepository.findAll();

        for (Absence a : list) {
            table.addCell(String.valueOf(a.getId()));
            table.addCell(String.valueOf(a.getAbsence_day()));
            table.addCell(a.getReason());
            table.addCell(a.getEmployee().getName() + " " + a.getEmployee().getSurname());


        }

    }



    public void export(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des absences"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(4);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f,3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }








}
