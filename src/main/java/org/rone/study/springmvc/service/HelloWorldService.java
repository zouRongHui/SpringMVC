package org.rone.study.springmvc.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;

@Service
public class HelloWorldService {

    private Image base64ToImage(String base64Data) throws IOException, BadElementException {
        //去掉前缀，data:image/png;base64,
        String imageData = (base64Data.split("base64,"))[1];
//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] data = decoder.decodeBuffer(imageData);
        //java1.8中新增了对base64编码的API
        byte[] data = Base64.getDecoder().decode(imageData);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {// 调整异常数据
                data[i] += 256;
            }
        }
        Image image = Image.getInstance(data);
        return image;
    }

    public void downLoadPDF(HttpServletResponse response, String firstBase64Data, String secondBase64Data, String thirdBase64Data, String fourthBase64Data) throws IOException, DocumentException {
        // 1.新建document对象
        Document pdfDoc = new Document();

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
//        PdfWriter writer = PdfWriter.getInstance(pdfDoc, new FileOutputStream("E:/itext.pdf"));

        response.reset();
        response.setContentType("application/pdf;charset=utf-8");
        //下列模式在Firefox中文件名会乱码
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("客户分析报告" + ".pdf", "UTF-8"));
        //下列模式在chrome和Firefox中实测文件名中文无乱码
        String fileName = new String("客户分析报告.pdf".getBytes("UTF-8"), "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        PdfWriter pdfWriter = PdfWriter.getInstance(pdfDoc, response.getOutputStream());

        //设置页眉和页脚
        PdfPageEvent pdfPageEvent = new PdfPageEventHelper(){
            private PdfTemplate total;
            private BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            private int fontSize = 8;

            //文档打开时触发
            @Override
            public void onOpenDocument(PdfWriter writer, Document document) {
                super.onOpenDocument(writer, document);
                total = writer.getDirectContent().createTemplate(50, 50);
            }

            //单个页面结束时触发
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                super.onEndPage(writer, document);
                //页眉
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("稠州银行", new Font(baseFont, fontSize)),
                        (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F,
                        document.top() + 20F, 0);
                //页脚,格式为 第X页 /共Y页
                String foot = "第 " + writer.getPageNumber() + " 页 /共";
                //获取页脚第一部分文本长度， 用于调节第二部分的位置
                Float length = baseFont.getWidthPoint(foot, fontSize);
                PdfContentByte cb = writer.getDirectContent();
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Paragraph(foot, new Font(baseFont, fontSize)),
                        (document.rightMargin() + document.right() + document.leftMargin() - document.left() - length) / 2.0F,
                        document.bottom() - 20F, 0);
                //通过一个模板来实现显示总页数
                cb.addTemplate(total, (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F, document.bottom() - 20F);
            }

            //文档关闭时触发
            @Override
            public void onCloseDocument(PdfWriter writer, Document document) {
                super.onCloseDocument(writer, document);
                //在文档关闭时，才知道该文档的总页数
                total.beginText();
                total.setFontAndSize(baseFont, fontSize);
                total.showText(" " + writer.getPageNumber() + " 页");
                total.endText();
                total.closePath();
            }
        };
        pdfWriter.setPageEvent(pdfPageEvent);

        // 3.打开文档
        pdfDoc.open();

        //中文字体
        BaseFont chineseBaseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(chineseBaseFont);
        //文本样式
        Font titleFont = new Font(chineseBaseFont);
        titleFont.setSize(20);
        titleFont.setStyle(Font.BOLD);
        Font subtitleFont = new Font(chineseBaseFont);
        subtitleFont.setSize(15);
        subtitleFont.setStyle(Font.BOLD);

        // 4.添加内容
        //标题
        Paragraph titleParagraph = new Paragraph("稠州银行客户分析报告", titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        pdfDoc.add(titleParagraph);

        pdfDoc.add(new Paragraph(" "));

        //报告说明
        Paragraph reportInstruction = new Paragraph();
        reportInstruction.setFont(chineseFont);
        reportInstruction.add(new Chunk("报告说明", subtitleFont));
        reportInstruction.add(new Chunk("\n1. 本报告为稠州银行客户分析报告", new Font()));
        reportInstruction.add(new Chunk("\n2. 版权所有 浙江稠州商业银行", new Font()));
        reportInstruction.add(new Chunk("\n3. 本报告为稠州银行客户分析报告", new Font()));
        reportInstruction.add(new Chunk("\n4. XXXXXXXXXXXXXXXXXXXXXXXXXXXXX", new Font()));

        PdfPTable reportInstructionTable = new PdfPTable(1);
        PdfPCell reportInstructionCell = new PdfPCell();
        reportInstructionCell.setPhrase(reportInstruction);
        reportInstructionTable.addCell(reportInstructionCell);
        pdfDoc.add(reportInstructionTable);

        //客户基本信息图表
        pdfDoc.add(new Paragraph("客户基本信息", subtitleFont));
        PdfPTable customerTable = new PdfPTable(2);
        //表格宽度100%，默认80%
        customerTable.setWidthPercentage(100F);
        //垂直居中
        customerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        //边框宽度为0
        customerTable.getDefaultCell().setBorderWidth(0F);

        customerTable.addCell(base64ToImage(firstBase64Data));
        customerTable.addCell(base64ToImage(secondBase64Data));
        PdfPCell customerCell = new PdfPCell();
        customerCell.setBorderWidth(0F);
        //设置跨两列
        customerCell.setColspan(2);
        customerCell.setImage(base64ToImage(thirdBase64Data));
        customerTable.addCell(customerCell);
        customerCell = new PdfPCell();
        customerCell.setBorderWidth(0F);
        //设置跨两列
        customerCell.setColspan(2);
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
