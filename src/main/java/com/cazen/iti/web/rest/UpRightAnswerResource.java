package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.UpRightAnswer;
import com.cazen.iti.service.UpRightAnswerService;
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
 * REST controller for managing UpRightAnswer.
 */
@RestController
@RequestMapping("/api")
public class UpRightAnswerResource {

    private final Logger log = LoggerFactory.getLogger(UpRightAnswerResource.class);
        
    @Inject
    private UpRightAnswerService upRightAnswerService;

    /**
     * POST  /up-right-answers : Create a new upRightAnswer.
     *
     * @param upRightAnswer the upRightAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new upRightAnswer, or with status 400 (Bad Request) if the upRightAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/up-right-answers")
    @Timed
    public ResponseEntity<UpRightAnswer> createUpRightAnswer(@RequestBody UpRightAnswer upRightAnswer) throws URISyntaxException {
        log.debug("REST request to save UpRightAnswer : {}", upRightAnswer);
        if (upRightAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("upRightAnswer", "idexists", "A new upRightAnswer cannot already have an ID")).body(null);
        }
        UpRightAnswer result = upRightAnswerService.save(upRightAnswer);
        return ResponseEntity.created(new URI("/api/up-right-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upRightAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /up-right-answers : Updates an existing upRightAnswer.
     *
     * @param upRightAnswer the upRightAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated upRightAnswer,
     * or with status 400 (Bad Request) if the upRightAnswer is not valid,
     * or with status 500 (Internal Server Error) if the upRightAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/up-right-answers")
    @Timed
    public ResponseEntity<UpRightAnswer> updateUpRightAnswer(@RequestBody UpRightAnswer upRightAnswer) throws URISyntaxException {
        log.debug("REST request to update UpRightAnswer : {}", upRightAnswer);
        if (upRightAnswer.getId() == null) {
            return createUpRightAnswer(upRightAnswer);
        }
        UpRightAnswer result = upRightAnswerService.save(upRightAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("upRightAnswer", upRightAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /up-right-answers : get all the upRightAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of upRightAnswers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/up-right-answers")
    @Timed
    public ResponseEntity<List<UpRightAnswer>> getAllUpRightAnswers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UpRightAnswers");
        Page<UpRightAnswer> page = upRightAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/up-right-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /up-right-answers/:id : get the "id" upRightAnswer.
     *
     * @param id the id of the upRightAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the upRightAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/up-right-answers/{id}")
    @Timed
    public ResponseEntity<UpRightAnswer> getUpRightAnswer(@PathVariable Long id) {
        log.debug("REST request to get UpRightAnswer : {}", id);
        UpRightAnswer upRightAnswer = upRightAnswerService.findOne(id);
        return Optional.ofNullable(upRightAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /up-right-answers/:id : delete the "id" upRightAnswer.
     *
     * @param id the id of the upRightAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/up-right-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteUpRightAnswer(@PathVariable Long id) {
        log.debug("REST request to delete UpRightAnswer : {}", id);
        upRightAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upRightAnswer", id.toString())).build();
    }

}
