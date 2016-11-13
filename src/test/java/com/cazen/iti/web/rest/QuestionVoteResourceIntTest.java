package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.QuestionVote;
import com.cazen.iti.repository.QuestionVoteRepository;
import com.cazen.iti.service.QuestionVoteService;

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
 * Test class for the QuestionVoteResource REST controller.
 *
 * @see QuestionVoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class QuestionVoteResourceIntTest {

    private static final ZonedDateTime DEFAULT_C_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_C_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_C_TIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_C_TIME);

    @Inject
    private QuestionVoteRepository questionVoteRepository;

    @Inject
    private QuestionVoteService questionVoteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuestionVoteMockMvc;

    private QuestionVote questionVote;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionVoteResource questionVoteResource = new QuestionVoteResource();
        ReflectionTestUtils.setField(questionVoteResource, "questionVoteService", questionVoteService);
        this.restQuestionVoteMockMvc = MockMvcBuilders.standaloneSetup(questionVoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionVote createEntity(EntityManager em) {
        QuestionVote questionVote = new QuestionVote()
                .cTime(DEFAULT_C_TIME);
        return questionVote;
    }

    @Before
    public void initTest() {
        questionVote = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionVote() throws Exception {
        int databaseSizeBeforeCreate = questionVoteRepository.findAll().size();

        // Create the QuestionVote

        restQuestionVoteMockMvc.perform(post("/api/question-votes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(questionVote)))
                .andExpect(status().isCreated());

        // Validate the QuestionVote in the database
        List<QuestionVote> questionVotes = questionVoteRepository.findAll();
        assertThat(questionVotes).hasSize(databaseSizeBeforeCreate + 1);
        QuestionVote testQuestionVote = questionVotes.get(questionVotes.size() - 1);
        assertThat(testQuestionVote.getcTime()).isEqualTo(DEFAULT_C_TIME);
    }

    @Test
    @Transactional
    public void getAllQuestionVotes() throws Exception {
        // Initialize the database
        questionVoteRepository.saveAndFlush(questionVote);

        // Get all the questionVotes
        restQuestionVoteMockMvc.perform(get("/api/question-votes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(questionVote.getId().intValue())))
                .andExpect(jsonPath("$.[*].cTime").value(hasItem(DEFAULT_C_TIME_STR)));
    }

    @Test
    @Transactional
    public void getQuestionVote() throws Exception {
        // Initialize the database
        questionVoteRepository.saveAndFlush(questionVote);

        // Get the questionVote
        restQuestionVoteMockMvc.perform(get("/api/question-votes/{id}", questionVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionVote.getId().intValue()))
            .andExpect(jsonPath("$.cTime").value(DEFAULT_C_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionVote() throws Exception {
        // Get the questionVote
        restQuestionVoteMockMvc.perform(get("/api/question-votes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionVote() throws Exception {
        // Initialize the database
        questionVoteService.save(questionVote);

        int databaseSizeBeforeUpdate = questionVoteRepository.findAll().size();

        // Update the questionVote
        QuestionVote updatedQuestionVote = questionVoteRepository.findOne(questionVote.getId());
        updatedQuestionVote
                .cTime(UPDATED_C_TIME);

        restQuestionVoteMockMvc.perform(put("/api/question-votes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedQuestionVote)))
                .andExpect(status().isOk());

        // Validate the QuestionVote in the database
        List<QuestionVote> questionVotes = questionVoteRepository.findAll();
        assertThat(questionVotes).hasSize(databaseSizeBeforeUpdate);
        QuestionVote testQuestionVote = questionVotes.get(questionVotes.size() - 1);
        assertThat(testQuestionVote.getcTime()).isEqualTo(UPDATED_C_TIME);
    }

    @Test
    @Transactional
    public void deleteQuestionVote() throws Exception {
        // Initialize the database
        questionVoteService.save(questionVote);

        int databaseSizeBeforeDelete = questionVoteRepository.findAll().size();

        // Get the questionVote
        restQuestionVoteMockMvc.perform(delete("/api/question-votes/{id}", questionVote.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionVote> questionVotes = questionVoteRepository.findAll();
        assertThat(questionVotes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
