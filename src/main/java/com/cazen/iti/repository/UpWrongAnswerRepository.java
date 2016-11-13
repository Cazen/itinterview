package com.cazen.iti.repository;

import com.cazen.iti.domain.UpWrongAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UpWrongAnswer entity.
 */
@SuppressWarnings("unused")
public interface UpWrongAnswerRepository extends JpaRepository<UpWrongAnswer,Long> {

}
