package caojx.learn.aop.advice.introduction;

/**
 * (2) 给出新接口的实现类。接口实现类给出将要添加到目标对象的具体逻辑。当目标对象将要行
 * 使新的职能的时 候，会通过该实现类寻求帮忙。代码消单9-13给出了针对ITester的实现类。
 *
 * @author caojx
 * @version $Id: Tester.java,v 1.0 2019-02-20 20:22 caojx
 * @date 2019-02-20 20:22
 */
public class Tester implements ITester {

    //我们可以在接口实现类中添加相应的属性甚至辅助方法，就跟实现通常的业务对象一样。
    private boolean busyAsTester;

    @Override
    public boolean isBusyAsTester() {
        return false;
    }

    @Override
    public void testSoftware() {
        System.out.println("I will ensure the quality");
    }

    public void setBusyAsTester(boolean busyAsTester) {
        this.busyAsTester = busyAsTester;
    }
}