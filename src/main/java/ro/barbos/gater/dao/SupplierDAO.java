package ro.barbos.gater.dao;

import ro.barbos.gater.model.Person;
import ro.barbos.gater.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDAO {

    public static List<Supplier> getSuppliers() {
        Logger logger = Logger.getLogger("dao");
        List<Supplier> suppliers = null;

        StringBuilder sql = new StringBuilder("select id, entrydate, registerno, RegisterId, title, address, areacode, ContactLastName, ContactFirstName, ContactPhone, ContactEmail from supplier where usestatus = 0 order by id");

        Connection con =null;
        Statement stm =null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            suppliers = new ArrayList<Supplier>();
            while(rs.next())
            {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getLong("id"));
                supplier.setEntryDate(rs.getDate("entrydate"));
                supplier.setRegisterNo(rs.getString("registerno"));
                supplier.setRegisterId(rs.getString("RegisterId"));
                supplier.setTitle(rs.getString("title"));
                supplier.setAddress(rs.getString("address"));
                supplier.setAreaCode(rs.getString("areacode"));
                Person person = new Person();
                person.setFirstName(rs.getString("ContactFirstName"));
                person.setLastName(rs.getString("ContactLastName"));
                person.setPhone(rs.getString("ContactPhone"));
                person.setEmail(rs.getString("ContactEmail"));
                supplier.setContactPerson(person);
                suppliers.add(supplier);
            }
        }catch(Exception e)
        {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return suppliers;
    }

    public static Supplier create(Supplier supplier) {
        Logger logger = Logger.getLogger("dao");

        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        StringBuilder insertSql = new StringBuilder("insert into supplier(entrydate, registerno, RegisterId, title, address, areacode, ContactLastName, ContactFirstName, ContactPhone, ContactEmail) values('");
        insertSql.append(dateTimeFormatter.format(supplier.getEntryDate())).append("','");
        insertSql.append(DataAccess.escapeString(supplier.getRegisterNo())).append("','");
        insertSql.append(DataAccess.escapeString(supplier.getRegisterId())).append("','");
        insertSql.append(supplier.getTitle()).append("','");
        insertSql.append(supplier.getAddress()).append("','");
        insertSql.append(supplier.getAreaCode()).append("'");
        Person person = supplier.getContactPerson();
        insertSql.append(",").append(person.getLastName().length() > 0 ? "'" + person.getLastName() + "'" : "NULL");
        insertSql.append(",").append(person.getFirstName().length() > 0 ? "'" + person.getFirstName() + "'" : "NULL");
        insertSql.append(",").append(person.getPhone().length() > 0 ? "'" + person.getPhone() + "'" : "NULL");
        insertSql.append(",").append(person.getEmail().length() > 0 ? "'" + person.getEmail() + "'" : "NULL").append(")");

        Connection con =null;
        Statement stm =null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(insertSql.toString());
            int rez = 	stm.executeUpdate(insertSql.toString(), Statement.RETURN_GENERATED_KEYS);
            if(rez>0) {
                rs = stm.getGeneratedKeys();
                rs.next();
                supplier.setId(rs.getLong(1));
            }
        }catch(Exception e)
        {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
            supplier = null;
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return supplier;
    }

    public static boolean delete(Supplier supplier) {
        Logger logger = Logger.getLogger("dao");
        boolean status = false;

        StringBuilder deleteSql = new StringBuilder("delete from supplier where id = ").append(supplier.getId());


        Connection con =null;
        Statement stm =null;

        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(deleteSql.toString());
            int rez = stm.executeUpdate(deleteSql.toString());
            if(rez>0) {
                status = true;
            }
        }catch(Exception e)
        {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
        }
        finally
        {
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return status;
    }

    public static Supplier findById(Long id) {
        Logger logger = Logger.getLogger("dao");
        Supplier supplier = null;

        StringBuilder sql = new StringBuilder("select id, entrydate, registerno, title, address, usestatus, areacode from supplier where id = ").append(id).append("");

        Connection con =null;
        Statement stm =null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            if(rs.next())
            {
                supplier = new Supplier();
                supplier.setId(rs.getLong("id"));
                supplier.setEntryDate(rs.getDate("entrydate"));
                supplier.setRegisterNo(rs.getString("registerno"));
                supplier.setTitle(rs.getString("title"));
                supplier.setAddress(rs.getString("address"));
                supplier.setUseStatus(rs.getInt("usestatus") == 0);
                supplier.setAreaCode(rs.getString("areacode"));
            }
        }catch(Exception e)
        {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return supplier;
    }

}
