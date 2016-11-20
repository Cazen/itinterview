package com.cazen.iti.web.rest;

import com.cazen.iti.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.QuestionVote;
import com.cazen.iti.service.QuestionVoteService;
import com.cazen.iti.web.rest.util.HeaderUtil;
import com.cazen.iti.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing QuestionVote.
 */
@RestController
@RequestMapping("/api")
public class QuestionVoteResource {

    private final Logger log = LoggerFactory.getLogger(QuestionVoteResource.class);

    @Inject
    private QuestionVoteService questionVoteService;

    /**
     * POST  /question-votes : Create a new questionVote.
     *
     * @param questionVote the questionVote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionVote, or with status 400 (Bad Request) if the questionVote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-votes")
    @Timed
    public ResponseEntity<QuestionVote> createQuestionVote(@RequestBody QuestionVote questionVote) throws URISyntaxException {
        log.debug("REST request to save QuestionVote : {}", questionVote);
        if (questionVote.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionVote", "idexists", "A new questionVote cannot already have an ID")).body(null);
        }
        QuestionVote result = questionVoteService.save(questionVote);
        return ResponseEntity.created(new URI("/api/question-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionVote", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-votes : Updates an existing questionVote.
     *
     * @param questionVote the questionVote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionVote,
     * or with status 400 (Bad Request) if the questionVote is not valid,
     * or with status 500 (Internal Server Error) if the questionVote couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-votes")
    @Timed
    public ResponseEntity<QuestionVote> updateQuestionVote(@RequestBody QuestionVote questionVote) throws URISyntaxException {
        log.debug("REST request to update QuestionVote : {}", questionVote);
        if (questionVote.getId() == null) {
            return createQuestionVote(questionVote);
        }
        QuestionVote result = questionVoteService.save(questionVote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionVote", questionVote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-votes : get all the questionVotes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questionVotes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/question-votes")
    @Timed
    public ResponseEntity<List<QuestionVote>> getAllQuestionVotes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of QuestionVotes");
        Page<QuestionVote> page = questionVoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-votes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /question-votes/:id : get the "id" questionVote.
     *
     * @param id the id of the questionVote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionVote, or with status 404 (Not Found)
     */
    @GetMapping("/question-votes/{id}")
    @Timed
    public ResponseEntity<QuestionVote> getQuestionVote(@PathVariable Long id) {
        log.debug("REST request to get QuestionVote : {}", id);
        QuestionVote questionVote = questionVoteService.findOne(id);
        return Optional.ofNullable(questionVote)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-votes/:id : delete the "id" questionVote.
     *
     * @param id the id of the questionVote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-votes/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteQuestionVote(@PathVariable Long id) {
        log.debug("REST request to delete QuestionVote : {}", id);
        questionVoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionVote", id.toString())).build();
    }

}
