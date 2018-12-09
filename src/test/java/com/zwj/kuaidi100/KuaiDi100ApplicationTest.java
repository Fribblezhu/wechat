package com.zwj.kuaidi100;

import org.junit.Test;

import java.io.IOException;


/**
 * @author: zwj
 * @Date: 12/7/18
 * @Time: 4:23 PM
 * @description:
 */
public class KuaiDi100ApplicationTest {

    @Test
    public void createDeployment() {

        KuaiDi100Application application = KuaiDi100Application.getInstance();
        System.out.println(application.getExpressInfo("VC48483807385"));
    }

}
