package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.WrongAnswer;
import com.cazen.iti.service.WrongAnswerService;
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
 * REST controller for managing WrongAnswer.
 */
@RestController
@RequestMapping("/api")
public class WrongAnswerResource {

    private final Logger log = LoggerFactory.getLogger(WrongAnswerResource.class);
        
    @Inject
    private WrongAnswerService wrongAnswerService;

    /**
     * POST  /wrong-answers : Create a new wrongAnswer.
     *
     * @param wrongAnswer the wrongAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wrongAnswer, or with status 400 (Bad Request) if the wrongAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wrong-answers")
    @Timed
    public ResponseEntity<WrongAnswer> createWrongAnswer(@RequestBody WrongAnswer wrongAnswer) throws URISyntaxException {
        log.debug("REST request to save WrongAnswer : {}", wrongAnswer);
        if (wrongAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("wrongAnswer", "idexists", "A new wrongAnswer cannot already have an ID")).body(null);
        }
        WrongAnswer result = wrongAnswerService.save(wrongAnswer);
        return ResponseEntity.created(new URI("/api/wrong-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("wrongAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wrong-answers : Updates an existing wrongAnswer.
     *
     * @param wrongAnswer the wrongAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wrongAnswer,
     * or with status 400 (Bad Request) if the wrongAnswer is not valid,
     * or with status 500 (Internal Server Error) if the wrongAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wrong-answers")
    @Timed
    public ResponseEntity<WrongAnswer> updateWrongAnswer(@RequestBody WrongAnswer wrongAnswer) throws URISyntaxException {
        log.debug("REST request to update WrongAnswer : {}", wrongAnswer);
        if (wrongAnswer.getId() == null) {
            return createWrongAnswer(wrongAnswer);
        }
        WrongAnswer result = wrongAnswerService.save(wrongAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("wrongAnswer", wrongAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wrong-answers : get all the wrongAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wrongAnswers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/wrong-answers")
    @Timed
    public ResponseEntity<List<WrongAnswer>> getAllWrongAnswers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of WrongAnswers");
        Page<WrongAnswer> page = wrongAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wrong-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wrong-answers/:id : get the "id" wrongAnswer.
     *
     * @param id the id of the wrongAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wrongAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/wrong-answers/{id}")
    @Timed
    public ResponseEntity<WrongAnswer> getWrongAnswer(@PathVariable Long id) {
        log.debug("REST request to get WrongAnswer : {}", id);
        WrongAnswer wrongAnswer = wrongAnswerService.findOne(id);
        return Optional.ofNullable(wrongAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /wrong-answers/:id : delete the "id" wrongAnswer.
     *
     * @param id the id of the wrongAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wrong-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteWrongAnswer(@PathVariable Long id) {
        log.debug("REST request to delete WrongAnswer : {}", id);
        wrongAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("wrongAnswer", id.toString())).build();
    }

}
