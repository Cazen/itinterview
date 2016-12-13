package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.RightAnswer;
import com.cazen.iti.repository.RightAnswerRepository;
import com.cazen.iti.service.RightAnswerService;

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
 * Test class for the RightAnswerResource REST controller.
 *
 * @see RightAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class RightAnswerResourceIntTest {

    private static final String DEFAULT_OPTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    @Inject
    private RightAnswerRepository rightAnswerRepository;

    @Inject
    private RightAnswerService rightAnswerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRightAnswerMockMvc;

    private RightAnswer rightAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RightAnswerResource rightAnswerResource = new RightAnswerResource();
        ReflectionTestUtils.setField(rightAnswerResource, "rightAnswerService", rightAnswerService);
        this.restRightAnswerMockMvc = MockMvcBuilders.standaloneSetup(rightAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RightAnswer createEntity(EntityManager em) {
        RightAnswer rightAnswer = new RightAnswer()
                .optionText(DEFAULT_OPTION_TEXT)
                .delYn(DEFAULT_DEL_YN);
        return rightAnswer;
    }

    @Before
    public void initTest() {
        rightAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createRightAnswer() throws Exception {
        int databaseSizeBeforeCreate = rightAnswerRepository.findAll().size();

        // Create the RightAnswer

        restRightAnswerMockMvc.perform(post("/api/right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rightAnswer)))
            .andExpect(status().isCreated());

        // Validate the RightAnswer in the database
        List<RightAnswer> rightAnswerList = rightAnswerRepository.findAll();
        assertThat(rightAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        RightAnswer testRightAnswer = rightAnswerList.get(rightAnswerList.size() - 1);
        assertThat(testRightAnswer.getOptionText()).isEqualTo(DEFAULT_OPTION_TEXT);
        assertThat(testRightAnswer.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
    }

    @Test
    @Transactional
    public void createRightAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rightAnswerRepository.findAll().size();

        // Create the RightAnswer with an existing ID
        RightAnswer existingRightAnswer = new RightAnswer();
        existingRightAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRightAnswerMockMvc.perform(post("/api/right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRightAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RightAnswer> rightAnswerList = rightAnswerRepository.findAll();
        assertThat(rightAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRightAnswers() throws Exception {
        // Initialize the database
        rightAnswerRepository.saveAndFlush(rightAnswer);

        // Get all the rightAnswerList
        restRightAnswerMockMvc.perform(get("/api/right-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rightAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionText").value(hasItem(DEFAULT_OPTION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())));
    }

    @Test
    @Transactional
    public void getRightAnswer() throws Exception {
        // Initialize the database
        rightAnswerRepository.saveAndFlush(rightAnswer);

        // Get the rightAnswer
        restRightAnswerMockMvc.perform(get("/api/right-answers/{id}", rightAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rightAnswer.getId().intValue()))
            .andExpect(jsonPath("$.optionText").value(DEFAULT_OPTION_TEXT.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRightAnswer() throws Exception {
        // Get the rightAnswer
        restRightAnswerMockMvc.perform(get("/api/right-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRightAnswer() throws Exception {
        // Initialize the database
        rightAnswerService.save(rightAnswer);

        int databaseSizeBeforeUpdate = rightAnswerRepository.findAll().size();

        // Update the rightAnswer
        RightAnswer updatedRightAnswer = rightAnswerRepository.findOne(rightAnswer.getId());
        updatedRightAnswer
                .optionText(UPDATED_OPTION_TEXT)
                .delYn(UPDATED_DEL_YN);

        restRightAnswerMockMvc.perform(put("/api/right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRightAnswer)))
            .andExpect(status().isOk());

        // Validate the RightAnswer in the database
        List<RightAnswer> rightAnswerList = rightAnswerRepository.findAll();
        assertThat(rightAnswerList).hasSize(databaseSizeBeforeUpdate);
        RightAnswer testRightAnswer = rightAnswerList.get(rightAnswerList.size() - 1);
        assertThat(testRightAnswer.getOptionText()).isEqualTo(UPDATED_OPTION_TEXT);
        assertThat(testRightAnswer.getDelYn()).isEqualTo(UPDATED_DEL_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingRightAnswer() throws Exception {
        int databaseSizeBeforeUpdate = rightAnswerRepository.findAll().size();

        // Create the RightAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRightAnswerMockMvc.perform(put("/api/right-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rightAnswer)))
            .andExpect(status().isCreated());

        // Validate the RightAnswer in the database
        List<RightAnswer> rightAnswerList = rightAnswerRepository.findAll();
        assertThat(rightAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRightAnswer() throws Exception {
        // Initialize the database
        rightAnswerService.save(rightAnswer);

        int databaseSizeBeforeDelete = rightAnswerRepository.findAll().size();

        // Get the rightAnswer
        restRightAnswerMockMvc.perform(delete("/api/right-answers/{id}", rightAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RightAnswer> rightAnswerList = rightAnswerRepository.findAll();
        assertThat(rightAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
