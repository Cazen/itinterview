package com.cazen.iti.service.impl;

import com.cazen.iti.service.UpQuestionVoteService;
import com.cazen.iti.domain.UpQuestionVote;
import com.cazen.iti.repository.UpQuestionVoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing UpQuestionVote.
 */
@Service
@Transactional
public class UpQuestionVoteServiceImpl implements UpQuestionVoteService{

    private final Logger log = LoggerFactory.getLogger(UpQuestionVoteServiceImpl.class);
    
    @Inject
    private UpQuestionVoteRepository upQuestionVoteRepository;

    /**
     * Save a upQuestionVote.
     *
     * @param upQuestionVote the entity to save
     * @return the persisted entity
     */
    public UpQuestionVote save(UpQuestionVote upQuestionVote) {
        log.debug("Request to save UpQuestionVote : {}", upQuestionVote);
        UpQuestionVote result = upQuestionVoteRepository.save(upQuestionVote);
        return result;
    }

    /**
     *  Get all the upQuestionVotes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UpQuestionVote> findAll(Pageable pageable) {
        log.debug("Request to get all UpQuestionVotes");
        Page<UpQuestionVote> result = upQuestionVoteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one upQuestionVote by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UpQuestionVote findOne(Long id) {
        log.debug("Request to get UpQuestionVote : {}", id);
        UpQuestionVote upQuestionVote = upQuestionVoteRepository.findOne(id);
        return upQuestionVote;
    }

    /**
     *  Delete the  upQuestionVote by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UpQuestionVote : {}", id);
        upQuestionVoteRepository.delete(id);
    }
}
