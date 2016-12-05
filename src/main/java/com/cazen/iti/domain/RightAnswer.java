package com.cazen.iti.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RightAnswer.
 */
@Entity
@Table(name = "right_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RightAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "del_yn")
    private String delYn;

    @ManyToOne
    private CommonCode status;

    @ManyToOne
    private QuestionMaster questionMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public RightAnswer optionText(String optionText) {
        this.optionText = optionText;
        return this;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getDelYn() {
        return delYn;
    }

    public RightAnswer delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public CommonCode getStatus() {
        return status;
    }

    public RightAnswer status(CommonCode commonCode) {
        this.status = commonCode;
        return this;
    }

    public void setStatus(CommonCode commonCode) {
        this.status = commonCode;
    }

    public QuestionMaster getQuestionMaster() {
        return questionMaster;
    }

    public RightAnswer questionMaster(QuestionMaster questionMaster) {
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
        RightAnswer rightAnswer = (RightAnswer) o;
        if(rightAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rightAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RightAnswer{" +
            "id=" + id +
            ", optionText='" + optionText + "'" +
            ", delYn='" + delYn + "'" +
            '}';
    }
}
