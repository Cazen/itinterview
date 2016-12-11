package com.cazen.iti.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UpRightAnswer.
 */
@Entity
@Table(name = "up_right_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class UpRightAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "option_text")
    private String optionText;

    @Column(name = "del_yn")
    private String delYn;

    @ManyToOne
    @JoinColumn(name = "upQuestionMaster_Id")
    @JsonBackReference
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

    public UpRightAnswer optionText(String optionText) {
        this.optionText = optionText;
        return this;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getDelYn() {
        return delYn;
    }

    public UpRightAnswer delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public UpQuestionMaster getUpQuestionMaster() {
        return upQuestionMaster;
    }

    public UpRightAnswer upQuestionMaster(UpQuestionMaster upQuestionMaster) {
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
        UpRightAnswer upRightAnswer = (UpRightAnswer) o;
        if(upRightAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, upRightAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UpRightAnswer{" +
            "id=" + id +
            ", optionText='" + optionText + "'" +
            ", delYn='" + delYn + "'" +
            '}';
    }
}
