package tech.aaaaaa.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
//通过mybatis使用druid
public class DruidDataSourceFactory extends PooledDataSourceFactory {
    public DruidDataSourceFactory(){
        this.dataSource = new DruidDataSource();
    }
}
