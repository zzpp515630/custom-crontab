package me.service.cron.dynamic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.zzpp.dynamic.core.handler.DynamicClassHandler;
import org.springframework.stereotype.Component;

/**
 * @author zhangpeng
 * @create 2023/3/10 9:52
 */
@Component
@Slf4j
@Data
public class DynamicClassFactory {


    public DynamicClassHandler dynamicClassHandler;
}
