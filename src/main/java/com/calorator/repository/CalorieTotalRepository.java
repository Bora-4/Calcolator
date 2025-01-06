package com.calorator.repository;

import com.calorator.entity.CalorieTotalEntity;
import java.util.Date;

public interface CalorieTotalRepository {
    CalorieTotalEntity findByUserIdAndDate(int userId, Date date);
    CalorieTotalEntity save(CalorieTotalEntity calorieTotalEntity);
    void deleteById(int id);
}
