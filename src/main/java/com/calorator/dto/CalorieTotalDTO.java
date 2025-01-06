package com.calorator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalorieTotalDTO {
    private int id;
    private int userId;
    private Date date;
    private int totalCalories;
    private boolean isWarningTriggered;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters



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

    public boolean getIsWarningTriggered() {
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

    public void setIsWarningTriggered(boolean isWarningTriggered) {
        this.isWarningTriggered = isWarningTriggered;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
