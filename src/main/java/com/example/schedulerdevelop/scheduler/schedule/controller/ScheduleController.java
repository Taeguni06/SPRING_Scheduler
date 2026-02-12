package com.example.schedulerdevelop.scheduler.schedule.controller;

import com.example.schedulerdevelop.global.dto.PageResponse;
import com.example.schedulerdevelop.scheduler.schedule.dto.*;
import com.example.schedulerdevelop.scheduler.schedule.service.ScheduleService;
import com.example.schedulerdevelop.scheduler.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request, loginUser.getId()));
    }

    @GetMapping
    public ResponseEntity<PageResponse<GetScheduleResponse>> findAll(
            @RequestParam(required = false) String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<GetScheduleResponse> responsePage = scheduleService.findAll(userName, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new PageResponse<>(responsePage));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> findOne (@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            HttpServletRequest httpRequest,
            @RequestBody UpdateScheduleRequest request
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request, loginUser.getId()));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            HttpServletRequest httpRequest
    ) {

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse loginUser = (UserResponse) session.getAttribute("LOGIN_USER");

        scheduleService.delete(scheduleId, loginUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
