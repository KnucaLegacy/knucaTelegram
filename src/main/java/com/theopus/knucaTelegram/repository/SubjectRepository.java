package com.theopus.knucaTelegram.repository;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Room;
import com.theopus.knucaTelegram.entity.Subject;
import org.apache.pdfbox.pdmodel.graphics.predictor.Sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("select s from Subject s where s.name = :name")
    Subject findByName(@Param("name") String name);

}
