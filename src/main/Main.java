package main;

import dao.DAOFactory;

public class Main {

    public static void main (String[] args) throws Exception {

        System.out.println(DAOFactory.instance().getXMLDAO().load().size());

    }
}
