package nl.itopia.corendon.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import nl.itopia.corendon.model.EmployeeModel;
import nl.itopia.corendon.mvc.Controller;

/**
 *
 * @author Igor's_Boven
 */
public class ManagerStatisticsPDF {
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Image graph;
    
    public static void generateManagerReportPDF(File file, Chart chart){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addContent(file, document, chart);
            document.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private static void addContent(File file, Document document, Chart chart)
        throws DocumentException, BadElementException, IOException {
        Paragraph paragraph = new Paragraph();

        addEmptyLine(paragraph, 1);
        // TITLE
        paragraph.add(new Paragraph("Manager luggage statistics report", titleFont));

        addEmptyLine(paragraph, 1);
        
        String username = (EmployeeModel.currentEmployee.username);
        paragraph.add(new Paragraph("Report generated by: " + username + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            smallBold));
        addEmptyLine(paragraph, 3);
        paragraph.add(new Paragraph("This document is a report of Manager statistics of the lost luggage system.",
            smallBold));

        addEmptyLine(paragraph, 4);
        
        //Convert LineChart byte by byte to WritableImage
        WritableImage wi = chart.snapshot(null,null);

        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", byteOutput);
        }
        catch(Exception s) {
            System.out.println("Something went wrong with generating graph in PDF.");
        }
        
        
        graph = Image.getInstance(byteOutput.toByteArray());
        
        document.add(paragraph);
        document.add(graph);
        //OPEN PDF AFTER CREATION 
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            System.out.println("You need to have default program set to open .PDF files.");
        }        
    }

    private static void addEmptyLine(Paragraph paragraph, int lines) {
        for (int i = 0; i < lines; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}

