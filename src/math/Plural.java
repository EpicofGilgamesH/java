package math;

import lombok.Data;

/**
 * 复数运算实现类
 * 复数的标准形式: a+bi (a为实部，b为虚部，i为虚数单位)
 */
@Data
public class Plural {

	private double real;      // 实部
	private double imaginary; // 虚部

	public Plural(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	// 无参构造函数
	public Plural() {
		this(0, 0);
	}

	/*
	 * 加法:实部相加,虚部相加
	 */
	public Plural add(Plural other) {
		return new Plural(this.real + other.getReal(), this.imaginary + other.getImaginary());
	}

	/**
	 * 减法:实部相加,虚部相减
	 *
	 * @param other
	 * @return
	 */
	public Plural subtract(Plural other) {
		return new Plural(this.real - other.getReal(), this.imaginary - other.getImaginary());
	}

	/**
	 * 乘法: (a+bi)*(c+di) = ac+adi+bci-bd = ac-bd+(ad+bc)i
	 *
	 * @param other
	 * @return
	 */
	public Plural multiply(Plural other) {
		double newReal = this.real * other.getReal() - this.imaginary * other.getImaginary();
		double newImaginary = this.real * other.getImaginary() + this.imaginary * other.getReal();
		return new Plural(newReal, newImaginary);
	}

	/**
	 * 除法: (a+bi)/(c+di)=a/(c+di) +bi/(c+di) = (ac-adi+ba+bci)/(c^2+d^2)=(ac+bd)/(c^2+d^2) + (bc-ad)i/(c^2+d^2)
	 *
	 * @param other
	 * @return
	 */
	public Plural divide(Plural other) {
		double divisor = other.getReal() * other.getReal() + other.getImaginary() * other.getImaginary();
		if (divisor == 0) {
			throw new ArithmeticException("除数不能为零!");
		}
		double newReal = (this.real * other.getReal() + this.imaginary * other.getImaginary()) / divisor;
		double newImaginary = (this.imaginary * other.getReal() - this.real * other.getImaginary()) / divisor;
		return new Plural(newReal, newImaginary);
	}

	/**
	 * 特殊处理虚部的正负号
	 *
	 * @return
	 */
	@Override
	public String toString() {
		if (imaginary == 0) return real + "";
		if (real == 0) return imaginary + "i";
		if (imaginary > 0) {
			return real + " + " + imaginary + "i";
		} else {
			return real + " - " + Math.abs(imaginary) + "i";
		}
	}
}


