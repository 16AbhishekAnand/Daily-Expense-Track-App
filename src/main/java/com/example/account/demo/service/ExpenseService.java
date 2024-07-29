package com.example.account.demo.service;

import com.example.account.demo.model.Expense;
import com.example.account.demo.model.ExpenseDetail;
import com.example.account.demo.model.Users;
import com.example.account.demo.repositories.ExpenseDetailRepository;
import com.example.account.demo.repositories.ExpenseRepository;
import com.example.account.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public ExpenseDetail expenseDetailbyId(Long detailId) {
        return expenseDetailRepository.findById(detailId).orElse(null);
    }

    public List<Expense> getAllExpense() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElse(null);
    }

    public List<ExpenseDetail> getExpensesByUserId(Long userId) {
        return expenseDetailRepository.findByUser_UserId(userId);
    }


    public void processExpense(Long expenseId, Map<String, Object> expenseTypes) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        for (Map.Entry<String, Object> entry : expenseTypes.entrySet()) {
            String type = entry.getKey();
            Object value = entry.getValue();

            switch (type) {
                case "equal":
                    splitExpenseEqually(expense, (List<Long>) value);
                    break;
                case "exact":
                    splitExpenseByExactAmounts(expense, (Map<Long, Double>) value);
                    break;
                case "percent":
                    splitExpenseByPercentage(expense, (Map<Long, Double>) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown expense type: " + type);
            }
        }
    }

    public void splitExpenseEqually(Expense expense, List<Long> userIds) {
        double equalShare = expense.getAmount() / userIds.size();

        for (Long userId : userIds) {
            ExpenseDetail detail = new ExpenseDetail();
            detail.setExpense(expense);

            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            detail.setUser(user);

            detail.setAmount(equalShare);
            expenseDetailRepository.save(detail);
        }
    }

    public void splitExpenseByExactAmounts(Expense expense, Map<Long, Double> userAmounts) {
        for (Map.Entry<Long, Double> entry : userAmounts.entrySet()) {
            Long userId = entry.getKey();
            Double amount = entry.getValue();

            ExpenseDetail detail = new ExpenseDetail();
            detail.setExpense(expense);

            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            detail.setUser(user);

            detail.setAmount(amount);
            expenseDetailRepository.save(detail);
        }
    }

    public void splitExpenseByPercentage(Expense expense, Map<Long, Double> userAmounts) {
        double totalAmount = expense.getAmount();

        for (Map.Entry<Long, Double> entry : userAmounts.entrySet()) {
            Long userId = entry.getKey();
            Double percentage = entry.getValue();
            double amount = (percentage / 100) * totalAmount;

            ExpenseDetail detail = new ExpenseDetail();
            detail.setExpense(expense);

            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            detail.setUser(user);

            detail.setAmount(amount);
            detail.setPercentage(percentage);
            expenseDetailRepository.save(detail);
        }
    }


}