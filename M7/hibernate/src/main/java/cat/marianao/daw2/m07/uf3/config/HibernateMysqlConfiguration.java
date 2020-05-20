package cat.marianao.daw2.m07.uf3.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Provides the datasource configured in jdbc.properties (a mysql one) to
 * hibernate (loaded thorught
 * {@link cat.marianao.daw2.m07.uf3.config.HibernateConfiguration} and
 * "hibernate.properties"
 *
 * @author Toni
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = { "jdbc.properties", "hibernate.properties" })
@Import(value = { HibernateConfiguration.class })
public class HibernateMysqlConfiguration {
	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

}