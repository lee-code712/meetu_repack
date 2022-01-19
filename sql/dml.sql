-- ddl test를 위한 insert 레코드 예
insert into college (college_no, college_name) values ('0001', '인문대학');
insert into department (dept_no, dept_name, college_no) values ('0101', '국어국문학과', '0001');
insert into member (member_no, role, email, dept_no, name) values ('01010001', 1, 'hello@naver.com', '0101', '샘플');
insert into member (member_no, role, email, dept_no, name) values ('01020002', 0, 'bye@naver.com', '0101', '예시');
insert into office (office_no, longitude, latitude) values ('인문101', 37.607365317250675, 127.042562376703);
insert into student (year, member_no) values (3, '01010001');
insert into professor (member_no, major, office_no) values ('01020002', '고전문학(한문학)', '인문101');
insert into service_user (user_id, password, phone, member_no) values ('ex01020002', 'kim111', '010-1234-5678', '01020002');
insert into service_user (user_id, password, phone, member_no) values ('ex01010001', 'kim000', '010-1234-5678', '01010001');
insert into course (course_no, title, dept_no) values ('국문K0001', '고전문학의 이해', '0101');
insert into class (course_no, prof_no) values ('국문K0001', '01020002');
insert into alert_type (type_no, title) values (0, '상담 예약');
insert into consultable_time (able_date, able_time, prof_id) values (1, '13:00~16:00', 'ex01020002');
insert into consult (consult_id, start_date, end_date, reason, type, state, prof_id, stu_id, cancel_msg) values (consult_seq.NEXTVAL, TO_DATE('2021-03-10 13:00:00','YY/MM/DD HH24:MI:SS'), TO_DATE('2021-03-10 14:00:00','YY/MM/DD HH24:MI:SS'), '전담 교수 면담', 1, 3, 'ex01020002', 'ex01010001', '취소하는 이유');
insert into consult_record (content, consult_id) values ('상담 내용', '10005');
insert into alert (alert_id, alert_date, alert_msg, is_read, user_id, type_no) values ('0000', TO_DATE('2021-03-10 13:00:00','YY/MM/DD HH24:MI:SS'), '알림을 확인해주세요.', 1, 'ex01010001', 0);
insert into consult_backup (backup_id, start_date, end_date, reason, type, content, consult_id, stu_no, prof_no) values ('01010001', TO_DATE('2021-03-10 13:00:00','YY/MM/DD HH24:MI:SS'), TO_DATE('2021-03-10 14:00:00','YY/MM/DD HH24:MI:SS'), '이유', 1, '상담 내용', '10005', '01010001', '01020002');
insert into message (msg_id, sent_date, content, is_read, send_id, recv_id) values (message_seq.NEXTVAL, TO_DATE('2021-03-10 13:00:00','YY/MM/DD HH24:MI:SS'), '상담 내용', 0, 'ex01020002', 'ex01010001');
