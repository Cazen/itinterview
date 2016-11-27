package com.cazen.iti.web.rest;

import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.service.UploadQustionService;
import com.cazen.iti.web.rest.util.HeaderUtil;
import com.cazen.iti.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managing UploadQuestionMaster.
 */
@RestController
@RequestMapping("/app/question")
public class UploadQuestionResource {

    private final Logger log = LoggerFactory.getLogger(UploadQuestionResource.class);

    @Inject
    private UploadQustionService uploadQuestionService;

    /**
     * POST  /uploadQuestion : Create a new uploadQuestion.
     *
     * @param upQuestionMaster the upQuestionMaster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new upQuestionMaster, or with status 400 (Bad Request) if the upQuestionMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uploadQuestion")
    @Timed
    public ResponseEntity<UpQuestionMaster> createUpQuestionMaster(@RequestBody UpQuestionMaster upQuestionMaster) throws URISyntaxException {
        log.debug("REST request to save UpQuestionMaster : {}", upQuestionMaster);
        if (upQuestionMaster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("upQuestionMaster", "idexists", "A new upQuestionMaster cannot already have an ID")).body(null);
        }
        UpQuestionMaster result = uploadQuestionService.save(upQuestionMaster);
        return ResponseEntity.created(new URI("/api/uploadQuestion/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("upQuestionMaster", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uploadQuestion : Updates an existing upQuestionMaster.
     *
     * @param upQuestionMaster the upQuestionMaster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated upQuestionMaster,
     * or with status 400 (Bad Request) if the upQuestionMaster is not valid,
     * or with status 500 (Internal Server Error) if the upQuestionMaster couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uploadQuestion")
    @Timed
    public ResponseEntity<UpQuestionMaster> updateUpQuestionMaster(@RequestBody UpQuestionMaster upQuestionMaster) throws URISyntaxException {
        log.debug("REST request to update UpQuestionMaster : {}", upQuestionMaster);
        if (upQuestionMaster.getId() == null) {
            return createUpQuestionMaster(upQuestionMaster);
        }
        UpQuestionMaster result = uploadQuestionService.save(upQuestionMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("upQuestionMaster", upQuestionMaster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uploadQuestion : get all the upQuestionMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of upQuestionMasters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/uploadQuestion")
    @Timed
    public ResponseEntity<List<UpQuestionMaster>> getAllUpQuestionMasters(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UpQuestionMasters");
        Page<UpQuestionMaster> page = uploadQuestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/uploadQuestion");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /uploadQuestion/:id : get the "id" upQuestionMaster.
     *
     * @param id the id of the upQuestionMaster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the upQuestionMaster, or with status 404 (Not Found)
     */
    @GetMapping("/uploadQuestion/{id}")
    @Timed
    public ResponseEntity<UpQuestionMaster> getUpQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to get UpQuestionMaster : {}", id);
        UpQuestionMaster upQuestionMaster = uploadQuestionService.findOne(id);
        return Optional.ofNullable(upQuestionMaster)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /uploadQuestion/:id : delete the "id" upQuestionMaster.
     *
     * @param id the id of the upQuestionMaster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uploadQuestion/{id}")
    @Timed
    public ResponseEntity<Void> deleteUpQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to delete UpQuestionMaster : {}", id);
        uploadQuestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("upQuestionMaster", id.toString())).build();
    }

}
