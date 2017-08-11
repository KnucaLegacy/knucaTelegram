package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.telegram.UserTelegram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTelegramRepository extends JpaRepository<UserTelegram, Long > {

    UserTelegram getByNickname(String nickname);
}
