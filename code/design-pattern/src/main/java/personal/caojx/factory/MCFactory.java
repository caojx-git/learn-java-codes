package personal.caojx.factory;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: MCFactory
 * @Description: 圣诞系列加工厂
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午9:52
 */
public class MCFactory implements PersonFactory {

    @Override
    public Boy getBoy() {
        return new MCBoy();
    }

    @Override
    public Girl getGirl() {
        return new MCGirl();
    }
}
