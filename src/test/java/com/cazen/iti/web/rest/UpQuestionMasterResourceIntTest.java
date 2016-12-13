package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.repository.UpQuestionMasterRepository;
import com.cazen.iti.service.UpQuestionMasterService;

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
 * Test class for the UpQuestionMasterResource REST controller.
 *
 * @see UpQuestionMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class UpQuestionMasterResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_C_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_C_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIFFICULTY = 1;
    private static final Integer UPDATED_DIFFICULTY = 2;

    @Inject
    private UpQuestionMasterRepository upQuestionMasterRepository;

    @Inject
    private UpQuestionMasterService upQuestionMasterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUpQuestionMasterMockMvc;

    private UpQuestionMaster upQuestionMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UpQuestionMasterResource upQuestionMasterResource = new UpQuestionMasterResource();
        ReflectionTestUtils.setField(upQuestionMasterResource, "upQuestionMasterService", upQuestionMasterService);
        this.restUpQuestionMasterMockMvc = MockMvcBuilders.standaloneSetup(upQuestionMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpQuestionMaster createEntity(EntityManager em) {
        UpQuestionMaster upQuestionMaster = new UpQuestionMaster()
                .title(DEFAULT_TITLE)
                .delYn(DEFAULT_DEL_YN)
                .cTime(DEFAULT_C_TIME)
                .status(DEFAULT_STATUS)
                .difficulty(DEFAULT_DIFFICULTY);
        return upQuestionMaster;
    }

    @Before
    public void initTest() {
        upQuestionMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpQuestionMaster() throws Exception {
        int databaseSizeBeforeCreate = upQuestionMasterRepository.findAll().size();

        // Create the UpQuestionMaster

        restUpQuestionMasterMockMvc.perform(post("/api/up-question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upQuestionMaster)))
            .andExpect(status().isCreated());

        // Validate the UpQuestionMaster in the database
        List<UpQuestionMaster> upQuestionMasterList = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasterList).hasSize(databaseSizeBeforeCreate + 1);
        UpQuestionMaster testUpQuestionMaster = upQuestionMasterList.get(upQuestionMasterList.size() - 1);
        assertThat(testUpQuestionMaster.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUpQuestionMaster.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
        assertThat(testUpQuestionMaster.getcTime()).isEqualTo(DEFAULT_C_TIME);
        assertThat(testUpQuestionMaster.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUpQuestionMaster.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    @Transactional
    public void createUpQuestionMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = upQuestionMasterRepository.findAll().size();

        // Create the UpQuestionMaster with an existing ID
        UpQuestionMaster existingUpQuestionMaster = new UpQuestionMaster();
        existingUpQuestionMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpQuestionMasterMockMvc.perform(post("/api/up-question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUpQuestionMaster)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UpQuestionMaster> upQuestionMasterList = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUpQuestionMasters() throws Exception {
        // Initialize the database
        upQuestionMasterRepository.saveAndFlush(upQuestionMaster);

        // Get all the upQuestionMasterList
        restUpQuestionMasterMockMvc.perform(get("/api/up-question-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upQuestionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())))
            .andExpect(jsonPath("$.[*].cTime").value(hasItem(sameInstant(DEFAULT_C_TIME))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY)));
    }

    @Test
    @Transactional
    public void getUpQuestionMaster() throws Exception {
        // Initialize the database
        upQuestionMasterRepository.saveAndFlush(upQuestionMaster);

        // Get the upQuestionMaster
        restUpQuestionMasterMockMvc.perform(get("/api/up-question-masters/{id}", upQuestionMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(upQuestionMaster.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()))
            .andExpect(jsonPath("$.cTime").value(sameInstant(DEFAULT_C_TIME)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY));
    }

    @Test
    @Transactional
    public void getNonExistingUpQuestionMaster() throws Exception {
        // Get the upQuestionMaster
        restUpQuestionMasterMockMvc.perform(get("/api/up-question-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpQuestionMaster() throws Exception {
        // Initialize the database
        upQuestionMasterService.save(upQuestionMaster);

        int databaseSizeBeforeUpdate = upQuestionMasterRepository.findAll().size();

        // Update the upQuestionMaster
        UpQuestionMaster updatedUpQuestionMaster = upQuestionMasterRepository.findOne(upQuestionMaster.getId());
        updatedUpQuestionMaster
                .title(UPDATED_TITLE)
                .delYn(UPDATED_DEL_YN)
                .cTime(UPDATED_C_TIME)
                .status(UPDATED_STATUS)
                .difficulty(UPDATED_DIFFICULTY);

        restUpQuestionMasterMockMvc.perform(put("/api/up-question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUpQuestionMaster)))
            .andExpect(status().isOk());

        // Validate the UpQuestionMaster in the database
        List<UpQuestionMaster> upQuestionMasterList = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasterList).hasSize(databaseSizeBeforeUpdate);
        UpQuestionMaster testUpQuestionMaster = upQuestionMasterList.get(upQuestionMasterList.size() - 1);
        assertThat(testUpQuestionMaster.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUpQuestionMaster.getDelYn()).isEqualTo(UPDATED_DEL_YN);
        assertThat(testUpQuestionMaster.getcTime()).isEqualTo(UPDATED_C_TIME);
        assertThat(testUpQuestionMaster.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUpQuestionMaster.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    public void updateNonExistingUpQuestionMaster() throws Exception {
        int databaseSizeBeforeUpdate = upQuestionMasterRepository.findAll().size();

        // Create the UpQuestionMaster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUpQuestionMasterMockMvc.perform(put("/api/up-question-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(upQuestionMaster)))
            .andExpect(status().isCreated());

        // Validate the UpQuestionMaster in the database
        List<UpQuestionMaster> upQuestionMasterList = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUpQuestionMaster() throws Exception {
        // Initialize the database
        upQuestionMasterService.save(upQuestionMaster);

        int databaseSizeBeforeDelete = upQuestionMasterRepository.findAll().size();

        // Get the upQuestionMaster
        restUpQuestionMasterMockMvc.perform(delete("/api/up-question-masters/{id}", upQuestionMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UpQuestionMaster> upQuestionMasterList = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
