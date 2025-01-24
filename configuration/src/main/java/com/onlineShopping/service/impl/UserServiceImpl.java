package com.onlineShopping.service.impl;

import com.onlineShopping.model.UserDetails;
import com.onlineShopping.repository.UserRepository;
import com.onlineShopping.service.UserService;
import com.onlineShopping.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * @param userDetails
     * @return
     */
    @Override
    public UserDetails saveUser(UserDetails userDetails) {
        userDetails.setRole("ROLE_USER");
        userDetails.setEnable(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setFailedAttempt(0);


        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(encodedPassword);
        return userRepository.save(userDetails);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public UserDetails getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    /**
     * @param role
     * @return
     */
    @Override
    public List<UserDetails> getUsers(String role) {
        return userRepository.findByRole(role);
    }



    /**
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        Optional<UserDetails> userDetails = userRepository.findById(Long.valueOf(id));
        if (userDetails.isPresent()) {
            UserDetails userDetail = userDetails.get();
            userDetail.setEnable(status);
            userRepository.save(userDetail);
            return true;
        }
        return false;
    }

    /**
     * @param userDetails
     */
    @Override
    public void increaseFailedAttempt(UserDetails userDetails) {
        int attempt = userDetails.getFailedAttempt() + 1;
        userDetails.setFailedAttempt(attempt);
        userRepository.save(userDetails);
    }

    /**
     * @param userDetails
     */
    @Override
    public void userAccountLock(UserDetails userDetails) {
        userDetails.setAccountNonLocked(false);
        userDetails.setLockTime(new Date());
        userRepository.save(userDetails);

    }

    /**
     * @param userDetails
     * @return
     */
    @Override
    public boolean unlockAccountTimeExpired(UserDetails userDetails) {
        long lockTime = userDetails.getLockTime().getTime();
        long unlockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;
        long currentTime = System.currentTimeMillis();
        if (currentTime < unlockTime) {
            userDetails.setAccountNonLocked(true);
            userDetails.setFailedAttempt(0);
            userDetails.setLockTime(null);
            userRepository.save(userDetails);
            return true;
        }
        return false;
    }

    /**
     * @param userId
     */
    @Override
    public void resetAttempt(Long userId) {

    }

    /**
     * @param email
     * @param resetToken
     */
    @Override
    public void updateUserResetToken(String email, String resetToken) {

        UserDetails findByEmail = userRepository.findByEmail(email);
        findByEmail.setResetToken(resetToken);
        userRepository.save(findByEmail);
    }

    /**
     * @param token
     * @return
     */
    @Override
    public UserDetails getUserByToken(String token) {
        return userRepository.findByEmail(token);
    }

    /**
     * @param userDetails
     * @return
     */
    @Override
    public UserDetails updateUser(UserDetails userDetails) {
        return userRepository.save(userDetails);
    }

    /**
     * @param userDetails
     * @return
     */
    @Override
    public UserDetails updateUserProfile(UserDetails userDetails, MultipartFile img) {

        UserDetails dbUser = userRepository.findById(userDetails.getId()).get();

        if (!img.isEmpty()) {
            dbUser.setProfileImage(img.getOriginalFilename());
        }

        if (!ObjectUtils.isEmpty(dbUser)) {

            dbUser.setName(userDetails.getName());
            dbUser.setMobileNumber(userDetails.getMobileNumber());
            dbUser.setAddress(userDetails.getAddress());
            dbUser.setCity(userDetails.getCity());
            dbUser.setState(userDetails.getState());
            dbUser.setPinCode(userDetails.getPinCode());
            dbUser = userRepository.save(dbUser);
        }

        try {
            if (!img.isEmpty()) {
                File saveFile = new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "profile_img" + File.separator
                        + img.getOriginalFilename());

//			System.out.println(path);
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbUser;
    }

    /**
     * @param userDetails
     * @return
     */
    @Override
    public UserDetails saveAdmin(UserDetails userDetails) {
        userDetails.setRole("ROLE_ADMIN");
        userDetails.setEnable(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setFailedAttempt(0);

        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(encodedPassword);
        return userRepository.save(userDetails);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public Boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
