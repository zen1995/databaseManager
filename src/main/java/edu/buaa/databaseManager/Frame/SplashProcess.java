package edu.buaa.databaseManager.Frame;

import java.net.URL;
import java.sql.SQLException;

import javax.swing.UIManager;

/** 
 * 
  
 */
public class SplashProcess {

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		// 初始化闪屏Dialog时指定闪屏图片
		URL url = SplashProcess.class.getResource("/logo.png");
		final BackgroundDialog splashWindow = new BackgroundDialog(url);
		// 启动一个线程来加载数据
		new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 10; i++) {
						if (i < 9) {
							splashWindow.updateProcess("正在进行第" + i + "次缓存数据加载. . .", i * 9);
						} else
							splashWindow.updateProcess("Powered By 北航分布与移动计算实验室", i * 9);
						Thread.sleep(150);
					}
				} catch (InterruptedException ex) {
					// 异常不做处理
				}
				Window_Main window = new Window_Main();
				try {
					window.main(null);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
				splashWindow.updateProcess("正在启动主窗体. . .", 100);
				// SwingUtils.moveToScreenCenter(window);
				splashWindow.setVisible(false);
				// 数据加载完成，显示主窗体

				// 释放资源
				splashWindow.dispose();
			}
		}.start();
		// 显示闪屏Dialog
		splashWindow.setVisible(true);
	}
}