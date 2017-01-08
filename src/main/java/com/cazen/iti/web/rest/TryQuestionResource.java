package com.cazen.iti.web.rest;

import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.service.TryQustionService;
import com.cazen.iti.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

    /**
     * GET  /tryquestionnew : Create a new QuestionList and return to solving pages.
     *
     * @param category3SelectboxVal selected category3Id
     * @return the ResponseEntity with status 201 (Created) and with body the new QuestionMasterList, or with status 400 (Bad Request) if the category3Selectbox does not exists
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/tryquestionnew/{category3SelectboxVal}")
    @Timed
    public ResponseEntity<List<UpQuestionMaster>> getQuestionListbyCategory3(@PathVariable String category3SelectboxVal) throws URISyntaxException {

        log.debug("REST(POST) request to get tryQuestionNew : {}", category3SelectboxVal);
        if (category3SelectboxVal == null || category3SelectboxVal.length() == 0) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("category3Selectbox", "notextists", "카테고리를 선택해 주세요")).body(null);
        }

        //UpQuestionMaster result = uploadQuestionService.save(upQuestionMaster);
        List<UpQuestionMaster> upQuestionMasterList = new ArrayList<>();
        return ResponseEntity.created(new URI("/app/tryQuestionNew/"))
            .headers(HeaderUtil.createEntityCreationAlert("upQuestionMasterList", category3SelectboxVal))
            .body(upQuestionMasterList);
    }
}
