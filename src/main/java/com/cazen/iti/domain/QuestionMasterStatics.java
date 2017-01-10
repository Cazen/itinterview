package com.cazen.iti.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QuestionMasterStatics.
 */
@Entity
@Table(name = "question_master_statics")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionMasterStatics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "right_count")
    private Integer rightCount;

    @Column(name = "wrong_count")
    private Integer wrongCount;

    @Column(name = "up_vote_count")
    private Integer upVoteCount;

    @Column(name = "down_vote_count")
    private Integer downVoteCount;

    @OneToOne(mappedBy = "questionMasterStatics")
    @JsonIgnore
    private QuestionMaster questionMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRightCount() {
        return rightCount;
    }

    public QuestionMasterStatics rightCount(Integer rightCount) {
        this.rightCount = rightCount;
        return this;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    public Integer getWrongCount() {
        return wrongCount;
    }

    public QuestionMasterStatics wrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
        return this;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public Integer getUpVoteCount() {
        return upVoteCount;
    }

    public QuestionMasterStatics upVoteCount(Integer upVoteCount) {
        this.upVoteCount = upVoteCount;
        return this;
    }

    public void setUpVoteCount(Integer upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public Integer getDownVoteCount() {
        return downVoteCount;
    }

    public QuestionMasterStatics downVoteCount(Integer downVoteCount) {
        this.downVoteCount = downVoteCount;
        return this;
    }

    public void setDownVoteCount(Integer downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public QuestionMaster getQuestionMaster() {
        return questionMaster;
    }

    public QuestionMasterStatics questionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
        return this;
    }

    public void setQuestionMaster(QuestionMaster questionMaster) {
        this.questionMaster = questionMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionMasterStatics questionMasterStatics = (QuestionMasterStatics) o;
        if (questionMasterStatics.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionMasterStatics.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionMasterStatics{" +
            "id=" + id +
            ", rightCount='" + rightCount + "'" +
            ", wrongCount='" + wrongCount + "'" +
            ", upVoteCount='" + upVoteCount + "'" +
            ", downVoteCount='" + downVoteCount + "'" +
            '}';
    }
}
