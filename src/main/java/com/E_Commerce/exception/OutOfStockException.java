package com.E_Commerce.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) { super(message); }
}
