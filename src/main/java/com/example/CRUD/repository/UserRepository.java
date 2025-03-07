package com.example.CRUD.repository;

import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.enumeration.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity>{

    List<UserEntity> findByStatus(UserStatus status);
    // Search by name, email, phone, or unique code (for BOTH case)
   /* Page<UserEntity> findByNameContainingIgnoreCaseOrEmailIdContainingIgnoreCaseOrPhoneNumberStartingWithOrUniqueCodeStartingWithIgnoreCase(
            String name, String emailId, String phoneNumber, String uniqueCode, Pageable pageable);

    Page<UserEntity> findByStatusAndNameContainingIgnoreCaseOrStatusAndEmailIdContainingIgnoreCaseOrStatusAndPhoneNumberStartingWithOrStatusAndUniqueCodeStartingWithIgnoreCase(
            UserStatus status1, String name,
            UserStatus status2, String email,
            UserStatus status3, String phone,
            UserStatus status4, String uniqueCode,
            Pageable pageable);*/

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmailId(String emailId);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    boolean existsByEmailIdAndIdNot(String emailId, Long id);




}
