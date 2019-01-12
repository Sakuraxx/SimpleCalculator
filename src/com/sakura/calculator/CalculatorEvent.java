package com.sakura.calculator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**   
* @description: (为计算机中的按钮添加事件监听器) 
* @author sakura 
* @date 2018年12月15日 下午7:32:04   
*/
public class CalculatorEvent {
	private CalculatorFrame calInterface;
	//菜单条
	private JMenuBar mb = null; 
	//显示用户输入的表达式 不可编辑
	private JTextField expTextField = null;
	//临时显示结果的文本框 可从编辑
	private JTextField resTextField = null;
	//按钮区
	private JPanel buttons = null;
	//列表区 记录用户的运算结果
	@SuppressWarnings("rawtypes")
	private DefaultListModel resultModel = null;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JList resultList = null;
	//定义一个右键菜单用于设置程序风格
	private JPopupMenu pop = new JPopupMenu();
	
		
	//----------------------做算术运算时用到的值-------------
	//运算表达式里面的数字
	private List<String> numList = Arrays.asList("0","1","2","3","4","5",
			"6","7","8","9");
	//存储每一次按下+ - * / 后临时结果 或者输入的操作数 初始值为0
	private double resultTemp = 0.0;
	//记录最后一次按按钮是否按下了运算符 + - * /
	private boolean isLastTypedOp = false;
	//记录最后按下一次的运算符 + - * 、
	private String lastTypedOp=null;
	//记录1/x sqrt(x)的临时值
	private Double tempval=0.0;
	
	/**
	 * 构造函数
	 * 
	 * @param calInterface 给传入界面设置事件监听器
	 */
	public CalculatorEvent(CalculatorFrame calInterface) {
		this.calInterface = calInterface;
		mb = calInterface.getMb();
		expTextField = calInterface.getExpTextField();
		resTextField = calInterface.getResTextField();
		buttons = calInterface.getButtons();
		resultModel = calInterface.getResultModel();
		resultList = calInterface.getResultList();
		pop = calInterface.getPop();
		
		//设置事件处理
		setPopMenu();
		setButtonsClikedEvent();
		setTextFieldStateAndEvent();
		setMenuEvent();
	
	}
	
	/**
	 *	初始化计算的临时值
	 */
	private void initVal() {
		resultTemp = 0.0;
		isLastTypedOp = false;
		lastTypedOp = null;
		tempval = 0.0;
	}
	
	/**
	 *	设置菜单选项被点击的事件
	 */
	public void setMenuEvent() {
		for(Component c:mb.getComponents()) {
			if(c instanceof JMenu) {
				JMenu jmenu = (JMenu)c;
				@SuppressWarnings("deprecation")
				String jmenuLabel = jmenu.getActionCommand();
				if("编辑(E)".equals(jmenuLabel)) {
					//todo::editMemu::NC
				}else if("查看(V)".equals(jmenuLabel)) {
					//to::viewMenu::NC
				}else if("帮助(H)".equals(jmenuLabel)) {
					((JMenuItem)jmenu.getItem(2)).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String msg = "软件名称\t：简单的计算器\n"
									+ "开发者\t：sakura\n"
									+"开发时间\t：2018年12月";
							JOptionPane.showMessageDialog(null, msg, "关于软件", JOptionPane.PLAIN_MESSAGE);
						}
					});
				}
			}
		}
	}
	
	/**
	 *	设置数字按钮被点击后显示框和输入框（临时结果显示框）的显示
	 *
	 *	@param jbtStr 被点击的按钮上的字符串
	 */
	private void setNumEvent(String jbtStr) {
		if("".equals(expTextField.getText())) {									
			String resultTempStr = resTextField.getText();
			resultTempStr = resultTempStr+jbtStr;
			resTextField.setText(resultTempStr);
		}else {
			if(isLastTypedOp) {
				resTextField.setText(jbtStr);
				isLastTypedOp = false;
			}else {
				String expStr = expTextField.getText();
				char[] expChs = expStr.toCharArray();
				if(')' == expChs[expChs.length-1]) {
					if(lastTypedOp == null) {											
						resultModel.addElement(expStr+"="+resTextField.getText());
						initVal();
						expTextField.setText("");
						resTextField.setText(jbtStr);
					}else {
						int lastOpIndex = expStr.lastIndexOf(lastTypedOp);
						expTextField.setText(expStr.substring(0,lastOpIndex+1));
						resTextField.setText(jbtStr);
					}
				}else {
					String resultTempStr = resTextField.getText();
					resultTempStr = resultTempStr+jbtStr;
					resTextField.setText(resultTempStr);
				}
			}
		}
	}
	
	/**
	 *	设置按钮区的按钮被点击的事件
	 */
	public void setButtonsClikedEvent() {
		for(Component c:buttons.getComponents()) {
			if(c instanceof JButton) {
				JButton jbt = (JButton)c;
				jbt.addActionListener(new ActionListener() {
					@SuppressWarnings({ "deprecation", "unchecked" })
					@Override
					public void actionPerformed(ActionEvent e) {
						String jbtStr = jbt.getActionCommand();
						if("=".equals(jbtStr)){
							setEqualEvent();
						}else if("C".equals(jbtStr)) {
							//清空表达式显示框和输入数字的显示框
							expTextField.setText(""); 
							resTextField.setText("");
							initVal();
						}else if("CE".equals(jbtStr)) {
							//清空临时结果显示框
							resTextField.setText("");
						}else if("Backspace".equals(jbtStr)) {
							setBackspaceEvent();
						}else if("MC".equals(jbtStr)) {
							setMCEvent();
						}else if("sqrt".equals(jbtStr)) {
							if("".equals(resTextField.getText())){
								JOptionPane.showMessageDialog(null, "当前文本框内容为空", "Warning",JOptionPane.WARNING_MESSAGE);
							}else {
								//todo::sqrt::NC
							}
						}else if("1/x".equals(jbtStr)) {
							setReciprocalEvent();
						}else if("+".equals(jbtStr)) {
							setStateTypedOp('+');
						}else if("-".equals(jbtStr)) {
							setStateTypedOp('-');
						}else if("*".equals(jbtStr)) {
							setStateTypedOp('*');
						}else if("/".equals(jbtStr)) {
							setStateTypedOp('/');
						}
						else if ("+/-".equals(jbtStr)) {
							//tudo::"+/"::NC
						}else if(numList.contains(jbtStr)){
							setNumEvent(jbtStr);
						}else if(".".equals(jbtStr)) {
							String resultTempStr = resTextField.getText();
							if(resultTempStr == null) {
								resTextField.setText("0.");
							}else {
								if(!resultTempStr.contains(".")) {
									resTextField.setText(resultTempStr+".");
								}
							}
						}
					}
				});
			}	
		}
	}
	
	/**
	 *	为expTextField和resTextField设置状态以及事件监听器
	 */
	public void setTextFieldStateAndEvent() {
		//显示表达式的文本框不可以被编辑
		expTextField.setEditable(false);
		resTextField.setEditable(false);
		//内容显示向右对齐
		expTextField.setHorizontalAlignment(JTextField.RIGHT);
		resTextField.setHorizontalAlignment(JTextField.RIGHT);
		
		//todo::resTextField.addKeyListener::NC
		//添加按键事件监听器 当按下数字时可以显示在resTextField中
//		resTextField.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				super.keyTyped(e);
//				
//				int key = e.getKeyChar();
//				//System.out.println(key);
//				if(!(key >= KeyEvent.VK_0 && key <=KeyEvent.VK_9)) {
//					//取消输入事件
//					e.consume(); 
//				}else {
//					String str = expTextField.getText();
//					if("".equals(str)) {
//						expTextField.setText(String.valueOf(key));
//					}else {
//						str += String.valueOf(key);
//						expTextField.setText(str);
//					}
//				}
//			}
//		});
	}
	
	/**
	 *	设置右键弹出菜单设置界面风格
	 */
	public void setPopMenu() {
		
		// 用于组合四个风格菜单项的ButtonGroup
			ButtonGroup flavorGroup = new ButtonGroup();
		// 创建四个单选框按钮，用于设定程序的外观风格
		 JRadioButtonMenuItem metalItem = new JRadioButtonMenuItem("Metal风格", true);
		 JRadioButtonMenuItem windowsItem = new JRadioButtonMenuItem("Windows风格");
		 JRadioButtonMenuItem motifItem = new JRadioButtonMenuItem("Motif风格");
		 JRadioButtonMenuItem nimbusItem = new JRadioButtonMenuItem("Nimbus风格");
		 
		//--------------组合右键菜单、并安装右键菜单------------
		flavorGroup.add(metalItem);
		flavorGroup.add(windowsItem);
		flavorGroup.add(motifItem);
		flavorGroup.add(nimbusItem);
		pop.add(metalItem);
		pop.add(windowsItem);
		pop.add(motifItem);
		pop.add(nimbusItem);
		
		//为四个风格事件创建事件监听器
		ActionListener flavorListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (e.getActionCommand().equals("Metal风格")) {
						changeFlavor(1);
					} else if (e.getActionCommand().equals("Windows风格")) {
						changeFlavor(2);
					} else if (e.getActionCommand().equals("Motif风格")) {
						changeFlavor(3);
					} else if (e.getActionCommand().equals("Nimbus风格")) {
						changeFlavor(4);
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		};
		
		// 为三个菜单添加事件监听器
		metalItem.addActionListener(flavorListener);
		windowsItem.addActionListener(flavorListener);
		motifItem.addActionListener(flavorListener);
		nimbusItem.addActionListener(flavorListener);	
		//将右键菜单设置在菜单条上面
		mb.setComponentPopupMenu(pop);	 
	}
	
	/**
	 *	定义一个方法，用于改变界面风格
	 */
	public void changeFlavor(int flavor) throws Exception {
		switch (flavor) {
		// 设置Metal风格
		case 1:
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			break;
		// 设置Windows风格
		case 2:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			break;
		// 设置Motif风格
		case 3:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			break;
		case 4:
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			break;
		}
		// 更新f窗口内顶级容器以及内部所有组件的UI
		SwingUtilities.updateComponentTreeUI(calInterface.getContentPane());
		// 更新mb菜单条以及内部所有组件的UI
		//SwingUtilities.updateComponentTreeUI(mb);
		// 更新pop右键菜单以及内部所有组件的UI
		SwingUtilities.updateComponentTreeUI(pop);
	}
	
	/**
	 *	当按下+ - * /时设置两个显示文本框的状态 以及一些参数变化
	 */
	public  void setStateTypedOp(char op) {
		//输入数字的显示框 resultTextField 不为空
		if(!("".equals(resTextField.getText()))) {	
			if("".equals(expTextField.getText())) {
				expTextField.setText(resTextField.getText()+String.valueOf(op));
				//设置中间结果 
				resultTemp = Double.valueOf(resTextField.getText());
			}else {
				char[] expChs = expTextField.getText().toCharArray();
				//如果最后一次按下的是运算符 则替换上一次的运算符
				if(isLastTypedOp) {
					expChs[expChs.length-1] = op;
					expTextField.setText(String.valueOf(expChs));
				}else{
					//若lastTypedOp存在 则存在着resultTemp 
					if(lastTypedOp != null) {
						Double num = Double.valueOf(resTextField.getText());
						resultTemp = CalculatorUtil.calTempResult(lastTypedOp, resultTemp, num);
						if(')'==expChs[expChs.length-1]) {
							expTextField.setText(expTextField.getText()+String.valueOf(op));
						}else {
							expTextField.setText(expTextField.getText()+num+String.valueOf(op));
						}
						resTextField.setText(String.valueOf(resultTemp));						
					}else {
						expTextField.setText(expTextField.getText()+String.valueOf(op));
						resultTemp = Double.valueOf(resTextField.getText());
					}
				}
			}
			lastTypedOp = String.valueOf(op);
			isLastTypedOp = true;
		}
	}
	
	/**
	 *	=按钮被点击时
	 */
	private void setEqualEvent() {
		if("".equals(expTextField.getText())){
			if("".equals(resTextField.getText())) {									
				JOptionPane.showMessageDialog(null, "当前文本框内容为空", "Warning",JOptionPane.WARNING_MESSAGE);
			}else {
				resultModel.addElement(resTextField.getText()+"="+resTextField.getText());
			}
		}else {	
			String expression = expTextField.getText();
			//如果最后一次按钮按下的还是操作符即没有按下新的数字 
			if(isLastTypedOp) {
				//去掉运算表达式的最后一个操作符
				String resultExp = expression.substring(0, expression.length()-1);
				Double resultVal = resultTemp;
				resultModel.addElement(resultExp+"="+resultVal);
			}else {
				if(lastTypedOp!=null) {
					Double resultVal = CalculatorUtil.calTempResult(lastTypedOp, resultTemp, Double.valueOf(resTextField.getText()));
					char[] expChs = expression.toCharArray();
					if(')' == expChs[expChs.length-1]) {
						resultModel.addElement(expression+"="+resultVal);
					}else {											
						resultModel.addElement(expression+resTextField.getText()+"="+resultVal);
					}
				}else {
					resultModel.addElement(expression+"="+resTextField.getText());
				}
			}
		}
		//初始化全局的变量
		initVal();
		//情况文本显示框
		resTextField.setText("");
		expTextField.setText("");
	}
	
	/**
	 *	设置Backspace按钮被点击时的事件
	 */
	private void setBackspaceEvent() {
		if("".equals(resTextField.getText())){
			JOptionPane.showMessageDialog(null, "当前文本框内容为空", "Warning",JOptionPane.WARNING_MESSAGE);
		}else {
			String textFiledContent = resTextField.getText();
			int len = textFiledContent.length();
			if(len >= 2) {									
				resTextField.setText(textFiledContent.substring(0, textFiledContent.length()-1));
			}else {
				resTextField.setText("");
			}
		}
	}
	
	/**
	 *	设置MC按钮被点击时的事件
	 */
	private void setMCEvent() {
		if(resultModel.isEmpty()) {
			JOptionPane.showMessageDialog(null, "当前结果列表内容为空", "Warning",JOptionPane.WARNING_MESSAGE);
		}else {
			//在resultList中被选择的表项
			int [] index = resultList.getSelectedIndices();
			if(index.length>0) {
				int n =  JOptionPane.showConfirmDialog(null, "你确定要删除选择的运算结果吗？", "删除", 
						JOptionPane.YES_NO_OPTION);
				if(n == 0) {
					int lastIndext = index.length-1;
					while(lastIndext>=0) {
						resultModel.removeElementAt(lastIndext);
						lastIndext--;
					}
				}
			}else {
				int n = JOptionPane.showConfirmDialog(null, "你确定要删除所有的运算结果吗？", "删除", 
						JOptionPane.YES_NO_OPTION);
				if(n == 0) {
					resultModel.removeAllElements();
				}
			}
		}
	}
	
	/**
	 *	除法的处理
	 */
	private void setReciprocalEvent() {
		if("".equals(resTextField.getText())){
			JOptionPane.showMessageDialog(null, "当前文本框内容为空", "Warning",JOptionPane.WARNING_MESSAGE);
		}else {
			String numStr = resTextField.getText();
			if("0".equals(numStr)) {
				JOptionPane.showMessageDialog(null, "除数不可以为0", "Warning",JOptionPane.WARNING_MESSAGE);
			}else {
				Double num = Double.valueOf(numStr);
				//设置倒数的临时值
				tempval = 1.0/num;
				//显示当前倒数的结果
				resTextField.setText(String.valueOf(tempval));
				String reciprocalStr = "";
				//该数是输入第一个操作数
				if("".equals(expTextField.getText())) {
					reciprocalStr = "1.0/("+num+")";
					expTextField.setText(reciprocalStr);
				}else {
					if(lastTypedOp != null) {
						char[] expChs = expTextField.getText().toCharArray();
						int in=expChs.length-1;
						String expStr = expTextField.getText();
						if(')'==expChs[in]) {
							//找到算术表达式最后一个操作符的位置 更新其后的操作数
							int lastOpIndex = expStr.lastIndexOf(lastTypedOp);
							reciprocalStr = "1.0/("+expStr.substring(lastOpIndex+1)+")";
							expStr = expStr.substring(0,lastOpIndex+1)+reciprocalStr;
							expTextField.setText(expStr);	
						}else {
							expTextField.setText(expStr+"1.0/("+numStr+")");
						}
					}
					else {
						reciprocalStr = "1.0/("+expTextField.getText()+")";
						expTextField.setText(reciprocalStr);
					}
				}
			}
		}		
	}
}
