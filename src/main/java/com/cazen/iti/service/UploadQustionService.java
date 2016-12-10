package com.cazen.iti.service;

import com.cazen.iti.domain.UpQuestionMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UpQuestionMaster.
 */
public interface UploadQustionService {
    /**
     * Save a upQuestionMaster.
     *
     * @param upQuestionMaster the entity to save
     * @return the persisted entity
     */
    UpQuestionMaster save(UpQuestionMaster upQuestionMaster);

    /**
     *  Get all the upQuestionMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UpQuestionMaster> findAll(Pageable pageable);

    /**
     *  Get the "id" upQuestionMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UpQuestionMaster findOne(Long id);

    /**
     *  Delete the "id" upQuestionMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
