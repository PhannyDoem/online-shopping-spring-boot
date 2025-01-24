package com.onlineShopping.service;

import com.onlineShopping.model.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public UserDetails saveUser(UserDetails userDetails);

    public UserDetails getUserByEmail(String email);

    public List<UserDetails> getUsers(String role);


    public Boolean updateAccountStatus(Integer id, Boolean status);

    public void increaseFailedAttempt(UserDetails userDetails);

    public void userAccountLock(UserDetails userDetails);

    public boolean unlockAccountTimeExpired(UserDetails userDetails);

    public void resetAttempt(Long userId);

    public void updateUserResetToken(String email, String resetToken);

    public UserDetails getUserByToken(String token);

    public UserDetails updateUser(UserDetails userDetails);

    public UserDetails updateUserProfile(UserDetails userDetails, MultipartFile file);

    public UserDetails saveAdmin(UserDetails userDetails);

    public Boolean existsEmail(String email);

}
