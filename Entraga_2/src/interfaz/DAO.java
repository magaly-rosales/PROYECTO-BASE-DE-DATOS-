package interfaz;
import dao.DAOException;

import java.util.List;

public interface DAO<T , K>  {
    void guardar(T a) throws DAOException;
    void actualizar(T a) throws DAOException;
    void eliminar(T id) throws DAOException;
    List<T> listar() throws DAOException;
    T obtener(K id) throws DAOException;
}
