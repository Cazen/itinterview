package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.QuestionMaster;
import com.cazen.iti.repository.QuestionMasterRepository;
import com.cazen.iti.service.QuestionMasterService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.cazen.iti.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuestionMasterResource REST controller.
 *
 * @see QuestionMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class QuestionMasterResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_C_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_C_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    @Inject
    private QuestionMasterRepository questionMasterRepository;

    @Inject
    private QuestionMasterService questionMasterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuestionMasterMockMvc;

    private QuestionMaster questionMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionMasterResource questionMasterResource = new QuestionMasterResource();
        ReflectionTestUtils.setField(questionMasterResource, "questionMasterService", questionMasterService);
        this.restQuestionMasterMockMvc = MockMvcBuilders.standaloneSetup(questionMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionMaster createEntity(EntityManager em) {
        QuestionMaster questionMaster = new QuestionMaster()
                .title(DEFAULT_TITLE)
                .delYn(DEFAULT_DEL_YN)
                .cTime(DEFAULT_C_TIME)
                .author(DEFAULT_AUTHOR);
        return questionMaster;
    }

    @Before
    public void initTest() {
        questionMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionMaster() throws Exception {
        int databaseSizeBeforeCreate = questionMasterRepository.findAll().size();

        // Create the QuestionMaster

        restQuestionMasterMockMvc.perform(post("/api/question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionMaster)))
            .andExpect(status().isCreated());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionMaster testQuestionMaster = questionMasterList.get(questionMasterList.size() - 1);
        assertThat(testQuestionMaster.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testQuestionMaster.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
        assertThat(testQuestionMaster.getcTime()).isEqualTo(DEFAULT_C_TIME);
        assertThat(testQuestionMaster.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    @Transactional
    public void createQuestionMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionMasterRepository.findAll().size();

        // Create the QuestionMaster with an existing ID
        QuestionMaster existingQuestionMaster = new QuestionMaster();
        existingQuestionMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMasterMockMvc.perform(post("/api/question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestionMaster)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionMasters() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasterList
        restQuestionMasterMockMvc.perform(get("/api/question-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())))
            .andExpect(jsonPath("$.[*].cTime").value(hasItem(sameInstant(DEFAULT_C_TIME))))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
    }

    @Test
    @Transactional
    public void getQuestionMaster() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get the questionMaster
        restQuestionMasterMockMvc.perform(get("/api/question-masters/{id}", questionMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionMaster.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()))
            .andExpect(jsonPath("$.cTime").value(sameInstant(DEFAULT_C_TIME)))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionMaster() throws Exception {
        // Get the questionMaster
        restQuestionMasterMockMvc.perform(get("/api/question-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionMaster() throws Exception {
        // Initialize the database
        questionMasterService.save(questionMaster);

        int databaseSizeBeforeUpdate = questionMasterRepository.findAll().size();

        // Update the questionMaster
        QuestionMaster updatedQuestionMaster = questionMasterRepository.findOne(questionMaster.getId());
        updatedQuestionMaster
                .title(UPDATED_TITLE)
                .delYn(UPDATED_DEL_YN)
                .cTime(UPDATED_C_TIME)
                .author(UPDATED_AUTHOR);

        restQuestionMasterMockMvc.perform(put("/api/question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionMaster)))
            .andExpect(status().isOk());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeUpdate);
        QuestionMaster testQuestionMaster = questionMasterList.get(questionMasterList.size() - 1);
        assertThat(testQuestionMaster.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQuestionMaster.getDelYn()).isEqualTo(UPDATED_DEL_YN);
        assertThat(testQuestionMaster.getcTime()).isEqualTo(UPDATED_C_TIME);
        assertThat(testQuestionMaster.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionMaster() throws Exception {
        int databaseSizeBeforeUpdate = questionMasterRepository.findAll().size();

        // Create the QuestionMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionMasterMockMvc.perform(put("/api/question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionMaster)))
            .andExpect(status().isCreated());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionMaster() throws Exception {
        // Initialize the database
        questionMasterService.save(questionMaster);

        int databaseSizeBeforeDelete = questionMasterRepository.findAll().size();

        // Get the questionMaster
        restQuestionMasterMockMvc.perform(delete("/api/question-masters/{id}", questionMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionMaster> questionMasterList = questionMasterRepository.findAll();
        assertThat(questionMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
