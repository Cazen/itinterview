package com.cazen.iti.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserStatics.
 */
@Entity
@Table(name = "user_statics")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserStatics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "elo_rating")
    private Integer eloRating;

    @ManyToOne
    private User user;

    @ManyToOne
    private CommonCode category3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEloRating() {
        return eloRating;
    }

    public UserStatics eloRating(Integer eloRating) {
        this.eloRating = eloRating;
        return this;
    }

    public void setEloRating(Integer eloRating) {
        this.eloRating = eloRating;
    }

    public User getUser() {
        return user;
    }

    public UserStatics user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommonCode getCategory3() {
        return category3;
    }

    public UserStatics category3(CommonCode commonCode) {
        this.category3 = commonCode;
        return this;
    }

    public void setCategory3(CommonCode commonCode) {
        this.category3 = commonCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStatics userStatics = (UserStatics) o;
        if (userStatics.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userStatics.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserStatics{" +
            "id=" + id +
            ", eloRating='" + eloRating + "'" +
            '}';
    }
}
