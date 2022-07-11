package com.max.back.common;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJwt {
    //@Autowired
//    JwtTokenUtil jwtTokenUtil;
//
//    @Test
//    public void testcreateToken() {
//        String token = jwtTokenUtil.createToken("aaa");
//        System.out.println(token);
//    }
//
//    @Test
//    public void testUsername() {
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2NTUwMzg3OTMsImV4cCI6MTY1NTA0MjM5MywidXNlck5hbWUiOiJ1c2VyTmFtZSIsImlhdCI6MTY1NTAzODc5M30.oGLwuJXrM66C4ije5ne2kv_L627k2indPdrICeT_bxA";
//        String username = jwtTokenUtil.getUsername(token);
//        System.out.println(username);
//    }
//
//    @Autowired
//    private JwtConstBean myConfig;
//
//    @Test
//    public void testVer() {
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE2NTUwMzg3OTMsImV4cCI6MTY1NTA0MjM5MywidXNlck5hbWUiOiJ1c2VyTmFtZSIsImlhdCI6MTY1NTAzODc5M30.oGLwuJXrM66C4ije5ne2kv_L627k2indPdrICeT_bxA";
//        boolean bl  = JWTUtil.verify(token, myConfig.getSecret().getBytes());
//        System.out.println(bl);
//    }
}
