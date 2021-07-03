create table fxrate (
    from_crncy varchar(3) NOT NULL,
    to_crncy varchar(3) NOT NULL,
    rate DOUBLE NOT NULL,
    CONSTRAINT PRIMARY KEY (from_crncy, to_crncy)
) engine=InnoDB default charset=utf8;
insert into fxrate values ("USD","SGD",1.320000);
insert into fxrate values ("SGD","USD",0.760000);
insert into fxrate values ("USD","HKD",7.760000);
insert into fxrate values ("HKD","USD",0.130000);
insert into fxrate values ("HKD","SGD",0.170000);
insert into fxrate values ("SGD","HKD",5.860000);