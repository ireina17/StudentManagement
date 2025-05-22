CREATE TABLE IF NOT EXISTS student
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10),
    remark TEXT,
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