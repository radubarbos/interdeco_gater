package ro.barbos.gater.dao;

import ro.barbos.gater.data.DataSearchPagination;
import ro.barbos.gater.data.DataSearchResult;
import ro.barbos.gater.data.LumberLogUtil;
import ro.barbos.gater.data.METRIC;
import ro.barbos.gater.dto.*;
import ro.barbos.gater.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockDAO {
	
	public static List<LumberLogStockEntry> getCurrentLumbersLogs(LumberLogFilterDTO filter) {
		Logger logger = Logger.getLogger("dao");
		
		List<LumberLogStockEntry> lumberLogs = null;
        StringBuilder sql = new StringBuilder("select l.id, l.idplate, i.label, l.small_diameter, l.big_diameter, l.length, l.volume, l.lumbertype, l.lumberclass, st.name, stoe.entryDate, u.FullName, l.planId, l.reallength, l.realvolume from lumberlog l left join idplate i on l.idplate = i.id left join lumberstack st on l.stack = st.id join lumberentry_to_lumberlog le on l.id = le.lumberlogid join lumberentry stoe on le.entryid = stoe.id join user u on stoe.user = u.ID where l.Status=0 ");
        if(filter != null) {
            sql.append(" ");
            if(filter.getStacks() != null && filter.getStacks().size() > 0) {
                sql.append("and  l.stack = ").append(filter.getStacks().get(0));
            }
			if(filter.getIdPlates() != null && filter.getIdPlates().size() > 0) {
                sql.append("and  l.idplate = ").append(filter.getIdPlates().get(0));
            }
		}
		sql.append(" order by i.label");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	lumberLogs = new ArrayList<LumberLogStockEntry>();
	    	while(rs.next()) {
	    		LumberLog lumberLog = new LumberLog();
	    		lumberLog.setId(rs.getLong(1));
	    		IDPlate plate = new IDPlate();
	    		plate.setId(rs.getLong(2));
	    		plate.setLabel(rs.getString(3));
	    		lumberLog.setPlate(plate);
	    		lumberLog.setSmallRadius(rs.getDouble(4));
	    		lumberLog.setBigRadius(rs.getDouble(5));
	    		lumberLog.setLength(rs.getDouble(6));
	    		lumberLog.setRealLength(rs.getLong(14));
	    		lumberLog.setVolume(rs.getDouble(7));
	    		lumberLog.setRealVolume(rs.getDouble(15));
	    		lumberLog.setLumberType(rs.getLong(8));
	    		lumberLog.setLumberClass(rs.getLong(9));
	    		lumberLog.setCutPlanId(rs.getLong(13));
	    		LumberStack stack = new LumberStack();
	    		stack.setName(rs.getString(10));
	    		lumberLog.setStack(stack);
	    		LumberLogStockEntry stockEntry = new LumberLogStockEntry();
	    		stockEntry.setLumberLog(lumberLog);
	    		Date entryDate = rs.getTimestamp(11);
	    		User user = new User();
	    		user.setName(rs.getString(12));
	    		stockEntry.setUser(user);
	    		stockEntry.setEntryDate(entryDate);
	    		lumberLogs.add(stockEntry);
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    return lumberLogs;
	}
	
	public static List<LumberStackInfoDTO> getCurrentStackInfo() {
		Logger logger = Logger.getLogger("dao");
		
		List<LumberStackInfoDTO> stacksInfo = null;
        StringBuilder sql = new StringBuilder("select s.id, s.name, s.minimum, s.maximum, count(l.id) as lumbertotal, sum((l.volume/1000000000)) as volume from lumberstack s left join lumberlog l on l.stack = s.id where l.Status = 0 group by s.id");

        Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	stacksInfo = new ArrayList<LumberStackInfoDTO>();
	    	while(rs.next()) {
	    		LumberStackInfoDTO infoDTO = new LumberStackInfoDTO();
	    		LumberStackDTO stack = new LumberStackDTO();
	    		stack.setId(rs.getLong(1));
	    		stack.setName(rs.getString(2));
	    		stack.setMinimum(rs.getDouble(3));
	    		stack.setMaximum(rs.getDouble(4));
	    		stack.setMetric(METRIC.MILIMETER);
	    		infoDTO.setStack(stack);
	    		infoDTO.setTotalLumberLogs(rs.getLong(5));
	    		infoDTO.setTotalVolume(rs.getDouble(6));
	    		stacksInfo.add(infoDTO);
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    return stacksInfo;
	}
	
	public static List<TypeStockDTO> getCurrentStockTypeInfo() {
		Logger logger = Logger.getLogger("dao");
		
		List<TypeStockDTO> typeInfo = null;
        StringBuilder sql = new StringBuilder("select t.id, t.name, count(l.id) as lumbertotal, sum((l.volume/1000000000)) as volume from lumbertype t left join lumberlog l on l.lumbertype = t.id where l.Status=0 group by t.id");

        Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.fine(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	typeInfo = new ArrayList<TypeStockDTO>();
	    	while(rs.next()) {
	    		TypeStockDTO typeDTO = new TypeStockDTO();
	    		typeDTO.setId(rs.getLong(1));
	    		typeDTO.setName(rs.getString(2));
	    		typeDTO.setTotalLumberLogs(rs.getLong(3));
	    		typeDTO.setTotalVolume(rs.getDouble(4));
	    		typeInfo.add(typeDTO);
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    return typeInfo;
	}
	
	public static List<ClassStockDTO> getCurrentStockClassInfo() {
		Logger logger = Logger.getLogger("dao");
		
		List<ClassStockDTO> typeInfo = null;
        StringBuilder sql = new StringBuilder("select c.id, c.name, count(l.id) as lumbertotal, sum((l.volume/1000000000)) as volume from lumberclass c left join lumberlog l on l.lumberclass = c.id where l.Status=0 group by c.id");

        Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.fine(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	typeInfo = new ArrayList<ClassStockDTO>();
	    	while(rs.next()) {
	    		ClassStockDTO lumberClassDTO = new ClassStockDTO();
	    		lumberClassDTO.setId(rs.getLong(1));
	    		lumberClassDTO.setName(rs.getString(2));
	    		lumberClassDTO.setTotalLumberLogs(rs.getLong(3));
	    		lumberClassDTO.setTotalVolume(rs.getDouble(4));
	    		typeInfo.add(lumberClassDTO);
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    return typeInfo;
	}
	
	
	public static DataSearchResult<ProcessedLumberLog> getProcessedHistory(ProcessedLumberLogFilterDTO filter, DataSearchPagination pagination) {
		Logger logger = Logger.getLogger("dao");
		
		List<ProcessedLumberLog> processedLogs = null;
		DataSearchResult<ProcessedLumberLog> result = new DataSearchResult<>();
		boolean doTotal = false;
		StringBuilder totalSql = null;
		StringBuilder sql = new StringBuilder("select e.entrydate, e.small_diameter, e.big_diameter, e.length, e.volume, e.idlumbertype, e.idlumberclass, u.FullName, ip.Label from lumberlog_processed e left join user u on e.userid = u.id left join idplate ip on e.idplate = ip.id");
		StringBuilder where = null;
		
		if(filter != null) {
			where = new StringBuilder();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			boolean addedWhere = false;
			where.append(" where ");
			if(filter.getStartDate() != null) {
				where.append(" e.entryDate >= '").append(dateFormat.format(filter.getStartDate())).append("'");
				addedWhere = true;
			}
			if(filter.getEndDate() != null) {
				if(addedWhere) {
					where.append(" and ");
				}
				Date finalDate = filter.getEndDate();
				if(filter.isIncludeEndDate()) {
					int dat = finalDate.getDate();
					finalDate.setDate(dat + 1);
				}
				where.append(" e.entryDate < '").append(dateFormat.format(finalDate)).append("'");
			}
		}
		if(where != null) {
			sql.append(where);
		}
		sql.append(" order by e.entrydate desc");
		if(pagination != null && pagination.getPageIndex() == 0) {
			totalSql = new StringBuilder("select count(*) from lumberlog_processed e ");
			if(where != null) {
				totalSql.append(where);
			}
			doTotal = true;
		}
		if(pagination != null) {
			sql.append(" limit ").append(pagination.getPageIndex() * pagination.getPageSize()).append(",").append(pagination.getPageSize());
		}
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString()); 
	    	if(doTotal) {
	    		logger.info(totalSql.toString()); 
	    		rs = stm.executeQuery(totalSql.toString());
	    		if(rs.next()) {
	    			result.setTotal(rs.getLong(1));
	    		}
	    	}
	        rs = stm.executeQuery(sql.toString());
	    	processedLogs = new ArrayList<ProcessedLumberLog>();
	    	while(rs.next()) {
	    		ProcessedLumberLog processedLumber = new ProcessedLumberLog();
	    		processedLumber.setProcessedDate(rs.getDate(1));
	    		LumberLog lumberLog = new LumberLog();
	    		lumberLog.setSmallRadius(rs.getDouble(2));
	    		lumberLog.setBigRadius(rs.getDouble(3));
	    		lumberLog.setLength(rs.getDouble(4));
	    		lumberLog.setVolume(rs.getDouble(5));
	    		lumberLog.setLumberType(rs.getLong(6));
	    		lumberLog.setLumberClass(rs.getLong(7));
	    		processedLumber.setLumberLog(lumberLog);
	    		User user = new User();
	    		user.setName(rs.getString(8));
	    		processedLumber.setUser(user);
	    		IDPlate plate = new IDPlate();
	    		plate.setLabel(rs.getString(9));
	    		lumberLog.setPlate(plate);
	    		processedLogs.add(processedLumber);
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    result.setData(processedLogs);
	    return result;
	}

	public static Map getStockStatictics() {
		Logger logger = Logger.getLogger("dao");
		
		Map<String, Object> statistics = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("select count(*) as no, sum(volume/1000000000) as volume, lumbertype from lumberlog where Status = 0 group by lumbertype;");

        Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getNewDatabaseConnection();
	    	stm = con.createStatement();
	    	logger.fine(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	long totalLumberLogs = 0;
	    	double totalVolume = 0;
	    	while(rs.next()) {
	    		int count = rs.getInt(1);
	    		double vol = rs.getDouble(2);
	    		int lumberType = rs.getInt(3);
	    		totalLumberLogs += count;
	    		totalVolume += vol;
	    		statistics.put(""+lumberType, count);
	    	}
	    	if(!statistics.containsKey("1")) statistics.put("1", 0);
	    	if(!statistics.containsKey("2")) statistics.put("2", 0);
	    	if(!statistics.containsKey("3")) statistics.put("3", 0);
	    	if(!statistics.containsKey("4")) statistics.put("4", 0);
	    	statistics.put("LUMBER_LOG_COUNT", totalLumberLogs);
	    	statistics.put("LUMBER_LOG_VOLUME", totalVolume);
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
			if(con!=null) try{con.close();}catch(Exception e){}
		}
	    
	    return statistics;
	}
	
	public static List<LumberLog> getJustCurrentLumbersLogs(LumberLogFilterDTO filter) {
		Logger logger = Logger.getLogger("dao");
		
		List<LumberLog> lumberLogs = null;
		StringBuilder sql = new StringBuilder("select l.id, l.idplate, i.label, l.small_diameter, l.big_diameter, l.length, l.volume, l.lumbertype, l.lumberclass, st.name, l.reallength, l.realvolume, l.planId from lumberlog l left join idplate i on l.idplate = i.id left join lumberstack st on l.stack = st.id");
		if(filter != null) {
            sql.append(" where l.Status=0 ");
            String operator = "";
			if(filter.getStacks() != null && filter.getStacks().size() > 0) {
                sql.append(" and l.stack = ").append(filter.getStacks().get(0));
            }
			if(filter.getIdPlates() != null && filter.getIdPlates().size() > 0) {
                sql.append(" and l.idplate = ").append(filter.getIdPlates().get(0));
            }
			if(filter.isAvailable()) {
				sql.append(" l.planId is NULL ");
                operator = " and ";
			}
			if(filter.getMinLength() != -1) {
				sql.append(operator).append(" l.length >= ").append(filter.getMinLength());
			}
		}
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getDatabaseConnection();
	    	con.setAutoCommit(true);
	    	stm = con.createStatement();
	    	logger.info(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	lumberLogs = new ArrayList<LumberLog>();
	    	while(rs.next()) {
	    		LumberLog lumberLog = new LumberLog();
	    		lumberLog.setId(rs.getLong(1));
	    		IDPlate plate = new IDPlate();
	    		plate.setId(rs.getLong(2));
	    		plate.setLabel(rs.getString(3));
	    		lumberLog.setPlate(plate);
	    		lumberLog.setSmallRadius(rs.getDouble(4));
	    		lumberLog.setBigRadius(rs.getDouble(5));
	    		lumberLog.setLength(rs.getDouble(6));
	    		lumberLog.setRealLength(rs.getLong(11));
	    		lumberLog.setVolume(rs.getDouble(7));
	    		lumberLog.setRealVolume(rs.getDouble(12));
	    		lumberLog.setLumberType(rs.getLong(8));
	    		lumberLog.setLumberClass(rs.getLong(9));
                lumberLog.setCutPlanId(rs.getLong(13));
	    		LumberStack stack = new LumberStack();
	    		stack.setName(rs.getString(10));
	    		lumberLog.setStack(stack);
	    		lumberLogs.add(lumberLog);
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	       logger.log(Level.INFO, "Error", e);
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
		}
	    
	    return lumberLogs;
	}
	
	public static int adjustStock() {
		Logger logger = Logger.getLogger("dao");
		int error = 0;
		StringBuilder sql = new StringBuilder("select id, small_diameter, big_diameter, length, reallength from lumberlog");
		
		Connection con =null;
	    Statement stm =null;
	    ResultSet rs = null;
	    try {
	    	con = DataAccess.getInstance().getNewDatabaseConnection();
	    	stm = con.createStatement();
	    	logger.fine(sql.toString()); 
	    	rs = stm.executeQuery(sql.toString());
	    	while(rs.next()) {
	    		LumberLog lumberLog = new LumberLog();
	    		lumberLog.setId(rs.getLong(1));
	    		lumberLog.setSmallRadius(rs.getDouble(2));
	    		lumberLog.setBigRadius(rs.getDouble(3));
	    		lumberLog.setLength(rs.getDouble(4));
	    		lumberLog.setRealLength(rs.getLong(5));
	    		LumberLogDAO.refreshLumberLog(lumberLog);
	    		LumberLogUtil.trimAndSetLength(lumberLog);
				LumberLogUtil.calculateVolume(lumberLog);
				Statement smt2 = con.createStatement();
				int update = smt2.executeUpdate("update lumberlog set length="+lumberLog.getLength()+", volume="+lumberLog.getVolume()+" where id="+lumberLog.getId());
				error+=update;
				smt2.close();
	    	}
	    }catch(Exception e){
	       logger.warning(e.getMessage());
	    }
	    finally{
	    	if(rs!=null) try{rs.close();}catch(Exception e){}
			if(stm!=null) try{stm.close();}catch(Exception e){}
			if(con!=null) try{con.close();}catch(Exception e){}
		}
	    
	    return error;
	}

    public static List<SupplierStockDTO> getCurrentStockSupplierInfo() {
        Logger logger = Logger.getLogger("dao");

        List<SupplierStockDTO> results = null;
        StringBuilder sql = new StringBuilder("select s.id, s.title, count(l.id) as lumbertotal, sum((l.volume/1000000000)) as volume from supplier s left join lumberlog l on l.SupplierId = s.id where s.usestatus =0 and l.Status=0 group by s.id");

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            con = DataAccess.getInstance().getDatabaseConnection();
            con.setAutoCommit(true);
            stm = con.createStatement();
            logger.fine(sql.toString());
            rs = stm.executeQuery(sql.toString());
            results = new ArrayList<>();
            while (rs.next()) {
                SupplierStockDTO stockDTO = new SupplierStockDTO();
                stockDTO.setId(rs.getLong(1));
                stockDTO.setName(rs.getString(2));
                stockDTO.setTotalLumberLogs(rs.getLong(3));
                stockDTO.setTotalVolume(rs.getDouble(4));
                results.add(stockDTO);
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
}
