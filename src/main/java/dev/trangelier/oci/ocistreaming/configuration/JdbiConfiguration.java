package dev.trangelier.oci.ocistreaming.configuration;


import dev.trangelier.oci.ocistreaming.db.ConsumerInboxDao;
import dev.trangelier.oci.ocistreaming.db.DataMessageDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class JdbiConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource driverManagerDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public DataMessageDao dataMessageDao(DataSource dataSource) {
        return jdbi(dataSource).onDemand(DataMessageDao.class);
    }

    @Bean
    public ConsumerInboxDao consumerInboxDao(DataSource dataSource) {
        return jdbi(dataSource).onDemand(ConsumerInboxDao.class);
    }

    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(dataSource);
        Jdbi jdbi = Jdbi.create(proxy);
        return jdbi
                .installPlugin(new SqlObjectPlugin());
    }
}
