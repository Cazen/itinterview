package com.cazen.iti.repository;

import com.cazen.iti.domain.QuestionVote;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionVote entity.
 */
@SuppressWarnings("unused")
public interface QuestionVoteRepository extends JpaRepository<QuestionVote,Long> {

    @Query("select questionVote from QuestionVote questionVote where questionVote.voter.login = ?#{principal.username}")
    List<QuestionVote> findByVoterIsCurrentUser();

}
