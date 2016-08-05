package ro.barbos.gater.dao;

import ro.barbos.gater.model.Machine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by radu on 8/4/2016.
 */
public class MachineDAO {

    public static List<Machine> getMachines() {
        Logger logger = Logger.getLogger("dao");
        List<Machine> machines = null;

        StringBuilder sql = new StringBuilder("select id, label from inventory_machine");

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            machines = new ArrayList<>();
            while(rs.next())
            {
                Machine machine = new Machine();
                machine.setId(rs.getInt("id"));
                machine.setLabel(rs.getString("label"));
                machines.add(machine);
            }
        }catch(Exception e)
        {
            logger.warning(e.getMessage());
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return machines;
    }

    public static Machine create(Machine machine) {
        Logger logger = Logger.getLogger("dao");

        StringBuilder insertSql = new StringBuilder("insert into inventory_machine(label, type) values('");
        insertSql.append(DataAccess.escapeString(machine.getLabel())).append("',").append(MachineType.PRODUCTION.getCode()).append(")");
        StringBuilder checkSql = new StringBuilder("select * from inventory_machine where label ='").
                append(DataAccess.escapeString(machine.getLabel())).append("'");

        Connection con =null;
        Statement stm =null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(checkSql.toString());
            rs = stm.executeQuery(checkSql.toString());
            if(rs.next()) {
                machine = null;
            } else {
                logger.fine(insertSql.toString());
                int rez = 	stm.executeUpdate(insertSql.toString(), Statement.RETURN_GENERATED_KEYS);
                if(rez>0) {
                    rs = stm.getGeneratedKeys();
                    rs.next();
                    machine.setId(rs.getInt(1));
                }
            }

        }catch(Exception e)
        {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
            machine = null;
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return machine;
    }

    public static boolean delete(Machine machine) {
        Logger logger = Logger.getLogger("dao");
        boolean status = false;

        StringBuilder deleteSql = new StringBuilder("delete from inventory_machine where id = ").append(machine.getId());


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

    public static Machine getMachine(Long machineId) {
        Logger logger = Logger.getLogger("dao");
        Machine machine = null;

        StringBuilder sql = new StringBuilder("select id, label from inventory_machine where id = ").append(machineId);

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            while(rs.next())
            {
                machine = new Machine();
                machine.setId(rs.getInt("id"));
                machine.setLabel(rs.getString("label"));
            }
        }catch(Exception e)
        {
            logger.warning(e.getMessage());
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return machine;
    }
}
