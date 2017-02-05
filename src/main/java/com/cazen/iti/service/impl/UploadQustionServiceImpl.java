package com.cazen.iti.service.impl;

import com.cazen.iti.config.Constants;
import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.repository.UpQuestionMasterRepository;
import com.cazen.iti.repository.UpRightAnswerRepository;
import com.cazen.iti.repository.UpWrongAnswerRepository;
import com.cazen.iti.repository.UploadQuestionRepository;
import com.cazen.iti.service.UploadQustionService;
import com.cazen.iti.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;

/**
 * Service Implementation for managing Upload Question.
 */
@Service
@Transactional
public class UploadQustionServiceImpl implements UploadQustionService {

    private final Logger log = LoggerFactory.getLogger(UploadQustionServiceImpl.class);

    @Inject
    private UpQuestionMasterRepository upQuestionMasterRepository;

    @Inject
    private UpRightAnswerRepository upRightAnswerRepository;

    @Inject
    private UpWrongAnswerRepository upWrongAnswerRepository;

    @Inject
    private UploadQuestionRepository uploadQuestionRepository;

    @Inject
    private UserService userService;

    /**
     * Save a upQuestionMaster.
     *
     * @param upQuestionMaster the entity to save
     * @return the persisted entity
     */
    public UpQuestionMaster save(UpQuestionMaster upQuestionMaster) {

        //Set Upload Master Properties
        upQuestionMaster.setUser(userService.getUserWithAuthorities());
        upQuestionMaster.setcTime(ZonedDateTime.now());
        upQuestionMaster.setDelYn("N");
        upQuestionMaster.setStatus(uploadQuestionRepository.findByCd_Id(Constants.QSTN_STAT_WAIT).getCdId());

        upQuestionMaster.getUpRightAnswers().forEach(t -> {
            t.setDelYn("N");
        });
        log.debug("Request to change UpRightAnswer : {}", upQuestionMaster.getUpRightAnswers());

        upQuestionMaster.getUpWrongAnswers().forEach(t -> {
            t.setDelYn("N");
        });
        log.debug("Request to change UpWrongAnswer : {}", upQuestionMaster.getUpWrongAnswers());

        log.debug("Request to save UpQuestionMaster : {}", upQuestionMaster);
        UpQuestionMaster result = upQuestionMasterRepository.save(upQuestionMaster);


        return result;
    }

    /**
     * Get all the upQuestionMasters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)

    public Page<UpQuestionMaster> findAll(Pageable pageable) {
        log.debug("Request to get all UpQuestionMasters");
        Page<UpQuestionMaster> result = upQuestionMasterRepository.findByUserIsCurrentUser(pageable);
        return result;
    }

    /**
     * Get one upQuestionMaster by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UpQuestionMaster findOne(Long id) {
        log.debug("Request to get UpQuestionMaster : {}", id);
        UpQuestionMaster upQuestionMaster = upQuestionMasterRepository.findOne(id);
        return upQuestionMaster;
    }

    /**
     * Update Y to delYn column instead of delete it.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UpQuestionMaster : {}", id);
        UpQuestionMaster upQuestionMaster = upQuestionMasterRepository.findOne(id);
        upQuestionMaster.setDelYn("Y");
        upQuestionMasterRepository.save(upQuestionMaster);
    }
}
