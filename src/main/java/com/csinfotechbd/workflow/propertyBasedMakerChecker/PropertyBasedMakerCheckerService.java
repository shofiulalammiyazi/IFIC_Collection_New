package com.csinfotechbd.workflow.propertyBasedMakerChecker;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.utillity.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.csinfotechbd.utillity.StringUtils;

/**
 * Created by Yasir Araphat
 * Created at 30 March, 2021
 */

@Service
@RequiredArgsConstructor
public class PropertyBasedMakerCheckerService<T extends PropertyBasedMakerCheckerModel> {

    private final PropertyBasedMakerCheckerDao repository;
    private final CommonUtils<T> commonUtils;
    private final AuditTrailService auditTrailService;

    /**
     * Logic to make an entity in pending state.
     * All the entries are primarily by default in pending state as enabled == false and remark == null.
     * isPending = !enabled && remark = null || modified by != null && !enabled && remark != null
     *
     * @param identifierProperty
     * @param identifierPropertyValues
     * @return
     */
    public int makePending(Class<T> type, String identifierProperty, Object... identifierPropertyValues) {
        boolean isValidRequest = isValidRequest(type, identifierProperty, identifierPropertyValues);
        if (!isValidRequest) return 0;
        String entityName = type.getSimpleName();
        return repository.makePending(entityName, identifierProperty, identifierPropertyValues);
    }

    /**
     * Logic to approve a data entity.
     * isApproved = enabled && remark == null
     *
     * @param identifierProperty
     * @param identifierPropertyValues
     * @return
     */
    public int approve(Class<T> type, String identifierProperty, Object[] identifierPropertyValues) {
        boolean isValidRequest = isValidRequest(type, identifierProperty, identifierPropertyValues);

        if (!isValidRequest) return 0;

        String entityName = type.getSimpleName();
        int numberOfApprovedEntities = repository.approve(entityName, identifierProperty, identifierPropertyValues);

        if (numberOfApprovedEntities > 0) {
            String module = entityName + " Approved";
            auditTrailService.saveCreatedData(module, identifierPropertyValues);
        }
        return numberOfApprovedEntities;
    }

    /**
     * Logic to reject a data entry.
     * isRejected = modified by == null && !enabled && remark != null
     *
     * @param identifierProperty
     * @param identifierPropertyValues
     * @return
     */
    public int reject(Class<T> type, String identifierProperty, Object[] identifierPropertyValues, String remark) {
        boolean isValidRequest = isValidRequest(type, identifierProperty, identifierPropertyValues);

        if (!isValidRequest || !StringUtils.hasText(remark)) return 0;
        String entityName = type.getSimpleName();
        int numberOfApprovedEntities = repository.reject(entityName, identifierProperty, identifierPropertyValues, remark);

        if (numberOfApprovedEntities > 0) {
            String module = entityName + " Rejected";
            auditTrailService.saveCreatedData(module, identifierPropertyValues);
        }
        return numberOfApprovedEntities;
    }

    private boolean isValidRequest(Class<T> type, String identifierProperty, Object[] identifierPropertyValues) {
        boolean fieldExists = commonUtils.doesFieldExistInClass(type, identifierProperty);
        return fieldExists &&
                identifierPropertyValues.length > 0;
    }
}
