package com.calorator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "calorie_total")
public class CalorieTotalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "total_calories")
    private int totalCalories;

    @Column(name = "is_warning_triggered")
    private boolean isWarningTriggered;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public boolean isWarningTriggered() {
        return isWarningTriggered;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setWarningTriggered(boolean isWarningTriggered) {
        this.isWarningTriggered = isWarningTriggered;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

