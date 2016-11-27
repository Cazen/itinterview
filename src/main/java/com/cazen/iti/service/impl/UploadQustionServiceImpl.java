package com.cazen.iti.service.impl;

import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.repository.UpQuestionMasterRepository;
import com.cazen.iti.service.UploadQustionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Upload Question.
 */
@Service
@Transactional
public class UploadQustionServiceImpl implements UploadQustionService {

    private final Logger log = LoggerFactory.getLogger(UploadQustionServiceImpl.class);

    @Inject
    private UpQuestionMasterRepository upQuestionMasterRepository;

    /**
     * Save a upQuestionMaster.
     *
     * @param upQuestionMaster the entity to save
     * @return the persisted entity
     */
    public UpQuestionMaster save(UpQuestionMaster upQuestionMaster) {
        log.debug("Request to save UpQuestionMaster : {}", upQuestionMaster);
        UpQuestionMaster result = upQuestionMasterRepository.save(upQuestionMaster);
        return result;
    }

    /**
     *  Get all the upQuestionMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UpQuestionMaster> findAll(Pageable pageable) {
        log.debug("Request to get all UpQuestionMasters");
        Page<UpQuestionMaster> result = upQuestionMasterRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one upQuestionMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UpQuestionMaster findOne(Long id) {
        log.debug("Request to get UpQuestionMaster : {}", id);
        UpQuestionMaster upQuestionMaster = upQuestionMasterRepository.findOne(id);
        return upQuestionMaster;
    }

    /**
     *  Delete the  upQuestionMaster by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UpQuestionMaster : {}", id);
        upQuestionMasterRepository.delete(id);
    }
}
