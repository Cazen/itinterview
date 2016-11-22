package com.cazen.iti.repository;

import com.cazen.iti.domain.CommonCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommonCode entity.
 */
@SuppressWarnings("unused")
public interface CommonCodeRepository extends JpaRepository<CommonCode,Long> {

}
