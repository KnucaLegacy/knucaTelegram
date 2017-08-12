package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.telegram.UserTelegram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserTelegramRepository extends PagingAndSortingRepository<UserTelegram, Long > {

    UserTelegram getByNickname(String nickname);
}
