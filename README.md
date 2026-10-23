# Sistema de Estoque de Medicamentos — P1

Aplicação desktop criada em **Java 21** e **JavaFX** para gerenciamento simples de estoque de medicamentos.

## Funcionalidades

- Cadastrar medicamentos e os dados do fornecedor
- Consultar medicamentos por código ou pela seleção na tabela
- Listar medicamentos cadastrados
- Excluir medicamentos
- Salvar e recuperar dados no arquivo `medicamentos.csv`, separado por `;`
- Validar código, nome, validade, quantidade, preço e CNPJ numérico
- Relatório de medicamentos com vencimento nos próximos 30 dias
- Relatório de medicamentos com menos de 5 unidades em estoque

## Tecnologias

Java 21, JavaFX 21, Maven, Programação Orientada a Objetos e Stream API.

## Executar

1. Instale o JDK 21.
2. Abra o projeto no IntelliJ IDEA como projeto Maven.
3. Aguarde o Maven baixar as dependências.
4. Execute a classe `com.samuel.provasamuel.Launcher`.

Também é possível executar pelo terminal, na raiz do projeto, com Maven instalado:

```bash
mvn clean javafx:run
```

## Persistência

O arquivo `medicamentos.csv` é criado automaticamente no diretório de execução quando o primeiro medicamento é cadastrado e carregado ao iniciar a aplicação. Ao excluir um registro, o arquivo é atualizado. Os dados de teste locais não são enviados ao GitHub.

O CSV é simples, sem cabeçalho e sem ponto e vírgula dentro dos campos.
