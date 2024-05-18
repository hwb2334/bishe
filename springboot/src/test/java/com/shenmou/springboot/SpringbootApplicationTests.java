package com.shenmou.springboot;

import com.shenmou.springboot.entity.dto.RGoodsDTO;
import com.shenmou.springboot.service.IRGoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

	@Resource
	private IRGoodsService goodsService;
	@Test
	void runRec() {
		List<RGoodsDTO> recDTO = goodsService.findRecDTO(14);
		System.out.println(recDTO);
	}

}
