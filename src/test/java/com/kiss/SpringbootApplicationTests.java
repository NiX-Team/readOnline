package com.kiss;

import com.kiss.jpa.TxtChapterJpa;
import com.kiss.model.TxtChapterMsgModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	private TxtChapterJpa jpa;

	@Test
	public void contextLoads() {
		MainApplication.main(new String[0]);
		TxtChapterMsgModel model = new TxtChapterMsgModel();
//		model.setTxtSn("txt15081293624374440");
		System.out.println(jpa.findAll(Example.of(model,ExampleMatcher.matching().withMatcher("txtSn", ExampleMatcher.GenericPropertyMatchers.exact()))).size());
	}

}
