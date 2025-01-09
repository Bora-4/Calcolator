package com.calorator.service.impl;

import com.calorator.dto.CalorieThresholdDTO;
import com.calorator.entity.CalorieThresholdEntity;
import com.calorator.entity.UserEntity;
import com.calorator.repository.CalorieThresholdRepository;
import com.calorator.repository.UserRepository;
import com.calorator.service.CalorieThresholdService;
import com.calorator.mapper.CalorieThresholdMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CalorieThresholdServiceImpl implements CalorieThresholdService {

    @Autowired
    private CalorieThresholdRepository calorieThresholdRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalorieThresholdMapper calorieThresholdMapper;

    @Override
    public CalorieThresholdDTO getThresholdByUserIdAndDate(Long userId, Date date) {
        CalorieThresholdEntity entity = calorieThresholdRepository.findByUserIdAndDate(userId, date);
        return entity != null ? calorieThresholdMapper.toDTO(entity) : null;
    }

    @Override
    public CalorieThresholdDTO saveThreshold(CalorieThresholdDTO thresholdDTO) {

        UserEntity user = userRepository.findById(thresholdDTO.getUser().getId());

        CalorieThresholdEntity threshold = calorieThresholdMapper.toEntity(thresholdDTO, user);
        threshold = calorieThresholdRepository.save(threshold);
        return calorieThresholdMapper.toDTO(threshold);
    }

    @Override
    public void updateTotalCalories(Long userId, int calories, Date date) {

        UserEntity user = userRepository.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User with id " + userId + " was not found.");
        }
        CalorieThresholdEntity threshold = calorieThresholdRepository.findByUserIdAndDate(userId, date);
        if (threshold == null) {
            threshold = new CalorieThresholdEntity();
            threshold.setUser(user);
            threshold.setThresholdDate(new Date());
            threshold.setTotalCalories(calories);
            threshold.setWarningTriggered(false);
        } else {
            threshold.setTotalCalories(threshold.getTotalCalories() + calories);
        }
        calorieThresholdRepository.save(threshold);
    }

    @Override
    public boolean isThresholdExceeded(Long userId, Date date) {
        CalorieThresholdEntity thresholdEntity = calorieThresholdRepository.findByUserIdAndDate(userId, date);
        int defaultCalorieThreshold = 2500;
        if (thresholdEntity != null && thresholdEntity.getTotalCalories() > defaultCalorieThreshold) {
            thresholdEntity.setWarningTriggered(true);
            calorieThresholdRepository.save(thresholdEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<Date> getExceededThresholdDates(Long userId, Date startDate, Date endDate) {
        return calorieThresholdRepository.findExceededThresholdDates(userId, startDate, endDate); }


}
