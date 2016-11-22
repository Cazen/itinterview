package com.cazen.iti.service;

import com.cazen.iti.domain.UpRightAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing UpRightAnswer.
 */
public interface UpRightAnswerService {

    /**
     * Save a upRightAnswer.
     *
     * @param upRightAnswer the entity to save
     * @return the persisted entity
     */
    UpRightAnswer save(UpRightAnswer upRightAnswer);

    /**
     *  Get all the upRightAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UpRightAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" upRightAnswer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UpRightAnswer findOne(Long id);

    /**
     *  Delete the "id" upRightAnswer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
