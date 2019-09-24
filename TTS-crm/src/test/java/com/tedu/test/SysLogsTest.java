package com.tedu.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tedu.sys.dao.SysLogDao;
import com.tedu.sys.entity.SysLog;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogsTest {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@Test
	public void testDeleteObject() {
		int rows = sysLogDao.deleteObject(9);
		System.out.println(rows);
	}
	
	@Test
	public void testDeleteObjects() {
		int rows = sysLogDao.deleteObjects(9,10,11);
		System.out.println(rows);
	}
	
	@Test
	public void testFindPageObjects() {
		List<SysLog> objects = sysLogDao.findPageObjects("", 2, 3);
		System.out.println(objects);
	}
	
}
