package com.unisoft.auth;

import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Component
public class SessionEndListener implements ApplicationListener<SessionDestroyedEvent> {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;
    @Autowired
    private LateReasonExplainRepository lateReasonExplainRepository;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        event.getSecurityContexts().parallelStream().forEach(sec -> {
            Authentication auth = sec.getAuthentication();
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            User user = userDao.findUserByUsername(userPrincipal.getUsername());
            user.setLoggedIn(false);
            userDao.update(user);

            Date loginTime = null;
            try {
                loginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(LocalDate.now().toString() + " 06:00 AM");
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            LateReasonExplainInfo lateReasonExplainInfo = lateReasonExplainRepository.findByUserAndCreatedDateGreaterThan(user, loginTime);
            if (lateReasonExplainInfo != null) {
                lateReasonExplainInfo.setModifiedDate(new Date());
                lateReasonExplainRepository.save(lateReasonExplainInfo);
            }
        });
    }
}
