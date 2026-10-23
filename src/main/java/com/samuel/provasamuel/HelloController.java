package com.samuel.provasamuel;

import com.samuel.provasamuel.model.Fornecedor;
import com.samuel.provasamuel.model.Medicamento;
import com.samuel.provasamuel.repository.MedicamentoRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HelloController {
    private final MedicamentoRepository repository = new MedicamentoRepository();

    @FXML private TextField campoCodigo;
    @FXML private TextField campoNome;
    @FXML private TextField campoDescricao;
    @FXML private TextField campoPrincipioAtivo;
    @FXML private DatePicker campoValidade;
    @FXML private TextField campoQuantidade;
    @FXML private TextField campoPreco;
    @FXML private CheckBox campoControlado;

    @FXML private TextField campoCnpj;
    @FXML private TextField campoRazaoSocial;
    @FXML private TextField campoTelefone;
    @FXML private TextField campoEmail;
    @FXML private TextField campoCidade;
    @FXML private TextField campoEstado;

    @FXML private TableView<Medicamento> tabelaMedicamentos;
    @FXML private TableColumn<Medicamento, String> colunaCodigo;
    @FXML private TableColumn<Medicamento, String> colunaNome;
    @FXML private TableColumn<Medicamento, String> colunaPreco;
    @FXML private TableColumn<Medicamento, String> colunaEstoque;

    @FXML public void initialize() {
        colunaCodigo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCodigo()));
        colunaNome.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNome()));
        colunaPreco.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPreco().toString()));
        colunaEstoque.setCellValueFactory(d -> new SimpleStringProperty(
                String.valueOf(d.getValue().getQuantidadeEstoque())));
        atualizarTabela();
    }

    @FXML private void cadastrar() {
        try {
            Fornecedor fornecedor = new Fornecedor(
                campoCnpj.getText().trim(), campoRazaoSocial.getText().trim(),
                campoTelefone.getText().trim(), campoEmail.getText().trim(),
                campoCidade.getText().trim(), campoEstado.getText().trim());
            Medicamento medicamento = new Medicamento(
                campoCodigo.getText().trim(), campoNome.getText().trim(),
                campoDescricao.getText().trim(), campoPrincipioAtivo.getText().trim(),
                campoValidade.getValue(), Integer.parseInt(campoQuantidade.getText().trim()),
                new BigDecimal(campoPreco.getText().trim().replace(",", ".")),
                campoControlado.isSelected(), fornecedor);
            repository.cadastrar(medicamento);
            atualizarTabela();
            limparCampos();
            mostrarMensagem(Alert.AlertType.INFORMATION, "Medicamento cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            mostrarMensagem(Alert.AlertType.ERROR, "Preço ou quantidade inválidos.");
        } catch (IllegalArgumentException e) {
            mostrarMensagem(Alert.AlertType.ERROR, e.getMessage());
        } catch (RuntimeException e) {
            mostrarMensagem(Alert.AlertType.ERROR, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    @FXML private void consultar() {
        String codigo = obterCodigo();
        if (codigo.isEmpty()) {
            mostrarMensagem(Alert.AlertType.WARNING, "Informe o código ou selecione um medicamento.");
            return;
        }
        Medicamento m = repository.buscarPorCodigo(codigo);
        if (m == null) {
            mostrarMensagem(Alert.AlertType.WARNING, "Medicamento não encontrado.");
            return;
        }
        Fornecedor f = m.getFornecedor();
        String dados = "Código: " + m.getCodigo() +
            "\nNome: " + m.getNome() + "\nDescrição: " + m.getDescricao() +
            "\nPrincípio ativo: " + m.getPrincipioAtivo() +
            "\nValidade: " + m.getDataValidade() +
            "\nQuantidade: " + m.getQuantidadeEstoque() +
            "\nPreço: R$ " + m.getPreco() +
            "\nControlado: " + (m.isControlado() ? "Sim" : "Não") +
            "\nFornecedor: " + f.getRazaoSocial() +
            "\nCNPJ: " + f.getCnpj() + "\nTelefone: " + f.getTelefone() +
            "\nE-mail: " + f.getEmail() + "\nCidade: " + f.getCidade() +
            "\nEstado: " + f.getEstado();
        mostrarMensagem(Alert.AlertType.INFORMATION, dados);
    }

    @FXML private void excluir() {
        String codigo = obterCodigo();
        if (codigo.isEmpty()) {
            mostrarMensagem(Alert.AlertType.WARNING, "Informe o código ou selecione um medicamento.");
            return;
        }
        try {
            boolean excluido = repository.excluir(codigo);
            if (excluido) {
                atualizarTabela();
                limparCampos();
                mostrarMensagem(Alert.AlertType.INFORMATION, "Medicamento excluído com sucesso!");
            } else {
                mostrarMensagem(Alert.AlertType.WARNING, "Medicamento não encontrado.");
            }
        } catch (RuntimeException e) {
            mostrarMensagem(Alert.AlertType.ERROR, "Erro ao excluir: " + e.getMessage());
        }
    }

    @FXML private void relatorioVencimento() {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(30);
        List<String> resultado = repository.listar().stream()
            .filter(m -> !m.getDataValidade().isBefore(hoje) &&
                         !m.getDataValidade().isAfter(limite))
            .map(m -> m.getCodigo() + " - " + m.getNome() +
                    " | Validade: " + m.getDataValidade())
            .toList();
        mostrarRelatorio("Medicamentos próximos do vencimento", resultado);
    }

    @FXML private void relatorioEstoqueBaixo() {
        List<String> resultado = repository.listar().stream()
            .filter(m -> m.getQuantidadeEstoque() < 5)
            .map(m -> m.getCodigo() + " - " + m.getNome() +
                    " | Quantidade: " + m.getQuantidadeEstoque())
            .toList();
        mostrarRelatorio("Medicamentos com estoque baixo", resultado);
    }

    private String obterCodigo() {
        String codigo = campoCodigo.getText().trim();
        if (!codigo.isEmpty()) return codigo;
        Medicamento selecionado = tabelaMedicamentos.getSelectionModel().getSelectedItem();
        return selecionado != null ? selecionado.getCodigo() : "";
    }

    private void atualizarTabela() {
        tabelaMedicamentos.getItems().setAll(repository.listar());
    }

    @FXML private void limpar() { limparCampos(); }

    private void limparCampos() {
        campoCodigo.clear(); campoNome.clear(); campoDescricao.clear();
        campoPrincipioAtivo.clear(); campoValidade.setValue(null);
        campoQuantidade.clear(); campoPreco.clear(); campoControlado.setSelected(false);
        campoCnpj.clear(); campoRazaoSocial.clear(); campoTelefone.clear();
        campoEmail.clear(); campoCidade.clear(); campoEstado.clear();
        tabelaMedicamentos.getSelectionModel().clearSelection();
    }

    private void mostrarRelatorio(String titulo, List<String> dados) {
        String texto = dados.isEmpty() ? "Nenhum medicamento encontrado." : String.join("\n", dados);
        TextArea area = new TextArea(texto);
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(12);
        area.setPrefColumnCount(55);
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Relatório");
        alerta.setHeaderText(titulo);
        alerta.getDialogPane().setContent(area);
        alerta.showAndWait();
    }

    private void mostrarMensagem(Alert.AlertType tipo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Sistema de Medicamentos");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
