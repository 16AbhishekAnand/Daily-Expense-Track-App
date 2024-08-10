package com.example.account.demo.repositories;

import com.example.account.demo.model.ExpenseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail,Long> {
    List<ExpenseDetail> findByUser_UserId(Long userId);

}
