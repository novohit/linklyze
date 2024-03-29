package com.linklyze.link.strategy;


import com.linklyze.common.util.WeightRandom;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author novo
 * @since 2023-03-16
 */
public class ShardingConfig {
    public static final List<Pair<String, Double>> dbNo = new ArrayList<>();

    public static final List<Pair<String, Double>> tbNo = new ArrayList<>();

    //private static final Random random = new Random();

    private static final WeightRandom<String, Double> dbWeightRandom;

    private static final WeightRandom<String, Double> tbWeightRandom;

    // 加权负载均衡
    static {
        dbNo.add(new Pair<>("0", 1D));
        dbNo.add(new Pair<>("1", 1D));
        dbNo.add(new Pair<>("a", 1D));
        dbWeightRandom = new WeightRandom<>(dbNo);
    }

    static {
        tbNo.add(new Pair<>("0", 1D));
        tbNo.add(new Pair<>("a", 1D));
        tbWeightRandom = new WeightRandom<>(tbNo);
    }


    // TODO 可以应用加权负载均衡思想 不单单是简单的随机策略

    public static String getRandomDBNo() {
//        int index = random.nextInt(dbNo.size());
//        return dbNo.get(index).getFirst();
        return dbWeightRandom.random();
    }

    public static String getRandomTbNo() {
//        int index = random.nextInt(tableNo.size());
//        return tableNo.get(index).getFirst();
        return tbWeightRandom.random();
    }

    /**
     * 根据短链的hash取模生成库位
     * 采取hash生成表位，如果要加权负载均衡，只能直接通过更改list的元素个数来控制
     *
     * @param code
     * @return
     */
    public static String getHashDBNo(String code) {
        int hashCode = code.hashCode();
        int index = Math.abs(hashCode) % dbNo.size();
        return dbNo.get(index).getFirst();
    }

    /**
     * 根据短链的hash取模生成表位
     *
     * @param code
     * @return
     */
    public static String getHashTbNo(String code) {
        int hashCode = code.hashCode();
        int index = Math.abs(hashCode) % tbNo.size();
        return tbNo.get(index).getFirst();
    }

}
