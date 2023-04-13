package hello.hellospring.service;

import hello.hellospring.repository.JPAMemberRepository;
import hello.hellospring.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class SpringConfig {

    private EntityManager em;
    @Autowired
    public SpringConfig(EntityManager em){
        this.em = em;
    }


    //private DataSource dataSource;

   /* @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }*/

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JPAMemberRepository(em);
        //return new MemoryMemberRepository();
        //return new  JDBCTemplateMemberRepository(datasource);
    }

}

