-- =========================
-- EMPRESAS
-- =========================
INSERT INTO empresa (id, nomeempresa, cnpj, localizacao, contato, datacadastro, dataalteracao)
VALUES
(1, 'HYBE Labels', '12345678000199', 'Seoul', 'contact@hybe.com', now(), now()),
(2, 'SM Entertainment', '98765432000188', 'Seoul', 'contact@sm.com', now(), now()),
(3, 'JYP Entertainment', '55566677000155', 'Seoul', 'contact@jype.com', now(), now()),
(4, 'Universal Music', '11122233000199', 'Los Angeles', 'contact@umusic.com', now(), now()),
(5, 'Warner Records', '22233344000111', 'Los Angeles', 'contact@warnerrecords.com', now(), now()),
(6, 'Columbia Records', '33344455000122', 'New York', 'contact@columbiarecords.com', now(), now()),
(7, 'Atlantic Records', '44455566000133', 'New York', 'contact@atlanticrecords.com', now(), now()),
(8, 'Capitol Records', '55566677000144', 'Los Angeles', 'contact@capitolrecords.com', now(), now()),
(9, 'Interscope Records', '66677788000155', 'Santa Monica', 'contact@interscope.com', now(), now()),
(10, 'Island Records', '77788899000166', 'London', 'contact@islandrecords.com', now(), now()),
(11, 'Roc-A-Fella Records', '88899900000177', 'New York', 'contact@rocafella.com', now(), now()),
(12, 'RCA Records', '99900011000188', 'New York', 'contact@rcarecords.com', now(), now()),
(13, 'Def Jam Recordings', '11100022000199', 'New York', 'contact@defjam.com', now(), now()),
(14, 'XL Recordings', '22200033000188', 'London', 'contact@xlrecordings.com', now(), now()),
(15, 'Republic Records', '33300044000177', 'New York', 'contact@republicrecords.com', now(), now()),
(16, 'YG Entertainment', '44400055000166', 'Seoul', 'contact@ygfamily.com', now(), now()),
(17, 'ADOR', '55500066000155', 'Seoul', 'contact@ador.world', now(), now()),
(18, 'Big Machine Records', '66600077000144', 'Nashville', 'contact@bigmachine.com', now(), now()),
(19, 'Parlophone', '77700088000133', 'London', 'contact@parlophone.co.uk', now(), now()),
(20, 'Apple Records', '88800099000122', 'London', 'contact@applerecords.com', now(), now());

-- =========================
-- PROJETOS MUSICAIS
-- =========================
INSERT INTO projetomusical (id, id_empresa, datacadastro, dataalteracao)
VALUES
(1,1,now(),now()), (2,1,now(),now()), (3,2,now(),now()), (4,3,now(),now()),
(5,3,now(),now()), (6,4,now(),now()), (7,1,now(),now()), (8,3,now(),now()),
(9,16,now(),now()), (10,16,now(),now()), (11,17,now(),now()), (12,4,now(),now()),
(13,6,now(),now()), (14,5,now(),now()), (15,5,now(),now()), (16,12,now(),now()),
(17,4,now(),now()), (18,7,now(),now()), (19,12,now(),now()), (20,6,now(),now()),
(21,7,now(),now()), (22,5,now(),now()), (23,6,now(),now()), (24,9,now(),now()),
(25,11,now(),now()), (26,12,now(),now()), (27,18,now(),now()), (28,6,now(),now()),
(29,9,now(),now()), (30,4,now(),now()), (31,10,now(),now()), (32,9,now(),now()),
(33,15,now(),now()), (34,12,now(),now()), (35,9,now(),now()), (36,9,now(),now()),
(37,13,now(),now()), (38,4,now(),now()), (39,11,now(),now()),
(40,1,now(),now()), (41,3,now(),now()), (42,2,now(),now()), (43,16,now(),now()),
(44,17,now(),now()), (45,2,now(),now()), (46,2,now(),now()), (47,2,now(),now()),
(48,5,now(),now()), (49,19,now(),now()), (50,4,now(),now()), (51,10,now(),now()),
(52,4,now(),now()), (53,14,now(),now()), (54,6,now(),now()), (55,5,now(),now()),
(56,20,now(),now());

-- =========================
-- ARTISTAS
-- =========================
INSERT INTO artista (id, nomecompleto, datanascimento, nacionalidade, nomeartistico, funcaoprincipal)
VALUES
(1,'Kim Namjoon','1994-09-12','Coreana','RM','Rapper'),
(2,'Min Yoongi','1993-03-09','Coreana','Suga','Produtor'),
(3,'Kim Taeyeon','1989-03-09','Coreana','Taeyeon','Vocalista'),
(4,'Im Nayeon','1995-09-22','Coreana','Nayeon','Vocalista'),
(5,'Son Chaeyoung','1999-04-23','Coreana','Chaeyoung','Rapper'),
(6,'Justin Drew Bieber','1994-03-01','Canadense','Justin Bieber','Vocalista'),
(7,'Jeon Jungkook','1997-09-01','Coreana','Jungkook','Vocalista'),
(8,'Park Jihyo','1997-02-01','Coreana','Jihyo','Vocalista'),
(9,'Lalisa Manobal','1997-03-27','Tailandesa','Lisa','Rapper'),
(10,'Jennie Kim','1996-01-16','Coreana','Jennie','Vocalista'),
(11,'Hanni Pham','2004-10-06','Australiana','Hanni','Vocalista'),
(12,'Lee Ji-eun','1993-05-16','Coreana','IU','Cantora'),
(13,'Michael Jackson','1958-08-29','Americana','Michael Jackson','Vocalista'),
(14,'Prince Rogers Nelson','1958-06-07','Americana','Prince','Multi-instrumentista'),
(15,'Madonna Louise Ciccone','1958-08-16','Americana','Madonna','Vocalista'),
(16,'Whitney Houston','1963-08-09','Americana','Whitney Houston','Vocalista'),
(17,'Stevie Wonder','1950-05-13','Americana','Stevie Wonder','Cantor'),
(18,'Stephanie Lynn Nicks','1948-05-26','Americana','Stevie Nicks','Vocalista'),
(19,'David Bowie','1947-01-08','Britânica','David Bowie','Cantor'),
(20,'Lauryn Hill','1975-05-26','Americana','Lauryn Hill','Cantora'),
(21,'Christopher Wallace','1972-05-21','Americana','The Notorious B.I.G.','Rapper'),
(22,'Alanis Morissette','1974-06-01','Canadense','Alanis Morissette','Vocalista'),
(23,'Mariah Carey','1969-03-27','Americana','Mariah Carey','Vocalista'),
(24,'Lady Gaga','1986-03-28','Americana','Lady Gaga','Vocalista'),
(25,'Kanye West','1977-06-08','Americana','Kanye West','Rapper'),
(26,'Usher Raymond IV','1978-10-14','Americana','Usher','Vocalista'),
(27,'Taylor Swift','1989-12-13','Americana','Taylor Swift','Cantora'),
(28,'Beyoncé Knowles-Carter','1981-09-04','Americana','Beyoncé','Vocalista'),
(29,'Kendrick Lamar','1987-06-17','Americana','Kendrick Lamar','Rapper'),
(30,'Ella Yelich-OConnor','1996-11-07','Neozelandesa','Lorde','Cantora'),
(31,'Amy Winehouse','1983-09-14','Britânica','Amy Winehouse','Vocalista'),
(32,'Olivia Rodrigo','2003-02-20','Americana','Olivia Rodrigo','Vocalista'),
(33,'Bad Bunny','1994-03-10','Porto-riquenha','Bad Bunny','Vocalista'),
(34,'SZA','1989-11-08','Americana','SZA','Vocalista'),
(35,'Eminem','1972-10-17','Americana','Eminem','Rapper'),
(36,'Travis Scott','1991-04-30','Americana','Travis Scott','Rapper'),
(37,'Rihanna','1988-02-20','Barbadiana','Rihanna','Vocalista'),
(38,'Robyn','1979-06-12','Sueca','Robyn','Vocalista'),
(39,'Jay-Z','1969-12-04','Americana','Jay-Z','Rapper');

-- =========================
-- GRUPOS MUSICAIS
-- =========================
INSERT INTO grupomusical (id, nomegrupo, datainicio, datatermino)
VALUES
(40,'BTS','2013-06-13',NULL),
(41,'Twice','2015-10-20',NULL),
(42,'Girls'' Generation','2007-08-05',NULL),
(43,'BLACKPINK','2016-08-08',NULL),
(44,'NewJeans','2022-07-22',NULL),
(45,'EXO','2012-04-08',NULL),
(46,'Red Velvet','2014-08-01',NULL),
(47,'aespa','2020-11-17',NULL),
(48,'Fleetwood Mac','1967-07-01',NULL),
(49,'Pink Floyd','1965-01-01',NULL),
(50,'ABBA','1972-01-01','1982-12-31'),
(51,'U2','1976-01-01',NULL),
(52,'Nirvana','1987-01-01','1994-04-05'),
(53,'Radiohead','1985-01-01',NULL),
(54,'Daft Punk','1993-01-01','2021-02-22'),
(55,'Arctic Monkeys','2002-01-01',NULL),
(56,'The Beatles','1960-01-01','1970-04-10');

INSERT INTO grupo_musical_artista (idgrupo, idartista)
VALUES
(40,1),(40,2),(40,7),
(41,4),(41,5),(41,8),
(43,9),(43,10),
(44,11),
(48,18);

-- =========================
-- GENEROS
-- =========================
INSERT INTO genero (id, nomegenero, descricao, datacadastro, dataalteracao)
VALUES
(1,'K-Pop','Pop Coreano',now(),now()),
(2,'Hip-Hop','Rap e Hip-Hop',now(),now()),
(3,'Pop','Pop internacional',now(),now()),
(4,'Rock','Rock clássico e contemporâneo',now(),now()),
(5,'R&B / Soul','Rhythm and blues e soul',now(),now()),
(6,'Alternativo','Alternative e indie',now(),now()),
(7,'Eletrônico','Dance, synthpop e eletrônico',now(),now());

-- =========================
-- SEED TEMPORÁRIO DE ÁLBUNS
-- projetos/generos separados por vírgula
-- tracks separados por |
-- =========================
CREATE TEMP TABLE seed_album (
  id BIGINT,
  titulo TEXT,
  lancamento DATE,
  descricao TEXT,
  produtor TEXT,
  idempresa BIGINT,
  projetos TEXT,
  generos TEXT,
  preco NUMERIC,
  estoque INTEGER,
  idioma TEXT,
  tracks TEXT
);

INSERT INTO seed_album VALUES
(1,'Rumours','1977-02-04','Clássico do Fleetwood Mac','Fleetwood Mac',5,'48,18','3,4',149.90,18,'English','Second Hand News|Dreams|Never Going Back Again|Don''t Stop|Go Your Own Way'),
(2,'Songs in the Key of Life','1976-09-28','Obra-prima soul de Stevie Wonder','Stevie Wonder',4,'17','5,3',159.90,12,'English','Love''s in Need of Love Today|Have a Talk with God|Village Ghetto Land|Contusion|Sir Duke'),
(3,'The Dark Side of the Moon','1973-03-01','Álbum conceitual do Pink Floyd','Pink Floyd',19,'49','4,6',169.90,15,'English','Speak to Me|Breathe|On the Run|Time|The Great Gig in the Sky'),
(4,'Arrival','1976-10-11','Pop clássico do ABBA','Benny Andersson',4,'50','3',119.90,20,'English','When I Kissed the Teacher|Dancing Queen|My Love, My Life|Dum Dum Diddle|Knowing Me, Knowing You'),
(5,'The Rise and Fall of Ziggy Stardust and the Spiders from Mars','1972-06-16','Clássico glam rock de David Bowie','Ken Scott',12,'19','4,6',139.90,9,'English','Five Years|Soul Love|Moonage Daydream|Starman|It Ain''t Easy'),

(6,'Thriller','1982-11-30','Álbum histórico de Michael Jackson','Quincy Jones',6,'13','3,5',149.90,25,'English','Wanna Be Startin'' Somethin''|Baby Be Mine|The Girl Is Mine|Thriller|Beat It'),
(7,'Purple Rain','1984-06-25','Trilha e álbum icônico de Prince','Prince',5,'14','3,4,5',149.90,11,'English','Let''s Go Crazy|Take Me with U|The Beautiful Ones|Computer Blue|Darling Nikki'),
(8,'Like a Virgin','1984-11-12','Pop dos anos 80 de Madonna','Nile Rodgers',5,'15','3',119.90,22,'English','Material Girl|Angel|Like a Virgin|Over and Over|Love Don''t Live Here Anymore'),
(9,'The Joshua Tree','1987-03-09','Clássico do U2','Daniel Lanois',10,'51','4,6',129.90,14,'English','Where the Streets Have No Name|I Still Haven''t Found What I''m Looking For|With or Without You|Bullet the Blue Sky|Running to Stand Still'),
(10,'Bella Donna','1981-07-27','Álbum solo de Stevie Nicks','Jimmy Iovine',7,'18','3,4',129.90,10,'English','Bella Donna|Kind of Woman|Stop Draggin'' My Heart Around|Think About It|After the Glitter Fades'),

(11,'Nevermind','1991-09-24','Marco do rock alternativo do Nirvana','Butch Vig',4,'52','4,6',119.90,16,'English','Smells Like Teen Spirit|In Bloom|Come as You Are|Breed|Lithium'),
(12,'The Miseducation of Lauryn Hill','1998-08-25','R&B e hip-hop de Lauryn Hill','Lauryn Hill',6,'20','2,5',139.90,12,'English','Lost Ones|Ex-Factor|To Zion|Doo Wop (That Thing)|Superstar'),
(13,'OK Computer','1997-05-21','Álbum alternativo do Radiohead','Nigel Godrich',14,'53','4,6',139.90,13,'English','Airbag|Paranoid Android|Subterranean Homesick Alien|Exit Music (For a Film)|Let Down'),
(14,'Ready to Die','1994-09-13','Clássico do The Notorious B.I.G.','Sean Combs',7,'21','2',129.90,8,'English','Things Done Changed|Gimme the Loot|Machine Gun Funk|Warning|Ready to Die'),
(15,'Jagged Little Pill','1995-06-13','Rock alternativo pop de Alanis Morissette','Glen Ballard',5,'22','3,6',109.90,17,'English','All I Really Want|You Oughta Know|Perfect|Hand in My Pocket|Right Through You'),
(16,'Butterfly','1997-09-16','Pop e R&B de Mariah Carey','Mariah Carey',6,'23','3,5',119.90,12,'English','Honey|Butterfly|My All|The Roof|Fourth of July'),

(17,'The Fame','2008-08-19','Dance-pop de Lady Gaga','RedOne',9,'24','3,7',109.90,21,'English','Just Dance|LoveGame|Paparazzi|Poker Face|Eh, Eh (Nothing Else I Can Say)'),
(18,'FutureSex/LoveSounds','2006-09-08','Pop, R&B e eletrônico de Justin Timberlake','Timbaland',12,'6','3,5,7',119.90,14,'English','FutureSex/LoveSound|SexyBack|Sexy Ladies|Let Me Talk to You|My Love'),
(19,'Back to Black','2006-10-27','Soul moderno de Amy Winehouse','Mark Ronson',10,'31','5',139.90,15,'English','Rehab|You Know I''m No Good|Me & Mr Jones|Just Friends|Back to Black'),
(20,'Graduation','2007-09-11','Hip-hop de Kanye West','Kanye West',11,'25','2,7',129.90,9,'English','Good Morning|Champion|Stronger|I Wonder|Good Life'),
(21,'In Rainbows','2007-10-10','Álbum alternativo do Radiohead','Nigel Godrich',14,'53','4,6',129.90,11,'English','15 Step|Bodysnatchers|Nude|Weird Fishes/Arpeggi|All I Need'),
(22,'Confessions','2004-03-23','R&B de Usher','Jermaine Dupri',12,'26','3,5',109.90,12,'English','Intro|Yeah!|Throwback|Confessions|Confessions Part II'),

(23,'1989','2014-10-27','Pop de Taylor Swift','Max Martin',18,'27','3',129.90,20,'English','Welcome to New York|Blank Space|Style|Out of the Woods|All You Had to Do Was Stay'),
(24,'Lemonade','2016-04-23','Álbum visual de Beyoncé','Beyoncé',6,'28','3,5,2',149.90,13,'English','Pray You Catch Me|Hold Up|Don''t Hurt Yourself|Sorry|6 Inch'),
(25,'To Pimp a Butterfly','2015-03-15','Hip-hop conceitual de Kendrick Lamar','Terrace Martin',9,'29','2,5',139.90,14,'English','Wesley''s Theory|For Free?|King Kunta|Institutionalized|These Walls'),
(26,'Random Access Memories','2013-05-17','Eletrônico disco do Daft Punk','Daft Punk',6,'54','3,7',149.90,9,'English','Give Life Back to Music|The Game of Love|Giorgio by Moroder|Within|Instant Crush'),
(27,'Melodrama','2017-06-16','Pop alternativo de Lorde','Jack Antonoff',4,'30','3,6',109.90,17,'English','Green Light|Sober|Homemade Dynamite|The Louvre|Liability'),
(28,'AM','2013-09-09','Rock alternativo do Arctic Monkeys','James Ford',5,'55','4,6',119.90,16,'English','Do I Wanna Know?|R U Mine?|One for the Road|Arabella|I Want It All'),
(29,'WINGS','2016-10-10','Álbum do BTS com solos dos integrantes','Pdogg',1,'40,1,2,7','1,2,3',119.90,24,'Korean','Intro: Boy Meets Evil|Blood Sweat & Tears|Begin|Lie|Stigma'),

(30,'MAP OF THE SOUL: 7','2020-02-21','Álbum icônico do BTS','Suga',1,'40,1,2,7','1,2,3',120.00,50,'Korean','ON|Black Swan|Filter|My Time|Louder than bombs'),
(31,'Taste of Love','2021-06-11','Álbum de verão do Twice','J.Y. Park',3,'41,4,5,8','1,3',90.00,30,'Korean','Alcohol-Free|First Time|Scandal|Conversation|Baby Blue Love'),
(32,'SOUR','2021-05-21','Pop alternativo de Olivia Rodrigo','Dan Nigro',9,'32','3,6',99.90,28,'English','brutal|traitor|drivers license|1 step forward, 3 steps back|deja vu'),
(33,'RENAISSANCE','2022-07-29','Dance, pop e R&B de Beyoncé','Beyoncé',6,'28','3,5,7',159.90,18,'English','I''M THAT GIRL|COZY|ALIEN SUPERSTAR|CUFF IT|ENERGY'),
(34,'Un Verano Sin Ti','2022-05-06','Álbum latino de Bad Bunny','Tainy',15,'33','3,2',119.90,20,'Spanish','Moscow Mule|Después de la Playa|Me Porto Bonito|Tití Me Preguntó|Un Ratito'),
(35,'SOS','2022-12-09','R&B contemporâneo de SZA','SZA',12,'34','5,3',129.90,19,'English','SOS|Kill Bill|Seek & Destroy|Low|Love Language'),

(36,'Girls'' Generation','2007-11-01','Primeiro álbum de estúdio do Girls'' Generation','Lee Soo-man',2,'42,3','1,3',99.90,15,'Korean','Girls'' Generation|Ooh La-La!|Baby Baby|Complete|Kissing You'),
(37,'THE ALBUM','2020-10-02','Primeiro álbum coreano do BLACKPINK','Teddy Park',16,'43,9,10','1,3,2,7',119.90,26,'Korean','How You Like That|Ice Cream|Pretty Savage|Bet You Wanna|Lovesick Girls'),
(38,'Get Up','2023-07-21','EP do NewJeans','250',17,'44,11','1,3,7',89.90,35,'Korean','New Jeans|Super Shy|ETA|Cool With You|Get Up'),
(39,'EXODUS','2015-03-30','Álbum do EXO','Kenzie',2,'45','1,3',99.90,12,'Korean','Call Me Baby|Transformer|What If...|My Answer|Exodus'),
(40,'The Perfect Red Velvet','2018-01-29','Álbum repackaged do Red Velvet','Kenzie',2,'46','1,3,5',99.90,18,'Korean','Bad Boy|All Right|Peek-A-Boo|Look|I Just'),
(41,'Palette','2017-04-21','Álbum de IU','IU',4,'12','1,3',109.90,17,'Korean','dlwlrma|Palette|Ending Scene|Can''t Love You Anymore|Jam Jam'),
(42,'Savage','2021-10-05','EP do aespa','Yoo Young-jin',2,'47','1,3,7',89.90,22,'Korean','aenergy|Savage|I''ll Make You Cry|YEPPI YEPPI|ICONIC'),

(43,'The Eminem Show','2002-05-26','Álbum de Eminem','Dr. Dre',9,'35','2',119.90,15,'English','White America|Business|Cleanin'' Out My Closet|Square Dance|The Kiss'),
(44,'DAMN.','2017-04-14','Álbum de Kendrick Lamar','Sounwave',9,'29','2',129.90,17,'English','BLOOD.|DNA.|YAH.|ELEMENT.|FEEL.'),
(45,'ASTROWORLD','2018-08-03','Álbum de Travis Scott','Travis Scott',9,'36','2,7',129.90,14,'English','STARGAZING|CAROUSEL|SICKO MODE|R.I.P. SCREW|STOP TRYING TO BE GOD'),
(46,'ANTI','2016-01-28','Álbum de Rihanna','Rihanna',13,'37','3,5',119.90,20,'English','Consideration|James Joint|Kiss It Better|Work|Desperado'),
(47,'Body Talk','2010-11-22','Electropop de Robyn','Klas Åhlund',4,'38','3,7',99.90,10,'English','Fembot|Dancing On My Own|Don''t Tell Me What to Do|Indestructible|Time Machine');

-- =========================
-- EMPRESAS FICTÍCIAS PARA PRODUÇÕES
-- Uma empresa única para cada produção, evitando erro 1:1
-- =========================
INSERT INTO empresa (id, nomeempresa, cnpj, localizacao, contato, datacadastro, dataalteracao)
SELECT
  100 + id,
  'AlbumMix Production Seed ' || id,
  LPAD((90000000000000 + id)::text, 14, '0'),
  'Seed City',
  'seed' || id || '@albummix.com',
  now(),
  now()
FROM seed_album;

-- =========================
-- PRODUCAO
-- =========================
INSERT INTO producao (id, produtor, engenheirogravacao, engenheiromixagem, engenheiromasterizacao, dataproducao, idempresa, datacadastro, dataalteracao)
SELECT
  id,
  produtor,
  'Studio Team',
  'Mix Engineer',
  'Mastering Engineer',
  lancamento,
  100 + id,
  now(),
  now()
FROM seed_album;

-- =========================
-- ALBUNS
-- =========================
INSERT INTO album (id, titulo, lancamento, descricao, formato, idproducao, datacadastro, dataalteracao)
SELECT id, titulo, lancamento, descricao, 5, id, now(), now()
FROM seed_album;

INSERT INTO album_projeto_musical (idalbum, idprojetomusical)
SELECT s.id, CAST(p AS BIGINT)
FROM seed_album s
CROSS JOIN LATERAL unnest(string_to_array(s.projetos, ',')) p;

INSERT INTO album_genero (idalbum, idgenero)
SELECT s.id, CAST(g AS BIGINT)
FROM seed_album s
CROSS JOIN LATERAL unnest(string_to_array(s.generos, ',')) g;

-- =========================
-- COMPOSICOES
-- =========================
INSERT INTO composicao (id, data, datacadastro, dataalteracao)
SELECT (s.id * 10 + t.ord)::BIGINT, s.lancamento, now(), now()
FROM seed_album s
CROSS JOIN LATERAL regexp_split_to_table(s.tracks, E'\\|') WITH ORDINALITY AS t(titulo, ord);

INSERT INTO composicao_projeto (idcomposicao, idprojetomusical)
SELECT DISTINCT (s.id * 10 + t.ord)::BIGINT, CAST(p AS BIGINT)
FROM seed_album s
CROSS JOIN LATERAL regexp_split_to_table(s.tracks, E'\\|') WITH ORDINALITY AS t(titulo, ord)
CROSS JOIN LATERAL unnest(string_to_array(s.projetos, ',')) p;

-- =========================
-- FAIXAS
-- =========================
INSERT INTO faixa (id, titulo, numerofaixa, duracao, idioma, tipoversao, idgenero, idcomposicao, idalbum, datacadastro, dataalteracao)
SELECT
  (s.id * 10 + t.ord)::BIGINT,
  t.titulo,
  t.ord,
  ROUND((2.70 + (t.ord * 0.31))::numeric, 2),
  s.idioma,
  1,
  CAST(split_part(s.generos, ',', 1) AS BIGINT),
  (s.id * 10 + t.ord)::BIGINT,
  s.id,
  now(),
  now()
FROM seed_album s
CROSS JOIN LATERAL regexp_split_to_table(s.tracks, E'\\|') WITH ORDINALITY AS t(titulo, ord);

-- =========================
-- PARTICIPAÇÕES
-- =========================
INSERT INTO participacao (id, papel, destaque, idfaixa, datacadastro, dataalteracao)
VALUES
(1,'Feat',false,302,now(),now()),
(2,'Feat',false,314,now(),now()),
(3,'Feat',false,374,now(),now()),
(4,'Feat',false,455,now(),now()),
(5,'Participação especial',true,245,now(),now());

INSERT INTO participacao_projeto (idparticipacao, idprojetomusical)
VALUES
(1,3),
(2,6),
(3,6),
(4,29),
(5,39);

-- =========================
-- AVALIAÇÕES
-- =========================
INSERT INTO avaliacaoprofissional (id, avaliador, comentario, nota, idalbum, datacadastro, dataalteracao)
VALUES
(1,'Rolling Stone','Clássico essencial.',10,1,now(),now()),
(2,'Billboard','Impacto cultural enorme.',9.8,6,now(),now()),
(3,'NME','Referência do alternativo.',9.7,13,now(),now()),
(4,'Pitchfork','Produção marcante.',9.4,25,now(),now()),
(5,'Billboard','Destaque contemporâneo.',9.2,33,now(),now()),
(6,'Rolling Stone','K-pop de alto impacto.',9.0,30,now(),now());

-- =========================
-- USUÁRIOS
-- =========================
INSERT INTO usuario (id, nome, login, senha, perfil, email, telefone, datacadastro, dataalteracao) VALUES
(1, 'Maximoff', 'admin',
 '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
 1, 'admin@albummix.com', '63999990000', now(), now()),
(2, 'Hirai', 'user',
 '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
 2, 'user@albummix.com', '63999990001', now(), now()),
(3, 'Minatozaki', 'user2',
 '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
 2, 'user2@albummix.com', '63999990002', now(), now());

-- =========================
-- PRODUTOS
-- =========================
INSERT INTO produto (id, album_id, preco, datacadastro, dataalteracao)
SELECT id, id, preco, now(), now()
FROM seed_album;

-- =========================
-- ESTOQUE
-- =========================
INSERT INTO estoque (produto_id, quantidade_disponivel)
SELECT id, estoque
FROM seed_album;

-- =========================
-- ENDEREÇOS
-- =========================
INSERT INTO endereco (id, rua, numero, complemento, bairro, cidade, uf, cep, usuario_id, datacadastro, dataalteracao)
VALUES
(1, 'Rua das ARMYs', '123', 'Ap 7', 'Centro', 'Seoul', 'SP', '01000-000', 2, now(), now()),
(2, 'Rua das ONCE', '456', NULL, 'Bairro Pop', 'Seoul', 'SP', '02000-000', 2, now(), now());

-- =========================
-- PEDIDOS
-- =========================
INSERT INTO pedido (id, data_criacao, total, usuario_id, endereco_entrega_id, observacao, status, datacadastro, dataalteracao)
VALUES
(1, now(), 210.00, 2, 1, 'Primeiro pedido do usuário user', 'PAGAMENTO_PENDENTE', now(), now()),
(2, now(), 180.00, 2, NULL, 'Pedido para retirada na loja', 'PAGO', now(), now());

-- =========================
-- ITENS DOS PEDIDOS
-- =========================
INSERT INTO item_pedido (id, produto_id, quantidade, preco_unitario, pedido_id, datacadastro, dataalteracao)
VALUES
(1, 30, 1, 120.00, 1, now(), now()),
(2, 31, 1, 90.00, 1, now(), now()),
(3, 31, 2, 90.00, 2, now(), now());

-- =========================
-- PAGAMENTOS
-- =========================
INSERT INTO pagamento (id, pedido_id, metodo_pagamento, status, valor, codigo_pagamento, data_criacao, datacadastro, dataalteracao)
VALUES
(1, 1, 'PIX', 'PENDENTE', 210.00, NULL, now(), now(), now()),
(2, 2, 'PIX', 'APROVADO', 180.00, 'SIMULADO-CODIGO-PIX-2', now(), now(), now());

DROP TABLE seed_album;

-- =========================
-- SEQUENCES
-- =========================
SELECT setval('empresa_id_seq',               (SELECT COALESCE(MAX(id), 0) FROM empresa));
SELECT setval('projetomusical_id_seq',        (SELECT COALESCE(MAX(id), 0) FROM projetomusical));
SELECT setval('genero_id_seq',                (SELECT COALESCE(MAX(id), 0) FROM genero));
SELECT setval('producao_id_seq',              (SELECT COALESCE(MAX(id), 0) FROM producao));
SELECT setval('album_id_seq',                 (SELECT COALESCE(MAX(id), 0) FROM album));
SELECT setval('composicao_id_seq',            (SELECT COALESCE(MAX(id), 0) FROM composicao));
SELECT setval('faixa_id_seq',                 (SELECT COALESCE(MAX(id), 0) FROM faixa));
SELECT setval('participacao_id_seq',          (SELECT COALESCE(MAX(id), 0) FROM participacao));
SELECT setval('avaliacaoprofissional_id_seq', (SELECT COALESCE(MAX(id), 0) FROM avaliacaoprofissional));
SELECT setval('usuario_id_seq',               (SELECT COALESCE(MAX(id), 1) FROM usuario), true);
SELECT setval('produto_id_seq',               (SELECT COALESCE(MAX(id), 0) FROM produto));
SELECT setval('endereco_id_seq',              (SELECT COALESCE(MAX(id), 0) FROM endereco));
SELECT setval('pedido_id_seq',                (SELECT COALESCE(MAX(id), 0) FROM pedido));
SELECT setval('item_pedido_id_seq',           (SELECT COALESCE(MAX(id), 0) FROM item_pedido));
SELECT setval('pagamento_id_seq',             (SELECT COALESCE(MAX(id), 0) FROM pagamento));