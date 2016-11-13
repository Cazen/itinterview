package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.QuestionMaster;
import com.cazen.iti.repository.QuestionMasterRepository;
import com.cazen.iti.service.QuestionMasterService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAA";
    private static final String UPDATED_DEL_YN = "BBBBB";

    private static final ZonedDateTime DEFAULT_C_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_C_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_C_TIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_C_TIME);

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

    @PostConstruct
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
                .cTime(DEFAULT_C_TIME);
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
        List<QuestionMaster> questionMasters = questionMasterRepository.findAll();
        assertThat(questionMasters).hasSize(databaseSizeBeforeCreate + 1);
        QuestionMaster testQuestionMaster = questionMasters.get(questionMasters.size() - 1);
        assertThat(testQuestionMaster.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testQuestionMaster.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
        assertThat(testQuestionMaster.getcTime()).isEqualTo(DEFAULT_C_TIME);
    }

    @Test
    @Transactional
    public void getAllQuestionMasters() throws Exception {
        // Initialize the database
        questionMasterRepository.saveAndFlush(questionMaster);

        // Get all the questionMasters
        restQuestionMasterMockMvc.perform(get("/api/question-masters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(questionMaster.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())))
                .andExpect(jsonPath("$.[*].cTime").value(hasItem(DEFAULT_C_TIME_STR)));
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
            .andExpect(jsonPath("$.cTime").value(DEFAULT_C_TIME_STR));
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
                .cTime(UPDATED_C_TIME);

        restQuestionMasterMockMvc.perform(put("/api/question-masters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedQuestionMaster)))
                .andExpect(status().isOk());

        // Validate the QuestionMaster in the database
        List<QuestionMaster> questionMasters = questionMasterRepository.findAll();
        assertThat(questionMasters).hasSize(databaseSizeBeforeUpdate);
        QuestionMaster testQuestionMaster = questionMasters.get(questionMasters.size() - 1);
        assertThat(testQuestionMaster.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testQuestionMaster.getDelYn()).isEqualTo(UPDATED_DEL_YN);
        assertThat(testQuestionMaster.getcTime()).isEqualTo(UPDATED_C_TIME);
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
        List<QuestionMaster> questionMasters = questionMasterRepository.findAll();
        assertThat(questionMasters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
