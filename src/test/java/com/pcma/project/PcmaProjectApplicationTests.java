package com.pcma.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pcsma.project.PcmaProjectApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PcmaProjectApplication.class)
@WebAppConfiguration
public class PcmaProjectApplicationTests {

	@Test
	public void contextLoads() {
	}

}
