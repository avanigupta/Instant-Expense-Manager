/**
 * Database to store the Push expense information.
 * @author udit.gupta
 */
package com.paisa.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyExpenseDatabase                                                                                                         
{         
	/**----------------------------------------------------------------
	Global Variables                                                
  	----------------------------------------------------------------*/ 
	public  String KEY_ROWID = "_id";  
	public static final String KEY_TITLE = "TITLE";                                                                              
	public static final String KEY_ICON_SET = "MESSAGE";                                                                              
	public static final String KEY_DATE = "DATE";                                                                              
	public static final String KEY_BANK = "BANK";                                                                              
	public static final String KEY_CATEGORY = "TYPE";
	public static final String KEY_AMOUNT = "AMOUNT";
	public static final String KEY_MONTH = "MONTH";





	Cursor c;

	private static final String TAG = "DataBase";                                                                                     
	private static final String DATABASE_NAME = "expenseDatabase";                                                                               
	private static final String DATABASE_TABLE = "expense"; 

	private static final int DATABASE_VERSION = 2;                                                                                     
	private static final String DATABASE_CREATE =                                                                                      
			"create table expense (_id integer  primary key  autoincrement , "                                                                 
					+ "TITLE text not null,MESSAGE text  null,DATE text not null,BANK text  null,TYPE text not null, AMOUNT double not null,MONTH num not null)";

	private Context context;                                                                                                     
	private DatabaseHelper mDbHelper;                                                                                                   
	private SQLiteDatabase db;    

	public MyExpenseDatabase(Context ctx)                                                                                                      

	{    
		this.context = ctx;                                                                                                       
		mDbHelper = new DatabaseHelper(context);
	}   

	private static class DatabaseHelper extends SQLiteOpenHelper                                                                 
	{        
		DatabaseHelper(Context context)                                                                                           
		{                                                                                                                              
			super(context, DATABASE_NAME, null, DATABASE_VERSION);                                                                         
		}
		/**----------------------------------------------------------------
	    TO CRETAE THE DATABASE                                               
	   ----------------------------------------------------------------*/
		@Override                                                                                                                          
		public void onCreate(SQLiteDatabase db)                                                                                            
		{          
			db.execSQL(DATABASE_CREATE); 
		} 
		/**----------------------------------------------------------------
	    Method to update the database                                                
	   ----------------------------------------------------------------*/
		@Override                                                                                                                          
		public void onUpgrade(SQLiteDatabase db,                                                                                           
				int oldVersion,                                                                                                            
				int newVersion)                                                                                                            
		{                                                                                                                                  
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE); 
			onCreate(db);                                                                                                                      
		}                                                                                                                                  
	}                                                                                                                                  
	/**----------------------------------------------------------------
    Method to open the database                                                
   ----------------------------------------------------------------*/                                                                                                        
	public MyExpenseDatabase open() throws SQLException                                                                                        
	{    
		if(mDbHelper==null)
		{
			mDbHelper = new DatabaseHelper(this.context);  
		}

		db = mDbHelper.getWritableDatabase();                                                                                      
		return this;   

	}                                                                                                                  
	/**----------------------------------------------------------------
    Method to close the database                                                
   ----------------------------------------------------------------*/                                                                                                        
	public void close()                                                                                                                
	{                                                                                                                                  
		mDbHelper.close();                                                                                                              
	}                                                                                                                              
	/*****************************************************************************
	 * Function Definitions
	 *****************************************************************************/

	/*****************************************************************************
	 * DESCRIPTION
	 *  Insert the expense information.
	 *  @param   title title of the message.
	 *  @param   message message in the expense.
	 *  @param   date timestamp of the expense.
	 *  @param   BANK product id if available.
	 *  @param   search search key if available.
	 *  @param   klass if the expense is general or promotional
	 *  @return long
	 *  
	 *****************************************************************************/                                                                                          
	public long insertInfo(String title,String message,String date,String BANK,String type,double amount,int month)                                                
	{                                                                                                                                  
		ContentValues initialValues = new ContentValues();                                                                             
		initialValues.put(KEY_TITLE, title);  
		initialValues.put(KEY_ICON_SET, message);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_BANK, BANK);
		initialValues.put(KEY_CATEGORY, type);
		initialValues.put(KEY_AMOUNT, amount);
		initialValues.put(KEY_MONTH, month);



		return db.insert(DATABASE_TABLE, null, initialValues);                                                                             
	} 



	/*****************************************************************************
	 * Function Definitions
	 *****************************************************************************/

	/*****************************************************************************
	 * DESCRIPTION
	 *  get all the info from database in descending order of inserted id
	 *  @return {@link Cursor} Containing info for tile,message,date,BANK(optional),search(optional)
	 *  
	 *****************************************************************************/                                                                                                  
	public Cursor getAllInfo()                                                                                                       
	{      		
		return db.query(DATABASE_TABLE, new String[]                                                                              
				{   KEY_ROWID ,  KEY_TITLE,KEY_ICON_SET,KEY_DATE,KEY_BANK,KEY_CATEGORY,KEY_AMOUNT,"SUM("+KEY_AMOUNT+")"},                                                          
				null ,                                                                                                                      
				null,                                                                                                                      
				KEY_CATEGORY,                                                                                                                      
				null,                                                                                                                      
				KEY_DATE+" DESC" );  
	}
	public Cursor getCategoryInfo(String category)                                                                                                       
	{      		
		String[] args = new String[]{category};

		return db.query(DATABASE_TABLE, new String[]                                                                              
				{   KEY_ROWID ,  KEY_TITLE,KEY_ICON_SET,KEY_DATE,KEY_BANK,KEY_CATEGORY,KEY_AMOUNT},                                                          
				KEY_CATEGORY+" = ?" ,                                                                                                                      
				args,                                                                                                                      
				null,                                                                                                                      
				null,                                                                                                                      
				KEY_DATE+" DESC" );  
	}

	public Cursor getWeeklyBudget(String category)                                                                                                       
	{      		
		String[] args = new String[]{category};

		return db.query(DATABASE_TABLE, new String[]                                                                              
				{   KEY_ROWID ,  KEY_TITLE,KEY_ICON_SET,KEY_DATE,KEY_BANK,KEY_CATEGORY,KEY_AMOUNT,"SUM("+KEY_AMOUNT+")"},                                                          
				KEY_CATEGORY + "="
						+ args,                                                                                                                      
						null,                                                                                                                      
						null,                                                                                                                      
						null,                                                                                                                      
						KEY_DATE+" DESC" );  
	}

	public Cursor getMonthlyBudget()                                                                                                       
	{      		
		return db.query(DATABASE_TABLE, new String[]                                                                              
				{   KEY_ROWID ,  KEY_TITLE,KEY_ICON_SET,KEY_DATE,KEY_BANK,KEY_CATEGORY,KEY_AMOUNT,"SUM("+KEY_AMOUNT+")",KEY_MONTH},                                                          
				null,                                                                                                                      
				null,                                                                                                                      
				KEY_MONTH,                                                                                                                      
				null,                                                                                                                      
				KEY_DATE+" DESC" );  
	}


	public boolean delete(String id) 
	{
		return db.delete(DATABASE_TABLE,null,null) > 0;
	}

	/*****************************************************************************
	 * Function Definitions
	 *****************************************************************************/

	/*****************************************************************************
	 * DESCRIPTION
	 * </p>
	 *  delete a particular id from the database.
	 *  @param id id to be deleted. 
	 *  @return {@link boolean}
	 *  
	 *****************************************************************************/                                                                                                  

	public boolean deleteId(String id) 
	{
		return db.delete(DATABASE_TABLE,null,null) > 0;
	}

	public void setContext(Context ctx)                                                                                                      
	{        //                                                                                                       
		this.context = ctx;                                                                                 

	} 
	public int updateTable(long id,String title,String iconSet,String category)
	{
		ContentValues initialValues = new ContentValues();      
		initialValues.put(KEY_TITLE, title);  
		initialValues.put(KEY_ICON_SET, iconSet);
		initialValues.put(KEY_CATEGORY, category);
		return db.update(DATABASE_TABLE, initialValues, KEY_ROWID + "="
				+ id, null) ;                                                                             

	}

}  