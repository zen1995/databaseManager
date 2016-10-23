package edu.buaa.databaseManager.Frame;

	import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.UIManager;



import edu.buaa.databaseManager.database.DatabaseHelper;

	public class Window_Main {

		public static void main(String[] args) throws SQLException  {
			// TODO Auto-generated method stub
			
		   
			DatabaseHelper helper = new DatabaseHelper();
			BaseWindow window = new BaseWindow(helper);
			Thread thread = new Thread(window);  
			thread.start();  
			window.show();
		}

	}


