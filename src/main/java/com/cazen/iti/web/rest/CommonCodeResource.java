package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.service.CommonCodeService;
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

/**
 * REST controller for managing CommonCode.
 */
@RestController
@RequestMapping("/api")
public class CommonCodeResource {

    private final Logger log = LoggerFactory.getLogger(CommonCodeResource.class);
        
    @Inject
    private CommonCodeService commonCodeService;

    /**
     * POST  /common-codes : Create a new commonCode.
     *
     * @param commonCode the commonCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commonCode, or with status 400 (Bad Request) if the commonCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/common-codes")
    @Timed
    public ResponseEntity<CommonCode> createCommonCode(@RequestBody CommonCode commonCode) throws URISyntaxException {
        log.debug("REST request to save CommonCode : {}", commonCode);
        if (commonCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("commonCode", "idexists", "A new commonCode cannot already have an ID")).body(null);
        }
        CommonCode result = commonCodeService.save(commonCode);
        return ResponseEntity.created(new URI("/api/common-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("commonCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /common-codes : Updates an existing commonCode.
     *
     * @param commonCode the commonCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commonCode,
     * or with status 400 (Bad Request) if the commonCode is not valid,
     * or with status 500 (Internal Server Error) if the commonCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/common-codes")
    @Timed
    public ResponseEntity<CommonCode> updateCommonCode(@RequestBody CommonCode commonCode) throws URISyntaxException {
        log.debug("REST request to update CommonCode : {}", commonCode);
        if (commonCode.getId() == null) {
            return createCommonCode(commonCode);
        }
        CommonCode result = commonCodeService.save(commonCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("commonCode", commonCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /common-codes : get all the commonCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commonCodes in body
     */
    @GetMapping("/common-codes")
    @Timed
    public List<CommonCode> getAllCommonCodes() {
        log.debug("REST request to get all CommonCodes");
        return commonCodeService.findAll();
    }

    /**
     * GET  /common-codes/:id : get the "id" commonCode.
     *
     * @param id the id of the commonCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commonCode, or with status 404 (Not Found)
     */
    @GetMapping("/common-codes/{id}")
    @Timed
    public ResponseEntity<CommonCode> getCommonCode(@PathVariable Long id) {
        log.debug("REST request to get CommonCode : {}", id);
        CommonCode commonCode = commonCodeService.findOne(id);
        return Optional.ofNullable(commonCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /common-codes/:id : delete the "id" commonCode.
     *
     * @param id the id of the commonCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/common-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommonCode(@PathVariable Long id) {
        log.debug("REST request to delete CommonCode : {}", id);
        commonCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("commonCode", id.toString())).build();
    }

}
