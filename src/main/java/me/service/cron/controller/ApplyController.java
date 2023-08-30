package me.service.cron.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.query.ApplyQuery;
import me.service.cron.model.request.CreateApplyRequest;
import me.service.cron.model.request.ModifyApplyRequest;
import me.service.cron.model.response.ApplyResponse;
import me.service.cron.service.ApplyService;
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
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping
    @ApiOperation("创建应用")
    public Result create(@RequestBody @Validated CreateApplyRequest request){
        return applyService.create(request);
    }

    @PutMapping
    @ApiOperation("修改应用")
    public Result modify(@RequestBody  @Validated ModifyApplyRequest request){
        return applyService.modify(request);
    }

    @GetMapping
    @ApiOperation("查询列表")
    public ListResult<ApplyResponse> list(@Validated ApplyQuery query){
        return applyService.list(query);
    }

    @PutMapping("/running/{applyId}")
    @ApiOperation("修改应用状态")
    public Result running(@PathVariable Long applyId){
        return applyService.running(applyId);
    }

    @PutMapping("/force/{applyId}")
    @ApiOperation("修改应用状态")
    public Result force(@PathVariable Long applyId){
        return applyService.force(applyId);
    }

}
