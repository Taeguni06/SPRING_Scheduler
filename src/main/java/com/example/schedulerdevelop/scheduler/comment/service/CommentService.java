package com.example.schedulerdevelop.scheduler.comment.service;

import com.example.schedulerdevelop.global.exception.NotFoundException;
import com.example.schedulerdevelop.scheduler.comment.dto.CommentResponse;
import com.example.schedulerdevelop.scheduler.comment.dto.CreateCommentRequest;
import com.example.schedulerdevelop.scheduler.comment.entity.Comment;
import com.example.schedulerdevelop.scheduler.comment.repository.CommentRepository;
import com.example.schedulerdevelop.scheduler.schedule.entity.Schedule;
import com.example.schedulerdevelop.scheduler.schedule.repository.ScheduleRepository;
import com.example.schedulerdevelop.scheduler.user.entity.User;
import com.example.schedulerdevelop.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse save(Long scheduleId, CreateCommentRequest request, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정을 찾을 수 없습니다.")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        Comment comment = new Comment(
                schedule,
                user,
                request.getContent()
        );

        Comment save = commentRepository.save(comment);
        return new CommentResponse(
                save.getId(),
                save.getContent(),
                save.getCreatedAt(),
                save.getModifiedAt()
        );
    }
}
