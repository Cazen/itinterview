package com.cazen.iti.service;

import com.cazen.iti.domain.UpQuestionVote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing UpQuestionVote.
 */
public interface UpQuestionVoteService {

    /**
     * Save a upQuestionVote.
     *
     * @param upQuestionVote the entity to save
     * @return the persisted entity
     */
    UpQuestionVote save(UpQuestionVote upQuestionVote);

    /**
     *  Get all the upQuestionVotes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UpQuestionVote> findAll(Pageable pageable);

    /**
     *  Get the "id" upQuestionVote.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UpQuestionVote findOne(Long id);

    /**
     *  Delete the "id" upQuestionVote.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
