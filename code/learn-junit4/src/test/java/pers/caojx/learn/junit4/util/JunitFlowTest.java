package pers.caojx.learn.junit4.util;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Description: JUnit运行流程
 *
 * @author caojx
 * Created by caojx on 2017年04月03 下午6:14:14
 */
public class JunitFlowTest {

    /**
     * 1.@BeforeClass修饰的方法会在所有的方法被调用之前被执行，而且该方法是静态的，所以当测试类运行后接着就会直接运行他，
     * 而且在内存中只会存在一份实力，比较适合加载配置文件
     * 2.@AfterClass所修饰的方法会在调用之后被执行，比较适合对资源的清理，如数据库的连接
     * 3.@Before和@After会在每个@Test标注的方法前后各执行一次
     */
    @BeforeClass
    public static void setUpBeforeClass () throws Exception{
        System.out.println("this is BeforeClass ..");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("this is AfterClass ..");

    }

    @Before
    public void setUp() throws Exception {
        System.out.println("this is Before ..");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("this is After ..");
    }

    @Test
    public void testAdd() throws Exception {
        System.out.println("this is testAdd ..");
    }

    @Test
    public void testSubtract() throws Exception {
        System.out.println("this is testSubtract ..");
    }

    @Test
    public void testMultiply() throws Exception {
        System.out.println("this is testMultiply ..");
    }

    @Test
    public void testDivide() throws Exception {
        System.out.println("this is testDivide ..");
    }

}