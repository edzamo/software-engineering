package com.solid.principle.dip;

import com.solid.principle.dip.example.good.EarningReport;
import com.solid.principle.dip.example.good.FileCSVExport;
import com.solid.principle.dip.example.good.FileDocxExport;
import com.solid.principle.dip.example.good.FilePDFExport;
import com.solid.principle.dip.example.good.FinalThesis;
import com.solid.principle.dip.example3.good.Company;
import com.solid.principle.dip.example3.good.factory.EmployeeMemoryPersistenceFactory;

public class DipMain {
    
    public static void main() {
        System.out.println("Dependency Inversion Principle");
        // Creating instances
        FinalThesis thesis = new FinalThesis();
        EarningReport earningReport = new EarningReport();
        
        // Using thesis as PDF export
        FilePDFExport pdfExport = thesis;
        pdfExport.exportPDF();
        
        // Using thesis as DOCX export
        FileDocxExport docxExport = thesis;
        docxExport.exportDoc();
        
        // Using earning report as CSV export
        FileCSVExport csvExport = earningReport;
        csvExport.exportCSV();
        
        System.out.println("All exports completed successfully!");

        System.out.println("Example 3");
  
      Company company = new Company(new EmployeeMemoryPersistenceFactory());
      System.out.println("company: " + company);

    }
}
