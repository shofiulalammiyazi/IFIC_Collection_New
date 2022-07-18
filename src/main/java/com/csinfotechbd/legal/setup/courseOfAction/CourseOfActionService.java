package com.csinfotechbd.legal.setup.courseOfAction;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseOfActionService {

    String save(CourseOfAction courseOfAction);

    List<CourseOfAction> findAll();

    List<CourseOfAction> findByEnabled(boolean enabled);

    List<CourseOfAction> findByCaseTypeId(Long caseTypeId);

    CourseOfAction findByNameLike(String courseOfActionName);

    List<CourseOfActionDto> findByCaseTypeIdAndContestedType(Long caseTypeId, String contestedType);

    CourseOfAction findById(Long id);

    void deleteById(Long id);

}
