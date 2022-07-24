package com.unisoft.utillity;

/*
  Created by Yasir Araphat on 19/10/2020
*/

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class PageUtils {

    private final Pattern numberPattern = Pattern.compile("^\\d+$");

    public Pageable getPageableFromRequest(HttpServletRequest request, boolean sortable) {
        String rpage = Objects.toString(request.getParameter("page"), "0");
        String rsize = Objects.toString(request.getParameter("size"), "10");
        int pageIndex = numberPattern.matcher(rpage).matches() ? Integer.parseInt(rpage) : 0;
        int pageSize = numberPattern.matcher(rsize).matches() ? Integer.parseInt(rsize) : 10;
        Sort sort = (sortable) ? getSortFromRequest(request) : Sort.unsorted();
        return PageRequest.of(pageIndex, pageSize, sort);
    }

    public Sort getSortFromRequest(HttpServletRequest request) {
        String sortCriteria = request.getParameter("sortCriteria");
        String sortDirection = request.getParameter("sortDirection");
        sortCriteria = StringUtils.hasText(sortCriteria) ? sortCriteria : "id";
        sortDirection = StringUtils.hasText(sortDirection) ? sortDirection : "DESC";
        return Sort.by(Sort.Direction.fromString(sortDirection), sortCriteria);
    }


    public JSONObject pageToJson(@NonNull Page page) {
        JSONObject data = new JSONObject();
        data.put("list", page.getContent());
        data.put("pageIndex", page.getNumber());
        data.put("totalPages", page.getTotalPages());
        data.put("totalElements", page.getTotalElements());
        return data;
    }
}
