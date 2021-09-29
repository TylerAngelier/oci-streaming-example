--liquibase formatted sql

--changeset trangelier:consumer-inbox-table
create table consumer_inbox(
    consumer_id varchar2(50 char) not null,
    request_id varchar2(50 char) not null,
    payload clob
);
-- rollback drop table consumer_inbox;

--changeset trangelier:consumer-inbox-pk
alter table consumer_inbox
add constraint consumer_inbox_pk primary key (consumer_id, request_id);
--rollback alter table consumer_inbox drop constraint consumer_inbox_pk;

