package com.cazen.iti.service.impl;

import com.cazen.iti.service.WrongAnswerService;
import com.cazen.iti.domain.WrongAnswer;
import com.cazen.iti.repository.WrongAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing WrongAnswer.
 */
@Service
@Transactional
public class WrongAnswerServiceImpl implements WrongAnswerService{

    private final Logger log = LoggerFactory.getLogger(WrongAnswerServiceImpl.class);
    
    @Inject
    private WrongAnswerRepository wrongAnswerRepository;

    /**
     * Save a wrongAnswer.
     *
     * @param wrongAnswer the entity to save
     * @return the persisted entity
     */
    public WrongAnswer save(WrongAnswer wrongAnswer) {
        log.debug("Request to save WrongAnswer : {}", wrongAnswer);
        WrongAnswer result = wrongAnswerRepository.save(wrongAnswer);
        return result;
    }

    /**
     *  Get all the wrongAnswers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<WrongAnswer> findAll(Pageable pageable) {
        log.debug("Request to get all WrongAnswers");
        Page<WrongAnswer> result = wrongAnswerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one wrongAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public WrongAnswer findOne(Long id) {
        log.debug("Request to get WrongAnswer : {}", id);
        WrongAnswer wrongAnswer = wrongAnswerRepository.findOne(id);
        return wrongAnswer;
    }

    /**
     *  Delete the  wrongAnswer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WrongAnswer : {}", id);
        wrongAnswerRepository.delete(id);
    }
}
