import java.util.Arrays;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm;


public class Test {
	public static void main(String[] args) throws FunctionEvaluationException {
		double x[] = new double[]{0.0,3.0,6.0};
		double y[] = new double[]{0.0,3.0,0.0};
		
		PolynomialFunctionLagrangeForm  p = new PolynomialFunctionLagrangeForm(x, y);
		double coeff[] = p.getCoefficients();
		for(double u=0.0; u<=6.0; u++)
			System.out.println( (int)p.value(u) );
	}
}
