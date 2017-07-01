package com.theopus.knucaTelegram.repository;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
