# Sistema de Estoque de Medicamentos — P1

Projeto Java 21 com interface JavaFX, Maven, persistência CSV e relatórios com Stream API.

## Funcionalidades

- Cadastrar, consultar, listar e excluir medicamentos associados a fornecedores
- Validar código, nome, data de validade, estoque, preço e CNPJ numérico
- Salvar os dados em `medicamentos.csv` (separador `;`) e carregá-los ao iniciar
- Gerar relatórios de estoque baixo (menos de 5 unidades) e vencimento em até 30 dias

## Executar

1. Instale Java JDK 21.
2. Abra o projeto no IntelliJ IDEA como projeto Maven e aguarde as dependências.
3. Execute `com.samuel.provasamuel.Launcher` ou rode `mvn clean javafx:run` na raiz do projeto.

## Arquivos principais

- `model/`: classes Medicamento e Fornecedor
- `repository/MedicamentoRepository.java`: operações e arquivo CSV
- `util/MedicamentoValidator.java`: validações
- `HelloController.java` e `hello-view.fxml`: interface e eventos
- `RELATORIO.md`: breve relatório da P1

O arquivo `medicamentos.csv` é gerado no diretório de execução após o primeiro cadastro. Não é incluído no GitHub para evitar publicar dados de teste.
