package com.wyu.plato.link;

import com.wyu.plato.common.util.WeightRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author novo
 * @since 2023-03-16
 */
@Slf4j
public class WeightRandomTest {
    List<Pair<String, Integer>> list;
    private WeightRandom<String, Integer> random;


    @Test
    public void random() {
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < 100000000; i++) {
            String randomKey = random.random();
            countMap.put(randomKey, countMap.getOrDefault(randomKey, 0) + 1);
        }

        for (Pair<String, Integer> pair : list) {
            log.debug("{}:{}", pair.getKey(), countMap.get(pair.getKey()));
        }
    }

    @Before
    public void init() {
        list = new ArrayList<>();
        list.add(new Pair("A", 1));
        list.add(new Pair("B", 2));
        list.add(new Pair("C", 3));
        list.add(new Pair("D", 4));
        //list.add(new Pair("E", 0));

        this.random = new WeightRandom(list);
    }
}
