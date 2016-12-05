package com.cazen.iti.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UpQuestionVote.
 */
@Entity
@Table(name = "up_question_vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UpQuestionVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "c_time")
    private ZonedDateTime cTime;

    @ManyToOne
    private UpQuestionMaster upquestionMaster;

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

    public UpQuestionVote cTime(ZonedDateTime cTime) {
        this.cTime = cTime;
        return this;
    }

    public void setcTime(ZonedDateTime cTime) {
        this.cTime = cTime;
    }

    public UpQuestionMaster getUpquestionMaster() {
        return upquestionMaster;
    }

    public UpQuestionVote upquestionMaster(UpQuestionMaster upQuestionMaster) {
        this.upquestionMaster = upQuestionMaster;
        return this;
    }

    public void setUpquestionMaster(UpQuestionMaster upQuestionMaster) {
        this.upquestionMaster = upQuestionMaster;
    }

    public User getVoter() {
        return voter;
    }

    public UpQuestionVote voter(User user) {
        this.voter = user;
        return this;
    }

    public void setVoter(User user) {
        this.voter = user;
    }

    public CommonCode getStatus() {
        return status;
    }

    public UpQuestionVote status(CommonCode commonCode) {
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
        UpQuestionVote upQuestionVote = (UpQuestionVote) o;
        if(upQuestionVote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, upQuestionVote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UpQuestionVote{" +
            "id=" + id +
            ", cTime='" + cTime + "'" +
            '}';
    }
}
