package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.QuestionMaster;
import com.cazen.iti.service.QuestionMasterService;
import com.cazen.iti.web.rest.util.HeaderUtil;
import com.cazen.iti.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing QuestionMaster.
 */
@RestController
@RequestMapping("/api")
public class QuestionMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionMasterResource.class);
        
    @Inject
    private QuestionMasterService questionMasterService;

    /**
     * POST  /question-masters : Create a new questionMaster.
     *
     * @param questionMaster the questionMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionMaster, or with status 400 (Bad Request) if the questionMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-masters")
    @Timed
    public ResponseEntity<QuestionMaster> createQuestionMaster(@RequestBody QuestionMaster questionMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionMaster : {}", questionMaster);
        if (questionMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionMaster", "idexists", "A new questionMaster cannot already have an ID")).body(null);
        }
        QuestionMaster result = questionMasterService.save(questionMaster);
        return ResponseEntity.created(new URI("/api/question-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionMaster", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-masters : Updates an existing questionMaster.
     *
     * @param questionMaster the questionMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionMaster,
     * or with status 400 (Bad Request) if the questionMaster is not valid,
     * or with status 500 (Internal Server Error) if the questionMaster couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-masters")
    @Timed
    public ResponseEntity<QuestionMaster> updateQuestionMaster(@RequestBody QuestionMaster questionMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionMaster : {}", questionMaster);
        if (questionMaster.getId() == null) {
            return createQuestionMaster(questionMaster);
        }
        QuestionMaster result = questionMasterService.save(questionMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionMaster", questionMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-masters : get all the questionMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questionMasters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/question-masters")
    @Timed
    public ResponseEntity<List<QuestionMaster>> getAllQuestionMasters(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of QuestionMasters");
        Page<QuestionMaster> page = questionMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /question-masters/:id : get the "id" questionMaster.
     *
     * @param id the id of the questionMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionMaster, or with status 404 (Not Found)
     */
    @GetMapping("/question-masters/{id}")
    @Timed
    public ResponseEntity<QuestionMaster> getQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionMaster : {}", id);
        QuestionMaster questionMaster = questionMasterService.findOne(id);
        return Optional.ofNullable(questionMaster)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-masters/:id : delete the "id" questionMaster.
     *
     * @param id the id of the questionMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionMaster : {}", id);
        questionMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionMaster", id.toString())).build();
    }

}
