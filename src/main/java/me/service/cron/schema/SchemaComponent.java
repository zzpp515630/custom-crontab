package me.service.cron.schema;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.mapper.InitializeMapper;
import me.service.cron.model.entity.SchemaHistoryEntity;
import me.service.cron.util.JarFileReadUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 描述：
 * 2021/12/21 9:32.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SchemaComponent {

    private final InitializeMapper initializeMapper;

    private final SqlSessionFactory sqlSessionFactory;


    public void execute() {
        init();
        //比较
        List<SqlFile> sqlFiles = readSql("db.migration");
        List<SqlFile> compare = compare(sqlFiles);
        if (CollectionUtils.isEmpty(compare)) {
            return;
        }
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            for (SqlFile sqlFile : compare) {
                initializeMapper.insert(sqlFile);
                long startTimeMillis = System.currentTimeMillis();
                ScriptRunner runner = new ScriptRunner(sqlSession.getConnection());
                try {
                    runner.setStopOnError(true);
                    runner.setEscapeProcessing(false);
                    runner.runScript(new InputStreamReader(new ByteArrayInputStream(sqlFile.getFile())));
                    long endTimeMillis = System.currentTimeMillis();
                    sqlFile.setExecutionTime(endTimeMillis - startTimeMillis);
                    sqlFile.setSuccess(Boolean.TRUE);
                    initializeMapper.updateById(sqlFile);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    private void init() {
        String sql = "CREATE TABLE \"schema_history\" (\n" +
                "  \"installed_rank\" integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "  \"version\" TEXT(50) DEFAULT NULL,\n" +
                "  \"description\" TEXT(200) NOT NULL,\n" +
                "  \"type\" TEXT(20) NOT NULL,\n" +
                "  \"script\" TEXT(1000) NOT NULL,\n" +
                "  \"checksum\" int(11) DEFAULT NULL,\n" +
                "  \"installed_by\" TEXT(100) NOT NULL,\n" +
                "  \"installed_on\" integer NOT NULL,\n" +
                "  \"execution_time\" integer(11) NOT NULL,\n" +
                "  \"success\" integer(1) NOT NULL\n" +
                ");";

        String index = "CREATE INDEX \"schema_history_s_idx\"\n" +
                "ON \"schema_history\" (\n" +
                "  \"success\" ASC\n" +
                ");";

        int count = initializeMapper.queryTable("schema_history");
        if (count > 0) {
            return;
        }
        initializeMapper.table(sql);
        initializeMapper.table(index);
    }

    public List<SqlFile> compare(List<SqlFile> sqlFiles) {
        sqlFiles.forEach(sqlFile -> {
            String fileName = sqlFile.getFileName();
            sqlFile.setVersion(fileName.substring(1, fileName.indexOf("__")));
            sqlFile.setDescription(fileName.substring(fileName.indexOf("__") + 2, fileName.indexOf(".") - 1));
            sqlFile.setType("SQL");
            sqlFile.setScript(fileName);
            sqlFile.setChecksum(123456);
            sqlFile.setInstalledBy("system");
            sqlFile.setInstalledOn(System.currentTimeMillis());
            sqlFile.setExecutionTime(0L);
            sqlFile.setSuccess(Boolean.FALSE);
        });
        Map<String, SchemaHistoryEntity> schemaHistoryMap = initializeMapper.selectList(Wrappers.emptyWrapper())
                .stream().collect(Collectors.toMap(SchemaHistoryEntity::getVersion, Function.identity()));
        for (int i = 0; i < sqlFiles.size(); i++) {
            SqlFile sqlFile = sqlFiles.get(i);
            SchemaHistoryEntity schemaHistoryEntity = schemaHistoryMap.get(sqlFile.getVersion());
            if (null != schemaHistoryEntity) {
                sqlFiles.remove(i);
                i--;
            }
        }
        return sqlFiles;
    }


    private List<SqlFile> readSql(String location) {
        JarFileReadUtils jarFileReadUtils = new JarFileReadUtils();
        Set<String> resourceNames = jarFileReadUtils.findResourceNames(location);
        return resourceNames.stream()
                .filter(resourceName -> {
                    String trim = resourceName.replace(location + "/", "").trim();
                    return StringUtils.isNotBlank(trim);
                })
                .map(resourceName -> {
                    InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + resourceName);
                    SqlFile sqlFile = new SqlFile();
                    sqlFile.setFileName(resourceName.replace(location + "/", ""));
                    try {
                        sqlFile.setFile(IOUtils.toByteArray(resourceAsStream));
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                    return sqlFile;
                }).collect(Collectors.toList());
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    private static class SqlFile extends SchemaHistoryEntity {
        /**
         * 文件名称
         */
        private String fileName;

        /**
         * 文件
         */
        private byte[] file;

    }

    private boolean isTomcat(String protocol) {
        return "war".equals(protocol);
    }
}
