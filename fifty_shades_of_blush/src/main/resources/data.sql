delete from articles;
delete from users;

insert into users (id, username, password, first_name, last_name, created_at, updated_at) values(1, 'natandaniel', 'natandaniel', 'natan', 'daniel', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, article_type, created_at, updated_at) 
values (1, 'BEAUTY', 'THIS IS A BEAUTY ARTICLE', 'THIS IS A BEAUTY ARTICLE', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, article_type, created_at, updated_at) 
values (2, 'FASHION', 'THIS IS A FASHION ARTICLE', 'THIS IS A FASHION ARTICLE', 'FASHION', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, article_type, created_at, updated_at) 
values (3, 'TRAVEL', 'THIS IS A TRAVEL ARTICLE', 'THIS IS A TRAVEL ARTICLE', 'TRAVEL', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, article_type, created_at, updated_at) 
values (4, 'LIFESTYLE', 'THIS IS A LIFESTYLE ARTICLE', 'THIS IS A LIFESTYLE ARTICLE', 'LIFESTYLE', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, article_type, created_at, updated_at) 
values (5, 'BEAUTY', 'THIS IS A 2ND BEAUTY ARTICLE', 'THIS IS A 2ND BEAUTY ARTICLE', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, article_type, created_at, updated_at) 
values (6, 'BEAUTY', 'THIS IS A 3ND BEAUTY ARTICLE', 'THIS IS A 3ND BEAUTY ARTICLE', 'BEAUTY', SYSDATE, SYSDATE);
