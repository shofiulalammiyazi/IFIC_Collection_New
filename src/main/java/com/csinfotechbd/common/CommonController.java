package com.csinfotechbd.common;

import org.springframework.ui.Model;

public interface CommonController<T> {

    public String create(Model model);

    public String create(Model model, T entity);

    public String view(Model model, Long id);

    public String list(Model model);

    public String delete();
}
