package dao;

import exceptions.DAOException;

import java.nio.file.Path;
import java.util.List;

public interface DAO<T> {

    List<T> load() throws DAOException;
    T getArquivo(Path path) throws DAOException;
    T getArquivoCancelado(Path path) throws DAOException;
}
