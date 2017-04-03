package pers.caojx.learn.junit4.util;

import org.junit.Test;
import pers.java.caojx.learn.junit4.util.Calculate;

import static org.junit.Assert.assertEquals;

/**
 * Description:测试失败的两种情况
 *
 * @author caojx
 * Created by caojx on 2017年04月03 下午5:52:52
 */
public class ErrorAndFailureTest {


    /**
     * 1.Failure一般由测试使用的断言方法判断失败所引起的，这表示测试点发现了问题，就是说程序输出的结果和我们预期的不一样
     * 2.error是有代码引起的异常，他可以产生于测试代码本身的错误，也可能是被测试代码中的一个隐藏的bug
     * 3.测试用例不是用来证明你是对的，而是用来证明你没有错
     */
    @Test
    public void testAdd(){
        assertEquals(5, new Calculate().add(3,3));
    }

    @Test
    public void testDivide(){
        assertEquals(3, new Calculate().divide(6,0));
    }
}
