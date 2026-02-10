package com.example.schedulerdevelop.scheduler.user.service;
import com.example.schedulerdevelop.global.exception.NotEqualsPasswordException;
import com.example.schedulerdevelop.global.exception.NotFoundException;
import com.example.schedulerdevelop.scheduler.user.dto.LoginRequest;
import com.example.schedulerdevelop.scheduler.user.dto.SignUpRequest;
import com.example.schedulerdevelop.scheduler.user.dto.UserResponse;
import com.example.schedulerdevelop.scheduler.user.entity.User;
import com.example.schedulerdevelop.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse signup(SignUpRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
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

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new NotFoundException("유저를 찾을 수 없습니다.")
        );

        if(!request.getPassword().equals(user.getPassword())) {
            throw new NotEqualsPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
