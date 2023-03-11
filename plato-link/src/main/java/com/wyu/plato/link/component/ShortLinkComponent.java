package com.wyu.plato.link.component;

import com.wyu.plato.common.util.CommonUtil;
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

    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //private static final String CHARS = "S7sX1x4tI8iVDdb0PcTWnyRzCApYeFhlrNk6UQKBE2G5ZfgoaOmuJ3vLqMHw9j";

    public static void main(String[] args) {
        List<String> list= Arrays.asList(CHARS.split(""));
        //list.forEach(System.out::println);
        Collections.shuffle(list);
        //list.forEach(System.out::println);
        String join = StringUtils.join(list.toArray(), "");
        System.out.println(join);
    }
    /**
     * 生成短链
     *
     * @param url
     * @return
     */
    public String createShortLink(String url) {
        long murmurHash32 = CommonUtil.murmurHash32(url);
        // 62进制转换
        return encodeToBase62(murmurHash32);
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
