package com.cazen.iti.repository;

import com.cazen.iti.domain.RightAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RightAnswer entity.
 */
@SuppressWarnings("unused")
public interface RightAnswerRepository extends JpaRepository<RightAnswer,Long> {

}
