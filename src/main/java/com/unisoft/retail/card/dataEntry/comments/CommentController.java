package com.unisoft.retail.card.dataEntry.comments;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/collection/card/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping(value="/list")
    public ResponseEntity<Map<String, Object>> getComments(@RequestParam(value = "id") String id){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = principal.getId();
        Map<String, Object> commentList = commentService.getComments(new Long(id));
        commentList.put("userId",userId);
        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/save")
    public ResponseEntity<CommentApiPayLoad> save(@RequestBody CommentApiPayLoad commentApiPayLoad){
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = principal.getId();
        commentService.save(commentApiPayLoad, userId);
        return ResponseEntity.ok(commentApiPayLoad);
    }
}
