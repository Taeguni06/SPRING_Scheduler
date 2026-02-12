package com.example.schedulerdevelop.scheduler.schedule.service;
import com.example.schedulerdevelop.global.exception.NotEqualsUserIdException;
import com.example.schedulerdevelop.global.exception.NotFoundException;
import com.example.schedulerdevelop.scheduler.comment.service.CommentService;
import com.example.schedulerdevelop.scheduler.schedule.dto.*;
import com.example.schedulerdevelop.scheduler.schedule.entity.Schedule;
import com.example.schedulerdevelop.scheduler.schedule.repository.ScheduleRepository;
import com.example.schedulerdevelop.scheduler.user.entity.User;
import com.example.schedulerdevelop.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        Schedule schedule = new Schedule(
                user,
                request.getTitle(),
                request.getContent());
        Schedule save = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                save.getId(),
                save.getTitle(),
                save.getContent(),
                user.getName(),
                save.getCreatedAt(),
                save.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<GetScheduleResponse> findAll(String userName, Pageable pageable) {
        Page<Schedule> schedules;

        if (userName != null && !userName.isBlank()) {
            schedules = scheduleRepository.findByUserName(pageable, userName);
        } else {
            schedules = scheduleRepository.findAll(pageable);
        }

        if (schedules.isEmpty()) {
            throw new NotFoundException("일정을 찾을 수 없습니다.");
        }

        //        List<GetScheduleResponse> dtos = new ArrayList<>();
//        for (Schedule schedule : schedules) {
//            GetScheduleResponse dto = new GetScheduleResponse(
//                    schedule.getTitle(),
//                    schedule.getContent(),
//                    (long) commentService.findByScheduleId(schedule.getId()).size(),
//                    schedule.getCreatedAt(),
//                    schedule.getModifiedAt(),
//                    schedule.getUser().getName()
//            );
//
//            dtos.add(dto);
//        }
        return schedules.map(schedule -> new GetScheduleResponse(
                schedule.getTitle(),
                schedule.getContent(),
                (long) commentService.findByScheduleId(schedule.getId()).size(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getUser().getName()
        ));
    }


    @Transactional(readOnly = true)
    public GetOneScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정을 찾을 수 없습니다.")
        );
        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                commentService.findByScheduleId(scheduleId)
        );
    }


    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정을 찾을 수 없습니다.")
        );

        if(!schedule.getUser().getId().equals(userId)) {
            throw new NotEqualsUserIdException("유저 ID가 일치하지 않습니다.");
        }
        schedule.update(request.getTitle());

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정을 찾을 수 없습니다.")
        );

        if(!schedule.getUser().getId().equals(userId)) {
            throw new NotEqualsUserIdException("유저 ID가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
