package com.theopus.knucaTelegram.service.data.repository;

import com.theopus.knucaTelegram.entity.schedule.Circumstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by irina on 02.08.17.
 */
public interface CircumstanceRepository extends PagingAndSortingRepository<Circumstance, Long> {


}
