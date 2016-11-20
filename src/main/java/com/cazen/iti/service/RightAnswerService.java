package com.cazen.iti.service;

import com.cazen.iti.domain.RightAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing RightAnswer.
 */
public interface RightAnswerService {

    /**
     * Save a rightAnswer.
     *
     * @param rightAnswer the entity to save
     * @return the persisted entity
     */
    RightAnswer save(RightAnswer rightAnswer);

    /**
     *  Get all the rightAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RightAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" rightAnswer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RightAnswer findOne(Long id);

    /**
     *  Delete the "id" rightAnswer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
