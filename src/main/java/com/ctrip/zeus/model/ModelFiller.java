package com.ctrip.zeus.model;

import com.ctrip.zeus.model.entity.Group;
import com.ctrip.zeus.model.entity.GroupServer;
import com.ctrip.zeus.model.entity.HealthCheck;
import com.ctrip.zeus.model.entity.LoadBalancingMethod;

/**
 * Created by zhoumy on 2015/6/10.
 */
public class ModelFiller {
    public static void fillGroup(Group g) {
        g.setSsl(false);
        g.setVersion(1);
        if (g.getHealthCheck() == null)
            g.setHealthCheck(new HealthCheck().setUri("/"));
        g.getHealthCheck().setIntervals(2000).setPasses(1).setFails(5);
        g.setLoadBalancingMethod(new LoadBalancingMethod().setType("roundrobin").setValue("test"));
    }

    public static void fillGroupServer(GroupServer gs) {
        gs.setPort(80).setWeight(5).setMaxFails(0).setFailTimeout(30);
    }
}
