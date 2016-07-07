package dao;


import entity.Xml;
import entity.XmlNull;
import exceptions.DAOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.DAOUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XMLDAO implements DAO<Xml> {

    @Override
    public List<Xml> load() throws DAOException {
        List<Xml> list = new ArrayList<>();
        try (DirectoryStream<Path> directory = DAOUtils.getDirectoryStream()) {

            directory.forEach(path -> {
                try {
                    list.add(getArquivo(path));
                } catch(DAOException e) {
                    e.printStackTrace();
                }
            });

        } catch(IOException e) {
            throw new DAOException(e);
        }
        return list;
    }

    @Override
    public Xml getArquivo(Path path) throws DAOException {
        Xml xml = null;
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(path));
            Element rootTag = (Element) doc.getElementsByTagName("NFe").item(0);
            xml = new Xml();

            if (!isValid(rootTag)) return new XmlNull();

            Element tagEmit = (Element) rootTag.getElementsByTagName("dest").item(0);
            Element tagCnpj_Cpf;

           if (tagEmit.getElementsByTagName("CNPJ").getLength() > 0) {
               tagCnpj_Cpf = (Element) tagEmit.getElementsByTagName("CNPJ").item(0);
           } else {
               tagCnpj_Cpf = (Element) tagEmit.getElementsByTagName("CPF").item(0);
           }
            xml.setCnpj(tagCnpj_Cpf.getTextContent());

            Element tagNome = (Element) tagEmit.getElementsByTagName("xNome").item(0);
            xml.setNome(tagNome.getTextContent());


            Element tagIde = (Element) rootTag.getElementsByTagName("ide").item(0);
            Element tagOperacao = (Element) tagIde.getElementsByTagName("natOp").item(0);
            xml.setOperacao(tagOperacao.getTextContent());

            Element tagEmissao = null;
            LocalDate date = null;

            if(tagIde.getElementsByTagName("dEmi").getLength() > 0) {
                tagEmissao = (Element) tagIde.getElementsByTagName("dEmi").item(0);
                date = LocalDate.parse(tagEmissao.getTextContent());
            } else {
                tagEmissao = (Element) tagIde.getElementsByTagName("dhEmi").item(0);
                date = LocalDate.parse(tagEmissao.getTextContent().split("T")[0]);
            }
            xml.setEmissao(date);

            Element tagNF = (Element) tagIde.getElementsByTagName("nNF").item(0);
            xml.setNumeroNota(tagNF.getTextContent());

            Element tagValorNF = (Element) rootTag.getElementsByTagName("vNF").item(0);
            xml.setValor(Double.parseDouble(tagValorNF.getTextContent()));

            xml.setChave(rootTag.getElementsByTagName("infNFe").item(0).getAttributes().getNamedItem("Id").getTextContent());

        }catch (Exception e) {
            throw new DAOException(e);
        }

        return xml;
    }

    private boolean isValid(Element element) {
        return element != null;
    }

    @Override
    public Xml getArquivoCancelado(Path path) throws DAOException {
        Xml xml = null;
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(path));
            Element rootTag = (Element) doc.getElementsByTagName("cancNFe").item(0);
            xml = new Xml();

            if (!isValid(rootTag)) return new XmlNull();

            Element tagCanc = (Element) rootTag.getElementsByTagName("infCanc").item(0);
            xml.setChave(tagCanc.getAttribute("Id"));


        }catch (Exception e) {
            throw new DAOException(e);
        }

        return xml;
    }
}
