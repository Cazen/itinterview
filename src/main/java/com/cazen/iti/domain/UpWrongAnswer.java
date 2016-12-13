package com.cazen.iti.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UpWrongAnswer.
 */
@Entity
@Table(name = "up_wrong_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UpWrongAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "del_yn")
    private String delYn;

    @ManyToOne
    private UpQuestionMaster upQuestionMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public UpWrongAnswer optionText(String optionText) {
        this.optionText = optionText;
        return this;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getDelYn() {
        return delYn;
    }

    public UpWrongAnswer delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public UpQuestionMaster getUpQuestionMaster() {
        return upQuestionMaster;
    }

    public UpWrongAnswer upQuestionMaster(UpQuestionMaster upQuestionMaster) {
        this.upQuestionMaster = upQuestionMaster;
        return this;
    }

    public void setUpQuestionMaster(UpQuestionMaster upQuestionMaster) {
        this.upQuestionMaster = upQuestionMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpWrongAnswer upWrongAnswer = (UpWrongAnswer) o;
        if (upWrongAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, upWrongAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UpWrongAnswer{" +
            "id=" + id +
            ", optionText='" + optionText + "'" +
            ", delYn='" + delYn + "'" +
            '}';
    }
}
