package me.jcala.jmooc.service;

import me.jcala.jmooc.entity.User;
import me.jcala.jmooc.repository.UserRepository;
import me.jcala.jmooc.service.inter.UserSer;
import me.jcala.jmooc.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSerSerImpl implements UserSer {

    private UserRepository userRepository;

    @Autowired
    public UserSerSerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(User user, HttpServletRequest request) {
        if (CommonUtils.isEmpty(user.getName(),user.getPassword())){
           return false;
        }

        User getUser=userRepository.findUserByNameAndPasswordAndType(user.getName(),user.getPassword(),user.getType());

      if (getUser!=null){
          user.setPassword(null);
          HttpSession session = request.getSession(true);
          session.setAttribute("cur_user",user);
          session.setMaxInactiveInterval(600);
          return true;
      }

        return false;
    }

}