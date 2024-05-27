package com.amigoscode.model;

import com.amigoscode.model.enums.ECurrency;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "[DateAndTime] cannot be null.")
    private LocalDateTime dateAndTime;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "[Product] cannot be null.")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "[Buyer] cannot be null.")
    private Customer buyer;

    @NotNull(message = "[Amount] cannot be null.")
    @Min(value = 0, message = "[Amount] must be greater than zero.")
    private double amount;

    @NotNull(message = "[Currency] cannot be null.")
    @Enumerated(EnumType.STRING)
    private ECurrency currency;

    // Default constructor for JPA
    protected Bid() {
    }

    public Bid(Product product, Customer buyer, double amount, ECurrency currency) {
        this.dateAndTime = LocalDateTime.now();
        this.product = product;
        this.buyer = buyer;
        this.amount = amount;
        this.currency = currency;
    }


    public int getId() {
        return id;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public Product getProduct() {
        return product;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public double getAmount() {
        return amount;
    }

    public ECurrency getCurrency() {
        return currency;
    }
}
