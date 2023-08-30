package me.service.cron.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.service.cron.model.entity.SchemaHistoryEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 描述：
 * 2021/12/21 9:21.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public interface InitializeMapper extends BaseMapper<SchemaHistoryEntity> {

    /**
     * 根据表名删除表
     *
     * @param tableName 表结构的map
     */
    @Delete("DROP TABLE IF EXISTS `${tableName}`;")
    void dropTableByName(@Param("tableName") String tableName);


    /**
     * 根据表名删除表
     *
     * @param table 表结构的map
     * @return 是否成功
     */
    @Insert("${table}")
    int table(@Param("table") String table);

    /**
     * 查询表结构
     * @param tableName
     * @return
     */
    @Select("select count(1) FROM sqlite_master where name = #{tableName}")
    int queryTable(@Param("tableName") String tableName);
}
