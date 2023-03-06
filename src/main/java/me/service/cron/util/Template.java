package me.service.cron.util;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangpeng
 * @create 2023/3/6 9:58
 */
public class Template {

    public Pair<Integer,String> job(){


        return Pair.of(0,"success");
//        return Pair.of(-1,"error");
    }
}
