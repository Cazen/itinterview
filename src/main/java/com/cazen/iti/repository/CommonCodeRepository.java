package com.cazen.iti.repository;

import com.cazen.iti.domain.CommonCode;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CommonCode entity.
 */
@SuppressWarnings("unused")
public interface CommonCodeRepository extends JpaRepository<CommonCode,Long> {

    @Query(value = "SELECT * FROM COMMON_CODE WHERE CD_ID = ?1", nativeQuery = true)
    CommonCode findByCd_Id(String codeId);
}
