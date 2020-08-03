/* DB ���� ���� */

-- ȸ��
DROP TABLE RM_Member;
CREATE TABLE RM_Member (
    member_no NUMBER(8),
    member_levelexp NUMBER(8),
    member_id VARCHAR2(80),
    member_pw VARCHAR2(100),
    member_nickname VARCHAR2(30),
    member_fullname VARCHAR2(60),
    member_gender VARCHAR2(4),
    member_birth VARCHAR2(20),
    member_phone VARCHAR2(20),
    member_joindate DATE,
    sessionKey VARCHAR2(50),
    sessionLimit TIMESTAMP,
    member_status VARCHAR2(20),
    member_login_trial NUMBER(4)
);

DROP SEQUENCE RM_Member_Seq;
CREATE SEQUENCE RM_Member_Seq;

-- SELECT * FROM RM_Member ORDER BY member_no DESC;
 
-- UPDATE RM_Member SET member_levelexp = member_levelexp + 10 WHERE member_no = 5; 

-- ȸ�� ���� Ű ����
    -- SELECT RM_Member_Seq.nextval FROM DUAL;

-- ȸ�� - ���� ī�װ�
DROP TABLE RM_MB_Level_Category;
CREATE TABLE RM_MB_Level_Category(
    level_category_no NUMBER(8),
    minimum_exp NUMBER(8),
    maximum_exp NUMBER(8)
);

DROP SEQUENCE RM_MB_Level_Category_seq;
CREATE SEQUENCE RM_MB_Level_Category_seq;

INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 0, 19);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 20, 49);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 50, 99);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 100, 199);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 200, 999);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 1000, 4999);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 5000, 19999);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 20000, 99999);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 100000, 999999);
INSERT INTO RM_MB_Level_Category VALUES(RM_MB_Level_Category_seq.nextval, 1000000, 10000000);

-- SELECT * FROM RM_MB_Level_Category ORDER BY level_category_no;

-- SELECT level_category_no FROM RM_Member, RM_MB_Level_Category WHERE member_no=2 AND member_levelexp BETWEEN minimum_exp AND maximum_exp;

-- ȸ�� - ���� ���� 
DROP TABLE RM_Member_Auth;
CREATE TABLE RM_Member_Auth(
    auth_no NUMBER(8) PRIMARY KEY,
    member_no NUMBER(8),
    auth_key VARCHAR2(100),
    auth_certification VARCHAR2(4)

);

DROP SEQUENCE RM_Member_Auth_Seq;
CREATE SEQUENCE RM_Member_Auth_Seq;

-- SELECT * FROM RM_Member_Auth;


-- ȸ�� - ����� �Ű�
DROP TABLE RM_MB_Report;
CREATE TABLE RM_MB_Report (
    report_no NUMBER(8),
    reporting_member_no NUMBER(8),
    reported_member_no NUMBER(8),
    report_category_no NUMBER(8),
    report_time DATE,
    report_status VARCHAR2(4)
);

DROP SEQUENCE RM_MB_Report_seq;
CREATE SEQUENCE RM_MB_Report_seq;

--INSERT INTO RM_MB_Report VALUES(RM_MB_Report_seq.nextval, 2, 1, 1, SYSDATE);
--select * from RM_MB_Report;

-- ����� �Ű� ����Ʈ ����¡
--SELECT report_no, reporting_member_id, reported_member_id, report_time, report_category_name, report_status
--FROM(
--     SELECT ROWNUM RNUM, T1.* 
--	 FROM
--            (SELECT rp.report_no report_no, mb1.member_id reporting_member_id, mb2.member_id reported_member_id, rp.report_time report_time, cg.report_category_name report_category_name, rp.report_status
--            FROM RM_MB_Report rp, RM_member mb1, RM_member mb2, RM_MB_RP_Category cg
--            WHERE rp.reporting_member_no = mb1.member_no 
--            AND rp.reported_member_no = mb2.member_no
--            AND rp.report_category_no = cg.report_category_no
--            ORDER BY rp.report_no DESC) t1
--    )t2
--WHERE T2.RNUM >= (1-1)*10+1 AND T2.RNUM <= 1*10;

-- ȸ�� - ����� �Ű� - ī�װ�
DROP TABLE RM_MB_RP_Category;
CREATE TABLE RM_MB_RP_Category (
    report_category_no NUMBER(8),
    report_category_name VARCHAR2(60)
);

DROP SEQUENCE RM_MB_RP_Category_seq;
CREATE SEQUENCE RM_MB_RP_Category_seq;

INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '�弳/�νŰ���');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '�Ǹ�ħ�� �� ���̹� ������');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '���');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, 'ȫ��/���������� �Խ�');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '�ҹ����� �Խ�');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '��Ÿ');


-- ������/����
DROP TABLE RM_Locationboard;
CREATE TABLE RM_Locationboard (
    locationboard_no NUMBER(8),
    member_no NUMBER(8),
    province_category_no NUMBER(8),
    location_category_no NUMBER(8),
    cost_category_no NUMBER(8),
    type_category_no NUMBER(8),
    time_category_no NUMBER(8),
    mood_category_no NUMBER(8),
    locationboard_title VARCHAR2(200),
    locationboard_content VARCHAR2(1000),
    locationboard_readcount NUMBER(8),
    locationboard_cost NUMBER(8),
    locationboard_time NUMBER(8),
    locationboard_storename VARCHAR2(100),
    locationboard_storeaddress VARCHAR2(100),
    locationboard_coordinate VARCHAR2(100),
    locationboard_storenumber VARCHAR2(20),
    locationboard_writedate DATE
);

DROP SEQUENCE RM_Locationboard_Seq;
CREATE SEQUENCE RM_Locationboard_Seq;

--SELECT * FROM RM_Locationboard;

-- ������/���� - ���� ���ε�
DROP TABLE RM_LC_Upload_File;
CREATE TABLE RM_LC_Upload_File(
    locationboard_file_no NUMBER(8),
    locationboard_no NUMBER(8),
    locationboard_originalname VARCHAR2(200),
    locationboard_file_link_path VARCHAR2(100),
    locationboard_file_real_path VARCHAR2(200),
    locationboard_file_upload_date DATE
);

DROP SEQUENCE RM_LC_Upload_File_seq;
CREATE SEQUENCE RM_LC_Upload_File_seq;

-- SELECT * FROM RM_LC_Upload_File ORDER BY locationboard_file_no DESC;
 
 --	   SELECT *  FROM RM_LC_Upload_File  WHERE locationboard_no = 7  AND rownum=1;

-- ������/���� - ���� ī�װ�
DROP TABLE RM_Province_Category;
CREATE TABLE RM_Province_Category(
    province_category_no NUMBER(8),
    province_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_Province_Category_Seq;
CREATE SEQUENCE RM_Province_Category_Seq;

INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '����Ư����');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '�λ걤����');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '��õ������');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '�뱸������');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '����������');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '���ֱ�����');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '��걤����');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '��⵵');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '������');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '��û��');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '����');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '���');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '���ֵ�');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '������');
INSERT INTO RM_Province_Category VALUES(15, '��������');

-- ������/���� - �з� ī�װ�
DROP TABLE RM_LC_Category;
CREATE TABLE RM_LC_Category(
    location_category_no NUMBER(8),
    location_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_LC_Category_seq;
CREATE SEQUENCE RM_LC_Category_seq;

INSERT INTO RM_LC_Category VALUES(RM_LC_Category_seq.nextval, '������');
INSERT INTO RM_LC_Category VALUES(RM_LC_Category_seq.nextval, '����');

-- SELECT * FROM RM_LC_Category;

-- ������/���� - ��� ī�װ�
DROP TABLE RM_LC_Cost_Category;
CREATE TABLE RM_LC_Cost_Category(
    cost_category_no NUMBER(8),
    cost_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_LC_Cost_Category_seq;
CREATE SEQUENCE RM_Cost_LC_Category_seq;

INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '1���� �̸�');
INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '1���� ~ 3����');
INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '3���� ~ 5����');
INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '5���� �̻�');

-- SELECT * FROM RM_LC_Cost_Category;

-- ������/���� - ���� ī�װ�
DROP TABLE RM_Type_Category;
CREATE TABLE RM_Type_Category(
    type_category_no NUMBER(8),
    type_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_Type_Category_seq;
CREATE SEQUENCE RM_Type_Category_seq;

INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, 'ȥ��');
INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, 'Ŀ��');
INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, '������');
INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, 'ģ����');

-- SELECT * FROM RM_Type_Category;

-- ������/���� - �ҿ�ð� ī�װ�
DROP TABLE RM_LC_Time_Category;
CREATE TABLE RM_LC_Time_Category(
    time_category_no NUMBER(8),
    time_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_LC_Time_Category_seq;
CREATE SEQUENCE RM_LC_Time_Category_seq;

INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '1�ð� �̸�');
INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '1 ~ 3�ð�');
INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '3 ~ 5�ð�');
INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '5�ð� �̻�');

-- SELECT * FROM RM_LC_Time_Category;

-- ������/���� - ������ ī�װ�
DROP TABLE RM_Mood_Category;
CREATE TABLE RM_Mood_Category(
    mood_category_no NUMBER(8),
    mood_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_Mood_Category_seq;
CREATE SEQUENCE RM_Mood_Category_seq;

INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '������');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '�ò�����');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '�ڿ�ģȭ��');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, 'Ȱ����');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '������ ����');

-- SELECT * FROM RM_Mood_Category;


-- ������/���� - ���� �߰�
DROP TABLE RM_LC_Myfavorite;
CREATE TABLE RM_LC_Myfavorite(
    lc_myfavorite_no NUMBER(8),
    member_no NUMBER(8),
    locationboard_no NUMBER(8)
);

DROP SEQUENCE RM_LC_Myfavorite_seq;
CREATE SEQUENCE RM_LC_Myfavorite_seq;

--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 1, 1);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 1, 2);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 1, 4);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 1, 5);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 2, 1);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 2, 2);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 2, 3);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 2, 4);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 2, 5);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 3, 2);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 3, 3);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 3, 4);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 3, 5);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 4, 1);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 4, 2);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 4, 3);
--INSERT INTO RM_LC_Myfavorite VALUES(RM_LC_Myfavorite_seq.nextval, 4, 4);

-- SELECT * FROM RM_LC_Myfavorite ORDER BY lc_myfavorite_no DESC;

-- ������/���� - ���ƿ�
DROP TABLE RM_LC_Like;
CREATE TABLE RM_LC_Like(
    locationboard_like_no NUMBER(8),
    member_no NUMBER(8),
    locationboard_no NUMBER(8)
);

DROP SEQUENCE RM_LC_Like_seq;
CREATE SEQUENCE RM_LC_Like_seq;

--INSERT INTO RM_LC_Like VALUES(RM_LC_Like_seq.nextval, 4, 3);

-- SELECT * FROM RM_LC_Like;

-- locationboard_no ������ ���� ���� locationboard_no ��������
--SELECT locationboard_no FROM RM_LC_Like GROUP BY locationboard_no HAVING COUNT(locationboard_no) = (SELECT MAX(COUNT(locationboard_no)) maxcount FROM RM_LC_Like GROUP BY locationboard_no);

-- ����ִ� ���� Null������, Null���� 0���� ��ȯ
-- ���ƿ� ���� ���� �� ����
--SELECT NVL(MAX(locationboard_no), 0) locationboard_no 
--FROM (
--            SELECT locationboard_no
  --          FROM RM_LC_Like
    --        GROUP BY locationboard_no
      --      HAVING COUNT(locationboard_no) = 
        --                                                    	(SELECT MAX(COUNT(locationboard_no)) maxcount
          --                                                  	 FROM RM_LC_Like
            --                                                     GROUP BY locationboard_no)) t2;
-- �ֽű� ����                     
--SELECT NVL(max(locationboard_no), 0) locationboard_no
		--	FROM RM_Locationboard;

-- ������/���� - ���
DROP TABLE RM_LC_Reply;
CREATE TABLE RM_LC_Reply(
    locatinoboard_reply_no NUMBER(8),
    member_no NUMBER(8),
    locationboard_no NUMBER(8),
    locationboard_reply_content VARCHAR2(500),
    locationboard_reply_writedate DATE
);

DROP SEQUENCE RM_LC_Reply_seq;
CREATE SEQUENCE RM_LC_Reply_seq;

-- SELECT * FROM RM_LC_Reply WHERE locatinoboard_reply_no = 1;  

-- SELECT * FROM RM_LC_Reply ORDER BY locatinoboard_reply_no DESC;


-- ��Ʈ ���� 
DROP TABLE RM_Routeboard;
CREATE TABLE RM_Routeboard(
    routeboard_no NUMBER(8),
    member_no NUMBER(8),
    time_category_no NUMBER(8),
    cost_category_no NUMBER(8),
    routeboard_title VARCHAR2(200),
    routeboard_content VARCHAR2(1000),
    routeboard_readcount NUMBER(8),
    routeboard_cost NUMBER(8),
    routeboard_time NUMBER(8),
    routeboard_share VARCHAR2(4),
    routeboard_writedate DATE
);

DROP SEQUENCE RM_Routeboard_seq;
CREATE SEQUENCE RM_Routeboard_seq;

-- SELECT RM_Routeboard_seq.nextval 	FROM DUAL;

--INSERT INTO RM_Routeboard VALUES(RM_Routeboard_seq.nextval, 1, 1, 1, '��Ʈ ���� ����1', '��Ʈ ���� ����1', 0, 320, 63000, 'Y', SYSDATE);

-- SELECT * FROM RM_Routeboard ORDER BY routeboard_no DESC;

-- ��Ʈ ���� - ���� ���ε�
DROP TABLE RM_RT_Upload_File;
CREATE TABLE RM_RT_Upload_File(
    routeboard_file_no NUMBER(8),
    routeboard_no NUMBER(8),
    routeboard_originalname VARCHAR2(200),
    routeboard_file_link_path VARCHAR2(100),
    routeboard_file_real_path VARCHAR2(200),
    routeboard_file_upload_date DATE
);

DROP SEQUENCE RM_RT_Upload_File_seq;
CREATE SEQUENCE RM_RT_Upload_File_seq;

-- SELECT * FROM RM_RT_Upload_File;

-- ��Ʈ ���� - ��� ī�װ�
DROP TABLE RM_RT_Cost_Category;
CREATE TABLE RM_RT_Cost_Category(
    cost_category_no NUMBER(8),
    cost_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_RT_Cost_Category_seq;
CREATE SEQUENCE RM_RT_Cost_Category_seq;

INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '3���� �̸�');
INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '3 ~ 6����');
INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '6 ~ 9����');
INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '9���� �̻�');

-- SELECT * FROM RM_RT_Cost_Category;

-- ��Ʈ ���� - �ҿ�ð� ī�װ�
DROP TABLE RM_RT_Time_Category;
CREATE TABLE RM_RT_Time_Category(
    time_category_no NUMBER(8),
    time_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_RT_Time_Category_seq;
CREATE SEQUENCE RM_RT_Time_Category_seq;

INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '3�ð� �̸�');
INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '3 ~ 6�ð�');
INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '6 ~ 9�ð�');
INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '9�ð� �̻�');

-- SELECT * FROM RM_RT_Time_Category;
  
    
-- ��Ʈ ���� - ���� �߰�
DROP TABLE RM_RT_Myfavorite;
CREATE TABLE RM_RT_Myfavorite(
    cr_myfavorite_no NUMBER(8),
    member_no NUMBER(8),
    routeboard_no NUMBER(8)
);

DROP SEQUENCE RM_RT_Myfavorite_seq;
CREATE SEQUENCE RM_RT_Myfavorite_seq;

-- SELECT * FROM RM_RT_Myfavorite;

-- ��Ʈ ���� - ���ƿ� 
DROP TABLE RM_RT_Like;
CREATE TABLE RM_RT_Like(
    routeboard_like_no NUMBER(8),
    member_no NUMBER(8),
    routeboard_no NUMBER(8)    
);

DROP SEQUENCE RM_RT_Like_seq;
CREATE SEQUENCE RM_RT_Like_seq;

-- SELECT * FROM RM_RT_Like;


-- ��Ʈ ���� - ���
DROP TABLE RM_RT_Reply;
CREATE TABLE RM_RT_Reply(
    routeboard_reply_no NUMBER(8),
    member_no NUMBER(8),
    routeboard_no NUMBER(8),
    routeboard_reply_content VARCHAR2(500),
    routeboard_reply_writedate DATE
);

DROP SEQUENCE RM_RT_Reply_seq;
CREATE SEQUENCE RM_RT_Reply_seq;

-- SELECT * FROM RM_RT_Reply;


--SELECT *
--FROM (
--            SELECT ROWNUM rnum, t1.*
--            FROM (
--                        SELECT  rb.routeboard_no, rb.routeboard_title, rb.routeboard_content, rb.routeboard_writedate
--                        FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb
--                        WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no
--                        ORDER BY rb.routeboard_no DESC) t1) t2
--WHERE t2.rnum >= (1-1)*12+1 AND t2.rnum <= (1-1)*12+12;

-- SELECT * FROM (SELECT ROWNUM rnum, t1.* FROM (SELECT rb.routeboard_no, rb.routeboard_title, rb.routeboard_content, rb.routeboard_writedate FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no ORDER BY rb.routeboard_no DESC) t1) t2 WHERE t2.rnum >= (1-1)*12+1 AND t2.rnum <= (1-1)*12+12;      
                   
-- SELECT  distinct(rb.routeboard_no), rb.routeboard_title, rb.routeboard_content, rb.routeboard_writedate
--                        FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb
--                        WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no
--                        ORDER BY rb.routeboard_no DESC;
                   
-- SELECT count(distinct(rb.routeboard_no)) FROM RM_routeboard rb, RM_Location_Route_Link lk, RM_Locationboard lb WHERE rb.routeboard_no = lk.routeboard_no AND lb.locationboard_no = lk.locationboard_no AND lb.province_category_no IN (1) ORDER BY rb.routeboard_no DESC;

-- select * from RM_Routeboard;


-- ��ũ(��ҿ� ��Ʈ�� ����)
DROP TABLE RM_Location_Route_Link;
CREATE TABLE RM_Location_Route_Link(
    link_no NUMBER(8),
    locationboard_no NUMBER(8),
    routeboard_no NUMBER(8),
    link_cost NUMBER(8),
    link_time NUMBER(8),
    link_order NUMBER(8)
);

DROP SEQUENCE RM_Location_Route_Link_Seq;
CREATE SEQUENCE RM_Location_Route_Link_Seq;

-- ��ũ Ű ����
    -- SELECT RM_Location_Route_Link_Seq.nextval FROM DUAL;

-- SELECT * FROM RM_Location_Route_Link ORDER BY link_no DESC;

-- select * from rm_locationboard;


-- ������/���� - �Խñ� �Ű�
DROP TABLE RM_LC_Report;
CREATE TABLE RM_LC_Report (
    report_no NUMBER(8),
    member_no NUMBER(8),
    locationboard_no NUMBER(8),
    report_category_no NUMBER(8),
    report_time DATE,
    report_status VARCHAR2(4)
);

DROP SEQUENCE RM_LC_Report_seq;
CREATE SEQUENCE RM_LC_Report_seq;
--select count(report_no) from RM_LC_Report;
--select * from RM_LC_Report;
--insert into RM_LC_Report values(RM_LC_Report_seq.nextval, 1,7,2,SYSDATE,'N');

-- ������/���� �Ű� ����Ʈ ����¡
--SELECT report_no, member_id, locationboard_title, report_time, report_category_name, report_status
--FROM(
--     SELECT ROWNUM RNUM, T1.* 
--	 FROM
--            (SELECT rp.report_no report_no, mb.member_id member_id, lb.locationboard_title locationboard_title, rp.report_time report_time, cg.report_category_name report_category_name, rp.report_status
--            FROM RM_LC_Report rp, RM_member mb, RM_locationboard lb, RM_LC_RT_RP_Category cg
--            WHERE rp.member_no = mb.member_no 
--            AND rp.locationboard_no = lb.locationboard_no
--            AND rp.report_category_no = cg.report_category_no
--            ORDER BY rp.report_no DESC) t1
--    )t2
--WHERE T2.RNUM >= (1-1)*10+1 AND T2.RNUM <= 1*10;


-- ��Ʈ ���� - �Խñ� �Ű�
DROP TABLE RM_RT_Report;
CREATE TABLE RM_RT_Report (
    report_no NUMBER(8),
    member_no NUMBER(8),
    routeboard_no NUMBER(8),
    report_category_no NUMBER(8),
    report_time DATE,
    report_status VARCHAR2(4)
);

DROP SEQUENCE RM_RT_Report_seq;
CREATE SEQUENCE RM_RT_Report_seq;

DROP SEQUENCE RM_LC_RT_RP_Category_seq;
CREATE SEQUENCE RM_LC_RT_RP_Category_seq;

--select * from RM_RT_Report;

-- ��Ʈ �Ű� ����Ʈ ����¡
--SELECT report_no, member_id, routeboard_no, routeboard_title, report_time, report_category_name, report_status
--FROM(
--     SELECT ROWNUM RNUM, T1.* 
--	 FROM
--            (SELECT rp.report_no report_no, mb.member_id member_id, rb.routeboard_no routeboard_no, rb.routeboard_title routeboard_title, rp.report_time report_time, cg.report_category_name report_category_name, rp.report_status
--            FROM RM_RT_Report rp, RM_member mb, RM_routeboard rb, RM_LC_RT_RP_Category cg
--            WHERE rp.member_no = mb.member_no 
--            AND rp.routeboard_no = rb.routeboard_no
--            AND rp.report_category_no = cg.report_category_no
--            ORDER BY rp.report_no DESC) t1
--    )t2
--WHERE T2.RNUM >= (1-1)*10+1 AND T2.RNUM <= 1*10;

-- �Ű� - �Խñ� �Ű� - ī�װ�
DROP TABLE RM_LC_RT_RP_Category;
CREATE TABLE RM_LC_RT_RP_Category (
    report_category_no NUMBER(8),
    report_category_name VARCHAR2(60)
);

INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '��������/ȫ����');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '������������');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '�ҹ�����');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '������/������');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '�弳/�νŰ���');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '��Ÿ');


-- ������
DROP TABLE RM_Admin;
CREATE TABLE RM_Admin (
    admin_no NUMBER(8) PRIMARY KEY,
    admin_id VARCHAR2(80),
    admin_pw VARCHAR2(100),
    admin_nickname VARCHAR2(30),
    admin_fullname VARCHAR2(60),
    admin_gender VARCHAR2(4),
    admin_birth VARCHAR2(20),
    amdin_phone VARCHAR2(20),
    admin_joindate DATE,
    admin_status VARCHAR2(10),
    admin_login_trial NUMBER(4)
);

DROP SEQUENCE RM_Admin_seq;
CREATE SEQUENCE RM_Admin_seq;

--SELECT * FROM RM_Admin;


-- ������ - �α��� ���� ��(������ ���)
DROP TABLE RM_Login_data;
CREATE TABLE RM_Login_data (
    login_no NUMBER(8) PRIMARY KEY,
    member_no NUMBER(8),
    login_date DATE,
    login_hit NUMBER(8)
);

DROP SEQUENCE RM_Login_data_seq;
CREATE SEQUENCE RM_Login_data_seq;

--SELECT * FROM RM_Login_data;


-- ������ - ��������
DROP TABLE RM_CS_Notice;
CREATE TABLE RM_CS_Notice (
notice_no NUMBER(8) PRIMARY KEY,
admin_no NUMBER(8),
notice_category_no NUMBER(8),
notice_title VARCHAR2(200),
notice_content VARCHAR2(1000),
notice_writedate DATE
);
DROP SEQUENCE RM_CS_Notice_seq;
CREATE SEQUENCE RM_CS_Notice_seq;


-- ������ - ���� ���� ����  (FAQ)
DROP TABLE RM_CS_Faq;
CREATE TABLE RM_CS_Faq (
faq_no NUMBER(8) PRIMARY KEY,
admin_no NUMBER(8),
faq_category_no NUMBER(8),
faq_title VARCHAR2(200),
faq_content VARCHAR2(1000),
faq_writedate DATE
);

DROP SEQUENCE RM_CS_Faq_seq;
CREATE SEQUENCE RM_CS_Faq_seq;
INSERT INTO RM_CS_Faq VALUES (RM_CS_Faq_seq.nextval, 1, 1, '��Ʈ���Դϴ�', '�ȳ��ϼ���');


-- ������ - ���� ���� ���� - ī�װ�
DROP TABLE RM_CS_FAQ_Category;
CREATE TABLE RM_CS_FAQ_Category(
    faq_category_no NUMBER(8),
    faq_category_name VARCHAR2(30)
);

INSERT INTO RM_CS_FAQ_Category VALUES (1,'������/����');
INSERT INTO RM_CS_FAQ_Category VALUES (2,'��Ʈ����');
INSERT INTO RM_CS_FAQ_Category VALUES (3,'���ǰ���/��Ʈ');
INSERT INTO RM_CS_FAQ_Category VALUES (4,'�����Խ���');
INSERT INTO RM_CS_FAQ_Category VALUES (5,'��������');

DROP SEQUENCE RM_CS_FAQ_Category_seq;
CREATE SEQUENCE RM_CS_FAQ_Category_seq;

-- SELECT * FROM RM_CS_FAQ_Category;


-- ������ - 1:1 ���ǳ���
DROP TABLE RM_CS_Inquiry;
CREATE TABLE RM_CS_Inquiry (
inquiry_no NUMBER(8) PRIMARY KEY,
member_no NUMBER(8),
inquiry_category_no NUMBER(8),
inquiry_title VARCHAR2(200),
inquiry_content VARCHAR2(1000),
inquiry_writedate DATE,
inquiry_answer_key VARCHAR2(8)
);
DROP SEQUENCE RM_CS_Inquiry_seq;
CREATE SEQUENCE RM_CS_Inquiry_seq;

-- SELECT * FROM RM_CS_Inquiry;


-- ������ - 1:1 ���ǳ��� - �亯   
DROP TABLE RM_CS_Inquiry_Answer;
CREATE TABLE RM_CS_Inquiry_Answer(
    inquiry_answer_no NUMBER(8),
    admin_no NUMBER(8),
    inquiry_no NUMBER(8),
    inquiry_answer_content VARCHAR2(200),
    inquiry_answer_writedate DATE
);

DROP SEQUENCE RM_CS_Inquiry_Answer_seq;
CREATE SEQUENCE RM_CS_Inquiry_Answer_seq;

-- SELECT * FROM RM_CS_Inquiry_Answer;


-- ������ - 1:1 ���ǳ��� - ī�װ�
DROP TABLE RM_CS_Inquiry_Category;
CREATE TABLE RM_CS_Inquiry_Category(
inquiry_category_no NUMBER(8) PRIMARY KEY,
inquiry_category_name VARCHAR2(30)
);

-- 1:1 ���� ī�װ� ���� ��
INSERT INTO RM_CS_Inquiry_Category VALUES (1,'������/����');
INSERT INTO RM_CS_Inquiry_Category VALUES (2,'��Ʈ ����');
INSERT INTO RM_CS_Inquiry_Category VALUES (3,'���� ����/��Ʈ');
INSERT INTO RM_CS_Inquiry_Category VALUES (4,'�����Խ���');
INSERT INTO RM_CS_Inquiry_Category VALUES (5,'����');
INSERT INTO RM_CS_Inquiry_Category VALUES (6,'����');


-- ������ - 1:1 ���ǳ��� - ÷������
DROP TABLE RM_CS_Inquiry_Upload_File;
CREATE TABLE RM_CS_Inquiry_Upload_File (
inquiry_file_no NUMBER(8) PRIMARY KEY,
inquiry_no NUMBER(8),
inquiry_file_link_path VARCHAR2(100),
inquiry_file_real_path VARCHAR2(200),
inquiry_file_upload_date DATE
);

DROP SEQUENCE RM_CS_Inquiry_Upload_File_seq;
CREATE SEQUENCE RM_CS_Inquiry_Upload_File_seq;


-- ����
DROP TABLE RM_NOTE;
CREATE TABLE RM_NOTE(
note_no number(8),
send_member_no number(8),
send_member_id varchar2(80),
recv_member_no number(8),
recv_member_id varchar2(80),
note_title varchar2(200),
note_content varchar2(1000),
note_send_date date,
note_read_date date,
note_send_delete char(1),
note_recv_delete char(1)
);

DROP SEQUENCE RM_NOTE_SEQ;
CREATE SEQUENCE RM_NOTE_SEQ;

--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '�ȳ��ϼ���', '�ȳ��ϼ���, �ݰ����ϴ�', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '�ູ�ϼ���', '������ �ߺ������׿�', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '�ű� �����?', '����ֳ���??', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '�ùٸ� �վı�', '��ħ����', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '�����ΰ���??', '�̹��� �ѹ� �������մϴ�', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '�������� ��õ', '��û�� �����Դϴ�. �� �ѹ� ��������', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', 'test', 'test', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', 'test�� ����', '�ƾƾƾƾƾ�', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', 'Ŀ�� �� ��', '�Ƹ޸�ī��', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '������', '������', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 3, 'test03@naver.com', 1, 'test01@naver.com', '������ �����ؼ� �����̿�', '�����??', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 3, 'test03@naver.com', '�ű�..', '��õ �������Դϴ�.', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 3, 'test03@naver.com', 1, 'test01@naver.com', '��õ ��õ', '���� ��õ', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 3, 'test03@naver.com', '���ߺ���', '�� ���ݿ� �ٸ������� ������', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 3, 'test03@naver.com', 1, 'test01@naver.com', '�߰� ���� ������ ��', '���� �����մϴ�', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 3, 'test03@naver.com', '�ٳ�ͺ���', '���� ���׿�', SYSDATE, NULL, 'N', 'N');


----���� ������ ��� ����
--SELECT * FROM RM_NOTE WHERE SEND_MEMBER_NO = 4 ORDER BY note_no desc;
--SELECT COUNT(*) FROM RM_NOTE WHERE SEND_MEMBER_NO= 4;
----���� ������ ��� ����
--SELECT * FROM RM_NOTE WHERE RECV_MEMBER_NO = 4;
--SELECT * FROM RM_MEMBER WHERE MEMBER_NO = 1;

--SELECT COUNT(*) FROM RM_MEMBER;
--SELECT * 
--FROM
--        (SELECT rownum rnum, t1.*
--        FROM
--                 (SELECT * 
--                 FROM RM_Locationboard
--                 WHERE member_no=1
--                ORDER BY locationboard_no DESC) t1) t2
--WHERE t2.rnum >= (1-1)*12+1 AND t2.rnum <= (1-1)*12+12;
--
--SELECT *  
--FROM         
--         (SELECT rownum r, t1.*    
--         FROM
--                    (SELECT *      
--                    FROM RM_Locationboard    
--                    WHERE member_no=1     AND location_category_no=1     ORDER BY locationboard_no DESC) t1) t2 
--WHERE t2.rnum >= (1-1)*12+1 AND t2.rnum <= (1-1)*12+12;


-- ä��â
DROP TABLE RM_Popchat;
CREATE TABLE RM_Popchat(
    popchat_no NUMBER(8),
    member_no NUMBER(8),
    popchat_content VARCHAR2(500),
    popchat_writedate DATE
);

DROP SEQUENCE RM_Popchat_Seq;
CREATE SEQUENCE RM_Popchat_Seq;

-- SELECT * FROM RM_Popchat ORDER BY popchat_no;   


-- ä��â - ���� ���ε�
DROP TABLE RM_PC_Upload_File;
CREATE TABLE RM_PC_Upload_File(
    popchat_file_no NUMBER(8),
    popchat_no NUMBER(8),
    popchat_file_originalname VARCHAR2(200),
    popchat_file_link_path VARCHAR2(100),
    popchat_file_real_path VARCHAR2(200),
    popchat_file_upload_date DATE
);

DROP SEQUENCE RM_PC_Upload_File_Seq;
CREATE SEQUENCE RM_PC_Upload_File_seq;

-- SELECT * FROM RM_PC_Upload_File ORDER BY popchat_no;


-- ���� �Խ���
DROP TABLE RM_Freeboard;
CREATE TABLE RM_Freeboard(
    freeboard_no NUMBER(8),
    member_no NUMBER(8),
    freeboard_title VARCHAR2(200),
    freeboard_content VARCHAR2(1000),
    freeboard_readcount NUMBER(8),
    freeboard_writedate DATE
);

DROP SEQUENCE RM_Freeboard_seq;
CREATE SEQUENCE RM_Freeboard_seq;

--SELECT * FROM RM_Freeboard;


-- ���� �Խ��� - ��� 
DROP TABLE RM_FB_Reply;
CREATE TABLE RM_FB_Reply(
    freeboard_reply_no NUMBER(8),
    member_no NUMBER(8),
    freeboard_no NUMBER(8),
    freeboard_reply_content VARCHAR2(500),
    freeboard_reply_writedate DATE
);

DROP SEQUENCE RM_FB_Reply_seq;
CREATE SEQUENCE RM_FB_Reply_seq;

-- SELECT * FROM RM_FB_Reply;


-- ���� �Խ��� - ���� ���ε� 
DROP TABLE RM_FB_Upload_File;
CREATE TABLE RM_FB_Upload_File(
    freeboard_file_no NUMBER(8),
    freeboard_no NUMBER(8),
    freeboard_originalname VARCHAR2(200),
    freeboard_file_link_path VARCHAR2(100),
    freeboard_file_real_path VARCHAR2(200),
    freeboard_file_upload_date DATE
);

DROP SEQUENCE RM_FB_Upload_File_seq;
CREATE SEQUENCE RM_FB_Upload_File_seq;

-- SELECT * FROM RM_FB_Upload_File;

COMMIT;

/* DB ���� �� */

