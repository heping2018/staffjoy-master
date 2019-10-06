package com.example.staffjoy.faraday.balancer;

import java.util.List;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 随机
 */
public class BalancerDestantions implements  Balancer{
    @Override
    public String chooseDesetination(List<String> destnations) {
        int index = destnations.size() == 0 ? 1 : current().nextInt(0,destnations.size());
        return destnations.get(index);
    }
}
