package pers.caojx.learn.junit4.util;

import org.junit.Test;
import pers.java.caojx.learn.junit4.util.Calculate;

import static org.junit.Assert.*;

/**
 * Description: Calculate测试类
 *
 * @author caojx
 * Created by caojx on 2017年04月03 下午4:12:12
 */
public class CalculateTest {

    /**
     * 测试add方法,使用junit中的Test注解,建议方法命名以test开头
     * 断言测试assertEquals(Expected,Actual) 第一个参数的期待值，第二个参数是实际值
     *
     * 1.测试方法上必须是哟该你@Test进行注解
     * 2.测试方法必须使用public void进行修饰，不能带返回值
     * 3.应该新建一个测试目录来存放我们的测试代码
     * 4.测试类的包应该和被测试类保持一致
     * 5.测试单元中的每个方法必须可以独立测试，测试方法间不能有任何依赖
     * 6.测试类使用Test作为类名的后缀(建议)
     * 7.测试方法使用test作为方法名的前缀(建议)
     */
    @Test
    public void testAdd(){
        assertEquals(6, new Calculate().add(3,3));
    }

    /**
     * 测试subtract方法
     */
    @Test
    public void testSubtract(){
        assertEquals(3, new Calculate().subtract(5,2));
    }

    /**
     * 测试multiply方法
     */
    @Test
    public void testMultiply(){
        assertEquals(4, new Calculate().multiply(2,2));
    }

    /**
     * 测试divide方法
     */
    @Test
    public void testDivide(){
        assertEquals(3, new Calculate().divide(6,2));
    }

}
