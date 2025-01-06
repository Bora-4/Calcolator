package com.calorator.service;

import com.calorator.dto.CalorieTotalDTO;
import java.util.Date;

public interface CalorieTotalService {
    CalorieTotalDTO getTotalByUserIdAndDate(int userId, Date date);
    CalorieTotalDTO saveTotal(CalorieTotalDTO totalDTO);
    void updateTotalCalories(int userId, int calories, Date date);
}
