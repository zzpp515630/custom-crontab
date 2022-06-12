package me.service.cron.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.TaskType;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.entity.LogEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 * 2021/12/17 14:51.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/task/type")
    @ApiOperation("获取任务类型")
    public ListResult<String> taskTypes() {
        List<String> taskTypes = Arrays.stream(TaskType.values()).map(Enum::name).collect(Collectors.toList());
        return new ListResult<>(taskTypes, new Integer(taskTypes.size()).longValue());
    }

    @GetMapping("/task/compare")
    @ApiOperation("获取任务类型")
    public ListResult<String> compareType() {
        List<String> compareTypes = Arrays.stream(CompareType.values()).map(Enum::name).collect(Collectors.toList());
        return new ListResult<>(compareTypes, new Integer(compareTypes.size()).longValue());
    }

    @GetMapping("/task/email/keyword")
    @ApiOperation("邮箱关键字")
    public GetResult<String> emailKeyword() {
        List<String> collect = Arrays.stream(LogEntity.class.getDeclaredFields())
                .map(field -> "{" + field.getName() + "}").collect(Collectors.toList());
        collect.add("{resultCode}");
        collect.add("{result}");
        return new GetResult<>(String.join(",", collect));
    }

}
