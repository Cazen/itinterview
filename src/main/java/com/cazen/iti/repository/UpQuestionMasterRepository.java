package com.cazen.iti.repository;

import com.cazen.iti.domain.UpQuestionMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the UpQuestionMaster entity.
 */
@SuppressWarnings("unused")
public interface UpQuestionMasterRepository extends JpaRepository<UpQuestionMaster,Long> {

    @Query("select upQuestionMaster from UpQuestionMaster upQuestionMaster where del_yn = 'N' and upQuestionMaster.user.login = ?#{principal.username}")
    Page<UpQuestionMaster> findByUserIsCurrentUser(Pageable pageable);
}
