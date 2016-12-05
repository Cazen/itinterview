package com.cazen.iti.service.impl;

import com.cazen.iti.service.QuestionVoteService;
import com.cazen.iti.domain.QuestionVote;
import com.cazen.iti.repository.QuestionVoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing QuestionVote.
 */
@Service
@Transactional
public class QuestionVoteServiceImpl implements QuestionVoteService{

    private final Logger log = LoggerFactory.getLogger(QuestionVoteServiceImpl.class);
    
    @Inject
    private QuestionVoteRepository questionVoteRepository;

    /**
     * Save a questionVote.
     *
     * @param questionVote the entity to save
     * @return the persisted entity
     */
    public QuestionVote save(QuestionVote questionVote) {
        log.debug("Request to save QuestionVote : {}", questionVote);
        QuestionVote result = questionVoteRepository.save(questionVote);
        return result;
    }

    /**
     *  Get all the questionVotes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<QuestionVote> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionVotes");
        Page<QuestionVote> result = questionVoteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one questionVote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public QuestionVote findOne(Long id) {
        log.debug("Request to get QuestionVote : {}", id);
        QuestionVote questionVote = questionVoteRepository.findOne(id);
        return questionVote;
    }

    /**
     *  Delete the  questionVote by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionVote : {}", id);
        questionVoteRepository.delete(id);
    }
}
