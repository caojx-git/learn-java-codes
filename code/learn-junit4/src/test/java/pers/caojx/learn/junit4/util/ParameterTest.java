package pers.caojx.learn.junit4.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import pers.java.caojx.learn.junit4.util.Calculate;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Description: JUnit参数化设置
 *
 * @author caojx
 * Created by caojx on 2017年04月03 下午10:14:14
 */
@RunWith(Parameterized.class)
public class ParameterTest {
    /*
	 * 1.更改默认的测试运行器为RunWith(Parameterized.class)
	 * 2.声明变量来存放预期值 和结果值
	 * 3.测试类声明一个带有参数的公共构造函数，并在其中为之声明变量赋值
	 * 4.声明一个返回值 为Collection的公共静态方法，并使用@Parameters进行修饰
	 */
    int expected =0;
    int input1 = 0;
    int input2 = 0;

    public ParameterTest(int expected,int input1,int input2) {
        this.expected = expected;
        this.input1 = input1;
        this.input2 = input2;
    }

    /**
     * 数组中的参数对应的是测试方法中的参数
     * @return
     */
    @Parameters
    public static Collection<Object[]> t() {
        return Arrays.asList(new Object[][]{
                {3,1,2},
                {4,2,2}
        }) ;
    }

    @Test
    public void testAdd() {
        assertEquals(expected, new Calculate().add(input1, input2));
    }
}
