package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.UserStatics;
import com.cazen.iti.repository.UserStaticsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserStaticsResource REST controller.
 *
 * @see UserStaticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class UserStaticsResourceIntTest {

    private static final Integer DEFAULT_ELO_RATING = 1;
    private static final Integer UPDATED_ELO_RATING = 2;

    @Inject
    private UserStaticsRepository userStaticsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserStaticsMockMvc;

    private UserStatics userStatics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserStaticsResource userStaticsResource = new UserStaticsResource();
        ReflectionTestUtils.setField(userStaticsResource, "userStaticsRepository", userStaticsRepository);
        this.restUserStaticsMockMvc = MockMvcBuilders.standaloneSetup(userStaticsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserStatics createEntity(EntityManager em) {
        UserStatics userStatics = new UserStatics()
                .eloRating(DEFAULT_ELO_RATING);
        return userStatics;
    }

    @Before
    public void initTest() {
        userStatics = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserStatics() throws Exception {
        int databaseSizeBeforeCreate = userStaticsRepository.findAll().size();

        // Create the UserStatics

        restUserStaticsMockMvc.perform(post("/api/user-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatics)))
            .andExpect(status().isCreated());

        // Validate the UserStatics in the database
        List<UserStatics> userStaticsList = userStaticsRepository.findAll();
        assertThat(userStaticsList).hasSize(databaseSizeBeforeCreate + 1);
        UserStatics testUserStatics = userStaticsList.get(userStaticsList.size() - 1);
        assertThat(testUserStatics.getEloRating()).isEqualTo(DEFAULT_ELO_RATING);
    }

    @Test
    @Transactional
    public void createUserStaticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStaticsRepository.findAll().size();

        // Create the UserStatics with an existing ID
        UserStatics existingUserStatics = new UserStatics();
        existingUserStatics.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStaticsMockMvc.perform(post("/api/user-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserStatics)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserStatics> userStaticsList = userStaticsRepository.findAll();
        assertThat(userStaticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserStatics() throws Exception {
        // Initialize the database
        userStaticsRepository.saveAndFlush(userStatics);

        // Get all the userStaticsList
        restUserStaticsMockMvc.perform(get("/api/user-statics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userStatics.getId().intValue())))
            .andExpect(jsonPath("$.[*].eloRating").value(hasItem(DEFAULT_ELO_RATING)));
    }

    @Test
    @Transactional
    public void getUserStatics() throws Exception {
        // Initialize the database
        userStaticsRepository.saveAndFlush(userStatics);

        // Get the userStatics
        restUserStaticsMockMvc.perform(get("/api/user-statics/{id}", userStatics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userStatics.getId().intValue()))
            .andExpect(jsonPath("$.eloRating").value(DEFAULT_ELO_RATING));
    }

    @Test
    @Transactional
    public void getNonExistingUserStatics() throws Exception {
        // Get the userStatics
        restUserStaticsMockMvc.perform(get("/api/user-statics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStatics() throws Exception {
        // Initialize the database
        userStaticsRepository.saveAndFlush(userStatics);
        int databaseSizeBeforeUpdate = userStaticsRepository.findAll().size();

        // Update the userStatics
        UserStatics updatedUserStatics = userStaticsRepository.findOne(userStatics.getId());
        updatedUserStatics
                .eloRating(UPDATED_ELO_RATING);

        restUserStaticsMockMvc.perform(put("/api/user-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserStatics)))
            .andExpect(status().isOk());

        // Validate the UserStatics in the database
        List<UserStatics> userStaticsList = userStaticsRepository.findAll();
        assertThat(userStaticsList).hasSize(databaseSizeBeforeUpdate);
        UserStatics testUserStatics = userStaticsList.get(userStaticsList.size() - 1);
        assertThat(testUserStatics.getEloRating()).isEqualTo(UPDATED_ELO_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingUserStatics() throws Exception {
        int databaseSizeBeforeUpdate = userStaticsRepository.findAll().size();

        // Create the UserStatics

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserStaticsMockMvc.perform(put("/api/user-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStatics)))
            .andExpect(status().isCreated());

        // Validate the UserStatics in the database
        List<UserStatics> userStaticsList = userStaticsRepository.findAll();
        assertThat(userStaticsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserStatics() throws Exception {
        // Initialize the database
        userStaticsRepository.saveAndFlush(userStatics);
        int databaseSizeBeforeDelete = userStaticsRepository.findAll().size();

        // Get the userStatics
        restUserStaticsMockMvc.perform(delete("/api/user-statics/{id}", userStatics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserStatics> userStaticsList = userStaticsRepository.findAll();
        assertThat(userStaticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
