package fr.dush.test.dblog.dao.context;

import javax.sql.DataSource;

public interface IDatasourceFactory {

	DataSource createDataSource();

}