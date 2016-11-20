package com.cazen.iti.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A QuestionVote.
 */
@Entity
@Table(name = "question_vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "c_time")
    private ZonedDateTime cTime;

    @ManyToOne
    private QuestionMaster questionMaster;

    @ManyToOne
    private User voter;

    @ManyToOne
    private CommonCode status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getcTime() {
        return cTime;
    }

    public QuestionVote cTime(ZonedDateTime cTime) {
        this.cTime = cTime;
        return this;
    }

    public void setcTime(ZonedDateTime cTime) {
        this.cTime = cTime;
    }

    public QuestionMaster getQuestionMaster() {
        return questionMaster;
    }

    public QuestionVote questionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
        return this;
    }

    public void setQuestionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
    }

    public User getVoter() {
        return voter;
    }

    public QuestionVote voter(User user) {
        this.voter = user;
        return this;
    }

    public void setVoter(User user) {
        this.voter = user;
    }

    public CommonCode getStatus() {
        return status;
    }

    public QuestionVote status(CommonCode commonCode) {
        this.status = commonCode;
        return this;
    }

    public void setStatus(CommonCode commonCode) {
        this.status = commonCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionVote questionVote = (QuestionVote) o;
        if(questionVote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionVote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionVote{" +
            "id=" + id +
            ", cTime='" + cTime + "'" +
            '}';
    }
}
