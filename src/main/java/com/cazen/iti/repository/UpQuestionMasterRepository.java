package com.cazen.iti.repository;

import com.cazen.iti.domain.UpQuestionMaster;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UpQuestionMaster entity.
 */
@SuppressWarnings("unused")
public interface UpQuestionMasterRepository extends JpaRepository<UpQuestionMaster,Long> {

    @Query("select upQuestionMaster from UpQuestionMaster upQuestionMaster where upQuestionMaster.author.login = ?#{principal.username}")
    List<UpQuestionMaster> findByAuthorIsCurrentUser();

}
