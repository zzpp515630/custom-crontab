package me.service.cron.controller;

import lombok.RequiredArgsConstructor;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.query.LogQuery;
import me.service.cron.model.response.LogResponse;
import me.service.cron.service.LogService;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 * 2021/12/13 16:59.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    public ListResult<LogResponse> list(LogQuery query) {
        return logService.list(query);
    }


    @GetMapping("/{logId}")
    public GetResult<LogResponse> get(@PathVariable Long logId) {
        return logService.get(logId);
    }


    @PutMapping("/clean/{taskId}")
    public Result clean(@PathVariable Long taskId) {
        logService.clean(taskId);
        return Result.success();
    }
}
