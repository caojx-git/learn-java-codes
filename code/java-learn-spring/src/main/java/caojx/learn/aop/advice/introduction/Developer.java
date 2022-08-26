package caojx.learn.aop.advice.introduction;

/**
 * 类注释，描述
 *
 * @author caojx
 * @version $Id: Developer.java,v 1.0 2019-02-20 20:17 caojx
 * @date 2019-02-20 20:17
 */
public class Developer implements IDeveloper {
    @Override
    public void developSoftware() {
        System.out.println("I am happy with programming");
    }
}