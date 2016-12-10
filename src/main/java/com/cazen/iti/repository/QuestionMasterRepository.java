package com.cazen.iti.repository;

import com.cazen.iti.domain.QuestionMaster;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionMaster entity.
 */
@SuppressWarnings("unused")
public interface QuestionMasterRepository extends JpaRepository<QuestionMaster,Long> {

}
