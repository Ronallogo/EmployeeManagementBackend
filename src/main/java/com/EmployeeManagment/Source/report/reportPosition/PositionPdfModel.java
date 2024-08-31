package com.EmployeeManagment.Source.report.reportPosition;


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
public class PositionPdfModel {


    @Autowired
    private PositionRepository positionRepository ;
    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);


        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("POSTE" , font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DESCRIPTION" , font));
        table.addCell(cell);


    }
    public void writeTableData(PdfPTable table){
        List<Position> list  = this.positionRepository.findAll() ;

        for(Position p : list){
            table.addCell(String.valueOf(p.getId())) ;
            table.addCell(p.getPosition_name()) ;
            table.addCell(p.getPosition_description()) ;

        }

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des postes"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(3);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }


}
