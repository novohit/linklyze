package com.wyu.plato.common.util;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 权重随机算法
 *
 * @author novo
 * @since 2023-03-16
 */
public class WeightRandom<K, V extends Number> {
    // TODO 对比O(N)和O(logN)的算法
    private final TreeMap<Double, K> weightMap = new TreeMap<>();
    private static final Logger logger = LoggerFactory.getLogger(WeightRandom.class);

    public WeightRandom(List<Pair<K, V>> list) {
        Preconditions.checkNotNull(list, "list can not be null!");
        for (Pair<K, V> pair : list) {
            Preconditions.checkArgument(pair.getValue().doubleValue() > 0, String.format("非法权重值：pair=%s", pair));
            double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey();//统一转为double
            this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());//权重累加
        }
    }

    public K random() {
        double randomWeight = this.weightMap.lastKey() * Math.random();
        SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }
}
