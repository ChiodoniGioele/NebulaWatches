package ch.chiodoni.cal.config;

import com.zaxxer.hikari.HikariDataSource;
import io.opentelemetry.instrumentation.jdbc.datasource.OpenTelemetryDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
public class Database {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @DependsOn("observationRegistry")
    public DataSource dataSource() {
        return new OpenTelemetryDataSource(
                dataSourceProperties()
                        .initializeDataSourceBuilder()
                        .type(HikariDataSource.class)
                        .build()
        );
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
