package com.cazen.iti.service.impl;

import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.repository.TryQuestionRepository;
import com.cazen.iti.service.TryQustionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Upload Question.
 */
@Service
@Transactional
public class TryQustionServiceImpl implements TryQustionService {

    private final Logger log = LoggerFactory.getLogger(TryQustionServiceImpl.class);

    @Inject
    private TryQuestionRepository trtQuestionRepository;

    /**
     *  Get the Category 123 CommonCode entity
     *
     *  @return the list of CommonCode
     */
    @Transactional(readOnly = true)
    public List<CommonCode> getCategory123CommonCodeList() {
        log.debug("Request to get category123CommonCode : {}");
        List<CommonCode> category123CommonCodeList = trtQuestionRepository.getCategory123CommonCodeList();
        return category123CommonCodeList;
    }
}
