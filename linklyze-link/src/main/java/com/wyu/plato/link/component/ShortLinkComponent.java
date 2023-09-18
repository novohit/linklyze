package com.wyu.plato.link.component;

import com.linklyze.common.util.CommonUtil;
import com.wyu.plato.link.strategy.ShardingConfig;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author novo
 * @since 2023-03-11
 */
@Component
public class ShortLinkComponent {

    @Getter
    public static class Link {
        private final String code;

        private final long hash32;

        public Link(String code, long hash32) {
            this.code = code;
            this.hash32 = hash32;
        }
    }

    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //private static final String CHARS = "S7sX1x4tI8iVDdb0PcTWnyRzCApYeFhlrNk6UQKBE2G5ZfgoaOmuJ3vLqMHw9j";

    public static void main(String[] args) {
        List<String> list = Arrays.asList(CHARS.split(""));
        //list.forEach(System.out::println);
        Collections.shuffle(list);
        //list.forEach(System.out::println);
        String join = StringUtils.join(list.toArray(), "");
        System.out.println(join);
    }

    /**
     * 生成短链
     * 这里有个问题 相同的url多次创建短链hash虽然一致，但是这里用的是随机库表位，导致生成出来的短链码不一致
     * 会导致冗余表插入时短链码和原表短链码不一致
     * 解决：
     * 1.可以在生产者端提前生成好短链再发送消息
     * 2.还是在消费者端生成短链 库表位不采取随机策略 而是hash取模
     *
     * @param url
     * @return
     */
    public Link createShortLink(String url) {
        long murmurHash32 = CommonUtil.murmurHash32(url);
        // 62进制转换
        String code62 = encodeToBase62(murmurHash32);
//        String randomDBNo = ShardingConfig.getRandomDBNo();
//        String randomTableNo = ShardingConfig.getRandomTbNo();
        String randomDBNo = ShardingConfig.getHashDBNo(code62);
        String randomTableNo = ShardingConfig.getHashTbNo(code62);
        // 分库分表入库配置：首位添加库位 末位添加表位
        String linkCode = randomDBNo + code62 + randomTableNo;
        return new Link(linkCode, murmurHash32);
    }


    public String encodeToBase62(long num) {
        // 特判一下 因为CHARS数组可以不按照顺序排列，打乱可以提高安全性
        // 正常情况不会为0
        if (num == 0) {
            return String.valueOf(CHARS.charAt(0));
        }
        // StringBuffer线程安全，StringBuilder线程不安全
        StringBuffer sb = new StringBuffer();
        while (num > 0) {
            int i = (int) (num % 62);
            sb.append(CHARS.charAt(i));
            num = num / 62;
        }

        return sb.reverse().toString();
    }
}
