package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.UpWrongAnswer;
import com.cazen.iti.service.UpWrongAnswerService;
import com.cazen.iti.web.rest.util.HeaderUtil;
import com.cazen.iti.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
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
 * REST controller for managing UpWrongAnswer.
 */
@RestController
@RequestMapping("/api")
public class UpWrongAnswerResource {

    private final Logger log = LoggerFactory.getLogger(UpWrongAnswerResource.class);
        
    @Inject
    private UpWrongAnswerService upWrongAnswerService;

    /**
     * POST  /up-wrong-answers : Create a new upWrongAnswer.
     *
     * @param upWrongAnswer the upWrongAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new upWrongAnswer, or with status 400 (Bad Request) if the upWrongAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/up-wrong-answers")
    @Timed
    public ResponseEntity<UpWrongAnswer> createUpWrongAnswer(@RequestBody UpWrongAnswer upWrongAnswer) throws URISyntaxException {
        log.debug("REST request to save UpWrongAnswer : {}", upWrongAnswer);
        if (upWrongAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("upWrongAnswer", "idexists", "A new upWrongAnswer cannot already have an ID")).body(null);
        }
        UpWrongAnswer result = upWrongAnswerService.save(upWrongAnswer);
        return ResponseEntity.created(new URI("/api/up-wrong-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upWrongAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /up-wrong-answers : Updates an existing upWrongAnswer.
     *
     * @param upWrongAnswer the upWrongAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated upWrongAnswer,
     * or with status 400 (Bad Request) if the upWrongAnswer is not valid,
     * or with status 500 (Internal Server Error) if the upWrongAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/up-wrong-answers")
    @Timed
    public ResponseEntity<UpWrongAnswer> updateUpWrongAnswer(@RequestBody UpWrongAnswer upWrongAnswer) throws URISyntaxException {
        log.debug("REST request to update UpWrongAnswer : {}", upWrongAnswer);
        if (upWrongAnswer.getId() == null) {
            return createUpWrongAnswer(upWrongAnswer);
        }
        UpWrongAnswer result = upWrongAnswerService.save(upWrongAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("upWrongAnswer", upWrongAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /up-wrong-answers : get all the upWrongAnswers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of upWrongAnswers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/up-wrong-answers")
    @Timed
    public ResponseEntity<List<UpWrongAnswer>> getAllUpWrongAnswers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UpWrongAnswers");
        Page<UpWrongAnswer> page = upWrongAnswerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/up-wrong-answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /up-wrong-answers/:id : get the "id" upWrongAnswer.
     *
     * @param id the id of the upWrongAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the upWrongAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/up-wrong-answers/{id}")
    @Timed
    public ResponseEntity<UpWrongAnswer> getUpWrongAnswer(@PathVariable Long id) {
        log.debug("REST request to get UpWrongAnswer : {}", id);
        UpWrongAnswer upWrongAnswer = upWrongAnswerService.findOne(id);
        return Optional.ofNullable(upWrongAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /up-wrong-answers/:id : delete the "id" upWrongAnswer.
     *
     * @param id the id of the upWrongAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/up-wrong-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteUpWrongAnswer(@PathVariable Long id) {
        log.debug("REST request to delete UpWrongAnswer : {}", id);
        upWrongAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upWrongAnswer", id.toString())).build();
    }

}
