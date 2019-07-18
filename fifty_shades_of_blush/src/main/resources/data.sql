delete from users;
delete from articles;

insert into users (id, firstName, lastName, email, password, role, created_at, updated_at) values(1, 'Julia', 'Fernandez', 'juliafernandez1221@gmail.com','50shades', 'ROLE_ADMIN', SYSDATE, SYSDATE);

insert into articles(id, title, user_id, body, article_type, created_at, updated_at)
values (1, 'Beauty', 1, 'Beauty Beauty Beauty Beauty Beauty Beauty Beauty Beauty', 'BEAUTY', SYSDATE, SYSDATE);

insert into articles(id, title, user_id, body, article_type, created_at, updated_at)
values (2, 'Fashion', 1, 'Fashion Fashion Fashion Fashion Fashion Fashion Fashion Fashion', 'FASHION', SYSDATE, SYSDATE);

insert into articles(id, title, user_id, body, article_type, created_at, updated_at)
values (3, 'Travel', 1, 'Travel Travel Travel Travel Travel Travel Travel Travel', 'TRAVEL', SYSDATE, SYSDATE);

insert into articles(id, title, user_id, body, article_type, created_at, updated_at)
values (4, 'Lifestyle', 1, 'Lifestyle Lifestyle Lifestyle Lifestyle Lifestyle Lifestyle Lifestyle Lifestyle', 'LIFESTYLE', SYSDATE, SYSDATE);