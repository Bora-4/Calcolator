package com.calorator.service;

import com.calorator.dto.UserSpendingDTO;
import java.util.List;

public interface UserSpendingService {

    void saveUserSpending(UserSpendingDTO userSpendingDTO);

    void updateUserSpending(UserSpendingDTO userSpendingDTO);

    void deleteUserSpending(Long spendingId);

    UserSpendingDTO findUserSpendingById(Long spendingId);

    List<UserSpendingDTO> findAllUserSpendings();
}
