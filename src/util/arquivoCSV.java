package util;


import entity.Xml;
import exceptions.DAOException;

import java.io.PrintStream;
import java.util.List;

public class arquivoCSV {

    public static void exportarCompras(List<Xml> list) throws DAOException {
        exportar("compras.csv", list);
    }

    public static void exportarVendas(List<Xml> list) throws DAOException {
        exportar("vendas.csv", list);
    }

    public static void exportarCanceladas(List<Xml> list) throws DAOException {
        exportar("canceladas.csv", list);
    }

    private static void exportar(String arquivo, List<Xml> list) throws DAOException {
        try (PrintStream stream = new PrintStream(arquivo)) {

            stream.println("CNPJ, NOME, EMISSAO, OPERACAO, NUMERO, VALOR, CHAVE");

            list.forEach(compra -> {
                stream.println(
                        compra.getCnpj() + ","
                                + compra.getNome() + ","
                                + compra.getEmissao() + ","
                                + compra.getOperacao() + ","
                                + compra.getNumeroNota() + ","
                                + compra.getValor() + ","
                                + compra.getChave()
                );
            });

        }catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
