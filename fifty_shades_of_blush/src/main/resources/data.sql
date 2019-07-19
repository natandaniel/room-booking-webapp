delete from articles;
delete from users;

insert into users (id, username, password, first_name, last_name, created_at, updated_at) values(1, 'natandaniel', 'natandaniel', 'natan', 'daniel', SYSDATE, SYSDATE);

insert into articles (id, title, user_id, body, article_type, created_at, updated_at) 
values (1, 'BEAUTY', 1, 'THIS IS A BEAUTY ARTICLE', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, user_id, body, article_type, created_at, updated_at) 
values (2, 'FASHION', 1, 'THIS IS A FASHION ARTICLE', 'FASHION', SYSDATE, SYSDATE);

insert into articles (id, title, user_id, body, article_type, created_at, updated_at) 
values (3, 'TRAVEL', 1, 'THIS IS A TRAVEL ARTICLE', 'TRAVEL', SYSDATE, SYSDATE);

insert into articles (id, title, user_id, body, article_type, created_at, updated_at) 
values (4, 'LIFESTYLE', 1, 'THIS IS A LIFESTYLE ARTICLE', 'LIFESTYLE', SYSDATE, SYSDATE);
