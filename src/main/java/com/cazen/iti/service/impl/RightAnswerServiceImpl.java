package com.cazen.iti.service.impl;

import com.cazen.iti.service.RightAnswerService;
import com.cazen.iti.domain.RightAnswer;
import com.cazen.iti.repository.RightAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing RightAnswer.
 */
@Service
@Transactional
public class RightAnswerServiceImpl implements RightAnswerService{

    private final Logger log = LoggerFactory.getLogger(RightAnswerServiceImpl.class);
    
    @Inject
    private RightAnswerRepository rightAnswerRepository;

    /**
     * Save a rightAnswer.
     *
     * @param rightAnswer the entity to save
     * @return the persisted entity
     */
    public RightAnswer save(RightAnswer rightAnswer) {
        log.debug("Request to save RightAnswer : {}", rightAnswer);
        RightAnswer result = rightAnswerRepository.save(rightAnswer);
        return result;
    }

    /**
     *  Get all the rightAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<RightAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all RightAnswers");
        Page<RightAnswer> result = rightAnswerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one rightAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RightAnswer findOne(Long id) {
        log.debug("Request to get RightAnswer : {}", id);
        RightAnswer rightAnswer = rightAnswerRepository.findOne(id);
        return rightAnswer;
    }

    /**
     *  Delete the  rightAnswer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RightAnswer : {}", id);
        rightAnswerRepository.delete(id);
    }
}
