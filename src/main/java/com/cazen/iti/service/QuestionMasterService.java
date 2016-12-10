package com.cazen.iti.service;

import com.cazen.iti.domain.QuestionMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing QuestionMaster.
 */
public interface QuestionMasterService {

    /**
     * Save a questionMaster.
     *
     * @param questionMaster the entity to save
     * @return the persisted entity
     */
    QuestionMaster save(QuestionMaster questionMaster);

    /**
     *  Get all the questionMasters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuestionMaster> findAll(Pageable pageable);

    /**
     *  Get the "id" questionMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuestionMaster findOne(Long id);

    /**
     *  Delete the "id" questionMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
