package com.max.back.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MpGenerator {
	private static String dirverName = "com.mysql.cj.jdbc.Driver";
	private static String url = " jdbc:mysql://123.57.20.244:3306/neusoft?serverTimezone=UTC";
	private static String user = "root";
	private static String password = "pcW0637dM3078VrhOK922w6cVV0I0D";

	private static String author = "max";
	private static String outputDir = "/home/maqs/gitlocal/spring_meeting/meeing_springboot/src/main/java";
	private static String subPackageName = "test";
	private static String tableName1 = "test";
	//private static String tableName2 = "tb_yueding_detai";

	@Test
	public void generatorSource() {
		AutoGenerator mpg = new AutoGenerator();

		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDir);
		gc.setFileOverride(true);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);
		gc.setBaseResultMap(false);
		gc.setBaseColumnList(false);
		gc.setAuthor(author);// 作者

		gc.setControllerName("%sCtl");
		gc.setServiceName("%sSer");
		gc.setServiceImplName("%sSerImpl");
		gc.setMapperName("%sDao");
		mpg.setGlobalConfig(gc);

		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setDriverName(dirverName);
		dsc.setUrl(url);
		dsc.setUsername(user);
		dsc.setPassword(password);

		mpg.setDataSource(dsc);

		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setInclude(new String[] { tableName1 });

		strategy.setSuperServiceClass(null);
		strategy.setSuperServiceImplClass(null);
		strategy.setSuperMapperClass(null);
		strategy.setEntityTableFieldAnnotationEnable(true);
		strategy.setEntityLombokModel(true);

		mpg.setStrategy(strategy);

		PackageConfig pc = new PackageConfig();
		pc.setParent("com.neusoft.meeting.meeting");
		pc.setController(subPackageName + ".api");
		pc.setService(subPackageName + ".service");
		pc.setServiceImpl(subPackageName + ".service.impl");
		pc.setMapper(subPackageName + ".dao");
		pc.setEntity(subPackageName + ".entity");
		mpg.setPackageInfo(pc);

		// 执行生成
		mpg.execute();
	}
}
