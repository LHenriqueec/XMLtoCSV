import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import dao.DAOFactory;
import entity.Xml;
import exceptions.DAOException;
import org.junit.Before;
import org.junit.Test;
import util.DAOUtils;
import util.SearchFile;
import util.arquivoCSV;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainTeste {

    private Path dir;
    private Path start;

    @Before
    public void init() {
        dir = Paths.get("C:\\Users\\LuizS\\Desktop\\Backup Flag\\Notas Compra\\Backup");
        start = Paths.get("C:\\Users\\LuizS\\Desktop\\Backup Flag\\Autorizadas\\backup\\Autorizados");
    }

    @Test
    public void testSize() throws Exception {
        List<Xml> list = DAOFactory.instance().getXMLDAO().load();
        System.out.println(list.size());
    }

    @Test
    public void test() throws Exception {
        try (DirectoryStream<Path> directory = DAOUtils.getDirectoryStream()) {
            directory.forEach(System.out::println);
        }
    }

    @Test
    public void criarArquivo() throws DAOException {
        arquivoCSV.exportarCompras(DAOFactory.instance().getXMLDAO().load());
    }

    @Test
    public void buscarArquivos() throws IOException, DAOException {
        Files.walkFileTree(start, new SearchFile());
        System.out.println(SearchFile.list.size());
        arquivoCSV.exportarVendas(SearchFile.list);
    }

    @Test
    public void crarArquivoCancelados() throws IOException, DAOException {
        Files.walkFileTree(start, new SearchFile());
        SearchFile.list.stream().filter(xml -> xml instanceof Xml).collect(Collectors.toList());
        System.out.println(SearchFile.list.size());
        arquivoCSV.exportarCanceladas(SearchFile.list);
    }
}
