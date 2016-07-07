package dao;


public class DAOFactory {

    private static DAOFactory dao;

    private DAOFactory() {
    }

    public static DAOFactory instance() {
        if (dao == null) dao = new DAOFactory();
        return dao;
    }

    public DAO getXMLDAO() {
        return new XMLDAO();
    }
}
