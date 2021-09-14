DROP TABLE IF EXISTS blog_user;

CREATE TABLE blog_user(
  user_id IDENTITY,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  user_name VARCHAR(26) NOT NULL,
  user_password VARCHAR(50),
  user_email VARCHAR(36) NOT NULL,
  is_admin BOOLEAN DEFAULT FALSE,
  active BOOLEAN DEFAULT TRUE,
  can_comment BOOLEAN DEFAULT TRUE,
  can_blog BOOLEAN DEFAULT TRUE,
  UNIQUE INDEX(user_name),
  PRIMARY KEY(user_id)
);




DROP TABLE IF EXISTS blog;

CREATE TABLE blog(
	blog_id IDENTITY,
	user_id INT NOT NULL,
	blog_title VARCHAR(60) NOT NULL,
	created_datetime TIMESTAMP DEFAULT NOW(),
	edited_datetime TIMESTAMP DEFAULT NOW(),
	published_datetime TIMESTAMP,
	blog_text CLOB NOT NULL,
	likes INT DEFAULT 0,
	dislikes INT DEFAULT 0,
	rating INT DEFAULT 0,
	active BOOLEAN DEFAULT TRUE,
	comment_security TINYINT DEFAULT 7,
	status VARCHAR(16) DEFAULT 'DRAFT',
	visibility VARCHAR(16) DEFAULT 'PUBLIC',
	FOREIGN KEY(user_id) REFERENCES blog_user(user_id),
	PRIMARY KEY(blog_id)
);

CREATE INDEX ndx_title ON blog(blog_title);


DROP TABLE IF EXISTS blog_like;

CREATE TABLE EXISTS blog_like(
	blog_id INT NOT NULL,
	user_name INT NOT NULL,
	FOREIGN KEY(blog_id) REFERENCES blog(blog_id),
	PRIMARY KEY(blog_id, user_name)
);


DROP TABLE IF EXISTS blog_category;

CREATE TABLE blog_category(
	category_id IDENTITY,
	blog_id INT NOT NULL,
	category_text varchar(60) NOT NULL,
	INDEX USING BTREE (category_text) ENGINE = MyISAM,
	FOREIGN KEY(blog_id) REFERENCES blog(blog_id),
	PRIMARY KEY(category_id)
);

CREATE INDEX ndx_category ON blog_category(category_text);




DROP TABLE IF EXISTS blog_comment;

CREATE TABLE blog_comment(
	comment_id IDENTITY,
	blog_id INT NOT NULL,
	comment_title VARCHAR(100) NOT NULL,
	comment_text CLOB NOT NULL,
	comment_user INT NOT NULL,
	parent_comment_id INT,
	likes INT DEFAULT 0,
	dislikes INT DEFAULT 0,
	rating INT DEFAULT 0,
	active BOOLEAN DEFAULT TRUE,
	created_datetime TIMESTAMP DEFAULT NOW(),
	FOREIGN KEY(blog_id) REFERENCES blog(blog_id),
	FOREIGN KEY(comment_user) REFERENCES blog_user(user_id),
	PRIMARY KEY(comment_id)
);



DROP TABLE IF EXISTS blog_history;

CREATE TABLE blog_history(
	history_id IDENTITY,
	history_type VARCHAR(20) NOT NULL,
	history_table VARCHAR(30) NOT NULL,
	table_id INT,
	table_column VARCHAR(30) NOT NULL,
	username VARCHAR(26) NOT NULL,
	old_value VARCHAR(60),
	new_value VARCHAR(60),
	message VARCHAR(256),
	created_date TIMESTAMP DEFAULT NOW(),
	PRIMARY KEY(history_id)
);