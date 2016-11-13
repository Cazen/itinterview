package com.cazen.iti.service;

import com.cazen.iti.domain.UpWrongAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing UpWrongAnswer.
 */
public interface UpWrongAnswerService {

    /**
     * Save a upWrongAnswer.
     *
     * @param upWrongAnswer the entity to save
     * @return the persisted entity
     */
    UpWrongAnswer save(UpWrongAnswer upWrongAnswer);

    /**
     *  Get all the upWrongAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UpWrongAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" upWrongAnswer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UpWrongAnswer findOne(Long id);

    /**
     *  Delete the "id" upWrongAnswer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
