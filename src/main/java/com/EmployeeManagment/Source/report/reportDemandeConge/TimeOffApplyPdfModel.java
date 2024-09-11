package com.EmployeeManagment.Source.report.reportDemandeConge;

import com.EmployeeManagment.Source.Content.Entity.Content;
import com.EmployeeManagment.Source.TimeOff.Entity.TimeOffApply;
import com.EmployeeManagment.Source.TimeOff.Repository.TimeOffApplyRepository;
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
public class TimeOffApplyPdfModel {

    @Autowired
    private TimeOffApplyRepository   timeOffApplyRepository ;
    public void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.darkGray);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);

        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("DEBUT DE CONGÉ" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("FIN DE CONGÉ" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TYPE DE CONGÉ" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("STATUS" , font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TITULAIRE DU CONGÉ" , font));
        table.addCell(cell);





    }
    public void writeTableData(PdfPTable table){
        List<TimeOffApply> list  = this.timeOffApplyRepository.findAll() ;

        for(TimeOffApply t : list){
            table.addCell(String.valueOf(t.getId())) ;
            table.addCell(String.valueOf(t.getBeginning())) ;
            table.addCell(String.valueOf(t.getEnd())) ;
            table.addCell(t.getType() );
            table.addCell((t.isValidate())? "congé validé" : "congé non validé");
            table.addCell(t.getEmployee().getName() + " " + t.getEmployee().getSurname() ) ;

        }

    }

    public void exportDemande(HttpServletResponse response) throws IOException {
        Document document  = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());


        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Listes des demandes de congés"  , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f,3.5f,3.5f,3.5f,3.5f,3.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }








}
