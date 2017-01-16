package com.cazen.iti.repository;

import com.cazen.iti.domain.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 *  * Spring Data JPA repository for the CommonCode entity.
 *   */
@SuppressWarnings("unused")
public interface TryQuestionRepository extends JpaRepository<CommonCode,Long> {
    @Query(value = "SELECT * FROM COMMON_CODE WHERE CD_TP LIKE 'QSTN_SEC%'", nativeQuery = true)
    List<CommonCode> getCategory123CommonCodeList();

    @Query(value = "SELECT id FROM QUESTION_MASTER WHERE CATEGORY3_ID = ?1 ORDER BY RAND() LIMIT 7", nativeQuery = true)
    List<BigInteger> getQuestionMasterIdList7Randomly(long id);
}

