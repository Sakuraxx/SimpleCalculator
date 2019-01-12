package com.sakura.calculator;
/**   
* @description: (测试计算器类) 
* @author sakura  
* @date 2018年12月15日 下午9:08:42 
*/
public class CalculatorTest {
	public static void main(String[] args) {
		CalculatorFrame calInterface = new CalculatorFrame();
		CalculatorEvent calEvent = new CalculatorEvent(calInterface);	
		SwingConsole.run(calInterface, "简单计算器", 600, 480);
	}
}
