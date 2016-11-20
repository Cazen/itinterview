package com.cazen.iti.web.rest;

import com.cazen.iti.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.UpQuestionVote;
import com.cazen.iti.service.UpQuestionVoteService;
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
 * REST controller for managing UpQuestionVote.
 */
@RestController
@RequestMapping("/api")
public class UpQuestionVoteResource {

    private final Logger log = LoggerFactory.getLogger(UpQuestionVoteResource.class);

    @Inject
    private UpQuestionVoteService upQuestionVoteService;

    /**
     * POST  /up-question-votes : Create a new upQuestionVote.
     *
     * @param upQuestionVote the upQuestionVote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new upQuestionVote, or with status 400 (Bad Request) if the upQuestionVote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/up-question-votes")
    @Timed
    public ResponseEntity<UpQuestionVote> createUpQuestionVote(@RequestBody UpQuestionVote upQuestionVote) throws URISyntaxException {
        log.debug("REST request to save UpQuestionVote : {}", upQuestionVote);
        if (upQuestionVote.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("upQuestionVote", "idexists", "A new upQuestionVote cannot already have an ID")).body(null);
        }
        UpQuestionVote result = upQuestionVoteService.save(upQuestionVote);
        return ResponseEntity.created(new URI("/api/up-question-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upQuestionVote", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /up-question-votes : Updates an existing upQuestionVote.
     *
     * @param upQuestionVote the upQuestionVote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated upQuestionVote,
     * or with status 400 (Bad Request) if the upQuestionVote is not valid,
     * or with status 500 (Internal Server Error) if the upQuestionVote couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/up-question-votes")
    @Timed
    public ResponseEntity<UpQuestionVote> updateUpQuestionVote(@RequestBody UpQuestionVote upQuestionVote) throws URISyntaxException {
        log.debug("REST request to update UpQuestionVote : {}", upQuestionVote);
        if (upQuestionVote.getId() == null) {
            return createUpQuestionVote(upQuestionVote);
        }
        UpQuestionVote result = upQuestionVoteService.save(upQuestionVote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("upQuestionVote", upQuestionVote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /up-question-votes : get all the upQuestionVotes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of upQuestionVotes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/up-question-votes")
    @Timed
    public ResponseEntity<List<UpQuestionVote>> getAllUpQuestionVotes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UpQuestionVotes");
        Page<UpQuestionVote> page = upQuestionVoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/up-question-votes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /up-question-votes/:id : get the "id" upQuestionVote.
     *
     * @param id the id of the upQuestionVote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the upQuestionVote, or with status 404 (Not Found)
     */
    @GetMapping("/up-question-votes/{id}")
    @Timed
    public ResponseEntity<UpQuestionVote> getUpQuestionVote(@PathVariable Long id) {
        log.debug("REST request to get UpQuestionVote : {}", id);
        UpQuestionVote upQuestionVote = upQuestionVoteService.findOne(id);
        return Optional.ofNullable(upQuestionVote)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /up-question-votes/:id : delete the "id" upQuestionVote.
     *
     * @param id the id of the upQuestionVote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/up-question-votes/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUpQuestionVote(@PathVariable Long id) {
        log.debug("REST request to delete UpQuestionVote : {}", id);
        upQuestionVoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upQuestionVote", id.toString())).build();
    }

}
