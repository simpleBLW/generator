package com.simpleblw.generator.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

/**
 * PDF Service.
 *
 * @author <a href="diwad1024@gmail.com">Dawid Pietryga</a>
 */
@Service
public class PDFService {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${output.dir}")
  private String outputDir;

  public String writePDF(String html, String lang, String name, String title) {
    try {
      logger.info("Generating: " + outputDir + name + ".pdf");
      FileOutputStream outputStream = new FileOutputStream(outputDir + name + ".pdf");

      PdfWriter pdfWriter = new PdfWriter(outputStream);
      PdfDocument pdfDoc = new PdfDocument(pdfWriter);
      pdfDoc.getCatalog().setLang(new PdfString(lang));
      pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));

      PdfDocumentInfo pdfMetaData = pdfDoc.getDocumentInfo();
      pdfMetaData.setAuthor("Simpleblw");
      pdfMetaData.addCreationDate();
      pdfMetaData.setCreator("simpleblw");
      pdfMetaData.setKeywords("recipes,przepisy,blw");
      pdfMetaData.setSubject(title);

      FontProvider fontProvider = new FontProvider();
      fontProvider.addStandardPdfFonts();

      ConverterProperties props = new ConverterProperties();
      props.setFontProvider(fontProvider);

      HtmlConverter.convertToPdf(html, pdfDoc, props);

      pdfDoc.close();
      logger.info("Generated: " + outputDir + name + ".pdf");
    } catch (IOException ex) {
      logger.error("Error write pdf : " + outputDir + name + ".pdf", ex);
      return ex.getMessage();
    }
    return "OK";
  }
}
