package me.service.cron.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.query.TaskQuery;
import me.service.cron.model.request.CreateTaskRequest;
import me.service.cron.model.request.ModifyTaskRequest;
import me.service.cron.model.response.TaskResponse;
import me.service.cron.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：
 * 2021/12/13 9:49.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ApiOperation("创建")
    public Result create(@RequestBody @Validated CreateTaskRequest request) {
        return taskService.create(request);
    }

    @PutMapping
    @ApiOperation("编辑")
    public Result modify(@RequestBody @Validated ModifyTaskRequest request) {
        return taskService.modify(request);
    }

    @GetMapping
    @ApiOperation(("查看列表"))
    public ListResult<TaskResponse> list(TaskQuery query) {
        return taskService.list(query);
    }

    @GetMapping("/{taskId}")
    public GetResult<TaskResponse> get(@PathVariable Long taskId){
        return taskService.get(taskId);
    }

    @DeleteMapping("/{taskId}")
    @ApiOperation("删除")
    public Result delete(@PathVariable Long taskId) {
        return taskService.delete(taskId);
    }

    @PutMapping("/execute/{taskId}")
    @ApiOperation("执行一次")
    public Result execute(@PathVariable Long taskId) {
        return taskService.execute(taskId);
    }

    @PutMapping("/running/{taskId}")
    @ApiOperation("启动or停止")
    public Result running(@PathVariable Long taskId) {
        return taskService.running(taskId);
    }

    @GetMapping("/cron/analysis")
    @ApiOperation("cron表达式解析")
    public GetResult<List<String>> cronAnalysis(@RequestParam String cron,
                                                   @RequestParam Integer num){
        return taskService.cronAnalysis(cron,num);
    }

}
