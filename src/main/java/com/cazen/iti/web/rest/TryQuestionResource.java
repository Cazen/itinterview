package com.cazen.iti.web.rest;

import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.service.TryQustionService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing UploadQuestionMaster.
 */
@RestController
@RequestMapping("/api/question")
public class TryQuestionResource {

    private final Logger log = LoggerFactory.getLogger(TryQuestionResource.class);

    @Inject
    private TryQustionService tryQuestionService;


    /**
     * GET  /tryQuestion : get all category 123 Code.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of upQuestionMasters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tryquestion")
    @Timed
    public ResponseEntity<List<CommonCode>> getAllUpQuestionMasters()
        throws URISyntaxException {
        log.debug("REST request to get a page of UpQuestionMasters");

        List<CommonCode> category123CommonCodeList = tryQuestionService.getCategory123CommonCodeList();
        category123CommonCodeList.forEach(commonCode -> commonCode.setHardCodedParentId(commonCode.getParent().getId()));
        category123CommonCodeList.forEach(commonCode -> commonCode.setHardCodedParentParentId(commonCode.getParent().getParent().getId()));
        return new ResponseEntity<>(category123CommonCodeList, HttpStatus.OK);
    }


}
