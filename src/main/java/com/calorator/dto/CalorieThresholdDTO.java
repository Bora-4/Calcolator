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
public class CalorieThresholdDTO {
    private int thresholdId;
    private UserDTO user;
    private Date thresholdDate;
    private int totalCalories;
    private boolean isWarningTriggered;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public int getThresholdId() {
        return thresholdId;
    }

    public void setThresholdId(int thresholdId) {
        this.thresholdId = thresholdId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getThresholdDate() {
        return thresholdDate;
    }

    public void setThresholdDate(Date thresholdDate) {
        this.thresholdDate = thresholdDate;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public boolean isWarningTriggered() {
        return isWarningTriggered;
    }

    public void setWarningTriggered(boolean warningTriggered) {
        isWarningTriggered = warningTriggered;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
