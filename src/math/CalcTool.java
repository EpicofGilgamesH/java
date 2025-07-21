package math;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalcTool extends JFrame implements ActionListener {

	// 声明组件
	private final JTextField textField1;
	private final JTextField textField2;
	private final JTextField resultField;
	private final JButton addButton, subButton, mulButton, divButton;

	public CalcTool() {
		// 设置窗口属性
		super("计算");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 150);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 流式布局，水平和垂直间距10像素

		// 创建文本框
		textField1 = new JTextField(10);
		textField2 = new JTextField(10);
		resultField = new JTextField(10);
		resultField.setEditable(false); // 结果框不可编辑

		// 创建按钮
		addButton = new JButton("加");
		subButton = new JButton("差");
		mulButton = new JButton("乘");
		divButton = new JButton("除");

		// 添加事件监听器
		addButton.addActionListener(this);
		subButton.addActionListener(this);
		mulButton.addActionListener(this);
		divButton.addActionListener(this);

		// 添加组件到窗口
		add(new JLabel("数字 1:"));
		add(textField1);
		add(new JLabel("数字 2:"));
		add(textField2);
		add(addButton);
		add(subButton);
		add(mulButton);
		add(divButton);
		add(new JLabel("结果:"));
		add(resultField);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 获取输入并转换为double [1,5]
			double num1 = Double.parseDouble(textField1.getText());
			double num2 = Double.parseDouble(textField2.getText());
			double result = 0;

			// 根据按钮执行运算
			if (e.getSource() == addButton) {
				result = num1 + num2;
			} else if (e.getSource() == subButton) {
				result = num1 - num2;
			} else if (e.getSource() == mulButton) {
				result = num1 * num2;
			} else if (e.getSource() == divButton) {
				if (num2 == 0) throw new ArithmeticException("除数不能为零");
				result = num1 / num2;
			}

			resultField.setText(String.format("%.2f", result)); // 显示两位小数

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "请输入有效数字！", "输入错误", JOptionPane.ERROR_MESSAGE); // 异常处理 [1,9](@ref)
			resultField.setText("");
		} catch (ArithmeticException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "计算错误", JOptionPane.ERROR_MESSAGE);
			resultField.setText("");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(CalcTool::new); // 确保GUI线程安全
	}
}
