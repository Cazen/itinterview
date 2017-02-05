package com.cazen.iti.repository;

import com.cazen.iti.domain.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the CommonCode entity.
 */
@SuppressWarnings("unused")
public interface UploadQuestionRepository extends JpaRepository<CommonCode,Long> {
    @Query(value = "SELECT * FROM COMMON_CODE WHERE CD_ID = ?1", nativeQuery = true)
    CommonCode findByCd_Id(String codeId);
}
