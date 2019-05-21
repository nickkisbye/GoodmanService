package dk.goodmanservice.goodmanservice;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;


public class Test {

    public static void main(String[] args) throws IOException, DocumentException {


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\nkh94\\Documents\\GitHub\\1SemesterProjekt\\src\\main\\java\\dk\\goodmanservice\\goodmanservice\\faktura.pdf"));
        document.open();
        document.add(new Paragraph("Test"));
        document.close();

    }

}
