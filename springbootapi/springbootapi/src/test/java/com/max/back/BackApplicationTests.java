package com.max.back;

import com.max.back.neusoft.dao.DrugspecificationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackApplicationTests {
@Autowired
private DrugspecificationMapper drugspecificationMapper;
	@Test
	public void contextLoads() {
		System.out.println(drugspecificationMapper.selectList(null));
	}

}
