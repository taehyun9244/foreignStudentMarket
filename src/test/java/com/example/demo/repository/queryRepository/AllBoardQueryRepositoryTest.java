package com.example.demo.repository.queryRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AllBoardQueryRepositoryTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;



}