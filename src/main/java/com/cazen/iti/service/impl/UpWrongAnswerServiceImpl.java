package com.cazen.iti.service.impl;

import com.cazen.iti.service.UpWrongAnswerService;
import com.cazen.iti.domain.UpWrongAnswer;
import com.cazen.iti.repository.UpWrongAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing UpWrongAnswer.
 */
@Service
@Transactional
public class UpWrongAnswerServiceImpl implements UpWrongAnswerService{

    private final Logger log = LoggerFactory.getLogger(UpWrongAnswerServiceImpl.class);
    
    @Inject
    private UpWrongAnswerRepository upWrongAnswerRepository;

    /**
     * Save a upWrongAnswer.
     *
     * @param upWrongAnswer the entity to save
     * @return the persisted entity
     */
    public UpWrongAnswer save(UpWrongAnswer upWrongAnswer) {
        log.debug("Request to save UpWrongAnswer : {}", upWrongAnswer);
        UpWrongAnswer result = upWrongAnswerRepository.save(upWrongAnswer);
        return result;
    }

    /**
     *  Get all the upWrongAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UpWrongAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all UpWrongAnswers");
        Page<UpWrongAnswer> result = upWrongAnswerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one upWrongAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UpWrongAnswer findOne(Long id) {
        log.debug("Request to get UpWrongAnswer : {}", id);
        UpWrongAnswer upWrongAnswer = upWrongAnswerRepository.findOne(id);
        return upWrongAnswer;
    }

    /**
     *  Delete the  upWrongAnswer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UpWrongAnswer : {}", id);
        upWrongAnswerRepository.delete(id);
    }
}
