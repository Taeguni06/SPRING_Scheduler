package com.example.schedulerdevelop.scheduler.user.service;
import com.example.schedulerdevelop.global.exception.NotEqualsException;
import com.example.schedulerdevelop.global.exception.NotFoundException;
import com.example.schedulerdevelop.global.security.PasswordEncoder;
import com.example.schedulerdevelop.scheduler.user.dto.*;
import com.example.schedulerdevelop.scheduler.user.entity.User;
import com.example.schedulerdevelop.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signup(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword
        );
        User save = userRepository.save(user);
        return new UserResponse(
                save.getId(),
                save.getName(),
                save.getEmail(),
                save.getCreatedAt(),
                save.getModifiedAt()
        );
    }

    @Transactional
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotEqualsException("비밀번호가 일치하지 않습니다.");
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new NotFoundException("유저를 찾을 수 없습니다.");
        }

        List<UserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            UserResponse dto = new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );

            dtos.add(dto);
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotEqualsException("비밀번호가 일치하지 않습니다.");
        }

        user.update(
                request.getName(),
                request.getEmail()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotEqualsException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(userId);
    }
}
