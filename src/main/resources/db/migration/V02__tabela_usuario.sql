CREATE TABLE IF NOT EXISTS tb_usuario(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	login VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(50) NOT NULL,
	grupo_id BIGINT(20) NOT NULL,
	ativo TINYINT(1),
	PRIMARY KEY (id),
	FOREIGN KEY (grupo_id) REFERENCES tb_grupo(id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_usuario (nome, login, email, senha, grupo_id, ativo) VALUES('Márcio Costa', 'admin', 'admin@admin', 'admin', 1, 1);