package com.cazen.iti.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WrongAnswer.
 */
@Entity
@Table(name = "wrong_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WrongAnswer implements Serializable {

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
    @JoinColumn(name = "questionMaster_Id")
    @JsonBackReference
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

    public WrongAnswer optionText(String optionText) {
        this.optionText = optionText;
        return this;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getDelYn() {
        return delYn;
    }

    public WrongAnswer delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public CommonCode getStatus() {
        return status;
    }

    public WrongAnswer status(CommonCode commonCode) {
        this.status = commonCode;
        return this;
    }

    public void setStatus(CommonCode commonCode) {
        this.status = commonCode;
    }

    public QuestionMaster getQuestionMaster() {
        return questionMaster;
    }

    public WrongAnswer questionMaster(QuestionMaster questionMaster) {
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
        WrongAnswer wrongAnswer = (WrongAnswer) o;
        if (wrongAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wrongAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WrongAnswer{" +
            "id=" + id +
            ", optionText='" + optionText + "'" +
            ", delYn='" + delYn + "'" +
            '}';
    }
}
