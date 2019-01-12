package com.sakura.calculator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**   
* @description:统一控制Swing界面的显示框架 
* @author sakura 
* @date 2018年12月15日 下午3:27:14 
*/
public class SwingConsole {
	/**
	 * @param f 
	 * @param title 标题
	 * @param width 宽度
	 * @param height 长度
	 */
	public static void run(final JFrame f, String title, int width, int height) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				f.setTitle(title);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	
				f.setSize(width, height);
				f.setVisible(true);
			}
		});
	}
}
