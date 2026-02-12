package com.example.schedulerdevelop.scheduler.comment.service;

import com.example.schedulerdevelop.global.exception.NotEqualsException;
import com.example.schedulerdevelop.global.exception.NotFoundException;
import com.example.schedulerdevelop.global.exception.OverCommentException;
import com.example.schedulerdevelop.scheduler.comment.dto.CommentResponse;
import com.example.schedulerdevelop.scheduler.comment.dto.CreateCommentRequest;
import com.example.schedulerdevelop.scheduler.comment.dto.UpdateCommentRequest;
import com.example.schedulerdevelop.scheduler.comment.entity.Comment;
import com.example.schedulerdevelop.scheduler.comment.repository.CommentRepository;
import com.example.schedulerdevelop.scheduler.schedule.entity.Schedule;
import com.example.schedulerdevelop.scheduler.schedule.repository.ScheduleRepository;
import com.example.schedulerdevelop.scheduler.user.entity.User;
import com.example.schedulerdevelop.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse save(Long scheduleId, CreateCommentRequest request, Long userId) {
        if (commentRepository.findByScheduleId(scheduleId).size() >= 10) {
            throw new OverCommentException("댓글 개수 초과 (10개 미만)");
        }

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

    @Transactional(readOnly = true)
    public List<CommentResponse> findByScheduleId(Long scheduleId) {
        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);

        List<CommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse dto = new CommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );

            dtos.add(dto);
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public CommentResponse findOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글을 찾을 수 없습니다.")
        );

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional
    public CommentResponse update(Long commentId, UpdateCommentRequest request, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글을 찾을 수 없습니다.")
        );

        if (!comment.getUser().getId().equals(userId)) {
            throw new NotEqualsException("유저 ID가 일치하지 않습니다.");
        }

        comment.update(request.getTitle());

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글을 찾을 수 없습니다.")
        );

        if (!comment.getUser().getId().equals(userId)) {
            throw new NotEqualsException("유저 ID가 일치하지 않습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}
