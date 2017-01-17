package com.cazen.iti.web.rest;

import com.cazen.iti.domain.*;
import com.cazen.iti.service.CommonCodeService;
import com.cazen.iti.service.QuestionMasterService;
import com.cazen.iti.service.TryQustionService;
import com.cazen.iti.service.util.AES256Util;
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
import java.time.ZonedDateTime;
import java.util.*;

/**
 * * REST controller for managing UploadQuestionMaster.
 */
@RestController
@RequestMapping("/api/question")
public class TryQuestionResource {

    private final Logger log = LoggerFactory.getLogger(TryQuestionResource.class);
    @Inject
    private TryQustionService tryQuestionService;
    @Inject
    private CommonCodeService commonCodeService;
    @Inject
    private QuestionMasterService questionMasterService;
    @Inject
    private AES256Util aes256Util;

    /**
     * * GET  /tryQuestion : get all category 123 Code.
     * *
     * * @return the ResponseEntity with status 200 (OK) and the list of upQuestionMasters in body
     * * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tryquestion")
    @Timed
    public ResponseEntity<List<CommonCode>> getAllUpQuestionMasters() throws URISyntaxException {
        log.debug("REST request to get a page of UpQuestionMasters");

        List<CommonCode> category123CommonCodeList = tryQuestionService.getCategory123CommonCodeList();
        category123CommonCodeList.forEach(commonCode -> commonCode.setHardCodedParentId(commonCode.getParent().getId()));
        category123CommonCodeList.forEach(commonCode -> commonCode.setHardCodedParentParentId(commonCode.getParent().getParent().getId()));
        return new ResponseEntity<>(category123CommonCodeList, HttpStatus.OK);
    }

    /**
     * * POST  /tryquestionnew : Create a new QuestionList and return to solving pages.
     * *
     * * @param category3SelectboxVal selected category3Id
     * * @return the ResponseEntity with status 201 (Created) and with body the new QuestionMasterList, or with status 400 (Bad Request) if the category3Selectbox does not exists
     * * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tryquestionnew")
    @Timed
    public ResponseEntity<QuestionMasterForUser> getQuestionListbyCategory3(@RequestBody Map<String, String> category3SelectboxVal) throws URISyntaxException {

        log.debug("REST(POST) request to get tryQuestionNew : {}", category3SelectboxVal.get("category3SelectboxVal"));
        log.debug("category3SelectboxVal = " + category3SelectboxVal);
        if (category3SelectboxVal == null || category3SelectboxVal.get("category3SelectboxVal").length() == 0) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("category3Selectbox", "notextists", "카테고리를 선택해 주세요")).body(null);
        }

        List<Long> questionMasterIdList =
            tryQuestionService.getQuestionMasterIdList7Randomly(commonCodeService.findByCd_Id(category3SelectboxVal.get("category3SelectboxVal")).getId());
        QuestionMasterForUser questionMasterForUser = assembleQuestionMasterForUser(questionMasterIdList);

        log.debug("working working questionMasterForUser.getQuestionMasterList().size() = " + questionMasterForUser.getQuestionMasterList().size());

        return new ResponseEntity<>(questionMasterForUser, HttpStatus.OK);
    }

    /**
     * * POST  /tryQuestionAnswer : Submit a answer and return a result
     * *
     * * @param category3SelectboxVal selected category3Id
     * * @return the ResponseEntity with status 201 (Created) and with body the new QuestionMasterList, or with status 400 (Bad Request) if the category3Selectbox does not exists
     * * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tryQuestionAnswer")
    @Timed
    public ResponseEntity<QuestionMasterForUser> submitTryQuestion(@RequestBody SubmitTryQuestionForUser submitTryQuestionForUser) throws URISyntaxException {

        log.debug("REST(POST) request to get tryQuestionNew : {}", submitTryQuestionForUser.getStartTime() + "_" + submitTryQuestionForUser.getAnswerOne());

        return ResponseEntity.created(new URI("/app/question/tryQuestionAnswer"))
            .headers(HeaderUtil.createEntityCreationAlert("tryQuestionAnswer", "test"))
            .body(null);

    }


    private QuestionMasterForUser assembleQuestionMasterForUser(List<Long> questionMasterIdList) {
        QuestionMasterForUser questionMasterForUser = new QuestionMasterForUser();
        ArrayList<QuestionMaster> questionMasterList = new ArrayList<>();

        ZonedDateTime startTime = ZonedDateTime.now().withNano(0);
        questionMasterForUser.setStartTime(startTime);

        try {
            String generatedId = aes256Util.aesEncode(
                startTime.getYear() + "_" + startTime.getMonthValue() + "_" + startTime.getDayOfMonth() + "_" + startTime.getHour() + "_" + startTime
                    .getMinute() + "_" + startTime.getSecond() + "_" + startTime.getNano() + "_" + startTime.getZone());
            questionMasterForUser.setGeneratedId(generatedId);
        } catch (Exception e) {
            log.error("Exception while encrypt generatedId: ", e);
        }

        questionMasterIdList.forEach(questionMasterId -> {
            QuestionMaster questionMaster = questionMasterService.findOne(questionMasterId);

            Set<AnswersForUser> answersForUserSet = new HashSet<>();

            questionMaster.getRightAnswers().forEach(ra -> {

                try {
                    AnswersForUser answersForUser = new AnswersForUser();
                    answersForUser.setGeneratedId(aes256Util.aesEncode(Math.random() + "_R_" + ra.getId()));
                    answersForUser.setOptionText(ra.getOptionText());
                    answersForUserSet.add(answersForUser);
                } catch (Exception e) {
                    log.error("Exception while encrypt rightAnswer: ", e);
                }
            });

            questionMaster.getWrongAnswers().forEach(wa -> {
                try {
                    AnswersForUser answersForUser = new AnswersForUser();
                    answersForUser.setGeneratedId(aes256Util.aesEncode(Math.random() + "_W_" + wa.getId()));
                    answersForUser.setOptionText(wa.getOptionText());
                    answersForUserSet.add(answersForUser);
                } catch (Exception e) {
                    log.error("Exception while encrypt rightAnswer: ", e);
                }
            });

            questionMaster.setAnswersForUsersSet(answersForUserSet);

            try {
                questionMaster.setGeneratedId(aes256Util.aesEncode(questionMaster.getId().toString()));
            } catch (Exception e) {
                log.error("Exception while encrypt generatedId: ", e);
            }

            questionMaster.setWrongAnswers(null);
            questionMaster.setRightAnswers(null);
            questionMaster.setDelYn(null);
            questionMaster.cTime(null);
            questionMaster.setStatus(null);

            questionMasterList.add(questionMaster);

        });

        questionMasterForUser.setQuestionMasterList(questionMasterList);

        return questionMasterForUser;
    }
}

