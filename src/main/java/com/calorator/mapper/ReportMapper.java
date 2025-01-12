package com.calorator.mapper;

import com.calorator.dto.ReportDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;

public class ReportMapper {

    private ReportMapper(){
        // private constructor
    }

    public static ReportEntity toEntity(ReportDTO reportDTO, UserEntity user) {

        if (reportDTO == null || user == null){
            throw new NullPointerException("ReportDTO or UserEntity cannot be null!");
        }

        ReportEntity report = new ReportEntity();
        report.setId(reportDTO.getId());
        report.setAdmin(user);
        report.setReportDate(reportDTO.getReportDate());
        report.setCreatedAt(reportDTO.getCreatedAt());
        report.setUpdatedAt(reportDTO.getUpdatedAt());

        return report;

    }

    public static ReportDTO toDTO (ReportEntity report){

        if (report == null){
            throw new NullPointerException("ReportEntity cannot be null!");
        }

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReportDate(report.getReportDate());
        reportDTO.setCreatedAt(report.getCreatedAt());
        reportDTO.setUpdatedAt(report.getUpdatedAt());

        if(report.getAdmin() != null){
            reportDTO.setAdmin(UserMapper.toDTO(report.getAdmin()));
        } else {
            reportDTO.setAdmin(null);
        }

        return reportDTO;
    }
}
