package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.UpRightAnswer;
import com.cazen.iti.repository.UpRightAnswerRepository;
import com.cazen.iti.service.UpRightAnswerService;

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
 * Test class for the UpRightAnswerResource REST controller.
 *
 * @see UpRightAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class UpRightAnswerResourceIntTest {

    private static final String DEFAULT_OPTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    @Inject
    private UpRightAnswerRepository upRightAnswerRepository;

    @Inject
    private UpRightAnswerService upRightAnswerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUpRightAnswerMockMvc;

    private UpRightAnswer upRightAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UpRightAnswerResource upRightAnswerResource = new UpRightAnswerResource();
        ReflectionTestUtils.setField(upRightAnswerResource, "upRightAnswerService", upRightAnswerService);
        this.restUpRightAnswerMockMvc = MockMvcBuilders.standaloneSetup(upRightAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpRightAnswer createEntity(EntityManager em) {
        UpRightAnswer upRightAnswer = new UpRightAnswer()
                .optionText(DEFAULT_OPTION_TEXT)
                .delYn(DEFAULT_DEL_YN);
        return upRightAnswer;
    }

    @Before
    public void initTest() {
        upRightAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpRightAnswer() throws Exception {
        int databaseSizeBeforeCreate = upRightAnswerRepository.findAll().size();

        // Create the UpRightAnswer

        restUpRightAnswerMockMvc.perform(post("/api/up-right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upRightAnswer)))
            .andExpect(status().isCreated());

        // Validate the UpRightAnswer in the database
        List<UpRightAnswer> upRightAnswerList = upRightAnswerRepository.findAll();
        assertThat(upRightAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        UpRightAnswer testUpRightAnswer = upRightAnswerList.get(upRightAnswerList.size() - 1);
        assertThat(testUpRightAnswer.getOptionText()).isEqualTo(DEFAULT_OPTION_TEXT);
        assertThat(testUpRightAnswer.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
    }

    @Test
    @Transactional
    public void createUpRightAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upRightAnswerRepository.findAll().size();

        // Create the UpRightAnswer with an existing ID
        UpRightAnswer existingUpRightAnswer = new UpRightAnswer();
        existingUpRightAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpRightAnswerMockMvc.perform(post("/api/up-right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUpRightAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UpRightAnswer> upRightAnswerList = upRightAnswerRepository.findAll();
        assertThat(upRightAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUpRightAnswers() throws Exception {
        // Initialize the database
        upRightAnswerRepository.saveAndFlush(upRightAnswer);

        // Get all the upRightAnswerList
        restUpRightAnswerMockMvc.perform(get("/api/up-right-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upRightAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionText").value(hasItem(DEFAULT_OPTION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())));
    }

    @Test
    @Transactional
    public void getUpRightAnswer() throws Exception {
        // Initialize the database
        upRightAnswerRepository.saveAndFlush(upRightAnswer);

        // Get the upRightAnswer
        restUpRightAnswerMockMvc.perform(get("/api/up-right-answers/{id}", upRightAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upRightAnswer.getId().intValue()))
            .andExpect(jsonPath("$.optionText").value(DEFAULT_OPTION_TEXT.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUpRightAnswer() throws Exception {
        // Get the upRightAnswer
        restUpRightAnswerMockMvc.perform(get("/api/up-right-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpRightAnswer() throws Exception {
        // Initialize the database
        upRightAnswerService.save(upRightAnswer);

        int databaseSizeBeforeUpdate = upRightAnswerRepository.findAll().size();

        // Update the upRightAnswer
        UpRightAnswer updatedUpRightAnswer = upRightAnswerRepository.findOne(upRightAnswer.getId());
        updatedUpRightAnswer
                .optionText(UPDATED_OPTION_TEXT)
                .delYn(UPDATED_DEL_YN);

        restUpRightAnswerMockMvc.perform(put("/api/up-right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUpRightAnswer)))
            .andExpect(status().isOk());

        // Validate the UpRightAnswer in the database
        List<UpRightAnswer> upRightAnswerList = upRightAnswerRepository.findAll();
        assertThat(upRightAnswerList).hasSize(databaseSizeBeforeUpdate);
        UpRightAnswer testUpRightAnswer = upRightAnswerList.get(upRightAnswerList.size() - 1);
        assertThat(testUpRightAnswer.getOptionText()).isEqualTo(UPDATED_OPTION_TEXT);
        assertThat(testUpRightAnswer.getDelYn()).isEqualTo(UPDATED_DEL_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingUpRightAnswer() throws Exception {
        int databaseSizeBeforeUpdate = upRightAnswerRepository.findAll().size();

        // Create the UpRightAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUpRightAnswerMockMvc.perform(put("/api/up-right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upRightAnswer)))
            .andExpect(status().isCreated());

        // Validate the UpRightAnswer in the database
        List<UpRightAnswer> upRightAnswerList = upRightAnswerRepository.findAll();
        assertThat(upRightAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUpRightAnswer() throws Exception {
        // Initialize the database
        upRightAnswerService.save(upRightAnswer);

        int databaseSizeBeforeDelete = upRightAnswerRepository.findAll().size();

        // Get the upRightAnswer
        restUpRightAnswerMockMvc.perform(delete("/api/up-right-answers/{id}", upRightAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UpRightAnswer> upRightAnswerList = upRightAnswerRepository.findAll();
        assertThat(upRightAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
