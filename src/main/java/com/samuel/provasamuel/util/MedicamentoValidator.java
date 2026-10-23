package com.samuel.provasamuel.util;
import com.samuel.provasamuel.model.Fornecedor;
import com.samuel.provasamuel.model.Medicamento;
import java.time.LocalDate;
public class MedicamentoValidator {
    public static void validar(Medicamento m) {
        if (m.getCodigo() == null || !m.getCodigo().matches("[a-zA-Z0-9]{7}"))
            throw new IllegalArgumentException("O código deve ter 7 caracteres alfanuméricos.");
        if (m.getNome() == null || m.getNome().trim().length() < 3)
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        if (m.getDataValidade() == null || m.getDataValidade().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("A data de validade não pode estar vencida.");
        if (m.getQuantidadeEstoque() < 0)
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        if (m.getPreco() == null || m.getPreco().signum() <= 0)
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        Fornecedor f = m.getFornecedor();
        if (f == null) throw new IllegalArgumentException("Informe um fornecedor.");
        if (!cnpjValido(f.getCnpj())) throw new IllegalArgumentException("O CNPJ informado é inválido.");
        String[] textos = {m.getNome(), m.getDescricao(), m.getPrincipioAtivo(),
            f.getRazaoSocial(), f.getTelefone(), f.getEmail(), f.getCidade(), f.getEstado()};
        for (String texto : textos) {
            if (texto == null || texto.isBlank() || texto.contains(";") ||
                texto.contains("\n") || texto.contains("\r"))
                throw new IllegalArgumentException("Os campos de texto devem estar preenchidos e não podem conter ponto e vírgula ou quebra de linha.");
        }
    }
    private static boolean cnpjValido(String cnpj) {
        if (cnpj == null) return false;
        cnpj = cnpj.replaceAll("[./-]", "");
        if (!cnpj.matches("\\d{14}") || cnpj.matches("(\\d)\\1{13}")) return false;
        int[] pesos1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int[] pesos2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
        int soma = 0;
        for (int i=0;i<12;i++) soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
        int resto=soma%11, digito1=resto<2?0:11-resto;
        if (digito1 != Character.getNumericValue(cnpj.charAt(12))) return false;
        soma=0;
        for (int i=0;i<13;i++) soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        resto=soma%11;
        int digito2=resto<2?0:11-resto;
        return digito2==Character.getNumericValue(cnpj.charAt(13));
    }
}
