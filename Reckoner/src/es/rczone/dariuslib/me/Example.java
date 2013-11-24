// Put the expression evaluator through its paces.

// Sample usage:

// $ java expr.Example '3.14159 * x^2' 0 4 1
// 0
// 3.14159
// 12.5664
// 28.2743
// 50.2654
//
// $ java expr.Example 'sin (pi/4 * x)' 0 4 1
// 0
// 0.707107
// 1
// 0.707107
// 1.22461e-16
//
// $ java expr.Example 'sin (pi/4 x)' 0 4 1
// I don't understand your formula "sin (pi/4 x)".
// 
// I got as far as "sin (pi/4" and then saw "x".
// I expected ")" at that point, instead.
// An example of a formula I can parse is "sin (pi/4 + x)".

package es.rczone.dariuslib.me;

/**
 * A simple example of parsing and evaluating an expression.
 */
public class Example {
	public static void main(String[] args) {

//		Expr expr;
//		try {
//			expr = Parser.parse(args[0]);
//		} catch (SyntaxException e) {
//			System.err.println(e.explain());
//			return;
//		}
//
//		double low = Double.valueOf(args[1]).doubleValue();
//		double high = Double.valueOf(args[2]).doubleValue();
//		double step = Double.valueOf(args[3]).doubleValue();
//
//		Variable x = Variable.make("x");
//		for (double xval = low; xval <= high; xval += step) {
//			x.setValue(xval);
//			System.out.println(expr.value());
//		}
		
		
		
		String formula = "(3*x)-1";
		Expr expr;
		try {
			expr = Parser.parse(formula);
		} catch (SyntaxException e) {
			System.err.println(e.explain());
			return;
		}
		
		Variable x = Variable.make("x");
		x.setValue(2);
		System.out.println(expr.value());
		x.setValue(0.5);
		System.out.println(expr.value());
		x.setValue(1);
		System.out.println(expr.value());
		
		
		
		String squareRoot = "sqrt(b^2 - 4*a*c) / (2*a)";
		
		try {
			expr = Parser.parse(squareRoot);
		} catch (SyntaxException e) {
			System.err.println(e.explain());
			return;
		}
		
		Variable a = Variable.make("a");
		a.setValue(1);
		Variable b = Variable.make("b");
		b.setValue(3);
		Variable c = Variable.make("c");
		c.setValue(2);
		System.out.println("SquareRoot: "+expr.value());
		

		double d = Math.toRadians(3.1416);
		
		String formulaPI = "cos("+d+")";
		try {
			expr = Parser.parse(formulaPI);
		} catch (SyntaxException e) {
			System.err.println(e.explain());
			return;
		}
		
		System.out.println("Pi: "+expr.value());
	}
	
	
}
