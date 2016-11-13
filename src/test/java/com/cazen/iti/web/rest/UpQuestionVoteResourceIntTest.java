package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.UpQuestionVote;
import com.cazen.iti.repository.UpQuestionVoteRepository;
import com.cazen.iti.service.UpQuestionVoteService;

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
 * Test class for the UpQuestionVoteResource REST controller.
 *
 * @see UpQuestionVoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class UpQuestionVoteResourceIntTest {

    private static final ZonedDateTime DEFAULT_C_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_C_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_C_TIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_C_TIME);

    @Inject
    private UpQuestionVoteRepository upQuestionVoteRepository;

    @Inject
    private UpQuestionVoteService upQuestionVoteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUpQuestionVoteMockMvc;

    private UpQuestionVote upQuestionVote;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UpQuestionVoteResource upQuestionVoteResource = new UpQuestionVoteResource();
        ReflectionTestUtils.setField(upQuestionVoteResource, "upQuestionVoteService", upQuestionVoteService);
        this.restUpQuestionVoteMockMvc = MockMvcBuilders.standaloneSetup(upQuestionVoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpQuestionVote createEntity(EntityManager em) {
        UpQuestionVote upQuestionVote = new UpQuestionVote()
                .cTime(DEFAULT_C_TIME);
        return upQuestionVote;
    }

    @Before
    public void initTest() {
        upQuestionVote = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpQuestionVote() throws Exception {
        int databaseSizeBeforeCreate = upQuestionVoteRepository.findAll().size();

        // Create the UpQuestionVote

        restUpQuestionVoteMockMvc.perform(post("/api/up-question-votes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(upQuestionVote)))
                .andExpect(status().isCreated());

        // Validate the UpQuestionVote in the database
        List<UpQuestionVote> upQuestionVotes = upQuestionVoteRepository.findAll();
        assertThat(upQuestionVotes).hasSize(databaseSizeBeforeCreate + 1);
        UpQuestionVote testUpQuestionVote = upQuestionVotes.get(upQuestionVotes.size() - 1);
        assertThat(testUpQuestionVote.getcTime()).isEqualTo(DEFAULT_C_TIME);
    }

    @Test
    @Transactional
    public void getAllUpQuestionVotes() throws Exception {
        // Initialize the database
        upQuestionVoteRepository.saveAndFlush(upQuestionVote);

        // Get all the upQuestionVotes
        restUpQuestionVoteMockMvc.perform(get("/api/up-question-votes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(upQuestionVote.getId().intValue())))
                .andExpect(jsonPath("$.[*].cTime").value(hasItem(DEFAULT_C_TIME_STR)));
    }

    @Test
    @Transactional
    public void getUpQuestionVote() throws Exception {
        // Initialize the database
        upQuestionVoteRepository.saveAndFlush(upQuestionVote);

        // Get the upQuestionVote
        restUpQuestionVoteMockMvc.perform(get("/api/up-question-votes/{id}", upQuestionVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upQuestionVote.getId().intValue()))
            .andExpect(jsonPath("$.cTime").value(DEFAULT_C_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingUpQuestionVote() throws Exception {
        // Get the upQuestionVote
        restUpQuestionVoteMockMvc.perform(get("/api/up-question-votes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpQuestionVote() throws Exception {
        // Initialize the database
        upQuestionVoteService.save(upQuestionVote);

        int databaseSizeBeforeUpdate = upQuestionVoteRepository.findAll().size();

        // Update the upQuestionVote
        UpQuestionVote updatedUpQuestionVote = upQuestionVoteRepository.findOne(upQuestionVote.getId());
        updatedUpQuestionVote
                .cTime(UPDATED_C_TIME);

        restUpQuestionVoteMockMvc.perform(put("/api/up-question-votes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUpQuestionVote)))
                .andExpect(status().isOk());

        // Validate the UpQuestionVote in the database
        List<UpQuestionVote> upQuestionVotes = upQuestionVoteRepository.findAll();
        assertThat(upQuestionVotes).hasSize(databaseSizeBeforeUpdate);
        UpQuestionVote testUpQuestionVote = upQuestionVotes.get(upQuestionVotes.size() - 1);
        assertThat(testUpQuestionVote.getcTime()).isEqualTo(UPDATED_C_TIME);
    }

    @Test
    @Transactional
    public void deleteUpQuestionVote() throws Exception {
        // Initialize the database
        upQuestionVoteService.save(upQuestionVote);

        int databaseSizeBeforeDelete = upQuestionVoteRepository.findAll().size();

        // Get the upQuestionVote
        restUpQuestionVoteMockMvc.perform(delete("/api/up-question-votes/{id}", upQuestionVote.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UpQuestionVote> upQuestionVotes = upQuestionVoteRepository.findAll();
        assertThat(upQuestionVotes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
