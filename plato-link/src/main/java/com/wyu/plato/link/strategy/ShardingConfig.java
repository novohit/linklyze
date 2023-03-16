package com.wyu.plato.link.strategy;


import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author novo
 * @since 2023-03-16
 */
public class ShardingConfig {
    private static final List<Pair<String, Double>> dbNo = new ArrayList<>();

    private static final List<Pair<String, Double>> tableNo = new ArrayList<>();

    private static final Random random = new Random();

    // 加权负载均衡
    static {
        dbNo.add(new Pair<>("0", 1D));
        dbNo.add(new Pair<>("1", 1D));
        dbNo.add(new Pair<>("a", 1D));
    }

    static {
        tableNo.add(new Pair<>("0", 1D));
        tableNo.add(new Pair<>("a", 1D));
    }


    // TODO 可以应用加权负载均衡思想 不单单是简单的随机策略

    public static String getRandomDBNo() {
        int index = random.nextInt(dbNo.size());
        return dbNo.get(index).getFirst();
    }

    public static String getRandomTableNo() {
        int index = random.nextInt(tableNo.size());
        return tableNo.get(index).getFirst();
    }

}
