/* DB 생성 시작 */

-- 회원
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

-- 회원 가입 키 생성
    -- SELECT RM_Member_Seq.nextval FROM DUAL;

-- 회원 - 레벨 카테고리
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

-- 회원 - 가입 인증 
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


-- 회원 - 사용자 신고
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

-- 사용자 신고 리스트 페이징
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

-- 회원 - 사용자 신고 - 카테고리
DROP TABLE RM_MB_RP_Category;
CREATE TABLE RM_MB_RP_Category (
    report_category_no NUMBER(8),
    report_category_name VARCHAR2(60)
);

DROP SEQUENCE RM_MB_RP_Category_seq;
CREATE SEQUENCE RM_MB_RP_Category_seq;

INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '욕설/인신공격');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '권리침해 및 사이버 괴롭힘');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '사기');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '홍보/영리목적글 게시');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '불법정보 게시');
INSERT INTO RM_MB_RP_Category VALUES(RM_MB_RP_Category_seq.nextval, '기타');


-- 여행지/맛집
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

-- 여행지/맛집 - 파일 업로드
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

-- 여행지/맛집 - 지역 카테고리
DROP TABLE RM_Province_Category;
CREATE TABLE RM_Province_Category(
    province_category_no NUMBER(8),
    province_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_Province_Category_Seq;
CREATE SEQUENCE RM_Province_Category_Seq;

INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '서울특별시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '부산광역시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '인천광역시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '대구광역시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '대전광역시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '광주광역시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '울산광역시');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '경기도');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '강원도');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '충청도');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '전라도');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '경상도');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '제주도');
INSERT INTO RM_Province_Category VALUES(RM_Province_Category_seq.nextval, '세종시');
INSERT INTO RM_Province_Category VALUES(15, '정보없음');

-- 여행지/맛집 - 분류 카테고리
DROP TABLE RM_LC_Category;
CREATE TABLE RM_LC_Category(
    location_category_no NUMBER(8),
    location_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_LC_Category_seq;
CREATE SEQUENCE RM_LC_Category_seq;

INSERT INTO RM_LC_Category VALUES(RM_LC_Category_seq.nextval, '여행지');
INSERT INTO RM_LC_Category VALUES(RM_LC_Category_seq.nextval, '맛집');

-- SELECT * FROM RM_LC_Category;

-- 여행지/맛집 - 비용 카테고리
DROP TABLE RM_LC_Cost_Category;
CREATE TABLE RM_LC_Cost_Category(
    cost_category_no NUMBER(8),
    cost_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_LC_Cost_Category_seq;
CREATE SEQUENCE RM_Cost_LC_Category_seq;

INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '1만원 미만');
INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '1만원 ~ 3만원');
INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '3만원 ~ 5만원');
INSERT INTO RM_LC_Cost_Category VALUES(RM_Cost_LC_Category_seq.nextval, '5만원 이상');

-- SELECT * FROM RM_LC_Cost_Category;

-- 여행지/맛집 - 유형 카테고리
DROP TABLE RM_Type_Category;
CREATE TABLE RM_Type_Category(
    type_category_no NUMBER(8),
    type_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_Type_Category_seq;
CREATE SEQUENCE RM_Type_Category_seq;

INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, '혼자');
INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, '커플');
INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, '가족과');
INSERT INTO RM_Type_Category VALUES(RM_Type_Category_seq.nextval, '친구와');

-- SELECT * FROM RM_Type_Category;

-- 여행지/맛집 - 소요시간 카테고리
DROP TABLE RM_LC_Time_Category;
CREATE TABLE RM_LC_Time_Category(
    time_category_no NUMBER(8),
    time_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_LC_Time_Category_seq;
CREATE SEQUENCE RM_LC_Time_Category_seq;

INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '1시간 미만');
INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '1 ~ 3시간');
INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '3 ~ 5시간');
INSERT INTO RM_LC_Time_Category VALUES(RM_LC_Time_Category_seq.nextval, '5시간 이상');

-- SELECT * FROM RM_LC_Time_Category;

-- 여행지/맛집 - 분위기 카테고리
DROP TABLE RM_Mood_Category;
CREATE TABLE RM_Mood_Category(
    mood_category_no NUMBER(8),
    mood_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_Mood_Category_seq;
CREATE SEQUENCE RM_Mood_Category_seq;

INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '조용함');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '시끄러움');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '자연친화적');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '활동적');
INSERT INTO RM_Mood_Category VALUES(RM_Mood_Category_seq.nextval, '분위기 좋음');

-- SELECT * FROM RM_Mood_Category;


-- 여행지/맛집 - 관심 추가
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

-- 여행지/맛집 - 좋아요
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

-- locationboard_no 개수가 가장 많은 locationboard_no 가져오기
--SELECT locationboard_no FROM RM_LC_Like GROUP BY locationboard_no HAVING COUNT(locationboard_no) = (SELECT MAX(COUNT(locationboard_no)) maxcount FROM RM_LC_Like GROUP BY locationboard_no);

-- 비어있는 값을 Null값으로, Null값을 0으로 변환
-- 좋아요 가장 많은 글 선택
--SELECT NVL(MAX(locationboard_no), 0) locationboard_no 
--FROM (
--            SELECT locationboard_no
  --          FROM RM_LC_Like
    --        GROUP BY locationboard_no
      --      HAVING COUNT(locationboard_no) = 
        --                                                    	(SELECT MAX(COUNT(locationboard_no)) maxcount
          --                                                  	 FROM RM_LC_Like
            --                                                     GROUP BY locationboard_no)) t2;
-- 최신글 선택                     
--SELECT NVL(max(locationboard_no), 0) locationboard_no
		--	FROM RM_Locationboard;

-- 여행지/맛집 - 댓글
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


-- 루트 공유 
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

--INSERT INTO RM_Routeboard VALUES(RM_Routeboard_seq.nextval, 1, 1, 1, '루트 공유 제목1', '루트 공유 내용1', 0, 320, 63000, 'Y', SYSDATE);

-- SELECT * FROM RM_Routeboard ORDER BY routeboard_no DESC;

-- 루트 공유 - 파일 업로드
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

-- 루트 공유 - 비용 카테고리
DROP TABLE RM_RT_Cost_Category;
CREATE TABLE RM_RT_Cost_Category(
    cost_category_no NUMBER(8),
    cost_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_RT_Cost_Category_seq;
CREATE SEQUENCE RM_RT_Cost_Category_seq;

INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '3만원 미만');
INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '3 ~ 6만원');
INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '6 ~ 9만원');
INSERT INTO RM_RT_Cost_Category VALUES(RM_RT_Cost_Category_seq.nextval, '9만원 이상');

-- SELECT * FROM RM_RT_Cost_Category;

-- 루트 공유 - 소요시간 카테고리
DROP TABLE RM_RT_Time_Category;
CREATE TABLE RM_RT_Time_Category(
    time_category_no NUMBER(8),
    time_category_name VARCHAR2(30)
);

DROP SEQUENCE RM_RT_Time_Category_seq;
CREATE SEQUENCE RM_RT_Time_Category_seq;

INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '3시간 미만');
INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '3 ~ 6시간');
INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '6 ~ 9시간');
INSERT INTO RM_RT_Time_Category VALUES(RM_RT_Time_Category_seq.nextval, '9시간 이상');

-- SELECT * FROM RM_RT_Time_Category;
  
    
-- 루트 공유 - 관심 추가
DROP TABLE RM_RT_Myfavorite;
CREATE TABLE RM_RT_Myfavorite(
    cr_myfavorite_no NUMBER(8),
    member_no NUMBER(8),
    routeboard_no NUMBER(8)
);

DROP SEQUENCE RM_RT_Myfavorite_seq;
CREATE SEQUENCE RM_RT_Myfavorite_seq;

-- SELECT * FROM RM_RT_Myfavorite;

-- 루트 공유 - 좋아요 
DROP TABLE RM_RT_Like;
CREATE TABLE RM_RT_Like(
    routeboard_like_no NUMBER(8),
    member_no NUMBER(8),
    routeboard_no NUMBER(8)    
);

DROP SEQUENCE RM_RT_Like_seq;
CREATE SEQUENCE RM_RT_Like_seq;

-- SELECT * FROM RM_RT_Like;


-- 루트 공유 - 댓글
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


-- 링크(장소와 루트를 연결)
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

-- 링크 키 생성
    -- SELECT RM_Location_Route_Link_Seq.nextval FROM DUAL;

-- SELECT * FROM RM_Location_Route_Link ORDER BY link_no DESC;

-- select * from rm_locationboard;


-- 여행지/맛집 - 게시글 신고
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

-- 여행지/맛집 신고 리스트 페이징
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


-- 루트 공유 - 게시글 신고
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

-- 루트 신고 리스트 페이징
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

-- 신고 - 게시글 신고 - 카테고리
DROP TABLE RM_LC_RT_RP_Category;
CREATE TABLE RM_LC_RT_RP_Category (
    report_category_no NUMBER(8),
    report_category_name VARCHAR2(60)
);

INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '영리목적/홍보성');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '개인정보노출');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '불법정보');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '음란성/선정성');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '욕설/인신공격');
INSERT INTO RM_LC_RT_RP_Category VALUES(RM_LC_RT_RP_Category_seq.nextval, '기타');


-- 관리자
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


-- 관리자 - 로그인 누적 수(관리자 통계)
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


-- 고객센터 - 공지사항
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


-- 고객센터 - 자주 묻는 질문  (FAQ)
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
INSERT INTO RM_CS_Faq VALUES (RM_CS_Faq_seq.nextval, 1, 1, '루트맵입니다', '안녕하세요');


-- 고객센터 - 자주 묻는 질문 - 카테고리
DROP TABLE RM_CS_FAQ_Category;
CREATE TABLE RM_CS_FAQ_Category(
    faq_category_no NUMBER(8),
    faq_category_name VARCHAR2(30)
);

INSERT INTO RM_CS_FAQ_Category VALUES (1,'여행지/맛집');
INSERT INTO RM_CS_FAQ_Category VALUES (2,'루트공유');
INSERT INTO RM_CS_FAQ_Category VALUES (3,'나의관심/루트');
INSERT INTO RM_CS_FAQ_Category VALUES (4,'자유게시판');
INSERT INTO RM_CS_FAQ_Category VALUES (5,'계정관련');

DROP SEQUENCE RM_CS_FAQ_Category_seq;
CREATE SEQUENCE RM_CS_FAQ_Category_seq;

-- SELECT * FROM RM_CS_FAQ_Category;


-- 고객센터 - 1:1 문의내역
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


-- 고객센터 - 1:1 문의내역 - 답변   
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


-- 고객센터 - 1:1 문의내역 - 카테고리
DROP TABLE RM_CS_Inquiry_Category;
CREATE TABLE RM_CS_Inquiry_Category(
inquiry_category_no NUMBER(8) PRIMARY KEY,
inquiry_category_name VARCHAR2(30)
);

-- 1:1 문의 카테고리 고정 행
INSERT INTO RM_CS_Inquiry_Category VALUES (1,'여행지/맛집');
INSERT INTO RM_CS_Inquiry_Category VALUES (2,'루트 공유');
INSERT INTO RM_CS_Inquiry_Category VALUES (3,'나의 관심/루트');
INSERT INTO RM_CS_Inquiry_Category VALUES (4,'자유게시판');
INSERT INTO RM_CS_Inquiry_Category VALUES (5,'계정');
INSERT INTO RM_CS_Inquiry_Category VALUES (6,'제안');


-- 고객센터 - 1:1 문의내역 - 첨부파일
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


-- 쪽지
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

--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '안녕하세요', '안녕하세요, 반갑습니다', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '행복하세요', '쪽지가 잘보내지네요', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '거기 어떤가요?', '재미있나요??', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '올바른 손씻기', '기침예절', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '맛집인가요??', '이번에 한번 가보려합니다', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '맛집으로 추천', '엄청난 맛집입니다. 꼭 한번 가보세요', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', 'test', 'test', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', 'test용 쪽지', '아아아아아아', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 2, 'test02@naver.com', '커피 한 잔', '아메리카노', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 2, 'test02@naver.com', 1, 'test01@naver.com', '집으로', '밖으로', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 3, 'test03@naver.com', 1, 'test01@naver.com', '여행지 관련해서 질문이요', '어떤가요??', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 3, 'test03@naver.com', '거긴..', '추천 여행지입니다.', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 3, 'test03@naver.com', 1, 'test01@naver.com', '추천 추천', '완전 추천', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 3, 'test03@naver.com', '비추비추', '그 가격에 다른곳으로 가세요', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 3, 'test03@naver.com', 1, 'test01@naver.com', '야경 보러 갈만한 곳', '완전 강추합니다', SYSDATE, NULL, 'N', 'N');
--INSERT INTO RM_NOTE VALUES(RM_NOTE_SEQ.NEXTVAL, 1, 'test01@naver.com', 3, 'test03@naver.com', '다녀와보니', '완전 좋네요', SYSDATE, NULL, 'N', 'N');


----보낸 쪽지함 출력 쿼리
--SELECT * FROM RM_NOTE WHERE SEND_MEMBER_NO = 4 ORDER BY note_no desc;
--SELECT COUNT(*) FROM RM_NOTE WHERE SEND_MEMBER_NO= 4;
----받은 쪽지함 출력 쿼리
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


-- 채팅창
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


-- 채팅창 - 파일 업로드
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


-- 자유 게시판
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


-- 자유 게시판 - 댓글 
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


-- 자유 게시판 - 파일 업로드 
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

/* DB 생성 끝 */

