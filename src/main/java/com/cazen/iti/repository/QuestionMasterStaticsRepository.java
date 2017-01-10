package com.cazen.iti.repository;

import com.cazen.iti.domain.QuestionMasterStatics;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionMasterStatics entity.
 */
@SuppressWarnings("unused")
public interface QuestionMasterStaticsRepository extends JpaRepository<QuestionMasterStatics,Long> {

}
