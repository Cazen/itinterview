package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.QuestionMasterStatics;
import com.cazen.iti.repository.QuestionMasterStaticsRepository;

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
 * Test class for the QuestionMasterStaticsResource REST controller.
 *
 * @see QuestionMasterStaticsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class QuestionMasterStaticsResourceIntTest {

    private static final Integer DEFAULT_RIGHT_COUNT = 1;
    private static final Integer UPDATED_RIGHT_COUNT = 2;

    private static final Integer DEFAULT_WRONG_COUNT = 1;
    private static final Integer UPDATED_WRONG_COUNT = 2;

    private static final Integer DEFAULT_UP_VOTE_COUNT = 1;
    private static final Integer UPDATED_UP_VOTE_COUNT = 2;

    private static final Integer DEFAULT_DOWN_VOTE_COUNT = 1;
    private static final Integer UPDATED_DOWN_VOTE_COUNT = 2;

    @Inject
    private QuestionMasterStaticsRepository questionMasterStaticsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuestionMasterStaticsMockMvc;

    private QuestionMasterStatics questionMasterStatics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionMasterStaticsResource questionMasterStaticsResource = new QuestionMasterStaticsResource();
        ReflectionTestUtils.setField(questionMasterStaticsResource, "questionMasterStaticsRepository", questionMasterStaticsRepository);
        this.restQuestionMasterStaticsMockMvc = MockMvcBuilders.standaloneSetup(questionMasterStaticsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionMasterStatics createEntity(EntityManager em) {
        QuestionMasterStatics questionMasterStatics = new QuestionMasterStatics()
                .rightCount(DEFAULT_RIGHT_COUNT)
                .wrongCount(DEFAULT_WRONG_COUNT)
                .upVoteCount(DEFAULT_UP_VOTE_COUNT)
                .downVoteCount(DEFAULT_DOWN_VOTE_COUNT);
        return questionMasterStatics;
    }

    @Before
    public void initTest() {
        questionMasterStatics = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionMasterStatics() throws Exception {
        int databaseSizeBeforeCreate = questionMasterStaticsRepository.findAll().size();

        // Create the QuestionMasterStatics

        restQuestionMasterStaticsMockMvc.perform(post("/api/question-master-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionMasterStatics)))
            .andExpect(status().isCreated());

        // Validate the QuestionMasterStatics in the database
        List<QuestionMasterStatics> questionMasterStaticsList = questionMasterStaticsRepository.findAll();
        assertThat(questionMasterStaticsList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionMasterStatics testQuestionMasterStatics = questionMasterStaticsList.get(questionMasterStaticsList.size() - 1);
        assertThat(testQuestionMasterStatics.getRightCount()).isEqualTo(DEFAULT_RIGHT_COUNT);
        assertThat(testQuestionMasterStatics.getWrongCount()).isEqualTo(DEFAULT_WRONG_COUNT);
        assertThat(testQuestionMasterStatics.getUpVoteCount()).isEqualTo(DEFAULT_UP_VOTE_COUNT);
        assertThat(testQuestionMasterStatics.getDownVoteCount()).isEqualTo(DEFAULT_DOWN_VOTE_COUNT);
    }

    @Test
    @Transactional
    public void createQuestionMasterStaticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionMasterStaticsRepository.findAll().size();

        // Create the QuestionMasterStatics with an existing ID
        QuestionMasterStatics existingQuestionMasterStatics = new QuestionMasterStatics();
        existingQuestionMasterStatics.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMasterStaticsMockMvc.perform(post("/api/question-master-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestionMasterStatics)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionMasterStatics> questionMasterStaticsList = questionMasterStaticsRepository.findAll();
        assertThat(questionMasterStaticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionMasterStatics() throws Exception {
        // Initialize the database
        questionMasterStaticsRepository.saveAndFlush(questionMasterStatics);

        // Get all the questionMasterStaticsList
        restQuestionMasterStaticsMockMvc.perform(get("/api/question-master-statics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionMasterStatics.getId().intValue())))
            .andExpect(jsonPath("$.[*].rightCount").value(hasItem(DEFAULT_RIGHT_COUNT)))
            .andExpect(jsonPath("$.[*].wrongCount").value(hasItem(DEFAULT_WRONG_COUNT)))
            .andExpect(jsonPath("$.[*].upVoteCount").value(hasItem(DEFAULT_UP_VOTE_COUNT)))
            .andExpect(jsonPath("$.[*].downVoteCount").value(hasItem(DEFAULT_DOWN_VOTE_COUNT)));
    }

    @Test
    @Transactional
    public void getQuestionMasterStatics() throws Exception {
        // Initialize the database
        questionMasterStaticsRepository.saveAndFlush(questionMasterStatics);

        // Get the questionMasterStatics
        restQuestionMasterStaticsMockMvc.perform(get("/api/question-master-statics/{id}", questionMasterStatics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionMasterStatics.getId().intValue()))
            .andExpect(jsonPath("$.rightCount").value(DEFAULT_RIGHT_COUNT))
            .andExpect(jsonPath("$.wrongCount").value(DEFAULT_WRONG_COUNT))
            .andExpect(jsonPath("$.upVoteCount").value(DEFAULT_UP_VOTE_COUNT))
            .andExpect(jsonPath("$.downVoteCount").value(DEFAULT_DOWN_VOTE_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionMasterStatics() throws Exception {
        // Get the questionMasterStatics
        restQuestionMasterStaticsMockMvc.perform(get("/api/question-master-statics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionMasterStatics() throws Exception {
        // Initialize the database
        questionMasterStaticsRepository.saveAndFlush(questionMasterStatics);
        int databaseSizeBeforeUpdate = questionMasterStaticsRepository.findAll().size();

        // Update the questionMasterStatics
        QuestionMasterStatics updatedQuestionMasterStatics = questionMasterStaticsRepository.findOne(questionMasterStatics.getId());
        updatedQuestionMasterStatics
                .rightCount(UPDATED_RIGHT_COUNT)
                .wrongCount(UPDATED_WRONG_COUNT)
                .upVoteCount(UPDATED_UP_VOTE_COUNT)
                .downVoteCount(UPDATED_DOWN_VOTE_COUNT);

        restQuestionMasterStaticsMockMvc.perform(put("/api/question-master-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionMasterStatics)))
            .andExpect(status().isOk());

        // Validate the QuestionMasterStatics in the database
        List<QuestionMasterStatics> questionMasterStaticsList = questionMasterStaticsRepository.findAll();
        assertThat(questionMasterStaticsList).hasSize(databaseSizeBeforeUpdate);
        QuestionMasterStatics testQuestionMasterStatics = questionMasterStaticsList.get(questionMasterStaticsList.size() - 1);
        assertThat(testQuestionMasterStatics.getRightCount()).isEqualTo(UPDATED_RIGHT_COUNT);
        assertThat(testQuestionMasterStatics.getWrongCount()).isEqualTo(UPDATED_WRONG_COUNT);
        assertThat(testQuestionMasterStatics.getUpVoteCount()).isEqualTo(UPDATED_UP_VOTE_COUNT);
        assertThat(testQuestionMasterStatics.getDownVoteCount()).isEqualTo(UPDATED_DOWN_VOTE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionMasterStatics() throws Exception {
        int databaseSizeBeforeUpdate = questionMasterStaticsRepository.findAll().size();

        // Create the QuestionMasterStatics

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionMasterStaticsMockMvc.perform(put("/api/question-master-statics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionMasterStatics)))
            .andExpect(status().isCreated());

        // Validate the QuestionMasterStatics in the database
        List<QuestionMasterStatics> questionMasterStaticsList = questionMasterStaticsRepository.findAll();
        assertThat(questionMasterStaticsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionMasterStatics() throws Exception {
        // Initialize the database
        questionMasterStaticsRepository.saveAndFlush(questionMasterStatics);
        int databaseSizeBeforeDelete = questionMasterStaticsRepository.findAll().size();

        // Get the questionMasterStatics
        restQuestionMasterStaticsMockMvc.perform(delete("/api/question-master-statics/{id}", questionMasterStatics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionMasterStatics> questionMasterStaticsList = questionMasterStaticsRepository.findAll();
        assertThat(questionMasterStaticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
