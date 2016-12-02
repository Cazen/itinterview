package com.cazen.iti.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UpQuestionMaster.
 */
@Entity
@Table(name = "up_question_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UpQuestionMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "del_yn")
    private String delYn;

    @Column(name = "c_time")
    private ZonedDateTime cTime;

    @Column(name = "author")
    private String author;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "upquestionMaster")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UpRightAnswer> uprightAnswers = new HashSet<>();

    @OneToMany(mappedBy = "upquestionMaster")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UpWrongAnswer> upwrongAnswers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public UpQuestionMaster title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDelYn() {
        return delYn;
    }

    public UpQuestionMaster delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public ZonedDateTime getcTime() {
        return cTime;
    }

    public UpQuestionMaster cTime(ZonedDateTime cTime) {
        this.cTime = cTime;
        return this;
    }

    public void setcTime(ZonedDateTime cTime) {
        this.cTime = cTime;
    }

    public String getAuthor() {
        return author;
    }

    public UpQuestionMaster author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public UpQuestionMaster status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<UpRightAnswer> getUprightAnswers() {
        return uprightAnswers;
    }

    public UpQuestionMaster uprightAnswers(Set<UpRightAnswer> upRightAnswers) {
        this.uprightAnswers = upRightAnswers;
        return this;
    }

    public UpQuestionMaster addUprightAnswer(UpRightAnswer upRightAnswer) {
        uprightAnswers.add(upRightAnswer);
        upRightAnswer.setUpquestionMaster(this);
        return this;
    }

    public UpQuestionMaster removeUprightAnswer(UpRightAnswer upRightAnswer) {
        uprightAnswers.remove(upRightAnswer);
        upRightAnswer.setUpquestionMaster(null);
        return this;
    }

    public void setUprightAnswers(Set<UpRightAnswer> upRightAnswers) {
        this.uprightAnswers = upRightAnswers;
    }

    public Set<UpWrongAnswer> getUpwrongAnswers() {
        return upwrongAnswers;
    }

    public UpQuestionMaster upwrongAnswers(Set<UpWrongAnswer> upWrongAnswers) {
        this.upwrongAnswers = upWrongAnswers;
        return this;
    }

    public UpQuestionMaster addUpwrongAnswer(UpWrongAnswer upWrongAnswer) {
        upwrongAnswers.add(upWrongAnswer);
        upWrongAnswer.setUpquestionMaster(this);
        return this;
    }

    public UpQuestionMaster removeUpwrongAnswer(UpWrongAnswer upWrongAnswer) {
        upwrongAnswers.remove(upWrongAnswer);
        upWrongAnswer.setUpquestionMaster(null);
        return this;
    }

    public void setUpwrongAnswers(Set<UpWrongAnswer> upWrongAnswers) {
        this.upwrongAnswers = upWrongAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpQuestionMaster upQuestionMaster = (UpQuestionMaster) o;
        if(upQuestionMaster.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, upQuestionMaster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UpQuestionMaster{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", delYn='" + delYn + "'" +
            ", cTime='" + cTime + "'" +
            ", author='" + author + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
