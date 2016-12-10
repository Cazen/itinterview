package com.cazen.iti.repository;

import com.cazen.iti.domain.UpQuestionVote;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UpQuestionVote entity.
 */
@SuppressWarnings("unused")
public interface UpQuestionVoteRepository extends JpaRepository<UpQuestionVote,Long> {

    @Query("select upQuestionVote from UpQuestionVote upQuestionVote where upQuestionVote.voter.login = ?#{principal.username}")
    List<UpQuestionVote> findByVoterIsCurrentUser();

}
