package com.cazen.iti.service.impl;

import com.cazen.iti.service.QuestionMasterService;
import com.cazen.iti.domain.QuestionMaster;
import com.cazen.iti.repository.QuestionMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing QuestionMaster.
 */
@Service
@Transactional
public class QuestionMasterServiceImpl implements QuestionMasterService{

    private final Logger log = LoggerFactory.getLogger(QuestionMasterServiceImpl.class);
    
    @Inject
    private QuestionMasterRepository questionMasterRepository;

    /**
     * Save a questionMaster.
     *
     * @param questionMaster the entity to save
     * @return the persisted entity
     */
    public QuestionMaster save(QuestionMaster questionMaster) {
        log.debug("Request to save QuestionMaster : {}", questionMaster);
        QuestionMaster result = questionMasterRepository.save(questionMaster);
        return result;
    }

    /**
     *  Get all the questionMasters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<QuestionMaster> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionMasters");
        Page<QuestionMaster> result = questionMasterRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one questionMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public QuestionMaster findOne(Long id) {
        log.debug("Request to get QuestionMaster : {}", id);
        QuestionMaster questionMaster = questionMasterRepository.findOne(id);
        return questionMaster;
    }

    /**
     *  Delete the  questionMaster by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionMaster : {}", id);
        questionMasterRepository.delete(id);
    }
}
