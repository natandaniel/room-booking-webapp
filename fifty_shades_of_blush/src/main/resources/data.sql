delete from articles;
delete from users;

insert into users (id, username, password, first_name, last_name, created_at, updated_at) values(1, 'natandaniel', 'natandaniel', 'natan', 'daniel', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (1, 'BEAUTY', 'THIS IS A BEAUTY ARTICLE', 'THIS IS A BEAUTY ARTICLE', 'makeup', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (2, 'FASHION', 'THIS IS A FASHION ARTICLE', 'THIS IS A FASHION ARTICLE', 'bag', 'FASHION', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (3, 'TRAVEL', 'THIS IS A TRAVEL ARTICLE', 'THIS IS A TRAVEL ARTICLE', 'thailand', 'TRAVEL', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (4, 'LIFESTYLE', 'THIS IS A LIFESTYLE ARTICLE', 'THIS IS A LIFESTYLE ARTICLE', 'bag2', 'LIFESTYLE', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (5, 'BEAUTY', 'THIS IS A 2ND BEAUTY ARTICLE', 'THIS IS A 2ND BEAUTY ARTICLE', 'eye', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (6, 'BEAUTY', 'THIS IS A 3ND BEAUTY ARTICLE', 'THIS IS A 3ND BEAUTY ARTICLE', 'women', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (7, 'BEAUTY', 'THIS IS A BEAUTY ARTICLE', 'THIS IS A BEAUTY ARTICLE', 'art', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (8, 'FASHION', 'THIS IS A FASHION ARTICLE', 'THIS IS A FASHION ARTICLE', 'chanel', 'FASHION', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (9, 'TRAVEL', 'THIS IS A TRAVEL ARTICLE', 'THIS IS A TRAVEL ARTICLE', 'blush', 'TRAVEL', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (10, 'LIFESTYLE', 'THIS IS A LIFESTYLE ARTICLE', 'THIS IS A LIFESTYLE ARTICLE', 'wedding', 'LIFESTYLE', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (11, 'BEAUTY', 'THIS IS A 2ND BEAUTY ARTICLE', 'THIS IS A 2ND BEAUTY ARTICLE', 'flower', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles (id, title, subtitle,  body, img_name, article_type, created_at, updated_at) 
values (12, 'BEAUTY', 'THIS IS A 3ND BEAUTY ARTICLE', 'THIS IS A 3ND BEAUTY ARTICLE', 'perfume', 'BEAUTY', SYSDATE, SYSDATE);
