package com.cazen.iti.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cazen.iti.domain.UserStatics;

import com.cazen.iti.repository.UserStaticsRepository;
import com.cazen.iti.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserStatics.
 */
@RestController
@RequestMapping("/api")
public class UserStaticsResource {

    private final Logger log = LoggerFactory.getLogger(UserStaticsResource.class);
        
    @Inject
    private UserStaticsRepository userStaticsRepository;

    /**
     * POST  /user-statics : Create a new userStatics.
     *
     * @param userStatics the userStatics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userStatics, or with status 400 (Bad Request) if the userStatics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-statics")
    @Timed
    public ResponseEntity<UserStatics> createUserStatics(@RequestBody UserStatics userStatics) throws URISyntaxException {
        log.debug("REST request to save UserStatics : {}", userStatics);
        if (userStatics.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userStatics", "idexists", "A new userStatics cannot already have an ID")).body(null);
        }
        UserStatics result = userStaticsRepository.save(userStatics);
        return ResponseEntity.created(new URI("/api/user-statics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userStatics", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-statics : Updates an existing userStatics.
     *
     * @param userStatics the userStatics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userStatics,
     * or with status 400 (Bad Request) if the userStatics is not valid,
     * or with status 500 (Internal Server Error) if the userStatics couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-statics")
    @Timed
    public ResponseEntity<UserStatics> updateUserStatics(@RequestBody UserStatics userStatics) throws URISyntaxException {
        log.debug("REST request to update UserStatics : {}", userStatics);
        if (userStatics.getId() == null) {
            return createUserStatics(userStatics);
        }
        UserStatics result = userStaticsRepository.save(userStatics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userStatics", userStatics.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-statics : get all the userStatics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userStatics in body
     */
    @GetMapping("/user-statics")
    @Timed
    public List<UserStatics> getAllUserStatics() {
        log.debug("REST request to get all UserStatics");
        List<UserStatics> userStatics = userStaticsRepository.findAll();
        return userStatics;
    }

    /**
     * GET  /user-statics/:id : get the "id" userStatics.
     *
     * @param id the id of the userStatics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userStatics, or with status 404 (Not Found)
     */
    @GetMapping("/user-statics/{id}")
    @Timed
    public ResponseEntity<UserStatics> getUserStatics(@PathVariable Long id) {
        log.debug("REST request to get UserStatics : {}", id);
        UserStatics userStatics = userStaticsRepository.findOne(id);
        return Optional.ofNullable(userStatics)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-statics/:id : delete the "id" userStatics.
     *
     * @param id the id of the userStatics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-statics/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserStatics(@PathVariable Long id) {
        log.debug("REST request to delete UserStatics : {}", id);
        userStaticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userStatics", id.toString())).build();
    }

}
