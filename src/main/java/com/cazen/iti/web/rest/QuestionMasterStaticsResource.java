package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.QuestionMasterStatics;

import com.cazen.iti.repository.QuestionMasterStaticsRepository;
import com.cazen.iti.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing QuestionMasterStatics.
 */
@RestController
@RequestMapping("/api")
public class QuestionMasterStaticsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionMasterStaticsResource.class);
        
    @Inject
    private QuestionMasterStaticsRepository questionMasterStaticsRepository;

    /**
     * POST  /question-master-statics : Create a new questionMasterStatics.
     *
     * @param questionMasterStatics the questionMasterStatics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionMasterStatics, or with status 400 (Bad Request) if the questionMasterStatics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-master-statics")
    @Timed
    public ResponseEntity<QuestionMasterStatics> createQuestionMasterStatics(@RequestBody QuestionMasterStatics questionMasterStatics) throws URISyntaxException {
        log.debug("REST request to save QuestionMasterStatics : {}", questionMasterStatics);
        if (questionMasterStatics.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionMasterStatics", "idexists", "A new questionMasterStatics cannot already have an ID")).body(null);
        }
        QuestionMasterStatics result = questionMasterStaticsRepository.save(questionMasterStatics);
        return ResponseEntity.created(new URI("/api/question-master-statics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionMasterStatics", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-master-statics : Updates an existing questionMasterStatics.
     *
     * @param questionMasterStatics the questionMasterStatics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionMasterStatics,
     * or with status 400 (Bad Request) if the questionMasterStatics is not valid,
     * or with status 500 (Internal Server Error) if the questionMasterStatics couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-master-statics")
    @Timed
    public ResponseEntity<QuestionMasterStatics> updateQuestionMasterStatics(@RequestBody QuestionMasterStatics questionMasterStatics) throws URISyntaxException {
        log.debug("REST request to update QuestionMasterStatics : {}", questionMasterStatics);
        if (questionMasterStatics.getId() == null) {
            return createQuestionMasterStatics(questionMasterStatics);
        }
        QuestionMasterStatics result = questionMasterStaticsRepository.save(questionMasterStatics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionMasterStatics", questionMasterStatics.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-master-statics : get all the questionMasterStatics.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of questionMasterStatics in body
     */
    @GetMapping("/question-master-statics")
    @Timed
    public List<QuestionMasterStatics> getAllQuestionMasterStatics(@RequestParam(required = false) String filter) {
        if ("questionmaster-is-null".equals(filter)) {
            log.debug("REST request to get all QuestionMasterStaticss where questionMaster is null");
            return StreamSupport
                .stream(questionMasterStaticsRepository.findAll().spliterator(), false)
                .filter(questionMasterStatics -> questionMasterStatics.getQuestionMaster() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all QuestionMasterStatics");
        List<QuestionMasterStatics> questionMasterStatics = questionMasterStaticsRepository.findAll();
        return questionMasterStatics;
    }

    /**
     * GET  /question-master-statics/:id : get the "id" questionMasterStatics.
     *
     * @param id the id of the questionMasterStatics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionMasterStatics, or with status 404 (Not Found)
     */
    @GetMapping("/question-master-statics/{id}")
    @Timed
    public ResponseEntity<QuestionMasterStatics> getQuestionMasterStatics(@PathVariable Long id) {
        log.debug("REST request to get QuestionMasterStatics : {}", id);
        QuestionMasterStatics questionMasterStatics = questionMasterStaticsRepository.findOne(id);
        return Optional.ofNullable(questionMasterStatics)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-master-statics/:id : delete the "id" questionMasterStatics.
     *
     * @param id the id of the questionMasterStatics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-master-statics/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionMasterStatics(@PathVariable Long id) {
        log.debug("REST request to delete QuestionMasterStatics : {}", id);
        questionMasterStaticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionMasterStatics", id.toString())).build();
    }

}
