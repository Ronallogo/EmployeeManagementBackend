package com.EmployeeManagment.Source.report.reportPayStub;


import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Exception.PayStubNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.itextpdf.layout.borders.Border;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import com.lowagie.text.pdf.draw.LineSeparator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class PayStubPdfModel {

    @Autowired
    private PayStubRepository payStubRepository;
    public void generatedPdf(Long id , HttpServletResponse response) throws IOException {
        PayStub payStub = this.payStubRepository.findById(id).orElseThrow(()-> new PayStubNotFoundException("payStub not found"));
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document , response.getOutputStream());
        try {
            PdfWriter.getInstance(document, new FileOutputStream("pay_stub.pdf"));
            document.open();

            // Ajouter le titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Bulletin de Paie", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter un espace
            document.add(new Paragraph(" "));

            // Créer une table avec 3 colonnes
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            // Ajouter l'en-tête
            addTableHeader(table, "Intitulé" , "Valeur");

            // Ajouter des lignes avec des données
            addRow(table, "Nom et Prénom de l'employé",   payStub.getEmployee().getName() + " "+ payStub.getEmployee().getSurname());
            addRow(table, "Username",   "@"+payStub.getEmployee().getName());
            addRow(table, "Date de naissance",  String.valueOf(payStub.getEmployee().getBirthday()));
            addRow(table, "Poste",   payStub.getEmployee().getPosition().getPosition_name());
            addRow(table, "Email",    payStub.getEmployee().getEmail());
            addRow(table, "Nombre de tâches",   payStub.getNbrTasks().toString());
            addRow(table, "Bonus",   payStub.getBonus().toString());
            addRow(table, "Montant",   payStub.getAmount().toString());
            addRow(table, "Date de paiement",   payStub.getPaymentDate().toString());

            // Ajouter la table au document
            document.add(table);

        } catch (Exception e) {
            throw new RuntimeException("payStub pdf not create .... error from creation");

        }
        document.close();

    }

    private void addRow(PdfPTable table, String name , String data) {
            table.addCell(new PdfPCell(new Paragraph(name))) ;

            table.addCell(new PdfPCell(new Paragraph(data))) ;
    }

    private void addTableHeader(PdfPTable table, String... columnTitles) {
          for(String title : columnTitles){
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(Color.darkGray);
              
                header.setPadding(5);
                header.setPhrase(new Paragraph(title , FontFactory.getFont(FontFactory.COURIER_OBLIQUE)));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(header);
          }

    }
    public void generatedPdf2(Long id , HttpServletResponse response){
        PayStub p = this.payStubRepository.findById(id).orElseThrow();

        try {
            // 1. Initialize the document and writer
            Document document = new Document(PageSize.A4); // Page size: A4
            PdfWriter.getInstance(document , response.getOutputStream());
            PdfWriter.getInstance(document, new FileOutputStream("bulletin_de_paie.pdf"));

            // 2. Set document properties (Margins, etc.)
            document.setMargins(20, 20, 20, 20); // Set margins like in your HTML
            float twocol = 285f ;
            float twocol150 = twocol + 150f ;
            float[] twocolumnWidth = {twocol150 , twocol };
            Table tableHeader = new Table(2 , 1);
            tableHeader.setWidths(twocolumnWidth);
            tableHeader.setBorder(0);
            Image logo =  Image.getInstance("EMPLO.png") ;
            logo.setAbsolutePosition(10, 700);  // Positioning similar to CSS
            logo.scaleAbsolute(100, 50);
            Cell c = new Cell(logo) ;
            c.setBorder(0);
            tableHeader.addCell(c);
            Paragraph title = new Paragraph("ASSISTANCE FISCALE ET COMPTABLE", FontFactory.getFont(FontFactory.HELVETICA, 9));
            c = new Cell(title);
            c.setVerticalAlignment(VerticalAlignment.CENTER);
            c.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tableHeader.addCell(c);

            // 3. Open the document to start adding content
            document.open();
            document.add(tableHeader);
            document.add(new Paragraph("\n\n\n"));

            Table table = new Table(1,6);
            Phrase phrase = new Phrase("BULLETIN DE PAIE" , FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE , 15));
            c = new Cell(phrase);
            c.setVerticalAlignment(VerticalAlignment.CENTER);
            c.setHorizontalAlignment(HorizontalAlignment.CENTER);
            c.setBackgroundColor(Color.darkGray);
            table.addCell(c);


            Table table1 = new Table(1 , 7);
            table1.setWidth(90f);
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table1.addCell(c);
            addFieldForTable(table1, "Nom de l'employeur :      ", "EMPLOYEE_MANAGER");
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table1.addCell(c);
            addFieldForTable(table1, "Adresse :     ", "KODJOVIAKOPE près de la pharmacie Emmanuel");
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table1.addCell(c);
            addFieldForTable(table1, "Tel :     ", "70916006 / 70854652");
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table1.addCell(c);
            table.addCell(new Cell(table1));
            table1.setBorder(0);


            phrase = new Phrase("INFORMATION DE L'EMPLOYÉ" , FontFactory.getFont(FontFactory.HELVETICA , 11));
            c = new Cell(phrase);
            c.setVerticalAlignment(VerticalAlignment.CENTER);
            c.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(c);








            // Employer details (Field and value pairs)


            Table table2 = new Table(2, 30 );
            table2.setWidth(90f);

            addFieldForTable(table2, "Id:   ", String.valueOf(p.getEmployee().getId()));
            addFieldForTable(table2, "Nom :   ", p.getEmployee().getName());
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);

            addFieldForTable(table2, "Prénom :   ", p.getEmployee().getSurname());
            addFieldForTable(table2, "Date de naissance :   ", String.valueOf(p.getEmployee().getBirthday()));
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);




            addFieldForTable(table2, "Email :   ", p.getEmployee().getEmail());
            addFieldForTable(table2, "Adresse :     ",  p.getEmployee().getAddress());
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);


            addFieldForTable(table2, "Tel :     ",  p.getEmployee().getPhone());
            addFieldForTable(table2, "Poste :     ",  p.getEmployee().getPosition().getPosition_name());
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table2.addCell(c);

            table2.setBorder(0);
            table.addCell(new Cell(table2));


            phrase = new Phrase("DETAILS DE PAYEMENT" , FontFactory.getFont(FontFactory.HELVETICA , 11));
            c = new Cell(phrase);
            c.setVerticalAlignment(VerticalAlignment.CENTER);
            c.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(c);

            Table table3 = new Table(2 , 6);

            // PayStub fields (Dynamic values)
            addFieldForTable(table3, "Montant :", String.valueOf(p.getAmount()));
            addFieldForTable(table3, "Bonus :", String.valueOf(p.getBonus()));
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table3.addCell(c);
            c = new Cell(new Phrase("\n"));
            c.setBorder(0);
            table3.addCell(c);
            addFieldForTable(table3, "Nombre de taches :", String.valueOf(p.getNbrTasks()));
            addFieldForTable(table3, "Date de payement :", String.valueOf(p.getPaymentDate()));

            table.addCell(new Cell(table3));
            document.add(table);

            // 9. Close the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e) ;
        }
    }
    private static void addField(Document document, String label, String value) throws DocumentException {
        // Create a paragraph for the field and value pair
        Paragraph field = new Paragraph();
        Chunk labelChunk = new Chunk(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        Chunk valueChunk = new Chunk(value, FontFactory.getFont(FontFactory.HELVETICA, 12));
        field.add(labelChunk);
        field.add(valueChunk);
        document.add(field);
    }

    private static void addFieldForTable(Table table, String label, String value) throws DocumentException {
        // Create a paragraph for the field and value pair
        Paragraph field = new Paragraph();
        Chunk labelChunk = new Chunk(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
        Chunk valueChunk = new Chunk(value, FontFactory.getFont(FontFactory.HELVETICA, 12));
        field.add(labelChunk);
        field.add(valueChunk);
        Cell c = new Cell(field);
        c.setBorder(0);
        table.addCell(c);
    }
                                                                                             

}
