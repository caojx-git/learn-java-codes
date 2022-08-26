package caojx.learn.ioc.beanfactory;

import caojx.learn.ioc.IFXNewsListener;
import caojx.learn.ioc.IFXNewsPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FXNewsProviderAnnotaion {

    @Autowired
    private IFXNewsListener newsListener;

    @Autowired
    private IFXNewsPersister newPersistener;


    public FXNewsProviderAnnotaion(IFXNewsListener newsListner, IFXNewsPersister newsPersister) {
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
