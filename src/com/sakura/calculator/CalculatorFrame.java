package com.sakura.calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**   
* @description: (构造计算器的图形界面) 
* @author sakura  
* @date 2018年12月15日 下午7:31:37 
*/
public class CalculatorFrame extends JFrame{
	//菜单条
	private JMenuBar mb = new JMenuBar();
	//显示用户输入的表达式 不可编辑
	private JTextField expTextField = new JTextField();
	//临时显示结果的文本框 可从编辑
	private JTextField resTextField = new JTextField();

	//按钮区
	private JPanel buttons = new JPanel();
	//列表区 记录用户的运算结果
	@SuppressWarnings("rawtypes")
	private DefaultListModel resultModel = new DefaultListModel();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JList resultList = new JList(resultModel);
	
	//定义一个右键菜单用于设置程序风格
	private JPopupMenu pop = new JPopupMenu();	
		
	/**
	 * 	构造函数
	 */
	public CalculatorFrame() {
		setLayout();
		setMenuBar();
	}
	
	/**
	 *	设置计算器整体布局
	 */
	private void setLayout() {
		//计算器上方的输入框和显示框
		JPanel textFields = new JPanel();
		textFields.setLayout(new BorderLayout());
		textFields.add(expTextField);
		textFields.add(resTextField,BorderLayout.SOUTH);
		
		//设置按钮区内部布局
		setButtonsLayout();
		
		//使用BorderLayout布局器布局整体
		add(textFields, BorderLayout.NORTH);
		add(buttons,BorderLayout.WEST);
		add(resultList);
		
		//监听窗口大小变化事件  对内部组件大小进行设置 
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent state) {
				if(state.getOldState() != state.getNewState()) {
					Toolkit tk = Toolkit.getDefaultToolkit();
					Dimension d = tk.getScreenSize();
					switch(state.getNewState()) {
					case Frame.MAXIMIZED_BOTH:
						expTextField.setPreferredSize(new Dimension(d.width, d.height/10));
						buttons.setPreferredSize(new Dimension(d.width/10*8, d.height/10*8));
						break;
					case Frame.NORMAL:
						setSize(d.width/3, d.height/3);
						setLocation((d.width-d.width/3)/2, (d.height-d.height/3)/2);
						expTextField.setPreferredSize(new Dimension(d.width/3, d.height/30));
						buttons.setPreferredSize(new Dimension(d.width/30*7, d.height/30*7));
						pack();
					}
				}
			}
		});
		
		//固定界面最小值
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setMinimumSize(new Dimension(d.width/3, d.height/3));
		setLocation((d.width-d.width/3)/2, (d.height-d.height/3)/2);
		textFields.setPreferredSize(new Dimension(d.width/3, d.height/30));
		buttons.setPreferredSize(new Dimension(d.width/30*7, d.height/30*7));
		
		//按组件大小自动适配
		pack();	
	}	
	
	/**
	 *	设置菜单条中的内容
	 */
	private void setMenuBar() {
		//菜单栏中的三项菜单
		 JMenu 									
			edit = new JMenu("编辑(E)"), 
			view = new JMenu("查看(V)"), 
			help = new JMenu("帮助(H)");	
		
		//创建“复制”、“粘贴”菜单项并为之指定图标
		 JMenuItem copyItem = new JMenuItem("复制", new ImageIcon("ico/copy.png"));
		 JMenuItem pasteItem = new JMenuItem("粘贴", new ImageIcon("ico/paste.png"));
		
		//创建视图菜单里面的“普通”、“科学”、“程序员”菜单项
		 JRadioButtonMenuItem bMenuItem1= new JRadioButtonMenuItem("普通");
		 JRadioButtonMenuItem bMenuItem2= new JRadioButtonMenuItem("科学");
		 JRadioButtonMenuItem bMenuItem3= new JRadioButtonMenuItem("程序员");
		//用于组合视图菜单里面的三个菜单项的ButtonGroup 避免可以多选
		 ButtonGroup viewMenuGroup = new ButtonGroup();
		
		//创建帮助菜单的菜单项 “帮助”、“关于”
		 JMenuItem helpItem = new JMenuItem("帮助");
		 JMenuItem aboutInfoItem = new JMenuItem("关于");
		 
		//----------------组合菜单、并为菜单添加事件监听器---------------
		//为copyItemf pasteItem等菜单项设置快捷键，使用大写字母
		copyItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		pasteItem.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		
		//为编辑 查看 帮助菜单设置助记符
		edit.setMnemonic('E');		//快捷键为alt + e
		view.setMnemonic('V');
		help.setMnemonic('H');
		
		edit.add(copyItem);
		edit.add(pasteItem);
	
		viewMenuGroup.add(bMenuItem1);
		viewMenuGroup.add(bMenuItem2);
		viewMenuGroup.add(bMenuItem3);
		view.add(bMenuItem1);
		view.add(bMenuItem2);
		view.add(bMenuItem3);
		
		help.add(helpItem);
		help.addSeparator();
		help.add(aboutInfoItem);
		
		mb.add(edit);
		mb.add(view);
		mb.add(help);
		//将这个类的菜单条设置为mb
		setJMenuBar(mb);		
	}
	
	/**
	 *	设置按钮区的布局
	 */
	private void setButtonsLayout() {
		GridBagLayout gbLayout = new GridBagLayout();
		buttons.setLayout(gbLayout);
		
		GridBagConstraints gbCons = new GridBagConstraints();
		gbCons.fill = GridBagConstraints.BOTH;
		gbCons.insets = new Insets(5, 5, 5, 5);
		gbCons.weightx = 4;		//纵向比例为3:4
		gbCons.weighty = 3;
		JButton jbt = null;
		
		jbt = new JButton("Backspace");
		gbCons.gridx = 1;
		gbCons.gridy = 0;
		gbCons.gridwidth = 2;
		buttons.add(jbt,gbCons);
		
		jbt = new JButton("CE");
		gbCons.gridx = 3;
		gbCons.gridy = 0;
		gbCons.gridwidth = 2;
		buttons.add(jbt, gbCons);
		
		jbt = new JButton("C");
		gbCons.gridx = 5;
		gbCons.gridy = 0;
		gbCons.gridwidth = 1;
		buttons.add(jbt, gbCons);
		
		String[] butsStr = {"MC","7","8","9","/","MR","4","5","6","*","MS","1","2","3","-","M+","0","+/-",".","+"};
		int k=0;
		for(int i=1;i<5;i++) {
			for(int j=0;j<5;j++) {
				gbCons.gridx = j;
				gbCons.gridy = i;
				buttons.add(new JButton(butsStr[k]), gbCons);
				k++;
			}
		}
		
		jbt = new JButton("sqrt");
		gbCons.gridx = 5;
		gbCons.gridy = 1;
		buttons.add(jbt, gbCons);
		
		jbt = new JButton("1/x");
		gbCons.gridx = 5;
		gbCons.gridy = 2;
		buttons.add(jbt, gbCons);
		
		jbt = new JButton("=");
		gbCons.gridheight = 2;
		gbCons.gridx = 5;
		gbCons.gridy = 3;
		buttons.add(jbt, gbCons);
	}
	
	public JMenuBar getMb() {
		return mb;
	}

	public void setMb(JMenuBar mb) {
		this.mb = mb;
	}

	public JTextField getExpTextField() {
		return expTextField;
	}

	public void setExpTextField(JTextField expTextField) {
		this.expTextField = expTextField;
	}

	public JTextField getResTextField() {
		return resTextField;
	}

	public void setResTextField(JTextField resTextField) {
		this.resTextField = resTextField;
	}

	public JPanel getButtons() {
		return buttons;
	}

	public void setButtons(JPanel buttons) {
		this.buttons = buttons;
	}

	public DefaultListModel getResultModel() {
		return resultModel;
	}

	public void setResultModel(DefaultListModel resultModel) {
		this.resultModel = resultModel;
	}

	public JList getResultList() {
		return resultList;
	}

	public void setResultList(JList resultList) {
		this.resultList = resultList;
	}

	public JPopupMenu getPop() {
		return pop;
	}

	public void setPop(JPopupMenu pop) {
		this.pop = pop;
	}
}
