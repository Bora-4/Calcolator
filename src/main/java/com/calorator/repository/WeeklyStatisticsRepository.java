package com.calorator.repository;
import com.calorator.entity.WeeklyStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyStatisticsRepository extends JpaRepository<WeeklyStatisticsEntity, Long> {
}