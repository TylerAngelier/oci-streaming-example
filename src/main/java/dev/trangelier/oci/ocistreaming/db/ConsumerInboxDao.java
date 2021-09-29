package dev.trangelier.oci.ocistreaming.db;

import dev.trangelier.oci.ocistreaming.model.ConsumerInbox;
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
@RegisterBeanMapper(ConsumerInbox.class)
public interface ConsumerInboxDao extends SqlObject {
    @SqlQuery
    List<ConsumerInbox> findAll();

    @SqlUpdate
    int insert(@BindBean ConsumerInbox inbox);
}
