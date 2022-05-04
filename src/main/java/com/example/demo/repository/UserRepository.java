package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

//    @Repository
//    @PersistenceContext
//    private EntityManager em;
//
//    public void save(User user){
//        em.persist(user);
//    }
//    public User findByUsername(String username){
//        return em.find(User.class, username);
//    }
//
//    public User findByPhoneNumber(String phoneNumber){
//        return em.find(User.class, phoneNumber);
//    }


}
