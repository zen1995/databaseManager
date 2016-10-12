package edu.buaa.databaseManager.Frame;

import java.awt.Toolkit;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class Window_Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
	    {
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("RootPane.setupButtonVisible",false);
	        BeautyEyeLNFHelper.translucencyAtFrameInactive =false; 
	       
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		BaseWindow window = new BaseWindow();
		Thread thread = new Thread(window);  
		thread.start();  
		window.show();
	}

}
