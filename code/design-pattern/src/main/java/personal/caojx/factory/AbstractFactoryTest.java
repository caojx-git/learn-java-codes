package personal.caojx.factory;


/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: AbstractFactoryTest
 * @Description: 抽象工厂测试类
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午9:55
 */
public class AbstractFactoryTest {

    public static void main(String[] args){
        //聖誕女孩
        PersonFactory factory = new MCFactory();
        Girl girl = factory.getGirl();
        girl.drawWomen();

        //新年男孩
        PersonFactory factory1 = new HNFactory();
        Boy boy = factory1.getBoy();
        boy.drawMan();
    }

}
