package com.cazen.iti.domain;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * A QuestionMaster.
 */
public class SubmitTryQuestionForUser {

    private static final long serialVersionUID = 1L;

    private String generatedId;

    private ZonedDateTime startTime;

    private String answerOne;

    private String answerTwo;

    private String answerThree;

    private String answerFour;

    private String answerFive;

    private String answerSix;

    private String answerSeven;

    public String getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public String getAnswerFive() {
        return answerFive;
    }

    public void setAnswerFive(String answerFive) {
        this.answerFive = answerFive;
    }

    public String getAnswerSix() {
        return answerSix;
    }

    public void setAnswerSix(String answerSix) {
        this.answerSix = answerSix;
    }

    public String getAnswerSeven() {
        return answerSeven;
    }

    public void setAnswerSeven(String answerSeven) {
        this.answerSeven = answerSeven;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SubmitTryQuestionForUser that = (SubmitTryQuestionForUser) o;

        if (generatedId != null ? !generatedId.equals(that.generatedId) : that.generatedId != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
            return false;
        if (answerOne != null ? !answerOne.equals(that.answerOne) : that.answerOne != null)
            return false;
        if (answerTwo != null ? !answerTwo.equals(that.answerTwo) : that.answerTwo != null)
            return false;
        if (answerThree != null ? !answerThree.equals(that.answerThree) : that.answerThree != null)
            return false;
        if (answerFour != null ? !answerFour.equals(that.answerFour) : that.answerFour != null)
            return false;
        if (answerFive != null ? !answerFive.equals(that.answerFive) : that.answerFive != null)
            return false;
        if (answerSix != null ? !answerSix.equals(that.answerSix) : that.answerSix != null)
            return false;
        return answerSeven != null ? answerSeven.equals(that.answerSeven) : that.answerSeven == null;

    }

    @Override
    public int hashCode() {
        int result = generatedId != null ? generatedId.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (answerOne != null ? answerOne.hashCode() : 0);
        result = 31 * result + (answerTwo != null ? answerTwo.hashCode() : 0);
        result = 31 * result + (answerThree != null ? answerThree.hashCode() : 0);
        result = 31 * result + (answerFour != null ? answerFour.hashCode() : 0);
        result = 31 * result + (answerFive != null ? answerFive.hashCode() : 0);
        result = 31 * result + (answerSix != null ? answerSix.hashCode() : 0);
        result = 31 * result + (answerSeven != null ? answerSeven.hashCode() : 0);
        return result;
    }
}