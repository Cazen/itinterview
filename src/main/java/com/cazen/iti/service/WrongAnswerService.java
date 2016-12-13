package com.cazen.iti.service;

import com.cazen.iti.domain.WrongAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WrongAnswer.
 */
public interface WrongAnswerService {

    /**
     * Save a wrongAnswer.
     *
     * @param wrongAnswer the entity to save
     * @return the persisted entity
     */
    WrongAnswer save(WrongAnswer wrongAnswer);

    /**
     *  Get all the wrongAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WrongAnswer> findAll(Pageable pageable);

    /**
     *  Get the "id" wrongAnswer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WrongAnswer findOne(Long id);

    /**
     *  Delete the "id" wrongAnswer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
