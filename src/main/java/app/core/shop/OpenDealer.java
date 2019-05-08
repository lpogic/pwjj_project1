package app.core.shop;

import app.core.OpenRoot;

public class OpenDealer extends AuthorizedDealer {

    private OpenRoot openRoot;

    public OpenDealer(OpenRoot openRoot) {
        this.openRoot = openRoot;
    }

    protected OpenRoot openRoot(){
        return openRoot;
    }

    protected OpenDealer rootDealer(){
        return openRoot.openDealer();
    }

    protected OpenDealer rootDealer(Object key){
        return openRoot.openDealer(key);
    }

    public void employ(){}
}
