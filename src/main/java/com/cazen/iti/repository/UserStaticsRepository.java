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

    @Query(value = "select * from User_Statics where CATEGORY3_ID = ?1", nativeQuery = true)
    UserStatics findByCategory3Id(long id);

}
