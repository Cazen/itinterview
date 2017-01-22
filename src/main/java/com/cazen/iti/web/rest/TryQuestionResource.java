package com.cazen.iti.web.rest;

import com.cazen.iti.domain.*;
import com.cazen.iti.repository.QuestionMasterRepository;
import com.cazen.iti.repository.QuestionMasterStaticsRepository;
import com.cazen.iti.repository.RightAnswerRepository;
import com.cazen.iti.repository.WrongAnswerRepository;
import com.cazen.iti.service.CommonCodeService;
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
import java.net.URISyntaxException;
import java.time.ZoneId;
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
    private QuestionMasterRepository questionMasterRepository;
    @Inject
    private RightAnswerRepository rightAnswerRepository;
    @Inject
    private WrongAnswerRepository wrongAnswerRepository;
    @Inject
    private QuestionMasterStaticsRepository questionMasterStaticsRepository;
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
        if (category3SelectboxVal == null || category3SelectboxVal.get("category3SelectboxVal").length() == 0) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("category3Selectbox", "notextists", "카테고리를 선택해 주세요")).body(null);
        }

        List<Long> questionMasterIdList =
            tryQuestionService.getQuestionMasterIdList7Randomly(commonCodeService.findByCd_Id(category3SelectboxVal.get("category3SelectboxVal")).getId());
        QuestionMasterForUser questionMasterForUser = assembleQuestionMasterForUserForTryPages(questionMasterIdList);

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

        QuestionMasterForUser questionMasterForUser;
        try {
            SubmitTryQuestionForUser decryptedSubmitEntity = aes256Util.decryptSubmitTryQuestionForUser(submitTryQuestionForUser);
            String[] generatedId = decryptedSubmitEntity.getGeneratedId().split("_");

            //Compare submitTime - startTime > 5min 5sec then raise exception
            ZonedDateTime startTime = ZonedDateTime.of(Integer.parseInt(generatedId[0]), Integer.parseInt(generatedId[1]), Integer.parseInt(generatedId[2]), Integer.parseInt(generatedId[3])
                , Integer.parseInt(generatedId[4]), Integer.parseInt(generatedId[5]), Integer.parseInt(generatedId[6]), ZoneId.systemDefault());
            startTime = startTime.plusMinutes(5).plusSeconds(5);
            ZonedDateTime now = ZonedDateTime.now().withNano(0);

            if(startTime.isBefore(now)) {
                log.error("Time Limit exceed: ", startTime);
                return new ResponseEntity<>(new QuestionMasterForUser(), HttpStatus.BAD_REQUEST);
            }


            questionMasterForUser = setQuestionMasterStaticsAndGetQMForUser(decryptedSubmitEntity);

        } catch (Exception e) {
            log.error("Exception while decryptedSubmitEntity: ", e);
            return new ResponseEntity<>(new QuestionMasterForUser(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(questionMasterForUser, HttpStatus.OK);
    }

    private QuestionMasterForUser setQuestionMasterStaticsAndGetQMForUser(SubmitTryQuestionForUser decryptedSubmitEntity) {

        QuestionMasterForUser questionMasterForUser = new QuestionMasterForUser();
        List<QuestionMaster> questionMasterList = new ArrayList<>();
        questionMasterForUser.setQuestionMasterList(questionMasterList);

        QuestionMasterStatics questionMasterStatics;

        List<Long> questionMasterIdList = new ArrayList<>();
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionOne()));
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionTwo()));
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionThree()));
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionFour()));
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionFive()));
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionSix()));
        questionMasterIdList.add(Long.parseLong(decryptedSubmitEntity.getQuestionSeven()));

        List<String> rightWrongList = new ArrayList<>();
        rightWrongList.add(decryptedSubmitEntity.getAnswerOne());
        rightWrongList.add(decryptedSubmitEntity.getAnswerTwo());
        rightWrongList.add(decryptedSubmitEntity.getAnswerThree());
        rightWrongList.add(decryptedSubmitEntity.getAnswerFour());
        rightWrongList.add(decryptedSubmitEntity.getAnswerFive());
        rightWrongList.add(decryptedSubmitEntity.getAnswerSix());
        rightWrongList.add(decryptedSubmitEntity.getAnswerSeven());

        //Will be replace to apply ELO rating
        int erningPoint = 0;
        for(int i = 0 ; i < 7 ; i++) {
            QuestionMaster questionMaster = questionMasterRepository.findOne(questionMasterIdList.get(i));
            questionMaster.setWrongAnswers(null);
            questionMaster.setRightAnswers(null);

            if (questionMaster.getQuestionMasterStatics() == null) {
                questionMasterStatics = new QuestionMasterStatics();


                questionMasterStatics.setDownVoteCount(0);
                questionMasterStatics.setQuestionMaster(questionMaster);
                questionMasterStatics.setRightCount(0);
                questionMasterStatics.setUpVoteCount(0);
                questionMasterStatics.setWrongCount(0);

                questionMaster.setQuestionMasterStatics(questionMasterStatics);
                questionMasterRepository.saveAndFlush(questionMaster);
            } else {
                questionMasterStatics = questionMasterStaticsRepository.findOne(questionMaster.getQuestionMasterStatics().getId());
            }

            if ("W".equals(rightWrongList.get(i).split("_")[1])) {
                int wrongCount = questionMasterStatics.getWrongCount();
                questionMasterStatics.setWrongCount(++wrongCount);
                erningPoint--;
                questionMaster.setSelectedAnswerString(wrongAnswerRepository.findOne(Long.parseLong(rightWrongList.get(i).split("_")[2])).getOptionText());
            } else if ("R".equals(rightWrongList.get(i).split("_")[1])) {
                int rightCount = questionMasterStatics.getRightCount();
                questionMasterStatics.setRightCount(++rightCount);
                erningPoint++;
                questionMaster.setSelectedAnswerString(rightAnswerRepository.findOne(Long.parseLong(rightWrongList.get(i).split("_")[2])).getOptionText());
            }

            questionMasterStaticsRepository.saveAndFlush(questionMasterStatics);


            //Set QuestionMasterForUser's questionMaster;
            questionMaster.setRightWrongString(rightWrongList.get(i).split("_")[1]);
            questionMaster.setQuestionMasterStatics(questionMasterStatics);
            List<QuestionMaster> tempQMList = questionMasterForUser.getQuestionMasterList();
            tempQMList.add(questionMaster);
            questionMasterForUser.setQuestionMasterList(tempQMList);
        }

        questionMasterForUser.setErningPoint(erningPoint);
        return questionMasterForUser;
    }

    private QuestionMasterForUser assembleQuestionMasterForUserForTryPages(List<Long> questionMasterIdList) {
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
            QuestionMaster questionMaster = questionMasterRepository.findOne(questionMasterId);

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

