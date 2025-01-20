package com.calorator.repository;

import com.calorator.entity.ReportEntity;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    void save(ReportEntity reportEntity);

    ReportEntity findById(Long id);

    List<ReportEntity> findAll();

    void update(ReportEntity reportEntity);

    void delete(Long id);

    Optional<ReportEntity> findByReportDate(String reportDate);

}
