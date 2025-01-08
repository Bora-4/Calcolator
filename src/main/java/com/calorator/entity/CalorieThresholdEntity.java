package com.calorator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "calorie_thresholds")

public class CalorieThresholdEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "threshold_id")
    private int thresholdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "threshold_date")
    @Temporal(TemporalType.DATE)
    private Date thresholdDate;

    @Column(name = "total_calories")
    private int totalCalories;

    @Column(name = "is_warning_triggered")
    private boolean isWarningTriggered;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    public int getThresholdId() {
        return thresholdId;
    }

    public void setThresholdId(int thresholdId) {
        this.thresholdId = thresholdId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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
