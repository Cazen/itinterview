package com.cazen.iti.service.impl;

import com.cazen.iti.service.UpRightAnswerService;
import com.cazen.iti.domain.UpRightAnswer;
import com.cazen.iti.repository.UpRightAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing UpRightAnswer.
 */
@Service
@Transactional
public class UpRightAnswerServiceImpl implements UpRightAnswerService{

    private final Logger log = LoggerFactory.getLogger(UpRightAnswerServiceImpl.class);
    
    @Inject
    private UpRightAnswerRepository upRightAnswerRepository;

    /**
     * Save a upRightAnswer.
     *
     * @param upRightAnswer the entity to save
     * @return the persisted entity
     */
    public UpRightAnswer save(UpRightAnswer upRightAnswer) {
        log.debug("Request to save UpRightAnswer : {}", upRightAnswer);
        UpRightAnswer result = upRightAnswerRepository.save(upRightAnswer);
        return result;
    }

    /**
     *  Get all the upRightAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UpRightAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all UpRightAnswers");
        Page<UpRightAnswer> result = upRightAnswerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one upRightAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UpRightAnswer findOne(Long id) {
        log.debug("Request to get UpRightAnswer : {}", id);
        UpRightAnswer upRightAnswer = upRightAnswerRepository.findOne(id);
        return upRightAnswer;
    }

    /**
     *  Delete the  upRightAnswer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UpRightAnswer : {}", id);
        upRightAnswerRepository.delete(id);
    }
}
