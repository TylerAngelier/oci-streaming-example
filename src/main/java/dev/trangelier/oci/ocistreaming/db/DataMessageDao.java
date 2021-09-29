package dev.trangelier.oci.ocistreaming.db;

import dev.trangelier.oci.ocistreaming.model.DataMessage;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@UseClasspathSqlLocator
@RegisterBeanMapper(DataMessage.class)
public interface DataMessageDao extends SqlObject {
    @SqlQuery
    List<DataMessage> findAll();

    @SqlUpdate
    int insert(@BindBean DataMessage dataMessage);
}
