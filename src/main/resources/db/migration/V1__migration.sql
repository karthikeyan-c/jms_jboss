create table structure (
    struct_id varchar(25) primary key NOT NULL,
    struct_crncy varchar(3) NOT NULL,
    struct_type char NOT NULL
) engine=InnoDB default charset=utf8;
insert into structure values ("SG00001","USD","B");
insert into structure values ("SG00002","USD","D");
insert into structure values ("SG00003","USD","N");
insert into structure values ("SG00004","USD","X");
insert into structure values ("SG00005","USD","C");

create table linkage (
    child_acct varchar(35) primary key NOT NULL,
    parent_acct varchar(35) NOT NULL,
    struct_id varchar(25) NOT NULL
) engine=InnoDB default charset=utf8;
insert into linkage values ("A001","A003","SG00001");
insert into linkage values ("A002","A003","SG00001");
insert into linkage values ("A004","A006","SG00001");
insert into linkage values ("A005","A006","SG00001");
insert into linkage values ("A003","A007","SG00001");
insert into linkage values ("A006","A007","SG00001");

create table sub_group_master (
    sub_group_id INT PRIMARY KEY not null,
    group_type CHAR not null
) engine=InnoDB default charset=utf8;
insert into sub_group_master values ("1","N");
insert into sub_group_master values ("2","N");
insert into sub_group_master values ("3","H");

create table sub_group_acct (
    acct_id varchar(35) NOT NULL,
    sub_group_id INT not null,
    CONSTRAINT PRIMARY KEY (acct_id, sub_group_id)
) engine=InnoDB default charset=utf8;
insert into sub_group_acct values ("A001","1");
insert into sub_group_acct values ("A002","1");
insert into sub_group_acct values ("A003","1");
insert into sub_group_acct values ("A004","2");
insert into sub_group_acct values ("A005","2");
insert into sub_group_acct values ("A006","2");
insert into sub_group_acct values ("A003","3");
insert into sub_group_acct values ("A006","3");
insert into sub_group_acct values ("A007","3");

create table account (
    acct_id varchar(35) primary key NOT NULL,
    acct_crncy varchar(3) NOT NULL,
    balance double(5,0) NOT NULL,
    un_utilized_amt double(5,0) NOT NULL
) engine=InnoDB default charset=utf8;
insert into account values ("A001","USD","100","0");
insert into account values ("A002","USD","100","0");
insert into account values ("A003","USD","100","0");
insert into account values ("A004","USD","100","0");
insert into account values ("A005","SGD","2","0");
insert into account values ("A006","SGD","5","0");
insert into account values ("A007","SGD","677","0");
insert into account values ("A008","SGD","555","0");
insert into account values ("A009","HKD","23423","0");
insert into account values ("A010","HKD","3456","0");
insert into account values ("A011","HKD","788","0");
insert into account values ("A012","USD","900","0");
insert into account values ("A013","USD","23","0");

create table limits (
    limit_id Int primary key NOT NULL,
    limit_type varchar(10) NOT NULL,
    limit_crncy varchar(3) NOT NULL,
    list_of_accts varchar(100) NOT NULL,
    limit_amt double(5,0),
    un_utilized_amt double(5,0)
) engine=InnoDB default charset=utf8;
insert into limits values ("1","ACCT","USD","A001|A002","123","123");
insert into limits values ("2","CRNCY","USD","A001|A002","456","456");
insert into limits values ("3","ACCT","USD","A001|A002","789","789");
insert into limits values ("4","XYZ","USD","A001|A002","321","321");