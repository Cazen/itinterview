package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.UpQuestionMaster;
import com.cazen.iti.repository.UpQuestionMasterRepository;
import com.cazen.iti.service.UpQuestionMasterService;

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
 * Test class for the UpQuestionMasterResource REST controller.
 *
 * @see UpQuestionMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class UpQuestionMasterResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAA";
    private static final String UPDATED_DEL_YN = "BBBBB";

    private static final ZonedDateTime DEFAULT_C_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_C_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_C_TIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_C_TIME);

    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";

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

    @PostConstruct
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
                .author(DEFAULT_AUTHOR);
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
        List<UpQuestionMaster> upQuestionMasters = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasters).hasSize(databaseSizeBeforeCreate + 1);
        UpQuestionMaster testUpQuestionMaster = upQuestionMasters.get(upQuestionMasters.size() - 1);
        assertThat(testUpQuestionMaster.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUpQuestionMaster.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
        assertThat(testUpQuestionMaster.getcTime()).isEqualTo(DEFAULT_C_TIME);
        assertThat(testUpQuestionMaster.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllUpQuestionMasters() throws Exception {
        // Initialize the database
        upQuestionMasterRepository.saveAndFlush(upQuestionMaster);

        // Get all the upQuestionMasters
        restUpQuestionMasterMockMvc.perform(get("/api/up-question-masters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(upQuestionMaster.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())))
                .andExpect(jsonPath("$.[*].cTime").value(hasItem(DEFAULT_C_TIME_STR)))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
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
            .andExpect(jsonPath("$.cTime").value(DEFAULT_C_TIME_STR))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()));
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
                .author(UPDATED_AUTHOR);

        restUpQuestionMasterMockMvc.perform(put("/api/up-question-masters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUpQuestionMaster)))
                .andExpect(status().isOk());

        // Validate the UpQuestionMaster in the database
        List<UpQuestionMaster> upQuestionMasters = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasters).hasSize(databaseSizeBeforeUpdate);
        UpQuestionMaster testUpQuestionMaster = upQuestionMasters.get(upQuestionMasters.size() - 1);
        assertThat(testUpQuestionMaster.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUpQuestionMaster.getDelYn()).isEqualTo(UPDATED_DEL_YN);
        assertThat(testUpQuestionMaster.getcTime()).isEqualTo(UPDATED_C_TIME);
        assertThat(testUpQuestionMaster.getAuthor()).isEqualTo(UPDATED_AUTHOR);
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
        List<UpQuestionMaster> upQuestionMasters = upQuestionMasterRepository.findAll();
        assertThat(upQuestionMasters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
