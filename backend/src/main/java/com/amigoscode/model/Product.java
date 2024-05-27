package com.amigoscode.model;

import com.amigoscode.model.enums.ECurrency;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "[Name] cannot be null.")
    @Size(min = 1, max = 250, message = "[Name] must be between 1 and 250 characters.")
    private String name;

    @NotNull(message = "[Description] cannot be null.")
    @Size(min = 1, max = 500, message = "[Description] must be between 1 and 500 characters.")
    private String description;

    @NotNull(message = "[StartingPrice] cannot be null.")
    @Min(value = 0, message = "[StartingPrice] cannot be negative.")
    private double startingPrice;

    @NotNull(message = "[Currency] cannot be null.")
    @Enumerated(EnumType.STRING)
    private ECurrency currency;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @NotNull(message = "[Seller] cannot be null.")
    private Customer seller;

    @NotNull(message = "[CreationDate] cannot be null.")
    private LocalDateTime creationDate;

    @NotNull(message = "[StartDate] cannot be null.")
    private LocalDateTime startDate;

    @NotNull(message = "[EndDate] cannot be null.")
    private LocalDateTime endDate;


    // Default constructor for JPA
    protected Product() {
    }

    public Product(String name, String description, double startingPrice, ECurrency currency, Customer seller, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currency = currency;
        this.seller = seller;
        this.creationDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public ECurrency getCurrency() {
        return currency;
    }

    public void setCurrency(ECurrency currency) {
        this.currency = currency;
    }

    public Customer getSeller() {
        return seller;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startingPrice=" + startingPrice +
                ", currency=" + currency +
                ", seller=" + seller +
                ", creationDate=" + creationDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
