package io.github.LucasMS115.spring_sales.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(){
        super("Order not found.");
    }
}
