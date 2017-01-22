package com.cazen.iti.domain;

import java.time.ZonedDateTime;

/**
 * A QuestionMaster.
 */
public class SubmitTryQuestionForUser {

    private static final long serialVersionUID = 1L;

    private String generatedId;

    private ZonedDateTime startTime;

    private String questionOne;

    private String questionTwo;

    private String questionThree;

    private String questionFour;

    private String questionFive;

    private String questionSix;

    private String questionSeven;

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

    public String getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(String questionOne) {
        this.questionOne = questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(String questionTwo) {
        this.questionTwo = questionTwo;
    }

    public String getQuestionThree() {
        return questionThree;
    }

    public void setQuestionThree(String questionThree) {
        this.questionThree = questionThree;
    }

    public String getQuestionFour() {
        return questionFour;
    }

    public void setQuestionFour(String questionFour) {
        this.questionFour = questionFour;
    }

    public String getQuestionFive() {
        return questionFive;
    }

    public void setQuestionFive(String questionFive) {
        this.questionFive = questionFive;
    }

    public String getQuestionSix() {
        return questionSix;
    }

    public void setQuestionSix(String questionSix) {
        this.questionSix = questionSix;
    }

    public String getQuestionSeven() {
        return questionSeven;
    }

    public void setQuestionSeven(String questionSeven) {
        this.questionSeven = questionSeven;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubmitTryQuestionForUser that = (SubmitTryQuestionForUser) o;

        if (generatedId != null ? !generatedId.equals(that.generatedId) : that.generatedId != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (questionOne != null ? !questionOne.equals(that.questionOne) : that.questionOne != null) return false;
        if (questionTwo != null ? !questionTwo.equals(that.questionTwo) : that.questionTwo != null) return false;
        if (questionThree != null ? !questionThree.equals(that.questionThree) : that.questionThree != null)
            return false;
        if (questionFour != null ? !questionFour.equals(that.questionFour) : that.questionFour != null) return false;
        if (questionFive != null ? !questionFive.equals(that.questionFive) : that.questionFive != null) return false;
        if (questionSix != null ? !questionSix.equals(that.questionSix) : that.questionSix != null) return false;
        if (questionSeven != null ? !questionSeven.equals(that.questionSeven) : that.questionSeven != null)
            return false;
        if (answerOne != null ? !answerOne.equals(that.answerOne) : that.answerOne != null) return false;
        if (answerTwo != null ? !answerTwo.equals(that.answerTwo) : that.answerTwo != null) return false;
        if (answerThree != null ? !answerThree.equals(that.answerThree) : that.answerThree != null) return false;
        if (answerFour != null ? !answerFour.equals(that.answerFour) : that.answerFour != null) return false;
        if (answerFive != null ? !answerFive.equals(that.answerFive) : that.answerFive != null) return false;
        if (answerSix != null ? !answerSix.equals(that.answerSix) : that.answerSix != null) return false;
        return answerSeven != null ? answerSeven.equals(that.answerSeven) : that.answerSeven == null;

    }

    @Override
    public int hashCode() {
        int result = generatedId != null ? generatedId.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (questionOne != null ? questionOne.hashCode() : 0);
        result = 31 * result + (questionTwo != null ? questionTwo.hashCode() : 0);
        result = 31 * result + (questionThree != null ? questionThree.hashCode() : 0);
        result = 31 * result + (questionFour != null ? questionFour.hashCode() : 0);
        result = 31 * result + (questionFive != null ? questionFive.hashCode() : 0);
        result = 31 * result + (questionSix != null ? questionSix.hashCode() : 0);
        result = 31 * result + (questionSeven != null ? questionSeven.hashCode() : 0);
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
