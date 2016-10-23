package edu.buaa.databaseManager.Frame;

import java.awt.Toolkit;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;

/**
 * 
 * @author 杨胜寒
 */
public class BackgroundDialog extends JDialog {

	private ImageIcon background;
	private JProgressBar progressBar;
	private JLabel progressInfo;

	public BackgroundDialog(URL splashPath) {
		super(new JFrame(), true);
		// 鼠标形状为等待，告知用户程序已经在很努力的加载了，此时不可操作
		setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
		// 背景图片
		background = new ImageIcon(splashPath);
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		((JPanel) getContentPane()).setOpaque(false);
		// 初始化窗体布局
		initUI();
		// 取消窗体默认装饰
		this.setUndecorated(true);
		// 把背景图片添加到分层窗格的最底层作为背景
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		setSize(background.getIconWidth(), background.getIconHeight());
		// 移至屏幕中央，覆盖闪屏区域
		// SwingUtils.moveToScreenCenter(this);
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 510) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - 70) / 2);
	}

	/**
	 * 初始化窗体UI，可以在这个方法中创建复杂的UI布局
	 */
	private void initUI() {
		progressBar = new JProgressBar();
		progressInfo = new JLabel();
		progressInfo.setText(" ");
		// progressInfo.setForeground(new java.awt.Color(204, 0, 204));
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(progressBar, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 410,
						Short.MAX_VALUE)
				.addComponent(progressInfo, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 410,
						Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap(265, Short.MAX_VALUE)
						.addComponent(progressInfo, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(progressBar,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

	}

	public void updateProcess(String info, int value) {
		progressInfo.setText(info);
		progressBar.setValue(value);
	}
}