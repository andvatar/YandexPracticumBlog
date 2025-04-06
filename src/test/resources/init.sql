delete from comment;
delete from post;
ALTER TABLE post alter column id restart with 1;
ALTER TABLE comment alter column id restart with 1;
insert into post (TITLE, CONTENT, TAGS, LIKES) values ('test1', 'test post 1', '{tag1, tag2}', 0);
insert into post (TITLE, CONTENT, TAGS, LIKES) values ('test2', 'test post 2', '{tag1, tag3}', 0);
insert into comment (POST_ID, CONTENT) values (1, 'test comment 1');
insert into comment (POST_ID, CONTENT) values (1, 'test comment 2');

