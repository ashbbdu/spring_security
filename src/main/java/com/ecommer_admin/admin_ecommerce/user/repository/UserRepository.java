package com.ecommer_admin.admin_ecommerce.user.repository;

import com.ecommer_admin.admin_ecommerce.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity , Long> {
    Optional<UserEntity> findByEmail(String email);
}
