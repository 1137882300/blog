package com.zhong;

import com.zhong.dao.IntegralRepository;
import com.zhong.service.IntegralService;
import com.zhong.vo.SignInDataVO;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private IntegralService integralService;

    @Autowired
    private IntegralRepository integralRepository;

    @Test
    void contextLoads() {
        String s = integralService.convertGrade(1526L);
        System.out.println(s);
    }

    @Test
    void testPair(){
        int i = integralRepository.updateByUid(20,50,126L);
        System.out.println(i);
    }

    @Test
    void testSignIn(){
        SignInDataVO signInDataVO = integralService.updateGradeAndIntegralByUid(126L);
        System.out.println(signInDataVO);
    }

}
