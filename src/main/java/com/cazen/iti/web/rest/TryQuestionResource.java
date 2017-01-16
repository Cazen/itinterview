package com.cazen.iti.web.rest;

import com.cazen.iti.domain.*;
import com.cazen.iti.service.CommonCodeService;
import com.cazen.iti.service.TryQustionService;
import com.cazen.iti.service.util.AES256Util;
import com.cazen.iti.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * REST controller for managing UploadQuestionMaster.
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
    private AES256Util aes256Util;
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
    public ResponseEntity<List<QuestionMaster>> getQuestionListbyCategory3(@PathVariable String category3SelectboxVal) throws URISyntaxException {

        log.debug("REST(POST) request to get tryQuestionNew : {}", category3SelectboxVal);
        log.debug("category3SelectboxVal = " + category3SelectboxVal);
        if (category3SelectboxVal == null || category3SelectboxVal.length() == 0) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("category3Selectbox", "notextists", "카테고리를 선택해 주세요")).body(null);
        }

        List<Long> questionMasterList = tryQuestionService.getQuestionMasterIdList7Randomly(commonCodeService.findByCd_Id(category3SelectboxVal).getId());
        log.debug("working working super working" + questionMasterList.toString() + " " + questionMasterList.get(0));
        //UpQuestionMaster result = uploadQuestionService.save(upQuestionMaster);

        //List<QuestionMaster> questionMasterList = getSampleQuestionMasterList();

        return new ResponseEntity<>(getSampleQuestionMasterList(), HttpStatus.OK);
    }

    private QuestionMasterForUser assembleQuestionMasterForUser(List<QuestionMaster> questionMasterList) {
        QuestionMasterForUser questionMasterForUser = new QuestionMasterForUser();
        Set<AnswersForUser> answersForUserSet = new HashSet<>();
        LocalDate startDate = LocalDate.now();
        LocalTime startTime = LocalTime.now();

        questionMasterList.forEach(qm -> {
            qm.setId(0l);
            qm.setDelYn("");
            qm.cTime(null);
            qm.setStatus("");

            qm.getRightAnswers().forEach(ra -> {
                AnswersForUser answersForUser = new AnswersForUser();
                try {
                    answersForUser.setGeneratedId(aes256Util.aesEncode("R_" + ra.getId()));
                } catch (Exception e) {
                    log.error("Exception while encrypt rightAnswer: ", e);
                }
                answersForUser.setOptionText(ra.getOptionText());
                answersForUserSet.add(answersForUser);
            });

            qm.getWrongAnswers().forEach(wa -> {
                AnswersForUser answersForUser = new AnswersForUser();
                try {
                    answersForUser.setGeneratedId(aes256Util.aesEncode("W_" + wa.getId()));
                } catch (Exception e) {
                    log.error("Exception while encrypt rightAnswer: ", e);
                }
                answersForUser.setOptionText(wa.getOptionText());
                answersForUserSet.add(answersForUser);
            });

            qm.setAnswersForUsersSet(answersForUserSet);
            try {
                qm.setGeneratedId(aes256Util.aesEncode(qm.getId().toString()));
            } catch (Exception e) {
                log.error("Exception while encrypt generatedId: ", e);
            }
        });

        questionMasterForUser.setQuestionMasterList(questionMasterList);

        return questionMasterForUser;
    }


    private List<QuestionMaster> getSampleQuestionMasterList() {
        List<QuestionMaster> sampleQuestionMasterList = new ArrayList<>();

        CommonCode code1 = new CommonCode();
        CommonCode code2 = new CommonCode();
        CommonCode code3 = new CommonCode();

        code1.setId(100l);code2.setId(202l);code3.setId(1201l);
        code1.setCdId("DEVELOPER");code2.setCdId("BIGDATA");code3.setCdId("HIVE");
        code1.setCdNm("개발자");code2.setCdNm("빅데이터\t");code3.setCdNm("Apache Hive");

        for(int i = 0; i < 7; i++) {
            QuestionMaster questionMaster = new QuestionMaster();
            questionMaster.setDelYn("N");
            questionMaster.setTitle("테스트용 문제 조금 길게 다시 변형해서 이정도 문제가 나오면 되겠지? " + i + "번");
            questionMaster.setCategory1(code1);
            questionMaster.setCategory2(code2);
            questionMaster.setCategory3(code3);
            questionMaster.setStatus("RUNNING");

            questionMaster.setRightAnswers(getRightAnswerSet());
            questionMaster.setWrongAnswers(getWrongAnswerSet());

            sampleQuestionMasterList.add(questionMaster);
        }

        return sampleQuestionMasterList;
    }

    private Set<RightAnswer> getRightAnswerSet() {
        Set<RightAnswer> rightAnswerSet = new HashSet<>();

        RightAnswer rightAnswer = new RightAnswer();
        rightAnswer.setDelYn("N");
        rightAnswer.setId(1l);
        rightAnswer.setOptionText("정답도 옵션에서 조금 길게 나와야 눈에 보입니다");

        rightAnswerSet.add(rightAnswer);
        return rightAnswerSet;
    }

    private Set<WrongAnswer> getWrongAnswerSet() {
        Set<WrongAnswer> wrongAnswerSet = new HashSet<>();

        for(long i = 0; i < 4; i++) {
            WrongAnswer wrongAnswer = new WrongAnswer();
            wrongAnswer.setDelYn("N");
            wrongAnswer.setId(i);
            wrongAnswer.setOptionText("오답도 옵션에서 조금 길게 나와야 눈에 보입니다" + i);

            wrongAnswerSet.add(wrongAnswer);
        }
        return wrongAnswerSet;
    }
}
