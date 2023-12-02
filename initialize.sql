CREATE DATABASE dbAppAndroid;
USE dbAppAndroid;

-- Tạo bảng người dùng với các trường id, tên, email và mật khẩu

CREATE TABLE users (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(50) NOT NULL,
  is_admin BOOLEAN NOT NULL
);

-- Tạo bảng khu vực với các trường id và tên
CREATE TABLE areas (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

-- Tạo bảng phòng với các trường id, tên, địa điểm và khu vực
CREATE TABLE rooms (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  location VARCHAR(50) NOT NULL,
  area_id INT REFERENCES areas(id) on update cascade on delete cascade
);

-- Tạo bảng tiêu chí với các trường id và tên
CREATE TABLE criteria (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

-- Tạo bảng mô tả tiêu chí với các trường id, nội dung và tiêu chí
CREATE TABLE descriptions (
  id INT PRIMARY KEY,
  content VARCHAR(100) NOT NULL,
  criterion_id INT REFERENCES criteria(id) on update cascade on delete cascade
);

-- Tạo bảng điểm với các trường id, người dùng, phòng, mô tả tiêu chí và điểm số
CREATE TABLE scores (
  id INT PRIMARY KEY,
  user_id INT REFERENCES users(id) on update cascade on delete cascade,
  room_id INT REFERENCES rooms(id) on update cascade on delete cascade,
  description_id INT REFERENCES descriptions(id) on update cascade on delete cascade,
  score INT CHECK (score >= 0 AND score <= 4)
);