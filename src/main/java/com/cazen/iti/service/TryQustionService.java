package com.cazen.iti.service;

import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.domain.QuestionMaster;

import java.util.List;

/**
 * Service Interface for managing UpQuestionMaster.
 */
public interface TryQustionService {

    /**
     *  Get the Category 123 CommonCode entity
     *
     *  @return the list of CommonCode
     */
    List<CommonCode> getCategory123CommonCodeList();

    /**
     *  Get the 7 random Question List
     *
     *  @return the list of QuestionMaster
     */
    List<QuestionMaster> getQuestionMasterList7Randomly(long id);
}
