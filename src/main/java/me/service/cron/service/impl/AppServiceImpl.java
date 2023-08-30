package me.service.cron.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.contents.TaskType;
import me.service.cron.dynamic.DynamicClassFactory;
import me.service.cron.mapper.AppMapper;
import me.service.cron.model.ApplyClass;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.entity.AppEntity;
import me.service.cron.model.query.ApplyQuery;
import me.service.cron.model.request.CreateApplyRequest;
import me.service.cron.model.request.ModifyApplyRequest;
import me.service.cron.model.response.ApplyDetailResponse;
import me.service.cron.model.response.ApplyResponse;
import me.service.cron.model.response.SystemResponse;
import me.service.cron.service.AppService;
import me.service.cron.service.SystemService;
import me.service.cron.util.PageUtils;
import me.zzpp.dynamic.core.utils.DynamicClassUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述：
 * 2021/12/21 16:32.
 * s
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AppServiceImpl extends ServiceImpl<AppMapper, AppEntity> implements AppService {

    private final SystemService systemService;

    private final DynamicClassFactory dynamicClassFactory;

    public static final Map<Long, ApplyClass> CODE_LOA_MAP = new HashMap<>(8);


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result create(CreateApplyRequest request) throws Exception {
        Long count = this.lambdaQuery().eq(AppEntity::getName, request.getName()).count();
        if (count > 0) {
            return Result.error("资源已存在");
        }


        String code = request.getCode();
        AppEntity entity = new AppEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setJavaName(DynamicClassUtils.getClassName(code));
        entity.setQuote(Boolean.FALSE);

        String javaName = entity.getJavaName();
        //获取路径配置
        SystemResponse system = systemService.get().getData();
        String codePath = system.getCodePath();
        if (StringUtils.isBlank(codePath)) {
            return Result.error("资源存储路径不存在");
        }
        File file = new File(codePath, javaName + ".java");
        if (file.exists()) {
            return Result.error("文件资源已存在");
        }

        this.save(entity);
        //保存文件
        OutputStream outputStream = Files.newOutputStream(file.toPath());
        IOUtils.write(code, outputStream);
        IOUtils.closeQuietly(outputStream);
        return load(code, entity);
    }

    @Override
    public Result modify(ModifyApplyRequest request) throws Exception {
        AppEntity query = this.getById(request.getId());
        if (null == query) {
            return Result.error("资源不存在");
        }
        Long count = this.lambdaQuery()
                .ne(AppEntity::getId, request.getId())
                .eq(AppEntity::getName, request.getName())
                .count();
        if (count > 0) {
            return Result.error("资源已存在");
        }
        String code = request.getCode();
        AppEntity entity = new AppEntity();
        entity.setId(query.getId());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setJavaName(DynamicClassUtils.getClassName(code));
        entity.setQuote(query.getQuote());
        this.updateById(entity);
        return modifyCode(code, entity);
    }

    @Override
    public Result remove(Long applyId) {
        AppEntity query = this.getById(applyId);
        if (null == query) {
            return Result.error("资源不存在");
        }
        CODE_LOA_MAP.remove(applyId);
        this.removeById(applyId);
        return Result.success();
    }

    @Override
    public GetResult<ApplyDetailResponse> get(Long applyId) throws Exception {
        AppEntity query = this.getById(applyId);
        ApplyDetailResponse response = new ApplyDetailResponse();
        response.setId(query.getId());
        response.setName(query.getName());
        response.setDescription(query.getDescription());
        response.setCode(loadCode(query));
        return new GetResult<>(response);
    }

    @Override
    public ListResult<ApplyResponse> list(ApplyQuery query) {
        Page<AppEntity> page = this.page(PageUtils.getPage(query), Wrappers.lambdaQuery(AppEntity.class)
                .like(StringUtils.isNotBlank(query.getName()), AppEntity::getName, query.getName())
        );
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            List<ApplyResponse> collect = page.getRecords().stream().map(x -> {
                ApplyResponse response = new ApplyResponse();
                response.setId(x.getId());
                response.setName(x.getName());
                response.setDescription(x.getDescription());
                response.setQuote(x.getQuote());
                response.setLoad(Boolean.FALSE);
                ApplyClass applyClass = CODE_LOA_MAP.get(x.getId());
                if (null != applyClass) {
                    response.setLoad(applyClass.getLoad());
                }
                return response;
            }).collect(Collectors.toList());
            return new ListResult<>(collect, page.getTotal());
        }
        return new ListResult<>(new ArrayList<>(), 0L);
    }

    @Override
    public Result running(Long applyId) {
        ApplyClass applyClass = CODE_LOA_MAP.get(applyId);
        Class<?> aClass = applyClass.getAClass();
        Pair<Integer, String> job = (Pair<Integer, String>) (dynamicClassFactory.getDynamicClassHandler().invoke(aClass, "job"));
        return new GetResult<>(job);
    }

    @Override
    public Result unload(Long applyId) {
        return null;
    }

    public Result modifyCode(String code, AppEntity entity) throws Exception {
        String javaName = entity.getJavaName();
        //获取路径配置
        SystemResponse system = systemService.get().getData();
        String codePath = system.getCodePath();
        if (StringUtils.isBlank(codePath)) {
            return Result.error("资源存储路径不存在");
        }
        File file = new File(codePath, javaName + ".java");
        OutputStream outputStream = Files.newOutputStream(file.toPath());
        IOUtils.write(code, outputStream);
        IOUtils.closeQuietly(outputStream);
        return load(code, entity);
    }

    @Override
    public String loadCode(AppEntity entity) throws Exception {
        String javaName = entity.getJavaName();
        //获取路径配置
        SystemResponse system = systemService.get().getData();
        String codePath = system.getCodePath();
        if (StringUtils.isBlank(codePath)) {
            throw new RuntimeException("资源存储路径不存在");
        }
        File file = new File(codePath, javaName + ".java");
        if (!file.exists()) {
            throw new RuntimeException("文件资源不存在");
        }
        InputStream inputStream = Files.newInputStream(file.toPath());
        String s = IOUtils.toString(inputStream);
        IOUtils.closeQuietly(inputStream);
        return s;
    }

    @Override
    public Result load(String code, AppEntity entity) throws Exception {
        SystemResponse system = systemService.get().getData();
        String codePath = system.getCodePath();
        List<String> libJarPath = new ArrayList<>();
        File[] files = new File(codePath, "lib").listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.getName().endsWith(".jar")) {
                    libJarPath.add(file.getAbsolutePath());
                }
            }
        }
        ApplicationHome h = new ApplicationHome(AppServiceImpl.class);
        File source = h.getSource();
        if (source.getName().equals(".jar")) {
            libJarPath.add(h.getSource().getAbsolutePath());
        }
        String javaName = entity.getJavaName();
        Class<?> aClass = dynamicClassFactory.getDynamicClassHandler().loadClass(libJarPath, code);
        ApplyClass applyClass = new ApplyClass();
        applyClass.setAClass(aClass);
        applyClass.setJavaName(javaName);
        applyClass.setLoad(Boolean.TRUE);
        CODE_LOA_MAP.put(entity.getId(), applyClass);
        return Result.success();
    }

    @Override
    public void quote(Boolean quote, TaskType taskType, String applyId) {
        if (TaskType.Java != taskType) {
            return;
        }
        this.lambdaUpdate()
                .set(AppEntity::getQuote, quote)
                .eq(AppEntity::getId, Long.parseLong(applyId))
                .update();
    }
}
