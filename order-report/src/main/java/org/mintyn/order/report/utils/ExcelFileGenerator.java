package org.mintyn.order.report.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mintyn.order.report.entities.OrderReport;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;

@Component
public class ExcelFileGenerator {

    public void generateTerminalRequestData(List<OrderReport> reports, String range){

        try {

            Workbook workbook = new XSSFWorkbook();
            CellStyle headerStyle = getBasicHeaderStyle(workbook);

            String[] headers = orderReportHeaders();
            Sheet sheet = workbook.createSheet("Order Reports");
            Row row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;

            for (OrderReport orderReport : reports) {
                int cellIndex = 0;

                row = sheet.createRow(rowIndex++);

                //  Date Created
                Cell cell = row.createCell(cellIndex++);
                cell.setCellValue(orderReport.getOrderCreatedDate().toString());

                //  Customer Name
                cell = row.createCell(cellIndex++);
                cell.setCellValue(orderReport.getCustomerName());

                // Customer Phone Number
                cell = row.createCell(cellIndex++);
                cell.setCellValue(orderReport.getCustomerPhoneNumber());

                // Product Name
                cell = row.createCell(cellIndex++);
                cell.setCellValue(orderReport.getProductName());

                // Product Price
                cell = row.createCell(cellIndex++);
                cell.setCellValue(String.valueOf(orderReport.getProductPrice()));

                // Order Quantity
                cell = row.createCell(cellIndex++);
                cell.setCellValue(orderReport.getOrderQuantity());

                cell = row.createCell(cellIndex);
                cell.setCellValue(String.valueOf(orderReport.getTotalProductPrice()));

            }

            for (int i = 0, l = headers.length; i < l; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream out = new FileOutputStream("Order Report for range: "+ range + ".xlsx");

            workbook.write(out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String[] orderReportHeaders() {
        return new String[]{"Date Created", "Customer Name", "Customer PhoneNo", "Product Name",
                "Product Price", "Order Quantity", "Total Price For Order"};
    }

    private static CellStyle getBasicHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop(BorderStyle.valueOf((short) 6));
        headerStyle.setBorderBottom(BorderStyle.valueOf((short)1));
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.forInt(1));
        return headerStyle;
    }
}
