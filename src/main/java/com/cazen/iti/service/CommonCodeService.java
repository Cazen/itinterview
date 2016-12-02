package com.cazen.iti.service;

import com.cazen.iti.domain.CommonCode;

import java.util.List;

/**
 * Service Interface for managing CommonCode.
 */
public interface CommonCodeService {

    /**
     * Save a commonCode.
     *
     * @param commonCode the entity to save
     * @return the persisted entity
     */
    CommonCode save(CommonCode commonCode);

    /**
     *  Get all the commonCodes.
     *
     *  @return the list of entities
     */
    List<CommonCode> findAll();

    /**
     *  Get the "id" commonCode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CommonCode findOne(Long id);

    /**
     *  Get the commonCode from unique CD_ID.
     *
     *  @param codeId the id of the entity
     *  @return the entity
     */
    CommonCode findByCd_Id(String codeId);

    /**
     *  Delete the "id" commonCode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
