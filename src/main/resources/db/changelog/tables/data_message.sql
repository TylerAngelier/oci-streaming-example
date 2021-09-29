--liquibase formatted sql

--changeset trangelier:data-message-table
create table data_message(
    id varchar2(50) not null,
    request_id varchar2(50 char) not null,
    message varchar2(2000 char) not null
);
-- rollback drop table data_message;

--changeset trangelier:data-message-pk
alter table data_message
add constraint data_message_pk primary key (id);
--rollback alter table data_message drop constraint data_message_pk;