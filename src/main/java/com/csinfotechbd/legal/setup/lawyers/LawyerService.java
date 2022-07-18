package com.csinfotechbd.legal.setup.lawyers;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LawyerService {

    String save(Lawyer lawyer);

    List<Lawyer> findAll();

    List<Lawyer> findByEnabled(boolean enabled);

    Lawyer findById(Long id);

    Lawyer findByContactNo(String... contacts);

    Lawyer findByContactOrName(String contact, String name);

    Lawyer findOrSave(Lawyer lawyer);

    List<Lawyer> findById(List<Long> idList);

    void deleteById(Long id);

    List<String> saveFromExcel(MultipartFile file);
}
