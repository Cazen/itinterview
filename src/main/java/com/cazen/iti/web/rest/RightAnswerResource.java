package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.RightAnswer;
import com.cazen.iti.service.RightAnswerService;
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
 * REST controller for managing RightAnswer.
 */
@RestController
@RequestMapping("/api")
public class RightAnswerResource {

    private final Logger log = LoggerFactory.getLogger(RightAnswerResource.class);
        
    @Inject
    private RightAnswerService rightAnswerService;

    /**
     * POST  /right-answers : Create a new rightAnswer.
     *
     * @param rightAnswer the rightAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rightAnswer, or with status 400 (Bad Request) if the rightAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/right-answers")
    @Timed
    public ResponseEntity<RightAnswer> createRightAnswer(@RequestBody RightAnswer rightAnswer) throws URISyntaxException {
        log.debug("REST request to save RightAnswer : {}", rightAnswer);
        if (rightAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rightAnswer", "idexists", "A new rightAnswer cannot already have an ID")).body(null);
        }
        RightAnswer result = rightAnswerService.save(rightAnswer);
        return ResponseEntity.created(new URI("/api/right-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rightAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /right-answers : Updates an existing rightAnswer.
     *
     * @param rightAnswer the rightAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rightAnswer,
     * or with status 400 (Bad Request) if the rightAnswer is not valid,
     * or with status 500 (Internal Server Error) if the rightAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/right-answers")
    @Timed
    public ResponseEntity<RightAnswer> updateRightAnswer(@RequestBody RightAnswer rightAnswer) throws URISyntaxException {
        log.debug("REST request to update RightAnswer : {}", rightAnswer);
        if (rightAnswer.getId() == null) {
            return createRightAnswer(rightAnswer);
        }
        RightAnswer result = rightAnswerService.save(rightAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rightAnswer", rightAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /right-answers : get all the rightAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rightAnswers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/right-answers")
    @Timed
    public ResponseEntity<List<RightAnswer>> getAllRightAnswers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RightAnswers");
        Page<RightAnswer> page = rightAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/right-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /right-answers/:id : get the "id" rightAnswer.
     *
     * @param id the id of the rightAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rightAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/right-answers/{id}")
    @Timed
    public ResponseEntity<RightAnswer> getRightAnswer(@PathVariable Long id) {
        log.debug("REST request to get RightAnswer : {}", id);
        RightAnswer rightAnswer = rightAnswerService.findOne(id);
        return Optional.ofNullable(rightAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /right-answers/:id : delete the "id" rightAnswer.
     *
     * @param id the id of the rightAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/right-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteRightAnswer(@PathVariable Long id) {
        log.debug("REST request to delete RightAnswer : {}", id);
        rightAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rightAnswer", id.toString())).build();
    }

}
