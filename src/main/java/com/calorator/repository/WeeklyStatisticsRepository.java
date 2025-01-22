package com.calorator.repository;

import com.calorator.entity.WeeklyStatisticsEntity;
import java.util.List;
import java.util.Optional;

public interface WeeklyStatisticsRepository {

    void save(WeeklyStatisticsEntity weeklyStatisticsEntity);

    WeeklyStatisticsEntity findById(Long id);

    List<WeeklyStatisticsEntity> findAll();

    void update(WeeklyStatisticsEntity weeklyStatisticsEntity);

    void delete(Long id);

    Optional<WeeklyStatisticsEntity> findByStatisticName(String statisticName);
}
