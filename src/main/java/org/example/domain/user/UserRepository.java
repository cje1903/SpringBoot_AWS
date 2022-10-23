package org.example.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);   // email을 통해 이미 존재하는 사용자인지 체크를 위한 메서드
}
