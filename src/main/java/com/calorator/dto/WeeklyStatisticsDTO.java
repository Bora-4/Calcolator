package com.calorator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklyStatisticsDTO {

    private Long id;
    private ReportDTO reportDTO;
    private String statisticName;
    private int statisticValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportDTO getReportDTO() {
        return reportDTO;
    }

    public void setReportDTO(ReportDTO reportDTO) {
        this.reportDTO = reportDTO;
    }

    public String getStatisticName() {
        return statisticName;
    }

    public void setStatisticName(String statisticName) {
        this.statisticName = statisticName;
    }

    public int getStatisticValue() {
        return statisticValue;
    }

    public void setStatisticValue(int statisticValue) {
        this.statisticValue = statisticValue;
    }
}
