package com.cazen.iti.service;

import com.cazen.iti.domain.QuestionVote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing QuestionVote.
 */
public interface QuestionVoteService {

    /**
     * Save a questionVote.
     *
     * @param questionVote the entity to save
     * @return the persisted entity
     */
    QuestionVote save(QuestionVote questionVote);

    /**
     *  Get all the questionVotes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuestionVote> findAll(Pageable pageable);

    /**
     *  Get the "id" questionVote.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuestionVote findOne(Long id);

    /**
     *  Delete the "id" questionVote.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
