package com.cazen.iti.repository;

import com.cazen.iti.domain.WrongAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WrongAnswer entity.
 */
@SuppressWarnings("unused")
public interface WrongAnswerRepository extends JpaRepository<WrongAnswer,Long> {

}
