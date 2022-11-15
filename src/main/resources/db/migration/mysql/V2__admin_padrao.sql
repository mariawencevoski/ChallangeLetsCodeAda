-- EMPRESA I

INSERT INTO `empresa` (`id`, `cnpj`, `data_atualizacao`, `data_criacao`, `razao_social`)
VALUES (NULL, '91719293000117', CURRENT_DATE(), CURRENT_DATE(), 'Tablet SA');

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`, 
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`) 
VALUES (NULL, '73463571080', CURRENT_DATE(), CURRENT_DATE(), 'admin@tablet.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
'Senha1234', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '91719293000117'));

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '84971982043', CURRENT_DATE(), CURRENT_DATE(), 'user@tablet.com', 'USER', 'ROLE_USER', NULL, NULL,
'Senha1234', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '91719293000117'));

-- EMPRESA II

INSERT INTO `empresa` (`id`, `cnpj`, `data_atualizacao`, `data_criacao`, `razao_social`)
VALUES (NULL, '78580441000105', CURRENT_DATE(), CURRENT_DATE(), 'Computer Ltda');

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '27637868044', CURRENT_DATE(), CURRENT_DATE(), 'admin@computer.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
'Senha1234', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '78580441000105'));

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '47989261081', CURRENT_DATE(), CURRENT_DATE(), 'user@computer.com', 'USER', 'ROLE_USER', NULL, NULL,
'Senha1234', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '78580441000105'));

-- EMPRESA III

INSERT INTO `empresa` (`id`, `cnpj`, `data_atualizacao`, `data_criacao`, `razao_social`)
VALUES (NULL, '81471262000136', CURRENT_DATE(), CURRENT_DATE(), 'Smartphone ME');

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '82296643078', CURRENT_DATE(), CURRENT_DATE(), 'admin@smartphone.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
'Senha1234', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '81471262000136'));

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`,
`perfil`, `qtd_horas_almoco`, `qtd_horas_trabalho_dia`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '48296007053', CURRENT_DATE(), CURRENT_DATE(), 'user@smartphone.com', 'USER', 'ROLE_USER', NULL, NULL,
'Senha1234', NULL,
(SELECT `id` FROM `empresa` WHERE `cnpj` = '81471262000136'));