package com.cazen.iti.repository;

import com.cazen.iti.domain.UserStatics;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserStatics entity.
 */
@SuppressWarnings("unused")
public interface UserStaticsRepository extends JpaRepository<UserStatics,Long> {

    @Query("select userStatics from UserStatics userStatics where userStatics.user.login = ?#{principal.username}")
    List<UserStatics> findByUserIsCurrentUser();

}
