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
 * A QuestionMaster.
 */
@Entity
@Table(name = "question_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionMaster implements Serializable {

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

    @OneToMany(mappedBy = "questionMaster")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RightAnswer> rightAnswers = new HashSet<>();

    @OneToMany(mappedBy = "questionMaster")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WrongAnswer> wrongAnswers = new HashSet<>();

    @ManyToOne
    private CommonCode status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public QuestionMaster title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDelYn() {
        return delYn;
    }

    public QuestionMaster delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public ZonedDateTime getcTime() {
        return cTime;
    }

    public QuestionMaster cTime(ZonedDateTime cTime) {
        this.cTime = cTime;
        return this;
    }

    public void setcTime(ZonedDateTime cTime) {
        this.cTime = cTime;
    }

    public String getAuthor() {
        return author;
    }

    public QuestionMaster author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<RightAnswer> getRightAnswers() {
        return rightAnswers;
    }

    public QuestionMaster rightAnswers(Set<RightAnswer> rightAnswers) {
        this.rightAnswers = rightAnswers;
        return this;
    }

    public QuestionMaster addRightAnswer(RightAnswer rightAnswer) {
        rightAnswers.add(rightAnswer);
        rightAnswer.setQuestionMaster(this);
        return this;
    }

    public QuestionMaster removeRightAnswer(RightAnswer rightAnswer) {
        rightAnswers.remove(rightAnswer);
        rightAnswer.setQuestionMaster(null);
        return this;
    }

    public void setRightAnswers(Set<RightAnswer> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public Set<WrongAnswer> getWrongAnswers() {
        return wrongAnswers;
    }

    public QuestionMaster wrongAnswers(Set<WrongAnswer> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
        return this;
    }

    public QuestionMaster addWrongAnswer(WrongAnswer wrongAnswer) {
        wrongAnswers.add(wrongAnswer);
        wrongAnswer.setQuestionMaster(this);
        return this;
    }

    public QuestionMaster removeWrongAnswer(WrongAnswer wrongAnswer) {
        wrongAnswers.remove(wrongAnswer);
        wrongAnswer.setQuestionMaster(null);
        return this;
    }

    public void setWrongAnswers(Set<WrongAnswer> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public CommonCode getStatus() {
        return status;
    }

    public QuestionMaster status(CommonCode commonCode) {
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
        QuestionMaster questionMaster = (QuestionMaster) o;
        if(questionMaster.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionMaster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionMaster{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", delYn='" + delYn + "'" +
            ", cTime='" + cTime + "'" +
            ", author='" + author + "'" +
            '}';
    }
}
