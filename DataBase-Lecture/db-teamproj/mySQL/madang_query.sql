-- 모든 학생 번호, 이름
select studentId, name from student;

-- 모든 교수 번호, 이름
select pofessorId, name from professor;

-- 학생 본인 듣고있는 총 학점  -> 학생 번호 입력받기
select sum(l.credit)'총학점' from lecture l,lecture_has_student ls where l.lectureId = ls.lectureId and ls.studentId=100 and ls.year=2021 and semester=1;


-- 관리자 
-- 데이터베이스 초기화 기능 -> madang_insert_data사용
-- ● 데이터베이스에 포함된 모든 테이블에 대한 입력/삭제/변경 기능단, 삭제/변경은 “1개”의 고정된 특정 조건이 아닌 “조건식”을 입력 받아서 삭제/변경하는 방식으로 구현해야 함 ?
-- 전체 테이블 보여주기
select * from Department;
select * from Professor;
select * from Student;
select * from Lecture;
select * from Club;
select * from Tuition;
select * from Professor_has_student;
select * from Lecture_has_student;
select * from Professor_has_department;
select * from Student_has_club;



-- 교수 사용자
--  입력된 학년/학기에 본인이 강의했던 과목에 대한 모든 정보를 보여주는 기능 ->교수번호,학년, 학기 입력받기
select * from lecture where professorId IN (select professorId from lecture_has_student where year = 2020&& semester=2&&professorId=222);
-- 위에서 표시된 과목 정보 중에서 하나를 “클릭”하면 해당 과목을 수강하는 (혹은 수강했던) 모든 학생에 대한 정보를 보여주는 기능 ->강좌번호 가져오기
select  * from student s where s.studentId in (select ls.studentId from lecture_has_student ls where ls.lectureId=424 and ls.professorId=200 );

-- 현재 본인이 “지도”하는 학생에 대한 정보를 보여주는 기능 -> 교수 번호 입력받기
select * from student where studentID in (select studentId from Professor_has_student where professorId=200);

-- 위에서 표시된 학생 정보 중에서 하나를 “클릭”하면 해당 학생이 수강했던 (혹은 수강하고 있는) 모든 과목에 대한 “성적” 정보를 보여주는 기능 -> 해당 학생 아이디 가져와 조건식에 넣기
select  l.name, ls.grade, ls.year, ls.semester from lecture_has_student ls, lecture l where ls.studentId=100 and ls.lectureId = l.lectureId ;

-- 본인이 소속된 학과에 대한 정보(학과장 정보 포함)를 보여주는 기능 -> 교수 번호 입력받기 (현재는 학과당 교수 한명 밖에 입력이 안되어 있어서 본인이 학과장입니다.)
select * from department where departmentId in ( select departmentId from professor where professorId=200);

-- 현재 학기에 대한 “강의 시간표” 표시 기능 : 현재 학기에 강의하는 과목을 시간표 형태로 표시함. 시간표는 요일/교시 ->교수 번호 입력받기, db로부터 해당 정보를 가져와 자바에서 구현 (만약 시간표 겹칠 때 예외처리)
select l.name,l.day, l.period,l.hour from lecture l where l.lectureId in (select ls.lectureid from lecture_has_student ls where ls.professorId = 200 and ls.year = 2021 and ls.semester =1) 
order by l.day desc, l.period ;

-- 현재 본인이 강의하는 과목에 대한 성적 입력 기능 -> 교수번호 입력해서 본인 강좌 조회 후 리스트 중 클릭해서 해당 학생의 점수 입력
select * from Lecture_has_student where professorId =200 and year=2021;
-- update Lecture_has_student set  attendanceScore = ,  midtermScore =,  finalScore= , otherScore =, totalScore= ,   grade =  where studentId=102;



-- 학생 사용자
-- 입력된 학년/학기에 본인이 수강했던 과목에 대한 모든 정보를 보여주는 기능 ->학생번호, 학년, 학기 입력받기
select * from lecture where lectureId IN (select lectureId from lecture_has_student where year = 2021&& semester=1&&studentId=100);
-- 현재 학기에 본인이 수강하는 모든 과목을 시간표 형태로 표시하는 기능 -> 학생번호 입력받기, db로부터 해당 정보를 가져와 자바에서 구현 (만약 시간표 겹칠 때 예외처리)
select l.name,l.day, l.period,l.hour from lecture l where l.lectureId in (select ls.lectureid from lecture_has_student ls where ls.studentId = 100 and ls.year = 2021 and ls.semester =1) 
order by l.day desc, l.period  ;

-- 본인이 소속된 동아리에 대한 정보를 보여주는 기능  -> 학생번호 입력받기
select * from club c ,student_has_club sc where c.clubid = sc.clubId and sc.studentId=100;
-- 특정 동아리의 회장 학생 번호 조회
select president_studentId from club where clubid=500;
-- 단, 동아리 회장의 경우에는 동아리에 “속한” 모든 학생들에 대한 정보를 보여주는 기능이 구현되어야 함-> 동아리 번호 입력받기
select * from student where studentId in (select studentId from student_has_club where clubId=500);

-- 본인의 성적표를 보여주는 기능 : 과목번호/과목명/취득학점/평점은 반드시 표시되어야 하며, GPA (grade point average)도 표시되어야 한다. ->학생 번호 입력받기
select l.lectureId, l.name, l.credit, ls.grade, ls.gpa,ls.year, ls.semester from lecture l, lecture_has_student ls where l.lectureId=ls.lectureId and ls.studentId=100;
