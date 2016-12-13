package com.cazen.iti.web.rest;

import com.cazen.iti.ItinterviewApp;

import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.repository.CommonCodeRepository;
import com.cazen.iti.service.CommonCodeService;

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
 * Test class for the CommonCodeResource REST controller.
 *
 * @see CommonCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItinterviewApp.class)
public class CommonCodeResourceIntTest {

    private static final String DEFAULT_CD_TP = "AAAAAAAAAA";
    private static final String UPDATED_CD_TP = "BBBBBBBBBB";

    private static final String DEFAULT_CD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CD_NM = "AAAAAAAAAA";
    private static final String UPDATED_CD_NM = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_YN = "AAAAAAAAAA";
    private static final String UPDATED_DEL_YN = "BBBBBBBBBB";

    @Inject
    private CommonCodeRepository commonCodeRepository;

    @Inject
    private CommonCodeService commonCodeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCommonCodeMockMvc;

    private CommonCode commonCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommonCodeResource commonCodeResource = new CommonCodeResource();
        ReflectionTestUtils.setField(commonCodeResource, "commonCodeService", commonCodeService);
        this.restCommonCodeMockMvc = MockMvcBuilders.standaloneSetup(commonCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonCode createEntity(EntityManager em) {
        CommonCode commonCode = new CommonCode()
                .cdTp(DEFAULT_CD_TP)
                .cdId(DEFAULT_CD_ID)
                .cdNm(DEFAULT_CD_NM)
                .delYn(DEFAULT_DEL_YN);
        return commonCode;
    }

    @Before
    public void initTest() {
        commonCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommonCode() throws Exception {
        int databaseSizeBeforeCreate = commonCodeRepository.findAll().size();

        // Create the CommonCode

        restCommonCodeMockMvc.perform(post("/api/common-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonCode)))
            .andExpect(status().isCreated());

        // Validate the CommonCode in the database
        List<CommonCode> commonCodeList = commonCodeRepository.findAll();
        assertThat(commonCodeList).hasSize(databaseSizeBeforeCreate + 1);
        CommonCode testCommonCode = commonCodeList.get(commonCodeList.size() - 1);
        assertThat(testCommonCode.getCdTp()).isEqualTo(DEFAULT_CD_TP);
        assertThat(testCommonCode.getCdId()).isEqualTo(DEFAULT_CD_ID);
        assertThat(testCommonCode.getCdNm()).isEqualTo(DEFAULT_CD_NM);
        assertThat(testCommonCode.getDelYn()).isEqualTo(DEFAULT_DEL_YN);
    }

    @Test
    @Transactional
    public void createCommonCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commonCodeRepository.findAll().size();

        // Create the CommonCode with an existing ID
        CommonCode existingCommonCode = new CommonCode();
        existingCommonCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonCodeMockMvc.perform(post("/api/common-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCommonCode)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CommonCode> commonCodeList = commonCodeRepository.findAll();
        assertThat(commonCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommonCodes() throws Exception {
        // Initialize the database
        commonCodeRepository.saveAndFlush(commonCode);

        // Get all the commonCodeList
        restCommonCodeMockMvc.perform(get("/api/common-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].cdTp").value(hasItem(DEFAULT_CD_TP.toString())))
            .andExpect(jsonPath("$.[*].cdId").value(hasItem(DEFAULT_CD_ID.toString())))
            .andExpect(jsonPath("$.[*].cdNm").value(hasItem(DEFAULT_CD_NM.toString())))
            .andExpect(jsonPath("$.[*].delYn").value(hasItem(DEFAULT_DEL_YN.toString())));
    }

    @Test
    @Transactional
    public void getCommonCode() throws Exception {
        // Initialize the database
        commonCodeRepository.saveAndFlush(commonCode);

        // Get the commonCode
        restCommonCodeMockMvc.perform(get("/api/common-codes/{id}", commonCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commonCode.getId().intValue()))
            .andExpect(jsonPath("$.cdTp").value(DEFAULT_CD_TP.toString()))
            .andExpect(jsonPath("$.cdId").value(DEFAULT_CD_ID.toString()))
            .andExpect(jsonPath("$.cdNm").value(DEFAULT_CD_NM.toString()))
            .andExpect(jsonPath("$.delYn").value(DEFAULT_DEL_YN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommonCode() throws Exception {
        // Get the commonCode
        restCommonCodeMockMvc.perform(get("/api/common-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommonCode() throws Exception {
        // Initialize the database
        commonCodeService.save(commonCode);

        int databaseSizeBeforeUpdate = commonCodeRepository.findAll().size();

        // Update the commonCode
        CommonCode updatedCommonCode = commonCodeRepository.findOne(commonCode.getId());
        updatedCommonCode
                .cdTp(UPDATED_CD_TP)
                .cdId(UPDATED_CD_ID)
                .cdNm(UPDATED_CD_NM)
                .delYn(UPDATED_DEL_YN);

        restCommonCodeMockMvc.perform(put("/api/common-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommonCode)))
            .andExpect(status().isOk());

        // Validate the CommonCode in the database
        List<CommonCode> commonCodeList = commonCodeRepository.findAll();
        assertThat(commonCodeList).hasSize(databaseSizeBeforeUpdate);
        CommonCode testCommonCode = commonCodeList.get(commonCodeList.size() - 1);
        assertThat(testCommonCode.getCdTp()).isEqualTo(UPDATED_CD_TP);
        assertThat(testCommonCode.getCdId()).isEqualTo(UPDATED_CD_ID);
        assertThat(testCommonCode.getCdNm()).isEqualTo(UPDATED_CD_NM);
        assertThat(testCommonCode.getDelYn()).isEqualTo(UPDATED_DEL_YN);
    }

    @Test
    @Transactional
    public void updateNonExistingCommonCode() throws Exception {
        int databaseSizeBeforeUpdate = commonCodeRepository.findAll().size();

        // Create the CommonCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommonCodeMockMvc.perform(put("/api/common-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commonCode)))
            .andExpect(status().isCreated());

        // Validate the CommonCode in the database
        List<CommonCode> commonCodeList = commonCodeRepository.findAll();
        assertThat(commonCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommonCode() throws Exception {
        // Initialize the database
        commonCodeService.save(commonCode);

        int databaseSizeBeforeDelete = commonCodeRepository.findAll().size();

        // Get the commonCode
        restCommonCodeMockMvc.perform(delete("/api/common-codes/{id}", commonCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommonCode> commonCodeList = commonCodeRepository.findAll();
        assertThat(commonCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
