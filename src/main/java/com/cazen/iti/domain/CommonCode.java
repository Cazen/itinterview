package com.cazen.iti.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CommonCode.
 */
@Entity
@Table(name = "common_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@commonCodeId")
public class CommonCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cd_tp")
    private String cdTp;

    @Column(name = "cd_id")
    private String cdId;

    @Column(name = "cd_nm")
    private String cdNm;

    @Column(name = "del_yn")
    private String delYn;

    @ManyToOne
    private CommonCode parent;

    public long getHardCodedParentId() {
        return hardCodedParentId;
    }

    public void setHardCodedParentId(long hardCodedParentId) {
        this.hardCodedParentId = hardCodedParentId;
    }

    public long getHardCodedParentParentId() {
        return hardCodedParentParentId;
    }

    public void setHardCodedParentParentId(long hardCodedParentParentId) {
        this.hardCodedParentParentId = hardCodedParentParentId;
    }

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private long hardCodedParentId;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private long hardCodedParentParentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCdTp() {
        return cdTp;
    }

    public CommonCode cdTp(String cdTp) {
        this.cdTp = cdTp;
        return this;
    }

    public void setCdTp(String cdTp) {
        this.cdTp = cdTp;
    }

    public String getCdId() {
        return cdId;
    }

    public CommonCode cdId(String cdId) {
        this.cdId = cdId;
        return this;
    }

    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    public String getCdNm() {
        return cdNm;
    }

    public CommonCode cdNm(String cdNm) {
        this.cdNm = cdNm;
        return this;
    }

    public void setCdNm(String cdNm) {
        this.cdNm = cdNm;
    }

    public String getDelYn() {
        return delYn;
    }

    public CommonCode delYn(String delYn) {
        this.delYn = delYn;
        return this;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public CommonCode getParent() {
        return parent;
    }

    public CommonCode parent(CommonCode commonCode) {
        this.parent = commonCode;
        return this;
    }

    public void setParent(CommonCode commonCode) {
        this.parent = commonCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonCode commonCode = (CommonCode) o;
        if (commonCode.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commonCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CommonCode{" +
            "id=" + id +
            ", cdTp='" + cdTp + "'" +
            ", cdId='" + cdId + "'" +
            ", cdNm='" + cdNm + "'" +
            ", delYn='" + delYn + "'" +
            '}';
    }
}
