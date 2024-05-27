package com.amigoscode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Rating {

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
    @JoinColumn(name = "rating_user_id", nullable = false)
    @NotNull(message = "[RatingUser] cannot be null.")
    private Customer ratingUser;

    @ManyToOne
    @JoinColumn(name = "rated_user_id", nullable = false)
    @NotNull(message = "[RatedUser] cannot be null.")
    private Customer ratedUser;

    @NotNull(message = "[Grade] cannot be null.")
    @Min(value = 1, message = "[Grade] must be between 1 and 10.")
    @Max(value = 10, message = "[Grade] must be between 1 and 10.")
    private int grade;

    private String comment;

    public Rating(Product product, Customer ratingUser, Customer ratedUser, int grade, String comment) {
        this.dateAndTime = LocalDateTime.now();
        this.product = product;
        this.ratingUser = ratingUser;
        this.ratedUser = ratedUser;
        this.grade = grade;
        this.comment = comment;
    }

    // Default constructor for JPA
    protected Rating() {
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

    public Customer getRatingUser() {
        return ratingUser;
    }

    public Customer getRatedUser() {
        return ratedUser;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", dateAndTime=" + dateAndTime +
                ", product=" + product +
                ", ratingUser=" + ratingUser +
                ", ratedUser=" + ratedUser +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                '}';
    }
}
