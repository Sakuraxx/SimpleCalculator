package com.sakura.calculator;
/**   
* @description: 计算运算表达式的值的工具类 
* @author sakura
* @date 2018年12月9日 下午12:18:52 
*/
public class CalculatorUtil {
	/**
	 *	计算上一次的中间结果与这次按下的数的结果
	 *	
	 *	@param op	运算符
	 *	@param tempResult	上一次运算的临时结果
	 *	@param	num 按钮上的数字
	 *	@return 本次运算的结果
	 */
	public static double calTempResult(String op, Double tempResult, Double num) {
		char ch = op.toCharArray()[0];
		switch (ch) {
		case '+':
			return tempResult+num;
		case '-':
			return tempResult-num;
		case '*':
			return tempResult*num;
		case '/':
			return tempResult/num;
		}
		return 0;
	}
}
