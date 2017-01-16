package com.cazen.iti.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "questionMaster", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<RightAnswer> rightAnswers = new HashSet<>();

    @OneToMany(mappedBy = "questionMaster", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonManagedReference
    private Set<WrongAnswer> wrongAnswers = new HashSet<>();

    @ManyToOne
    private CommonCode category1;

    @ManyToOne
    private CommonCode category2;

    @ManyToOne
    private CommonCode category3;

    @ManyToOne
    private User author;

    @OneToOne
    @JoinColumn(unique = true)
    private QuestionMasterStatics questionMasterStatics;

    public String getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
    }
    @Transient
    @JsonSerialize
    private String generatedId;

    public Set<AnswersForUser> getAnswersForUsersSet() {
        return answersForUsersSet;
    }

    public void setAnswersForUsersSet(Set<AnswersForUser> answersForUsersSet) {
        this.answersForUsersSet = answersForUsersSet;
    }
    @Transient
    @JsonSerialize
    private Set<AnswersForUser> answersForUsersSet;

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

    public String getStatus() {
        return status;
    }

    public QuestionMaster status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public CommonCode getCategory1() {
        return category1;
    }

    public QuestionMaster category1(CommonCode commonCode) {
        this.category1 = commonCode;
        return this;
    }

    public void setCategory1(CommonCode commonCode) {
        this.category1 = commonCode;
    }

    public CommonCode getCategory2() {
        return category2;
    }

    public QuestionMaster category2(CommonCode commonCode) {
        this.category2 = commonCode;
        return this;
    }

    public void setCategory2(CommonCode commonCode) {
        this.category2 = commonCode;
    }

    public CommonCode getCategory3() {
        return category3;
    }

    public QuestionMaster category3(CommonCode commonCode) {
        this.category3 = commonCode;
        return this;
    }

    public void setCategory3(CommonCode commonCode) {
        this.category3 = commonCode;
    }

    public User getAuthor() {
        return author;
    }

    public QuestionMaster author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public QuestionMasterStatics getQuestionMasterStatics() {
        return questionMasterStatics;
    }

    public QuestionMaster questionMasterStatics(QuestionMasterStatics questionMasterStatics) {
        this.questionMasterStatics = questionMasterStatics;
        return this;
    }

    public void setQuestionMasterStatics(QuestionMasterStatics questionMasterStatics) {
        this.questionMasterStatics = questionMasterStatics;
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
        if (questionMaster.id == null || id == null) {
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
            ", status='" + status + "'" +
            '}';
    }
}
