package ro.barbos.gater.dao.production;

import ro.barbos.gater.dao.DataAccess;
import ro.barbos.gater.dto.production.ProductionProductPalletDTO;
import ro.barbos.gater.model.Product;
import ro.barbos.gater.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by radu on 8/5/2016.
 */
public class ProductionMachineDAO {

    public static List<ProductionProductPalletDTO> getProductionProducts(Long machineId) {
        Logger logger = Logger.getLogger("dao");
        List<ProductionProductPalletDTO> records = null;

        StringBuilder sql = new StringBuilder("select id, entrydate, quantity, packageno, lumbertype, userid, productid from production_product where machineid = ").
                append(machineId).append(" order by entrydate desc");

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            records = new ArrayList<>();
            while(rs.next())
            {
                ProductionProductPalletDTO record = new ProductionProductPalletDTO();
                record.setId(rs.getLong("id"));
                record.setEntryDate(rs.getDate("entrydate"));
                record.setQuantity(rs.getInt("quantity"));
                record.setPackageNo(rs.getInt("packageno"));
                record.setLumberType(rs.getLong("lumbertype"));
                Product product = new Product();
                product.setId(rs.getLong("productid"));
                record.setProduct(product);
                User user = new User();
                record.setUser(user);
                records.add(record);
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
        return records;
    }

    public static ProductionProductPalletDTO create(ProductionProductPalletDTO productPallet) {
        Logger logger = Logger.getLogger("dao");

        StringBuilder insertSql = new StringBuilder("insert into production_product(quantity, packageno, lumbertype, userid, machineid, productid) values(");
        insertSql.append(productPallet.getQuantity()).append(",").append(productPallet.getPackageNo()).append(",");
        insertSql.append(productPallet.getLumberType()).append(",").append(productPallet.getUser().getID()).append(",").
                append(productPallet.getMachine().getId()).append(",").append(productPallet.getProduct().getId()).append(")");

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
                productPallet.setId(rs.getLong(1));
            }

        }catch(Exception e)
        {
            logger.warning(e.getMessage());
            logger.log(Level.INFO, "Error", e);
            productPallet = null;
        }
        finally
        {
            if(rs!=null) try{rs.close();}catch(Exception e){}
            if(stm!=null) try{stm.close();}catch(Exception e){}
        }
        return productPallet;
    }

    public static boolean delete(ProductionProductPalletDTO productPallet) {
        Logger logger = Logger.getLogger("dao");
        boolean status = false;

        StringBuilder deleteSql = new StringBuilder("delete from production_product where id = ").append(productPallet.getId());


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

}
