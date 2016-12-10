package com.cazen.iti.repository;

import com.cazen.iti.domain.UpRightAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UpRightAnswer entity.
 */
@SuppressWarnings("unused")
public interface UpRightAnswerRepository extends JpaRepository<UpRightAnswer,Long> {

}
