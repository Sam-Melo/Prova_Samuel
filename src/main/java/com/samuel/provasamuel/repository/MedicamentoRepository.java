package com.samuel.provasamuel.repository;
import com.samuel.provasamuel.model.Fornecedor;
import com.samuel.provasamuel.model.Medicamento;
import com.samuel.provasamuel.util.MedicamentoValidator;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class MedicamentoRepository {
    private List<Medicamento> medicamentos = new ArrayList<>();
    private final Path arquivo = Path.of("medicamentos.csv");
    public MedicamentoRepository() { carregar(); }
    public void cadastrar(Medicamento medicamento) {
        MedicamentoValidator.validar(medicamento);
        if (buscarPorCodigo(medicamento.getCodigo()) != null) throw new IllegalArgumentException("Código já cadastrado!");
        medicamentos.add(medicamento);
        salvar();
    }
    public List<Medicamento> listar() { return new ArrayList<>(medicamentos); }
    public Medicamento buscarPorCodigo(String codigo) {
        for (Medicamento m : medicamentos)
            if (m.getCodigo().equalsIgnoreCase(codigo)) return m;
        return null;
    }
    public boolean excluir(String codigo) {
        boolean removido = medicamentos.removeIf(m -> m.getCodigo().equalsIgnoreCase(codigo));
        if (removido) salvar();
        return removido;
    }
    private void salvar() {
        List<String> linhas = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            Fornecedor f = m.getFornecedor();
            linhas.add(String.join(";",
                m.getCodigo(), m.getNome(), m.getDescricao(), m.getPrincipioAtivo(),
                m.getDataValidade().toString(), String.valueOf(m.getQuantidadeEstoque()),
                m.getPreco().toString(), String.valueOf(m.isControlado()),
                f.getCnpj(), f.getRazaoSocial(), f.getTelefone(), f.getEmail(), f.getCidade(), f.getEstado()));
        }
        try { Files.write(arquivo, linhas); }
        catch (IOException e) { throw new RuntimeException("Erro ao salvar o arquivo CSV.", e); }
    }
    private void carregar() {
        if (!Files.exists(arquivo)) return;
        try {
            List<String> linhas = Files.readAllLines(arquivo);
            for (String linha : linhas) {
                String[] dados = linha.split(";", -1);
                if (dados.length != 14) throw new IllegalArgumentException("Linha inválida no CSV: " + linha);
                Fornecedor fornecedor = new Fornecedor(dados[8], dados[9], dados[10], dados[11], dados[12], dados[13]);
                Medicamento medicamento = new Medicamento(
                    dados[0], dados[1], dados[2], dados[3], LocalDate.parse(dados[4]),
                    Integer.parseInt(dados[5]), new BigDecimal(dados[6]),
                    Boolean.parseBoolean(dados[7]), fornecedor);
                medicamentos.add(medicamento);
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException("Erro ao carregar os medicamentos do CSV.", e);
        }
    }
}
