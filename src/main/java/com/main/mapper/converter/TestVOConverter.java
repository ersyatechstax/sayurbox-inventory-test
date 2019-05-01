package com.main.mapper.converter;

import com.main.mapper.TestMapper;
import com.main.persistence.domain.Test;
import com.main.vo.TestVO;
import org.springframework.stereotype.Component;

/**
 * created by ersya 30/03/2019
 */
@Component
public class TestVOConverter {
    public TestVO transferModelToVO(Test model){
        TestVO vo = TestMapper.INSTANCE.modelToVO(model);
        if(model.getSecureId().contains("ab1b2d18")){
            vo.setFlag("Exclusive");
        }
        else{
            vo.setFlag("Common");
        }
        vo.setFoo(model.getName() + " Foo");
        vo.setBar(model.getName() + " Bar");
        return vo;
    }
}
