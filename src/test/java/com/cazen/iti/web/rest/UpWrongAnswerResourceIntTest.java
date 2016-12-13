package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.UpWrongAnswer;
import com.cazen.iti.repository.UpWrongAnswerRepository;
import com.cazen.iti.service.UpWrongAnswerService;

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
 * Test class for the UpWrongAnswerResource REST controller.
 *
 * @see UpWrongAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class UpWrongAnswerResourceIntTest {

    private static final String DEFAULT_OPTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    @Inject
    private UpWrongAnswerRepository upWrongAnswerRepository;

    @Inject
    private UpWrongAnswerService upWrongAnswerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUpWrongAnswerMockMvc;

    private UpWrongAnswer upWrongAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UpWrongAnswerResource upWrongAnswerResource = new UpWrongAnswerResource();
        ReflectionTestUtils.setField(upWrongAnswerResource, "upWrongAnswerService", upWrongAnswerService);
        this.restUpWrongAnswerMockMvc = MockMvcBuilders.standaloneSetup(upWrongAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpWrongAnswer createEntity(EntityManager em) {
        UpWrongAnswer upWrongAnswer = new UpWrongAnswer()
                .optionText(DEFAULT_OPTION_TEXT)
                .delYn(DEFAULT_DEL_YN);
        return upWrongAnswer;
    }

    @Before
    public void initTest() {
        upWrongAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpWrongAnswer() throws Exception {
        int databaseSizeBeforeCreate = upWrongAnswerRepository.findAll().size();

        // Create the UpWrongAnswer

        restUpWrongAnswerMockMvc.perform(post("/api/up-wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upWrongAnswer)))
            .andExpect(status().isCreated());

        // Validate the UpWrongAnswer in the database
        List<UpWrongAnswer> upWrongAnswerList = upWrongAnswerRepository.findAll();
        assertThat(upWrongAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        UpWrongAnswer testUpWrongAnswer = upWrongAnswerList.get(upWrongAnswerList.size() - 1);
        assertThat(testUpWrongAnswer.getOptionText()).isEqualTo(DEFAULT_OPTION_TEXT);
        assertThat(testUpWrongAnswer.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
    }

    @Test
    @Transactional
    public void createUpWrongAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upWrongAnswerRepository.findAll().size();

        // Create the UpWrongAnswer with an existing ID
        UpWrongAnswer existingUpWrongAnswer = new UpWrongAnswer();
        existingUpWrongAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpWrongAnswerMockMvc.perform(post("/api/up-wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUpWrongAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UpWrongAnswer> upWrongAnswerList = upWrongAnswerRepository.findAll();
        assertThat(upWrongAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUpWrongAnswers() throws Exception {
        // Initialize the database
        upWrongAnswerRepository.saveAndFlush(upWrongAnswer);

        // Get all the upWrongAnswerList
        restUpWrongAnswerMockMvc.perform(get("/api/up-wrong-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upWrongAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionText").value(hasItem(DEFAULT_OPTION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())));
    }

    @Test
    @Transactional
    public void getUpWrongAnswer() throws Exception {
        // Initialize the database
        upWrongAnswerRepository.saveAndFlush(upWrongAnswer);

        // Get the upWrongAnswer
        restUpWrongAnswerMockMvc.perform(get("/api/up-wrong-answers/{id}", upWrongAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upWrongAnswer.getId().intValue()))
            .andExpect(jsonPath("$.optionText").value(DEFAULT_OPTION_TEXT.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUpWrongAnswer() throws Exception {
        // Get the upWrongAnswer
        restUpWrongAnswerMockMvc.perform(get("/api/up-wrong-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpWrongAnswer() throws Exception {
        // Initialize the database
        upWrongAnswerService.save(upWrongAnswer);

        int databaseSizeBeforeUpdate = upWrongAnswerRepository.findAll().size();

        // Update the upWrongAnswer
        UpWrongAnswer updatedUpWrongAnswer = upWrongAnswerRepository.findOne(upWrongAnswer.getId());
        updatedUpWrongAnswer
                .optionText(UPDATED_OPTION_TEXT)
                .delYn(UPDATED_DEL_YN);

        restUpWrongAnswerMockMvc.perform(put("/api/up-wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUpWrongAnswer)))
            .andExpect(status().isOk());

        // Validate the UpWrongAnswer in the database
        List<UpWrongAnswer> upWrongAnswerList = upWrongAnswerRepository.findAll();
        assertThat(upWrongAnswerList).hasSize(databaseSizeBeforeUpdate);
        UpWrongAnswer testUpWrongAnswer = upWrongAnswerList.get(upWrongAnswerList.size() - 1);
        assertThat(testUpWrongAnswer.getOptionText()).isEqualTo(UPDATED_OPTION_TEXT);
        assertThat(testUpWrongAnswer.getDelYn()).isEqualTo(UPDATED_DEL_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingUpWrongAnswer() throws Exception {
        int databaseSizeBeforeUpdate = upWrongAnswerRepository.findAll().size();

        // Create the UpWrongAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUpWrongAnswerMockMvc.perform(put("/api/up-wrong-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upWrongAnswer)))
            .andExpect(status().isCreated());

        // Validate the UpWrongAnswer in the database
        List<UpWrongAnswer> upWrongAnswerList = upWrongAnswerRepository.findAll();
        assertThat(upWrongAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUpWrongAnswer() throws Exception {
        // Initialize the database
        upWrongAnswerService.save(upWrongAnswer);

        int databaseSizeBeforeDelete = upWrongAnswerRepository.findAll().size();

        // Get the upWrongAnswer
        restUpWrongAnswerMockMvc.perform(delete("/api/up-wrong-answers/{id}", upWrongAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UpWrongAnswer> upWrongAnswerList = upWrongAnswerRepository.findAll();
        assertThat(upWrongAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
