package com.example.account.demo.controller;

import com.example.account.demo.model.Expense;
import com.example.account.demo.model.ExpenseDetail;
import com.example.account.demo.service.ExpenseService;
import com.example.account.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    // Add a new expense
    @PostMapping("/new-expense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.addExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    // Get individual expense
    @GetMapping("/user-expense/{userId}")
    public ResponseEntity<ExpenseDetail> getUserExpense(@PathVariable Long userId) {
        ExpenseDetail expenseDetails = expenseService.expenseDetailbyId(userId);
        return new ResponseEntity<>(expenseDetails, HttpStatus.OK);
    }

    // Get all expenses
    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpense();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Process expense with various types
    @PostMapping("/process/{expenseId}")
    public ResponseEntity<Void> processExpense(@PathVariable Long expenseId, @RequestBody Map<String, Object> expenseTypes) {
        // Ensure expenseTypes is structured correctly before passing
        expenseService.processExpense(expenseId, expenseTypes);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Below endPoints are optional.
    // Split expense equally
    @PostMapping("/split-equally/{expenseId}")
    public ResponseEntity<Void> splitExpenseEqually(@PathVariable Long expenseId, @RequestBody List<Long> userIds) {
        Expense expense = expenseService.getExpenseById(expenseId);
        expenseService.splitExpenseEqually(expense, userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Split expense by exact amounts
    @PostMapping("/split-exact/{expenseId}")
    public ResponseEntity<Void> splitExpenseByExactAmounts(@PathVariable Long expenseId, @RequestBody Map<String, Double> userAmounts) {
        Map<Long, Double> convertedUserAmounts = userAmounts.entrySet().stream()
                .collect(Collectors.toMap(entry -> Long.parseLong(entry.getKey()), Map.Entry::getValue));
        Expense expense = expenseService.getExpenseById(expenseId);
        expenseService.splitExpenseByExactAmounts(expense, convertedUserAmounts);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Split expense by percentage
    @PostMapping("/split-percentage/{expenseId}")
    public ResponseEntity<Void> splitExpenseByPercentage(@PathVariable Long expenseId, @RequestBody Map<String, Double> userAmounts) {
        Map<Long, Double> convertedUserAmounts = userAmounts.entrySet().stream()
                .collect(Collectors.toMap(entry -> Long.parseLong(entry.getKey()), Map.Entry::getValue));
        Expense expense = expenseService.getExpenseById(expenseId);
        expenseService.splitExpenseByPercentage(expense, convertedUserAmounts);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
