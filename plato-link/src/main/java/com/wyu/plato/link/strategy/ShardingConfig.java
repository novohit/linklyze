package com.wyu.plato.link.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author novo
 * @since 2023-03-16
 */
public class ShardingConfig {
    private static final List<String> dbNo = new ArrayList<>();

    private static final List<String> tableNo = new ArrayList<>();

    private static final Random random = new Random();

    static {
        dbNo.add("0");
        dbNo.add("1");
        dbNo.add("a");
    }

    static {
        tableNo.add("0");
        tableNo.add("a");
    }


    public static String getRandomDBNo() {
        int index = random.nextInt(dbNo.size());
        return dbNo.get(index);
    }

    public static String getRandomTableNo() {
        int index = random.nextInt(tableNo.size());
        return tableNo.get(index);
    }

}
