package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Transaction;

import java.util.List;

public interface TransactionDao {
    public Transaction save(Transaction transaction);

    public Transaction get(int transactionId);

    public void setSuccess(int transactionId);

    public List<Transaction> getAll();
}
