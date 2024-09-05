package com.EmployeeManagment.Source.report.reportContent;


import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.Content.Repository.ContentRepository;
import com.EmployeeManagment.Source.Position.Entity.Position;
import com.EmployeeManagment.Source.Position.Repository.PositionRepository;
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
public class ContentPdfModel {


    @Autowired
    private ContentRepository contentRepository ;
    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TITRE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("THEME" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("NATURE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("LANGUAGE" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("DATE DE CRÉATION" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("STATUS" , font));
        table.addCell(cell);



    }
    public void writeTableData(PdfPTable table){
        List<Content> list  = this.contentRepository.findAll() ;

        for(Content c : list){
            table.addCell(String.valueOf(c.getId())) ;
            table.addCell(c.getTitle()) ;
            table.addCell(c.getTheme()) ;
            table.addCell(c.getNature()) ;
            table.addCell(c.getLanguage()) ;
            table.addCell(String.valueOf(c.getCreation_date())) ;
            table.addCell(c.getStatus()) ;


        }

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des contenu"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f,3.5f,3.5f,3.5f,3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }





}
