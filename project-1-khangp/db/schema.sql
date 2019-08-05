drop table if exists Users;
drop table if exists Reimbursments;

create table Users(
	id serial primary key,
    username varchar not null,
    password varchar not null,
    userType text not null,
    firstName text not null,
    lastName text not null
);


create table Reimbursements(
    id serial primary key,
    username varchar not null,
	cost numeric(10,2),
	reimbursementType text not null,
	reimbursementStatus varchar not null
);

insert into Reimbursements(username, cost, reimbursementType, reimbursementStatus) values ('employee', 130, 'hotels', 'Submitted');
insert into Reimbursements(username, cost, reimbursementType, reimbursementStatus) values ('khangp', 20, 'food', 'Submitted');
insert into Reimbursements(username, cost, reimbursementType, reimbursementStatus) values ('khangp', 50, 'gas', 'Submitted');

insert into Users(username,password,userType,firstName,lastName) values('khangp','password','employee','khang','pham' );
insert into Users(username,password,userType,firstName,lastName) values('employee','password','employee','emp','ployee' );
insert into Users(username,password,userType,firstName,lastName) values('manager','password','manager','man','ager' );
insert into Users(username,password,userType,firstName,lastName) values('KPHAM','password','manager','K','PHAM' );