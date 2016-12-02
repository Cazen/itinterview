package com.cazen.iti.service.impl;

import com.cazen.iti.service.CommonCodeService;
import com.cazen.iti.domain.CommonCode;
import com.cazen.iti.repository.CommonCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing CommonCode.
 */
@Service
@Transactional
public class CommonCodeServiceImpl implements CommonCodeService{

    private final Logger log = LoggerFactory.getLogger(CommonCodeServiceImpl.class);

    @Inject
    private CommonCodeRepository commonCodeRepository;

    /**
     * Save a commonCode.
     *
     * @param commonCode the entity to save
     * @return the persisted entity
     */
    public CommonCode save(CommonCode commonCode) {
        log.debug("Request to save CommonCode : {}", commonCode);
        CommonCode result = commonCodeRepository.save(commonCode);
        return result;
    }

    /**
     *  Get all the commonCodes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CommonCode> findAll() {
        log.debug("Request to get all CommonCodes");
        List<CommonCode> result = commonCodeRepository.findAll();

        return result;
    }

    /**
     *  Get one commonCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CommonCode findOne(Long id) {
        log.debug("Request to get CommonCode : {}", id);
        CommonCode commonCode = commonCodeRepository.findOne(id);
        return commonCode;
    }

    /**
     *  Get one commonCode by unique codeId.
     *
     *  @param codeId the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CommonCode findByCd_Id(String codeId) {
        log.debug("Request to get CommonCode from codeId : {}", codeId);
        CommonCode commonCode = commonCodeRepository.findByCd_Id(codeId);
        return commonCode;
    }

    /**
     *  Delete the  commonCode by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonCode : {}", id);
        commonCodeRepository.delete(id);
    }
}
