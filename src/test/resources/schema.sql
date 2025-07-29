CREATE TABLE IF NOT EXISTS student
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    area VARCHAR(50) NOT NULL,
    age INT,
    sex VARCHAR(10),
    remark TEXT NOT NULL,
    is_Deleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    course_start TIMESTAMP,
    course_end TIMESTAMP
);

CREATE TABLE IF NOT EXISTS course_status
(
    id INT AUTO_INCREMENT PRIMARY KEY,
	course_id VARCHAR(36) NOT NULL,
    course_status VARCHAR(10) NOT NULL
);