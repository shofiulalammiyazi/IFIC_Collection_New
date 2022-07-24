package com.unisoft.collection.settings.memoNoteCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("collection/memoNoteCategory")
public class MemoNoteCategoryController {

    @Autowired
    private MemoNoteCategoryService memoNoteCategoryService;

    @GetMapping("/create")
    public String createMemeNoteCategory(Model model){
        model.addAttribute("memoNoteCategory", new MemoNoteCategory());
        return "collection/settings/memoNoteCategory/create";
    }

    @PostMapping("/save")
    public String saveMemoNoteCategory(@Valid MemoNoteCategory memoNoteCategory, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("memoNoteCategory", memoNoteCategory);
            redirectAttributes.addFlashAttribute("error","Memo Category Name required");
            return "collection/settings/memoNoteCategory/create";
        }
        try{
            if(memoNoteCategory.getId()!=null){
                MemoNoteCategory oldMemoNoteCategory = memoNoteCategoryService.findById(memoNoteCategory.getId());
                memoNoteCategory.setCreatedBy(oldMemoNoteCategory.getCreatedBy());
                memoNoteCategory.setCreatedDate(oldMemoNoteCategory.getCreatedDate());
            }
            memoNoteCategoryService.save(memoNoteCategory);
            redirectAttributes.addFlashAttribute("success","Saved Successfully!");
        }
        catch (DataIntegrityViolationException ex){
            memoNoteCategory.setId(null);
            model.addAttribute("memoNoteCategory", memoNoteCategory);
            model.addAttribute("error","Memo Category Name is already exist");
            return "collection/settings/memoNoteCategory/create";
        }
        return "redirect:/collection/memoNoteCategory/list";
    }

    @GetMapping("/edit")
    public String editMemoNoteCategory(@RequestParam("id") Long id, Model model){
        MemoNoteCategory memoNoteCategory = memoNoteCategoryService.findById(id);
        model.addAttribute("memoNoteCategory", memoNoteCategory);
        return "collection/settings/memoNoteCategory/create";
    }

    @GetMapping("/list")
    public String getMemoNoteCategoryList(Model model){
        model.addAttribute("memoNoteCategoryList", memoNoteCategoryService.getList());
        return "collection/settings/memoNoteCategory/memoNoteCategory-list";
    }
}
