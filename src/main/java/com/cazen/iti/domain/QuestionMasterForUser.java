package com.cazen.iti.domain;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * A QuestionMaster.
 */
public class QuestionMasterForUser {

    private static final long serialVersionUID = 1L;

    private List<QuestionMaster> questionMasterList;

    private ZonedDateTime startTime;

    private String generatedId;

    private String rightWrong;

    private int erningPoint;

    public int getErningPoint() {
        return erningPoint;
    }

    public void setErningPoint(int erningPoint) {
        this.erningPoint = erningPoint;
    }

    public String getRightWrong() {
        return rightWrong;
    }

    public void setRightWrong(String rightWrong) {
        this.rightWrong = rightWrong;
    }

    public List<QuestionMaster> getQuestionMasterList() {
        return questionMasterList;
    }

    public void setQuestionMasterList(List<QuestionMaster> questionMasterList) {
        this.questionMasterList = questionMasterList;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public String getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
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
