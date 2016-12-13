package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.service.UpQuestionMasterService;
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
 * REST controller for managing UpQuestionMaster.
 */
@RestController
@RequestMapping("/api")
public class UpQuestionMasterResource {

    private final Logger log = LoggerFactory.getLogger(UpQuestionMasterResource.class);
        
    @Inject
    private UpQuestionMasterService upQuestionMasterService;

    /**
     * POST  /up-question-masters : Create a new upQuestionMaster.
     *
     * @param upQuestionMaster the upQuestionMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new upQuestionMaster, or with status 400 (Bad Request) if the upQuestionMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/up-question-masters")
    @Timed
    public ResponseEntity<UpQuestionMaster> createUpQuestionMaster(@RequestBody UpQuestionMaster upQuestionMaster) throws URISyntaxException {
        log.debug("REST request to save UpQuestionMaster : {}", upQuestionMaster);
        if (upQuestionMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("upQuestionMaster", "idexists", "A new upQuestionMaster cannot already have an ID")).body(null);
        }
        UpQuestionMaster result = upQuestionMasterService.save(upQuestionMaster);
        return ResponseEntity.created(new URI("/api/up-question-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upQuestionMaster", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /up-question-masters : Updates an existing upQuestionMaster.
     *
     * @param upQuestionMaster the upQuestionMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated upQuestionMaster,
     * or with status 400 (Bad Request) if the upQuestionMaster is not valid,
     * or with status 500 (Internal Server Error) if the upQuestionMaster couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/up-question-masters")
    @Timed
    public ResponseEntity<UpQuestionMaster> updateUpQuestionMaster(@RequestBody UpQuestionMaster upQuestionMaster) throws URISyntaxException {
        log.debug("REST request to update UpQuestionMaster : {}", upQuestionMaster);
        if (upQuestionMaster.getId() == null) {
            return createUpQuestionMaster(upQuestionMaster);
        }
        UpQuestionMaster result = upQuestionMasterService.save(upQuestionMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("upQuestionMaster", upQuestionMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /up-question-masters : get all the upQuestionMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of upQuestionMasters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/up-question-masters")
    @Timed
    public ResponseEntity<List<UpQuestionMaster>> getAllUpQuestionMasters(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UpQuestionMasters");
        Page<UpQuestionMaster> page = upQuestionMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/up-question-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /up-question-masters/:id : get the "id" upQuestionMaster.
     *
     * @param id the id of the upQuestionMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the upQuestionMaster, or with status 404 (Not Found)
     */
    @GetMapping("/up-question-masters/{id}")
    @Timed
    public ResponseEntity<UpQuestionMaster> getUpQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to get UpQuestionMaster : {}", id);
        UpQuestionMaster upQuestionMaster = upQuestionMasterService.findOne(id);
        return Optional.ofNullable(upQuestionMaster)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /up-question-masters/:id : delete the "id" upQuestionMaster.
     *
     * @param id the id of the upQuestionMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/up-question-masters/{id}")
    @Timed
    public ResponseEntity<Void> deleteUpQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to delete UpQuestionMaster : {}", id);
        upQuestionMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upQuestionMaster", id.toString())).build();
    }

}
