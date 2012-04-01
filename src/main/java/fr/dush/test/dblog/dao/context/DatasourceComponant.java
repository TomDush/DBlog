package fr.dush.test.dblog.dao.context;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class DatasourceComponant {

	@Inject
	private IDatasourceFactory datasourceFactory;

	@Bean(name = "datasource")
	@Scope("language")
	public DataSource createDatasource() {
		return datasourceFactory.createDataSource();
	}

}
