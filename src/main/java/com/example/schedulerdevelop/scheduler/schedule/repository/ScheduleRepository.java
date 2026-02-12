package com.example.schedulerdevelop.scheduler.schedule.repository;

import com.example.schedulerdevelop.scheduler.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserName(String userName);
    Page<Schedule> findAll(Pageable pageable);
    Page<Schedule> findByUserName(Pageable pageable, String userName);
}
