package com.csinfotechbd.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<T extends CommonEntity> extends JpaRepository<T, Long> {

    List<T> findByEnabled(boolean enabled);
}
