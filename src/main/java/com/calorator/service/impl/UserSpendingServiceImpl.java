package com.calorator.service.impl;

import com.calorator.dto.ReportDTO;
import com.calorator.dto.UserDTO;
import com.calorator.dto.UserSpendingDTO;
import com.calorator.entity.ReportEntity;
import com.calorator.entity.UserEntity;
import com.calorator.entity.UserSpendingEntity;
import com.calorator.mapper.ReportMapper;
import com.calorator.mapper.UserMapper;
import com.calorator.mapper.UserSpendingMapper;
import com.calorator.repository.UserSpendingRepository;
import com.calorator.service.UserSpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSpendingServiceImpl implements UserSpendingService {

    private final UserSpendingRepository userSpendingRepository;

    @Autowired
    public UserSpendingServiceImpl(UserSpendingRepository userSpendingRepository) {
        this.userSpendingRepository = userSpendingRepository;
    }

    @Override
    public void saveUserSpending(UserSpendingDTO userSpendingDTO) {
        UserDTO userDTO = userSpendingDTO.getUser();
        UserEntity userEntity = UserMapper.toEntity(userDTO);

        ReportDTO reportDTO = userSpendingDTO.getReport();
        ReportEntity reportEntity = ReportMapper.toEntity(reportDTO, userEntity);

        UserSpendingEntity entity = UserSpendingMapper.toEntity(userSpendingDTO, userEntity, reportEntity);
        userSpendingRepository.save(entity);
    }

    @Override
    public void updateUserSpending(UserSpendingDTO userSpendingDTO) {
        UserDTO userDTO = userSpendingDTO.getUser();
        UserEntity userEntity = UserMapper.toEntity(userDTO);

        ReportDTO reportDTO = userSpendingDTO.getReport();
        ReportEntity reportEntity = ReportMapper.toEntity(reportDTO, userEntity);

        UserSpendingEntity entity = UserSpendingMapper.toEntity(userSpendingDTO, userEntity, reportEntity); 
        userSpendingRepository.update(entity);
    }

    @Override
    public void deleteUserSpending(Long spendingId) {
        userSpendingRepository.delete(spendingId);
    }

    @Override
    public UserSpendingDTO findUserSpendingById(Long spendingId) {
        UserSpendingEntity entity = userSpendingRepository.findById(spendingId);
        return UserSpendingMapper.toDTO(entity);
    }

    @Override
    public List<UserSpendingDTO> findAllUserSpendings() {
        return userSpendingRepository.findAll().stream()
                .map(UserSpendingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
