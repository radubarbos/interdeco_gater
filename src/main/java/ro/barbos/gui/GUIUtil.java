package ro.barbos.gui;

public class GUIUtil {

	public static String STOCK_KEY="stoc";
	public static String ORDER_QTYCHK_KEY="orderqtychk";
	public static String ORDER_KEY="order";
	public static String REPORT_MUP_KEY ="repmup";
	public static String USER_RIGHTS_KEY="userrights";
	public static String PRODUCT_KEY="product";
	public static String CLIENT_KEY="client";
	public static String PASSWORD_CHANGE_KEY="chgpass";
	public static String RECEPTIE_KEY="receptie";
	public static String IDPLATE_KEY="idplates";
	public static String STACKS_KEY="stacks";
	public static String STACKS_STOCKS_KEY="stacksstock";
	public static String TYPE_STOCKS_KEY="typestock";
    public static String SUPPLIER_STOCK_KEY = "supplierstock";
    public static String CLASS_STOCKS_KEY="classstock";
	public static String SUPPLIER_STOCKS_KEY="supplierstock";
    public static String LUMBER_TRANSPORT_STOCK_EY = "lumber_transport_stock";
    public static String TRANSPORT_STOCK_ENTRIES_KEY = "TRANSPORT_STOCK_ENTRIES";
    public static String PROCESSED_HISTORY_KEY="prochistory";
	public static String SETTINGS_KEY="settings";
	public static String STACKS_LUMBERS_KEY="stackslumbers";
	public static String CUT_SIMULATION_KEY="cut_simulation";
	public static String CUT_OPTIONS_KEY="cut_options";
	public static String CUT_PLAN_KEY="cut_plan";
	public static String HISTORY_CUT_PLAN_KEY="history_cut_plan";
	public static String USERS_KEY="users";
    public static String INVENTORY_MACHINE_KEY="inventory_machine";
    public static String INVENTORY_MACHINE_SINGLE_KEY="inventory_machine_single";

	public static MainContainer container =null;
	public static InfoPanel info =null;
	public static MainFrame main=null;
	
	public static String[] types = new String[]{"Brad", "Molid", "Fag", "Stejar"};
	public static String[] lumberClass = new String[]{"A", "A-B", "B", "B-C"};
	public static String[] metric = new String[]{"MM", "CM", "DM", "M"};
}
