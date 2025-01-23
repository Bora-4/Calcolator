package com.calorator.service.impl;

import com.calorator.dto.ReportDTO;
import com.calorator.dto.UserDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.ReportMapper;
import com.calorator.mapper.UserMapper;
import com.calorator.repository.ReportRepository;
import com.calorator.service.ReportService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void save(ReportDTO reportDTO) {
        UserDTO admin = reportDTO.getAdmin();
        UserEntity adminEntity = null;
        if (admin != null) {
            adminEntity = UserMapper.toEntity(admin);
        }
        ReportEntity reportEntity = ReportMapper.toEntity(reportDTO, adminEntity);
        reportRepository.save(reportEntity);
    }

    @Override
    public ReportDTO findById(Long id) {
        ReportEntity reportEntity = reportRepository.findById(id);
        if (reportEntity != null) {
            return ReportMapper.toDTO(reportEntity);
        }
        throw new EntityNotFoundException("Report with id " + id + " was not found.");
    }

    @Override
    public List<ReportDTO> findAll() {
        return reportRepository.findAll().stream()
                .map(ReportMapper::toDTO)
                .toList();
    }

    @Override
    public void update(ReportDTO reportDTO) {
        ReportEntity existingReport = reportRepository.findById(reportDTO.getId());
        if (existingReport != null) {
            ReportEntity updatedReport = ReportMapper.toEntity(reportDTO, existingReport.getAdmin());
            reportRepository.update(updatedReport);
        } else {
            throw new EntityNotFoundException("Report with id " + reportDTO.getId() + " was not found.");
        }
    }

    @Override
    public void delete(Long id) {
        ReportEntity reportEntity = reportRepository.findById(id);
        if (reportEntity == null) {
            throw new EntityNotFoundException("Report with id " + id + " was not found.");
        }
        reportRepository.delete(id);
    }

    @Override
    public ReportDTO findByReportDate(String reportDate) {
        if (reportDate == null || reportDate.isEmpty()) {
            throw new IllegalArgumentException("Report date cannot be null or empty.");
        }
        ReportEntity reportEntity = reportRepository.findByReportDate(reportDate);
        if (reportEntity == null) {
            throw new IllegalArgumentException("No report found for the given date: " + reportDate);
        }
        return ReportMapper.toDTO(reportEntity);
    }
}
