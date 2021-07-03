CREATE DATABASE lmsng;

USE lmsng;

show tables;
show index from account;
#drop database lmsng;

select * from account;
select * from limits;

update account set balance=115 where acct_id='A005';

drop table account;
drop table limits;
select * from flyway_schema_history;
drop table flyway_schema_history;

