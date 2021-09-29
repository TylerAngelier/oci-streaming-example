alter session set container=ORCLPDB1;

declare
    l_count number;
    l_tablespace varchar2(100 char) := 'USERS';
begin

    select count('x')
    into l_count
    from all_users
    where username = 'STREAMING';

    if (l_count = 0) then
        execute immediate '
            create user streaming identified by "streaming"
        ';
    end if;

end;
/