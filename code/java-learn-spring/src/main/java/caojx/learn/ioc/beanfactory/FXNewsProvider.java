package caojx.learn.ioc.beanfactory;

import caojx.learn.ioc.IFXNewsListener;
import caojx.learn.ioc.IFXNewsPersister;

public class FXNewsProvider {

    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;


    public FXNewsProvider(IFXNewsListener newsListner, IFXNewsPersister newsPersister) {
        this.newsListener = newsListner;
        this.newPersistener = newsPersister;
    }

    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersister getNewPersistener() {
        return newPersistener;
    }

    public void setNewPersistener(IFXNewsPersister newPersistener) {
        this.newPersistener = newPersistener;
    }
}
