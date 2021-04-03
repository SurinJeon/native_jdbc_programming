select user(), database();

show tables; -- 세 개의 테이블 들어와있어야함

desc title;
desc department;
desc employee;

select empno, empname, title, manager, salary, dept from employee;

select deptNo, deptName, floor from department;

select tno,tname from title;

select tno, tname
  from title
 where tno = 3;
 
select * from title;

insert into title values (6, '인턴');
 

delete from title where tno = 6;


update title set tname = '계약직' where tno = 6;
select * from title;


update department set deptname = '마케팅' where deptno = 2;
update department set deptname = '기획' where deptno = 2;


select * from department;
select * from employee;
select * from title;



create or replace view vm_employee (empno, empname, tno, tname, manager, mname, salary, deptno, deptname, floor)
    as
select e.empno, e.empname, t.tno, t.tname, m.empno, m.empname, e.salary, d.deptNo, d.deptName, d.floor
    from employee e join title t on e.title = t.tno left join employee m on e.manager = m.empno join department d on e.dept = d.deptNo;
    
select e.*, t.*, m.empno, m.empname, d.* 
  from employee e join title t on e.title = t.tno left join employee m on e.manager = m.empno join department d on e.dept = d.deptNo;
 
 
select empno, empname, tno, tname, manager, mname, salary, deptno, deptname, floor from vm_employee;

-- 20210222 view
create or replace view vw_full_emp
as
select e.empno,
       e.empname,
       t.tno as 'title_no',
       t.tname as 'title_name',
       e.manager as 'manager_no',
       m.empname as 'manager_name',
       e.salary,
       d.deptNo,
       d.deptName,
       d.floor
  from employee e join title t on e.title = t.tno left join employee m on e.manager = m.empno join department d on e.dept = d.deptNo;
  
 
 select empno, empname, title_no, title_name, manager_no, manager_name, salary, deptNo, deptname, floor
   from vw_full_emp;
   
  
 select empno, empname, title as title_no, manager as manager_no, salary, dept as deptNo 
   from employee;
   
insert into employee
values (1010, '김바둑', 5, 4377, 2000000, 1);

update employee set dept = 3 where empno = 1010;

delete from employee where empno = 1010;

select * from department;
select * from title;

delete from department where deptno = 5;
delete from title where tno = 6;

-- 부서가 1인 사원 정보를 출력
select *
  from department d join employee e on d.deptNo = e.dept 
 where d.deptNo  = 1;

-- subquery가 활용도가 더 높다
select empno, empname, title, manager, salary, dept 
  from employee
 where dept = (select deptno from department where deptNo = 1);


select * from title;

update title set tname='사장' where tno=1;
update title set tname='부장' where tno=2;


