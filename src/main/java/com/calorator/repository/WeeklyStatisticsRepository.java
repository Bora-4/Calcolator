package com.calorator.repository;

import com.calorator.entity.WeeklyStatisticsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyStatisticsRepository {

    void save(WeeklyStatisticsEntity weeklyStatisticsEntity);

    WeeklyStatisticsEntity findById(Long id);

    List<WeeklyStatisticsEntity> findAll();

    void update(WeeklyStatisticsEntity weeklyStatisticsEntity);

    void delete(Long id);

    WeeklyStatisticsEntity findByStatisticName(String statisticName);
}