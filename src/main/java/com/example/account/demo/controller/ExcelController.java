package com.example.account.demo.controller;

import com.example.account.demo.model.ExpenseDetail;
import com.example.account.demo.repositories.ExpenseDetailRepository;
import com.example.account.demo.service.ExpenseService;
import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/download")
public class ExcelController {

    @Autowired ExpenseService expenseService;
    @Autowired ExpenseDetailRepository expenseDetailRepository;

    @GetMapping("/excel-sheet")
    public void downloadFile(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"UserExpense.xlsx\"");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Employees");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ExpenseId");
            headerRow.createCell(1).setCellValue("ExpenseAmount");
            headerRow.createCell(2).setCellValue("ExpenseType");
            headerRow.createCell(3).setCellValue("UserId");
            headerRow.createCell(4).setCellValue("UserName");
            headerRow.createCell(5).setCellValue("PaidAmount");

            // Fetch data from the database and populate the sheet
            List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAll();
            int rowIndex = 1; // Field Name is defined.
            for (ExpenseDetail detail : expenseDetails) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(detail.getExpense().getExpenseId());
                row.createCell(1).setCellValue(detail.getExpense().getAmount()); // Expense amount
                row.createCell(2).setCellValue(detail.getExpense().getExpenseType());
                row.createCell(3).setCellValue(detail.getUser().getUserId());
                row.createCell(4).setCellValue(detail.getUser().getName());
                row.createCell(5).setCellValue(detail.getAmount()); // amount paid by user.
            }
            // Write the workbook to the response output stream
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
