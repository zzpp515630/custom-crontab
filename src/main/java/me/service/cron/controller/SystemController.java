package me.service.cron.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.service.cron.model.GetResult;
import me.service.cron.model.Result;
import me.service.cron.model.request.SystemRequest;
import me.service.cron.model.response.SystemResponse;
import me.service.cron.service.SystemService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 * 2021/12/16 15:09.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemService systemService;

    @GetMapping
    @ApiOperation("获取系统详情")
    public GetResult<SystemResponse> get(){
        return systemService.get();
    }


    @PostMapping
    public Result modify(@RequestBody @Validated SystemRequest request){
       return systemService.save(request);
    }

}
