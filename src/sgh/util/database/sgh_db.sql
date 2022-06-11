CREATE DATABASE sgh_db;
USE sgh_db;

CREATE TABLE `especialidades` (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador da especialidade',
    nome varchar(50) NOT NULL COMMENT 'Nome da especialidade'
);

CREATE TABLE `tipos` (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador do tipo',
    nome varchar(20) NOT NULL UNIQUE COMMENT 'Nome do tipo'
);

INSERT IGNORE INTO `tipos` (nome) VALUES ('Administrador'), ('Atendente'), ('Médico');

CREATE TABLE `usuarios` (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador dos usuários (administrativo, atendente e médicos)',
    tipo int NOT NULL COMMENT 'Identificador do tipo do usuário',
    CONSTRAINT fk_tipo FOREIGN KEY (tipo) REFERENCES tipos (id),
    nome varchar(50) NOT NULL COMMENT 'Nome e sobrenome do usuário do sistema',
    cpf char(11) NOT NULL UNIQUE COMMENT 'CPF do usuário para fins empregatícios',
    data_nascimento date NOT NULL COMMENT 'Data de nascimento do usuário',
    telefone char(11) NOT NULL COMMENT 'Telefone para contactar o usuário',
    email varchar(50) NOT NULL COMMENT 'Email para o usuário realizar login',
    cadastro char(8) DEFAULT NULL COMMENT 'CRM do usuário do tipo médico. Não obrigatório para outros tipos',
    senha varchar(255) NOT NULL COMMENT 'Senha do usuário criptografada',
    data_cadastro timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data em que o usuário foi criado',
    sexo char(1) DEFAULT NULL COMMENT 'Char de sexo para usuários do tipo médico',
    especialidade int DEFAULT NULL COMMENT 'Identificador de especialidade para usuários do tipo médico',
    CONSTRAINT fk_especialidade FOREIGN KEY (especialidade) REFERENCES especialidades (id)
);

INSERT IGNORE INTO `usuarios` (tipo, nome, cpf, data_nascimento, telefone, email, senha) VALUES (1, 'root', '00000000000', '1970-01-01', '00000000000', 'root@root.com', '3b180a7fc1235625d0531e8deacd264d');

CREATE TABLE `pacientes` (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador do paciente',
    nome varchar(50) NOT NULL COMMENT 'Nome e sobrenome do paciente',
    sexo char(1) NOT NULL COMMENT 'Char de sexo do paciente',
    data_nasc date NOT NULL COMMENT 'Data de nascimento do paciente',
    cpf char(11) NOT NULL COMMENT 'CPF do paciente para criação de ficha física',
    telefone char(11) NOT NULL COMMENT 'Telefone para entrar em contato com o paciente',
    endereco varchar(100) NOT NULL COMMENT 'Endereco completo do paciente'
);

CREATE TABLE `consultas` (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador da consulta',
    id_medico int NOT NULL COMMENT 'Identificador do médico',
    CONSTRAINT fk_medico FOREIGN KEY (id_medico) REFERENCES usuarios (id),
    id_paciente int NOT NULL COMMENT 'Identificador do paciente',
    CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes (id),
    `data` date NOT NULL COMMENT 'Data de ocorrência da consulta',
    hora char(5) NOT NULL COMMENT 'Hora de realização da consulta',
    data_atualizada timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data em que a consulta foi modificada',
    data_cadastrada timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação da consulta'
);

CREATE TABLE `exames` (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Identificador do exame',
    id_consulta int NOT NULL COMMENT 'Identificador da consulta',
    CONSTRAINT fk_consulta FOREIGN KEY (id_consulta) REFERENCES consultas (id),
    titulo varchar(255) NOT NULL COMMENT 'Nome para identificar o exame',
    descricao text COMMENT 'Descrição do procedimento do exame',
    resultado text COMMENT 'Resultado do exame'
);