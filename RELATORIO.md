# Breve relatório — Sistema de Estoque de Medicamentos

## Objetivo
Desenvolver uma aplicação desktop para controle de medicamentos utilizando Java e JavaFX, conforme a P1.

## Funcionalidades
A aplicação permite cadastrar, consultar pelo código, listar e excluir medicamentos associados a fornecedores. Armazena as informações em um arquivo CSV separado por ponto e vírgula e as recupera ao iniciar.

Os dois relatórios utilizam Stream API: medicamentos próximos do vencimento (30 dias) e medicamentos com estoque baixo (menos de 5 unidades).

## Decisões de desenvolvimento
As classes Medicamento e Fornecedor representam os dados com atributos privados, construtores e getters. MedicamentoRepository concentra o cadastro, consulta, exclusão e persistência; MedicamentoValidator verifica dados de entrada e dígitos verificadores do CNPJ numérico. A interface utiliza JavaFX, FXML e HelloController para eventos. LocalDate representa datas e BigDecimal representa preços.

## Considerações
Foi escolhida uma janela única e armazenamento CSV, sem banco SQL ou Spring Boot, para manter o projeto simples e de acordo com o enunciado.
