package com.EmployeeManagment.Source.Pay_Stub.Service;


import com.EmployeeManagment.Source.Pay_Stub.Entity.PayStub;
import com.EmployeeManagment.Source.Pay_Stub.Repository.PayStubRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServicePayStub {
    @Autowired
    private PayStubRepository payStubRepository ;


    public String generatePayStubReport(Long id, String reportFormat) {
        try {
            // Récupérer le PayStub
            PayStub payStub = payStubRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("PayStub with ID " + id + " not found."));

            // Charger et compiler le fichier JRXML
            File file = ResourceUtils.getFile("classpath:payStub.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            // Créer le data source avec un singleton du PayStub
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(payStub));

            // Ajouter des paramètres pour le rapport
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Employee_Manager");

            // Remplir le rapport avec les données
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exporter le rapport au format PDF
            if ("pdf".equalsIgnoreCase(reportFormat)) {
                String exportPath = "C:\\Users\\RON_ALLOGO\\Desktop\\payStub.pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
            }

            return "Report generated successfully.";

        } catch (FileNotFoundException e) {
            return "Template file not found: " + e.getMessage();
        } catch (JRException e) {
            return "Error generating report: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }



}


