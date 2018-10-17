package org.rone.study.springmvc.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Service
public class HelloWorldService {

    private Image base64ToImage(String base64Data) throws IOException, BadElementException {
        String imageData = (base64Data.split("base64,"))[1];
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = decoder.decodeBuffer(imageData);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {// 调整异常数据
                data[i] += 256;
            }
        }
        Image image = Image.getInstance(data);
        return image;
    }

    public void downLoadPDF(HttpServletResponse response, String firstBase64Data, String secondBase64Data, String thirdBase64Data, String fourthBase64Data) throws IOException, DocumentException {

//        //base64生成文件
//        //去掉前缀，data:image/png;base64,
//        String imageData = (firstBase64Data.split("base64,"))[1];
//
//        BASE64Decoder decoder = new BASE64Decoder();
//        // Base64解码
//        byte[] b = decoder.decodeBuffer(imageData);
//        for (int i = 0; i < b.length; ++i) {
//            if (b[i] < 0) {// 调整异常数据
//                b[i] += 256;
//            }
//        }
//
//        OutputStream out = new FileOutputStream("E:/image.jpg");
//        out.write(b);
//        out.flush();
//        out.close();


        // 1.新建document对象
        Document pdfDoc = new Document();

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
//        PdfWriter writer = PdfWriter.getInstance(pdfDoc, new FileOutputStream("E:/itext.pdf"));

        response.reset();
        response.setContentType("application/pdf;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("客户分析报告" + ".pdf", "UTF-8"));
        PdfWriter pdfWriter = PdfWriter.getInstance(pdfDoc, response.getOutputStream());

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
        customerCell.setImage(base64ToImage(firstBase64Data));
        customerTable.addCell(customerCell);
        customerCell = new PdfPCell();
        customerCell.setImage(base64ToImage(secondBase64Data));
        customerTable.addCell(customerCell);
        customerCell = new PdfPCell();
        customerCell.setImage(base64ToImage(thirdBase64Data));
        customerTable.addCell(customerCell);
        customerCell = new PdfPCell();
        customerCell.setImage(base64ToImage(fourthBase64Data));
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
//        writer.close();
        pdfWriter.close();



//        return pdfDoc;
    }

}
