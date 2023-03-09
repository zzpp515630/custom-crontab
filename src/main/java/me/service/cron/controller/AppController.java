package me.service.cron.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.query.ApplyQuery;
import me.service.cron.model.request.CreateApplyRequest;
import me.service.cron.model.request.ModifyApplyRequest;
import me.service.cron.model.response.ApplyDetailResponse;
import me.service.cron.model.response.ApplyResponse;
import me.service.cron.service.AppService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：
 * 2021/12/21 16:31.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @PostMapping
    @ApiOperation("创建应用")
    public Result create(@RequestBody @Validated CreateApplyRequest request) throws Exception {
        return appService.create(request);
    }

    @PutMapping
    @ApiOperation("修改应用")
    public Result modify(@RequestBody @Validated ModifyApplyRequest request) throws Exception {
        return appService.modify(request);
    }

    @DeleteMapping("/{applyId}")
    @ApiOperation("删除文件")
    public Result remove(@PathVariable Long applyId) {
        return appService.remove(applyId);
    }

    @GetMapping("/{applyId}")
    @ApiOperation("查询文件")
    public GetResult<ApplyDetailResponse> get(@PathVariable Long applyId) throws Exception {
        return appService.get(applyId);
    }
    @GetMapping
    @ApiOperation("查询列表")
    public ListResult<ApplyResponse> list(@Validated ApplyQuery query) {
        return appService.list(query);
    }

    @PutMapping("/running/{applyId}")
    @ApiOperation("执行应用")
    public Result running(@PathVariable Long applyId) {
        return appService.running(applyId);
    }

    @PutMapping("/unload/{applyId}")
    @ApiOperation("卸载应用")
    public Result unload(@PathVariable Long applyId) {
        return appService.unload(applyId);
    }



}
