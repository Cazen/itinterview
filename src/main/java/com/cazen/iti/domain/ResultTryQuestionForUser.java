package com.cazen.iti.domain;

import java.util.List;

/**
 * A QuestionMaster.
 */
public class ResultTryQuestionForUser {

    private static final long serialVersionUID = 1L;

    private List<QuestionMaster> questionMasterList;

    public List<QuestionMaster> getQuestionMasterList() {
        return questionMasterList;
    }

    public void setQuestionMasterList(List<QuestionMaster> questionMasterList) {
        this.questionMasterList = questionMasterList;
    }
}
