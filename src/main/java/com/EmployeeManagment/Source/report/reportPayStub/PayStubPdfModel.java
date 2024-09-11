package com.EmployeeManagment.Source.report.reportPayStub;


import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Exception.PayStubNotFoundException;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
    public void generetedPdf(Long id , HttpServletResponse response) throws IOException {
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
                                                                                             

}
