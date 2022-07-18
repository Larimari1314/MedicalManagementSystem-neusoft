package com.max.back;

import cn.hutool.core.date.DateTime;
import com.max.back.neusoft.dao.DrugspecificationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackApplicationTests {
@Autowired
private DrugspecificationMapper drugspecificationMapper;
	@Test
	public void contextLoads() {
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		System.out.println(simpleDateFormat.format(date));
	}

}
