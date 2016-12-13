package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.WrongAnswer;
import com.cazen.iti.repository.WrongAnswerRepository;
import com.cazen.iti.service.WrongAnswerService;

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
 * Test class for the WrongAnswerResource REST controller.
 *
 * @see WrongAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class WrongAnswerResourceIntTest {

    private static final String DEFAULT_OPTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    @Inject
    private WrongAnswerRepository wrongAnswerRepository;

    @Inject
    private WrongAnswerService wrongAnswerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWrongAnswerMockMvc;

    private WrongAnswer wrongAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WrongAnswerResource wrongAnswerResource = new WrongAnswerResource();
        ReflectionTestUtils.setField(wrongAnswerResource, "wrongAnswerService", wrongAnswerService);
        this.restWrongAnswerMockMvc = MockMvcBuilders.standaloneSetup(wrongAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WrongAnswer createEntity(EntityManager em) {
        WrongAnswer wrongAnswer = new WrongAnswer()
                .optionText(DEFAULT_OPTION_TEXT)
                .delYn(DEFAULT_DEL_YN);
        return wrongAnswer;
    }

    @Before
    public void initTest() {
        wrongAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createWrongAnswer() throws Exception {
        int databaseSizeBeforeCreate = wrongAnswerRepository.findAll().size();

        // Create the WrongAnswer

        restWrongAnswerMockMvc.perform(post("/api/wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrongAnswer)))
            .andExpect(status().isCreated());

        // Validate the WrongAnswer in the database
        List<WrongAnswer> wrongAnswerList = wrongAnswerRepository.findAll();
        assertThat(wrongAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        WrongAnswer testWrongAnswer = wrongAnswerList.get(wrongAnswerList.size() - 1);
        assertThat(testWrongAnswer.getOptionText()).isEqualTo(DEFAULT_OPTION_TEXT);
        assertThat(testWrongAnswer.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
    }

    @Test
    @Transactional
    public void createWrongAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wrongAnswerRepository.findAll().size();

        // Create the WrongAnswer with an existing ID
        WrongAnswer existingWrongAnswer = new WrongAnswer();
        existingWrongAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWrongAnswerMockMvc.perform(post("/api/wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWrongAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WrongAnswer> wrongAnswerList = wrongAnswerRepository.findAll();
        assertThat(wrongAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWrongAnswers() throws Exception {
        // Initialize the database
        wrongAnswerRepository.saveAndFlush(wrongAnswer);

        // Get all the wrongAnswerList
        restWrongAnswerMockMvc.perform(get("/api/wrong-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wrongAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionText").value(hasItem(DEFAULT_OPTION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())));
    }

    @Test
    @Transactional
    public void getWrongAnswer() throws Exception {
        // Initialize the database
        wrongAnswerRepository.saveAndFlush(wrongAnswer);

        // Get the wrongAnswer
        restWrongAnswerMockMvc.perform(get("/api/wrong-answers/{id}", wrongAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wrongAnswer.getId().intValue()))
            .andExpect(jsonPath("$.optionText").value(DEFAULT_OPTION_TEXT.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWrongAnswer() throws Exception {
        // Get the wrongAnswer
        restWrongAnswerMockMvc.perform(get("/api/wrong-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWrongAnswer() throws Exception {
        // Initialize the database
        wrongAnswerService.save(wrongAnswer);

        int databaseSizeBeforeUpdate = wrongAnswerRepository.findAll().size();

        // Update the wrongAnswer
        WrongAnswer updatedWrongAnswer = wrongAnswerRepository.findOne(wrongAnswer.getId());
        updatedWrongAnswer
                .optionText(UPDATED_OPTION_TEXT)
                .delYn(UPDATED_DEL_YN);

        restWrongAnswerMockMvc.perform(put("/api/wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWrongAnswer)))
            .andExpect(status().isOk());

        // Validate the WrongAnswer in the database
        List<WrongAnswer> wrongAnswerList = wrongAnswerRepository.findAll();
        assertThat(wrongAnswerList).hasSize(databaseSizeBeforeUpdate);
        WrongAnswer testWrongAnswer = wrongAnswerList.get(wrongAnswerList.size() - 1);
        assertThat(testWrongAnswer.getOptionText()).isEqualTo(UPDATED_OPTION_TEXT);
        assertThat(testWrongAnswer.getDelYn()).isEqualTo(UPDATED_DEL_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingWrongAnswer() throws Exception {
        int databaseSizeBeforeUpdate = wrongAnswerRepository.findAll().size();

        // Create the WrongAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWrongAnswerMockMvc.perform(put("/api/wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrongAnswer)))
            .andExpect(status().isCreated());

        // Validate the WrongAnswer in the database
        List<WrongAnswer> wrongAnswerList = wrongAnswerRepository.findAll();
        assertThat(wrongAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWrongAnswer() throws Exception {
        // Initialize the database
        wrongAnswerService.save(wrongAnswer);

        int databaseSizeBeforeDelete = wrongAnswerRepository.findAll().size();

        // Get the wrongAnswer
        restWrongAnswerMockMvc.perform(delete("/api/wrong-answers/{id}", wrongAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WrongAnswer> wrongAnswerList = wrongAnswerRepository.findAll();
        assertThat(wrongAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
