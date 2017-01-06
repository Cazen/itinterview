package com.cazen.iti.repository;

import com.cazen.iti.domain.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the CommonCode entity.
 */
@SuppressWarnings("unused")
public interface TryQuestionRepository extends JpaRepository<CommonCode,Long> {
    @Query(value = "SELECT * FROM COMMON_CODE WHERE CD_ID LIKE 'QSTN_SEC%'", nativeQuery = true)
    List<CommonCode> getCategory123CommonCodeList();
}
