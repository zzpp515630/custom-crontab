package me.service.cron.controller;

import lombok.RequiredArgsConstructor;
import me.service.cron.model.Result;
import me.service.cron.service.CloseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 * 2021/12/21 16:13.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/close")
@RequiredArgsConstructor
public class CloseController {

    private final CloseService closeService;

    @PostMapping
    public Result close() {
        closeService.close();
        return Result.success();
    }
}
