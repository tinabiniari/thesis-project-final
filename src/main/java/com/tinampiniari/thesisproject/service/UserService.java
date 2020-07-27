package com.tinampiniari.thesisproject.service;


import com.tinampiniari.thesisproject.data.model.Profile;
import com.tinampiniari.thesisproject.data.model.Tags;
import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.data.repository.ProfileRepository;
import com.tinampiniari.thesisproject.data.repository.RoleRepository;
import com.tinampiniari.thesisproject.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProfileRepository profileRepository;


    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setRole(user.getRole());
        user.setProfile(new Profile());
        return userRepository.save(user);
    }


    public Profile updateProfile(User user,Profile profile){
        if(profileRepository.countByUserId(user.getUserId()) > 0) {
            Profile oldProfile = profileRepository.findByUserId(user.getUserId());
            profile.setProfileId(oldProfile.getProfileId());
        }
        profile.setProfileId(user.getProfile().getProfileId());
        profile.setUserId(user.getUserId());
        return profileRepository.save(profile);
    }

    public User updateUser(User user){
        if(userRepository.countByUserId(user.getUserId())> 0){
            User old = userRepository.findByEmail(user.getEmail());
            user.setUserId(old.getUserId());
        }
        user.setUserId(user.getUserId());
        return userRepository.save(user);
    }
    public User findByEmail(String email) { return userRepository.findByEmail(email); }


}
