package personal.caojx.factory;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: HNFactory
 * @Description: 新年系列加工厂
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午9:53
 */
public class HNFactory implements PersonFactory{

    @Override
    public Boy getBoy() {
        return new HNBoy();
    }

    @Override
    public Girl getGirl() {
        return new HNGirl();
    }
}
