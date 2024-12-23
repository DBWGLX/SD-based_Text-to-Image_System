package com.llxx;

import com.llxx.pojo.Result;
import com.llxx.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToptApplicationTests {

    @Autowired
    JwtUtils jwtUtils;
    @Test
    void contextLoads() {
        try {

            JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEzMiwiZW1haWwiOiJmYW5neXVyYW4xMjNAZ21haWwuY29tIiwiZXhwIjoxNzM0OTY4NjQ1fQ.gzFHYcSPOeYBH43_NRJY9_DObdICzLtxnMUIYT6-3CE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
