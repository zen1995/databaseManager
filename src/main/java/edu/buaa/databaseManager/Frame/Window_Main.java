package edu.buaa.databaseManager.Frame;

import java.sql.SQLException;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import edu.buaa.databaseManager.database.DatabaseHelper;

public class Window_Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
			BeautyEyeLNFHelper.translucencyAtFrameInactive = false;

		} catch (Exception e) {
			// TODO exception
		}
		DatabaseHelper helper = new DatabaseHelper();
		BaseWindow window = new BaseWindow(helper);
		Thread thread = new Thread(window);
		thread.start();
		window.show();
	}

}
