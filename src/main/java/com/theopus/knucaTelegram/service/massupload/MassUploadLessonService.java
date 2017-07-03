package com.theopus.knucaTelegram.service.massupload;

import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by irina on 03.07.17.
 */

public interface MassUploadLessonService {

    List<Lesson> saveAll(List<Lesson> lessons);
}
