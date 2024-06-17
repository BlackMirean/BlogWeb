package com.rjgc.xzy.finalspringboot.Service;

import com.rjgc.xzy.finalspringboot.Pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
