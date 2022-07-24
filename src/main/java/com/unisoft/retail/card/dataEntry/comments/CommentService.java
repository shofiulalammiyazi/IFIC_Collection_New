package com.unisoft.retail.card.dataEntry.comments;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.user.User;
import com.unisoft.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    @Autowired
    UserRepository userRepository;

    public Map<String, Object> getComments(Long customerId){
        Map<String, Object> map = new HashMap<>();
        List<Comment> commentList = new ArrayList<>();
        Optional<CustomerBasicInfoEntity> customer = customerBasicInfoEntityRepository.findById(customerId);
        // customer already has the list of comments so you don't need to pull that again
        if(customer.isPresent()){
            commentList = commentRepository.findByCustomerBasicInfo(customer.get());
        }
        List<String> userList = new ArrayList<>();
        map.put("commentList",commentList);
        commentList.stream().forEach(comment -> {
            Optional<User> user = userRepository.findById(comment.getUserId());
            if(user.isPresent())
                userList.add(user.get().getFirstName() + " " + user.get().getLastName());
        });
        map.put("userList",userList);
        return map;
    }

    public Comment save(CommentApiPayLoad c,Long userId){
        Comment comment = null;
        Optional<CustomerBasicInfoEntity> customer = customerBasicInfoEntityRepository.findById(Long.parseLong(c.getCustomerId()));
        if(customer.isPresent()) {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM hh:mm aa");
            String time = dateFormat.format(date);

            comment = new Comment(c.getComment(),time,userId,customer.get());
            commentRepository.save(comment);
        }

        return comment;
    }
}
