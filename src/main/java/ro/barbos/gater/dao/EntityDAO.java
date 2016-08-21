package ro.barbos.gater.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by radu on 8/17/2016.
 */
public abstract class EntityDAO<T> {

    public List<T> findAll() {
        Logger logger = Logger.getLogger("dao");
        List<T> results = null;

        StringBuilder sql = getFindAllQuery();

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            results = new ArrayList<T>();
            while (rs.next()) {
                results.add(fromResultSet(rs));
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stm != null) try {
                stm.close();
            } catch (Exception e) {
            }
        }
        return results;
    }

    public abstract StringBuilder getFindAllQuery();

    public T findById(Long id) {
        Logger logger = Logger.getLogger("dao");
        T entity = null;

        StringBuilder sql = getFindByIdQuery(id);

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            if (rs.next()) {
                entity = fromResultSet(rs);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stm != null) try {
                stm.close();
            } catch (Exception e) {
            }
        }
        return entity;
    }

    public abstract StringBuilder getFindByIdQuery(Long id);

    public abstract T store(T entity);

    public abstract boolean delete(T entity);

    protected abstract T fromResultSet(ResultSet rs) throws SQLException;
}
