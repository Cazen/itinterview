package com.cazen.iti.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * A QuestionMaster.
 */
public class QuestionMasterForUser {

    private static final long serialVersionUID = 1L;

    List<QuestionMaster> questionMasterList;

    LocalDate startTime;

    public List<QuestionMaster> getQuestionMasterList() {
        return questionMasterList;
    }

    public void setQuestionMasterList(List<QuestionMaster> questionMasterList) {
        this.questionMasterList = questionMasterList;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionMasterForUser that = (QuestionMasterForUser) o;

        if (questionMasterList != null ? !questionMasterList.equals(that.questionMasterList) : that.questionMasterList != null)
            return false;
        return startTime != null ? startTime.equals(that.startTime) : that.startTime == null;

    }

    @Override
    public int hashCode() {
        int result = questionMasterList != null ? questionMasterList.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
    }
}
