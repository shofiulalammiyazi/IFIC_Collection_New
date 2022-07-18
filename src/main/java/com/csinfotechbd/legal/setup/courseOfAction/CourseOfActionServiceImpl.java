package com.csinfotechbd.legal.setup.courseOfAction;

import com.csinfotechbd.audittrail.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseOfActionServiceImpl implements CourseOfActionService {

    private final CourseOfActionRepository courseOfActionRepository;
    private final AuditTrailService auditTrailService;

    @Override
    public String save(CourseOfAction courseOfAction) {
        boolean isNewEntity = false;

        if (courseOfAction.getId() == null)
            isNewEntity = true;

        CourseOfAction previousEntity = new CourseOfAction();
        if (!isNewEntity){
            CourseOfAction oldEntity = courseOfActionRepository.getOne(courseOfAction.getId());
            BeanUtils.copyProperties(oldEntity, previousEntity);
        }

        courseOfActionRepository.save(courseOfAction);

        if (isNewEntity)
            auditTrailService.saveCreatedData("Course Of Action", courseOfAction);
        else
            auditTrailService.saveUpdatedData("Course Of Action", previousEntity, courseOfAction);
        return "1";
    }

    @Override
    public List<CourseOfAction> findAll() {
        return courseOfActionRepository.findAll();
    }

    @Override
    public List<CourseOfAction> findByEnabled(boolean enabled) {
        return courseOfActionRepository.findByEnabled(enabled);
    }

    @Override
    public List<CourseOfAction> findByCaseTypeId(Long caseTypeId) {
        return courseOfActionRepository.findByCaseTypeId(caseTypeId);
    }

    @Override
    public CourseOfAction findByNameLike(String name) {
        CourseOfAction courseOfAction = courseOfActionRepository.findFirstByNameContainsOrderBySequence(name);
        return courseOfAction != null ? courseOfAction : courseOfActionRepository.findFirstByNameContainsOrderBySequence("Others");
    }

    @Override
    public List<CourseOfActionDto> findByCaseTypeIdAndContestedType(Long caseTypeId, String contestedType) {
        List<CourseOfAction> list = courseOfActionRepository.findByCaseTypeIdAndContestedType(caseTypeId, contestedType);
        return list.stream().map(CourseOfActionDto::new).collect(Collectors.toList());
    }

    @Override
    public CourseOfAction findById(Long id) {
        return courseOfActionRepository.findById(id).orElse(new CourseOfAction());
    }

    @Override
    public void deleteById(Long id) {
        courseOfActionRepository.deleteById(id);
    }

//    private boolean alreadyExists(CourseOfAction courseOfAction){
//        CourseOfAction existingCourseOfAction = courseOfActionRepository.findByName(courseOfAction.getName());
//        return existingCourseOfAction != null && courseOfAction.equalsTo(existingCourseOfAction);
//    }

}
