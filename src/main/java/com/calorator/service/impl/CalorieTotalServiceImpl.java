package com.calorator.service.impl;

import com.calorator.dto.CalorieTotalDTO;
import com.calorator.entity.CalorieTotalEntity;
import com.calorator.repository.CalorieTotalRepository;
import com.calorator.service.CalorieTotalService;
import com.calorator.mapper.CalorieTotalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CalorieTotalServiceImpl implements CalorieTotalService {

    @Autowired
    private CalorieTotalRepository calorieTotalRepository;

    @Autowired
    private CalorieTotalMapper calorieTotalMapper;

    @Override
    public CalorieTotalDTO getTotalByUserIdAndDate(int userId, Date date) {
        CalorieTotalEntity entity = calorieTotalRepository.findByUserIdAndDate(userId, date);
        return entity != null ? calorieTotalMapper.toDTO(entity) : null;
    }

    @Override
    public CalorieTotalDTO saveTotal(CalorieTotalDTO totalDTO) {
        CalorieTotalEntity total = calorieTotalMapper.toEntity(totalDTO);
        total = calorieTotalRepository.save(total);
        return calorieTotalMapper.toDTO(total);
    }

    @Override
    public void updateTotalCalories(int userId, int calories, Date date) {

        CalorieTotalEntity total = calorieTotalRepository.findByUserIdAndDate(userId, date);
        if(total == null){
            total = new CalorieTotalEntity();
            total.setUserId(userId);
            total.setDate(new Date());
            total.setTotalCalories(calories);
        }
        else {
            total.setTotalCalories(total.getTotalCalories() + calories);
        }
        calorieTotalRepository.save(total);
    }


}
