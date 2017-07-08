package personal.caojx.factory;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: PersonFactory
 * @Description: 人物实现工厂接口
 * 工厂方法模式和抽象工厂模式對比
 * 1.工厂模式是一种极端情況的抽象工厂模式，而抽工厂模式可以堪称是工厂模式的推广
 * 2.工厂模式用来创建一个产品的等级结构，而抽象工厂模式是用来创建多个产品的等级结构
 * 3.工厂模式只有一个抽象产品类，而抽象工厂模式有多个抽象产品类
 *
 * 工厂模式的实现帮助了我们：
 * 1.系统可以在不修改具体工厂角色的情况下引进新的产品
 * 2.客户端不必关心对象的创建
 * 3.更好的理解面向对象的原则，面向接口编程，而不要面向实现编程
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午9:50
 */
public interface PersonFactory {

    //男孩接口
    Boy getBoy();

    //女孩接口
    Girl getGirl();
}
