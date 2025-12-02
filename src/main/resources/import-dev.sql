-- =========================
-- EMPRESAS
-- =========================
INSERT INTO empresa (id, nomeempresa, cnpj, localizacao, contato, datacadastro, dataalteracao)
VALUES 
(1, 'HY Entertainment', '12345678000199', 'Seoul', 'contact@hybe.com', now(), now()),
(2, 'SM Entertainment', '98765432000188', 'Seoul', 'contact@sm.com', now(), now()),
(3, 'JYP Entertainment', '55566677000155', 'Seoul', 'contact@jype.com', now(), now()),
(4, 'Universal Music', '11122233000199', 'Los Angeles', 'contact@umusic.com', now(), now());

-- =========================
-- PROJETOS MUSICAIS (ARTISTAS e GRUPOS)
-- =========================
INSERT INTO projetomusical (id, id_empresa, datacadastro, dataalteracao) VALUES
(1, 1, now(), now()), -- RM
(2, 1, now(), now()), -- Suga
(3, 2, now(), now()), -- Taeyeon
(4, 1, now(), now()), -- BTS
(5, 3, now(), now()), -- Nayeon
(6, 3, now(), now()), -- Chaeyoung
(7, 4, now(), now()), -- Justin Bieber
(8, 3, now(), now()); -- Twice

-- =========================
-- ARTISTAS
-- (herdam de ProjetoMusical)
-- =========================
INSERT INTO artista (id, nomecompleto, datanascimento, nacionalidade, nomeartistico, funcaoprincipal)
VALUES
(1, 'Kim Namjoon', '1994-09-12', 'Coreana', 'RM', 'Rapper'),
(2, 'Min Yoongi', '1993-03-09', 'Coreana', 'Suga', 'Produtor'),
(3, 'Kim Taeyeon', '1989-03-09', 'Coreana', 'Taeyeon', 'Vocalista'),
(5, 'Im Nayeon', '1995-09-22', 'Coreana', 'Nayeon', 'Vocalista'),
(6, 'Son Chaeyoung', '1999-04-23', 'Coreana', 'Chaeyoung', 'Rapper'),
(7, 'Justin Drew Bieber', '1994-03-01', 'Canadense', 'Justin Bieber', 'Vocalista');

-- =========================
-- GRUPOS MUSICAIS
-- =========================
INSERT INTO grupomusical (id, nomegrupo, datainicio, datatermino)
VALUES
(4, 'BTS', '2013-06-13', NULL),
(8, 'Twice', '2015-10-20', NULL);

-- membros BTS
INSERT INTO grupo_musical_artista (idgrupo, idartista) VALUES (4, 1);
INSERT INTO grupo_musical_artista (idgrupo, idartista) VALUES (4, 2);

-- membros Twice
INSERT INTO grupo_musical_artista (idgrupo, idartista) VALUES (8, 5);
INSERT INTO grupo_musical_artista (idgrupo, idartista) VALUES (8, 6);

-- =========================
-- GENEROS
-- =========================
INSERT INTO genero (id, nomegenero, descricao, datacadastro, dataalteracao)
VALUES 
(1, 'K-Pop', 'Pop Coreano', now(), now()),
(2, 'Hip-Hop', 'Rap e Hip-Hop', now(), now()),
(3, 'Pop', 'Pop Internacional e Coreano', now(), now());

-- =========================
-- PRODUCAO (1:1 com álbum)
-- =========================
INSERT INTO producao (id, produtor, engenheirogravacao, engenheiromixagem, engenheiromasterizacao, dataproducao, idempresa, datacadastro, dataalteracao)
VALUES 
(1, 'Suga', 'Pdogg', 'El Capitxn', 'Bang Si-hyuk', '2019-11-01', 1, now(), now()),
(2, 'J.Y. Park', 'Lee Woo-min', 'Collapsedone', 'JYP Studio', '2021-05-01', 3, now(), now());

-- =========================
-- ALBUNS
-- (formato = enum, mantido como você definiu)
-- =========================
INSERT INTO album (id, titulo, lancamento, descricao, formato, idproducao, datacadastro, dataalteracao)
VALUES
(1, 'MAP OF THE SOUL: 7', '2020-02-21', 'Álbum icônico do BTS', 5, 1, now(), now()),
(2, 'Taste of Love', '2021-06-11', 'Álbum de verão do Twice', 5, 2, now(), now());

-- relacionar principais ao album
INSERT INTO album_projeto_musical (idalbum, idprojetomusical) VALUES 
(1, 4), (1, 1), (1, 2), -- BTS, RM, Suga
(2, 8), (2, 5), (2, 6); -- Twice, Nayeon, Chaeyoung

-- generos dos álbuns
INSERT INTO album_genero (idalbum, idgenero) VALUES 
(1, 1), (1, 2),
(2, 1), (2, 3);

-- =========================
-- COMPOSICOES (1 por faixa)
-- =========================
INSERT INTO composicao (id, data, datacadastro, dataalteracao)
VALUES
(1, '2019-08-01', now(), now()), -- ON
(2, '2019-09-01', now(), now()), -- Black Swan
(3, '2021-03-01', now(), now()), -- Alcohol-Free
(4, '2021-04-01', now(), now()); -- Summer Love

-- compositores
INSERT INTO composicao_projeto (idcomposicao, idprojetomusical) VALUES
(1, 1), (1, 2),
(2, 1), (2, 2),
(3, 5), (3, 6),
(4, 5), (4, 6), (4, 7);

-- =========================
-- FAIXAS
-- (tipoversao = enum, mantido como você definiu)
-- =========================
INSERT INTO faixa (id, titulo, numerofaixa, duracao, idioma, tipoversao, idgenero, idcomposicao, idalbum, datacadastro, dataalteracao)
VALUES
(1, 'ON', 1, 4.06, 'Korean', 1, 1, 1, 1, now(), now()),
(2, 'Black Swan', 2, 3.23, 'Korean', 1, 2, 2, 1, now(), now()),
(3, 'Alcohol-Free', 1, 3.30, 'Korean', 1, 3, 3, 2, now(), now()),
(4, 'Summer Love (feat Justin Bieber)', 2, 3.15, 'English', 1, 3, 4, 2, now(), now());

-- =========================
-- PARTICIPAÇÕES
-- =========================
INSERT INTO participacao (id, papel, destaque, datacadastro, dataalteracao)
VALUES 
(1, 'Feat', false, now(), now()),
(2, 'Feat', false, now(), now());

-- Taeyeon no BTS (em Black Swan)
INSERT INTO participacao_projeto (idparticipacao, idprojetomusical) VALUES (1, 3);
UPDATE participacao SET idfaixa = 2 WHERE id = 1;

-- Justin Bieber com Twice
INSERT INTO participacao_projeto (idparticipacao, idprojetomusical) VALUES (2, 7);
UPDATE participacao SET idfaixa = 4 WHERE id = 2;

-- =========================
-- AVALIAÇÕES
-- =========================
INSERT INTO avaliacaoprofissional (id, avaliador, comentario, nota, datacadastro, dataalteracao)
VALUES
(1, 'Billboard', 'Masterpiece', 10, now(), now()),
(2, 'Rolling Stone', 'Impressionante', 9.5, now(), now()),
(3, 'NME', 'Fresh summer vibes', 8.8, now(), now()),
(4, 'Pitchfork', 'Vocals are amazing', 8.2, now(), now());

UPDATE avaliacaoprofissional SET idalbum = 1 WHERE id IN (1, 2);
UPDATE avaliacaoprofissional SET idalbum = 2 WHERE id IN (3, 4);

-- =========================
-- USUÁRIOS DO SISTEMA (AUTH)
-- =========================
-- senha em texto plano: 123456
-- hash gerado com PBKDF2WithHmacSHA512, salt "#$127732&", iterationCount=403, keyLength=512
INSERT INTO usuario (id, nome, login, senha, perfil, datacadastro, dataalteracao)
VALUES
    (1, 'Maximoff',  'admin',
     '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
     1,  -- Perfil.ADM 
     now(), now()),
    (2, 'Hirai',    'user',
     '+RMra81+PVL2HQWuh7xAkSohHzzzq62hw4zuaEpFHXbE0+pX+fzwOpTqmmuDA19zusgadv4fnMnHqLd2S32aXQ==',
     2,  -- Perfil.USER
     now(), now());


-- =========================
-- AJUSTE DAS SEQUENCES (PostgreSQL)
-- =========================
--  garante que os próximos INSERTs automáticos (via JPA) não reutilizem IDs já usados.

SELECT setval('empresa_id_seq',               (SELECT COALESCE(MAX(id), 0) FROM empresa));
SELECT setval('projetomusical_id_seq',        (SELECT COALESCE(MAX(id), 0) FROM projetomusical));
-- SELECT setval('artista_id_seq',               (SELECT COALESCE(MAX(id), 0) FROM artista));
-- SELECT setval('grupomusical_id_seq',          (SELECT COALESCE(MAX(id), 0) FROM grupomusical));
SELECT setval('genero_id_seq',                (SELECT COALESCE(MAX(id), 0) FROM genero));
SELECT setval('producao_id_seq',              (SELECT COALESCE(MAX(id), 0) FROM producao));
SELECT setval('album_id_seq',                 (SELECT COALESCE(MAX(id), 0) FROM album));
SELECT setval('composicao_id_seq',            (SELECT COALESCE(MAX(id), 0) FROM composicao));
SELECT setval('faixa_id_seq',                 (SELECT COALESCE(MAX(id), 0) FROM faixa));
SELECT setval('participacao_id_seq',          (SELECT COALESCE(MAX(id), 0) FROM participacao));
SELECT setval('avaliacaoprofissional_id_seq', (SELECT COALESCE(MAX(id), 0) FROM avaliacaoprofissional));
-- SELECT setval('usuario_id_seq', (SELECT COALESCE(MAX(id), 0) FROM usuario));

