package org.rone.study.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.FileOutputStream;
import java.io.IOException;

public class LocalTest {
    public static void main(String[] args) throws DocumentException, IOException {
        testIText();
    }

    public static void testPdfBox() throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDFont font = PDType1Font.HELVETICA_BOLD;
        PDPageContentStream content = new PDPageContentStream(doc, page);
        content.beginText();
        content.setFont(font, 12);
        content.showText("fuck, 你好");
//        content.moveTextPositionByAmount(100, 700);
//        content.drawString("hello,你好");

        content.endText();
        content.close();
        doc.save("E:\\pdfBox.pdf");
        doc.close();
    }

    public static void testIText() throws DocumentException, IOException {
        // 1.新建document对象
        Document pdfDoc = new Document();

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(pdfDoc, new FileOutputStream("E:/itext.pdf"));

        // 3.打开文档
        pdfDoc.open();

        //中文字体
        BaseFont chineseBaseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);

        // 4.添加内容
        //标题
        Font titleFont = new Font(chineseBaseFont);
        titleFont.setSize(20);
        titleFont.setStyle(Font.BOLD);
        Paragraph titleParagraph = new Paragraph("稠州银行客户分析报告", titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        pdfDoc.add(titleParagraph);

        pdfDoc.add(new Paragraph(" "));
        pdfDoc.add(new Paragraph(" "));

        //报告说明
        PdfPTable pdfPTable = new PdfPTable(1);
        PdfPCell pdfPCell = new PdfPCell();
        Paragraph reportInstruction = new Paragraph();
        reportInstruction.setFont(new Font(chineseBaseFont));
        Font boldFont = new Font(chineseBaseFont);
        boldFont.setSize(15);
        boldFont.setStyle(Font.BOLD);
        reportInstruction.add(new Chunk("报告说明", boldFont));
        reportInstruction.add(new Chunk("\n1. 本报告为稠州银行客户分析报告", new Font()));
        reportInstruction.add(new Chunk("\n2. 版权所有 浙江稠州商业银行", new Font()));
        reportInstruction.add(new Chunk("\n3. 本报告为稠州银行客户分析报告", new Font()));
        reportInstruction.add(new Chunk("\n4. XXXXXXXXXXXXXXXXXXXXXXXXXXXXX", new Font()));
//        reportInstruction.setAlignment(Element.ALIGN_CENTER);
        pdfPCell.setPhrase(reportInstruction);
        pdfPTable.addCell(pdfPCell);
        pdfDoc.add(pdfPTable);
//        pdfDoc.add(reportInstruction);

        //客户基本信息图表
        pdfDoc.add(new Paragraph("客户基本信息", boldFont));
        PdfPTable customerTable = new PdfPTable(1);
        PdfPCell customerCell = new PdfPCell();
//        Image image = Image.getInstance();
//        pdfDoc.add(image);
        customerCell.setPhrase(new Paragraph("ECharts图表", new Font(chineseBaseFont)));
        customerTable.addCell(customerCell);
        pdfDoc.add(customerTable);


        //设置属性
        //标题
        pdfDoc.addTitle("稠州银行客户分析报告");
        //作者
        pdfDoc.addAuthor("稠州银行");
        //主题
        pdfDoc.addSubject("客户分析报告");
        //关键字
        pdfDoc.addKeywords("客户分析报告");
        //创建时间
        pdfDoc.addCreationDate();
        //应用程序
        pdfDoc.addCreator("精准营销");

        //关闭文档
        pdfDoc.close();
        //关闭书写器
        writer.close();
    }
}
