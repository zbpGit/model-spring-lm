package com.model.routes;

import com.jfinal.config.Routes;
import com.model.controller.*;

/**
 * Created by Administrator on 2017/10/10.
 */
public class FontRoutes extends Routes {

    public void config() {
        add("/Annunciate",AnnunciateControllor.class);
        add("/asynchronous",AsynchronousControllor.class);
        add("/payment", AuthorizationController.class);
        add("/Detail", DetailControllor.class);
        add("/Picture", PictureControllor.class);
        add("/Model",ModelNameCardControllor.class);
    }
}
