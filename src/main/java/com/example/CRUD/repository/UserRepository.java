package com.example.CRUD.repository;

import com.example.CRUD.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.phoneNumber = :phoneNumber")
    Optional<UserEntity> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
