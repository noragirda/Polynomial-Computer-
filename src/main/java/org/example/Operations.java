package org.example;

import java.util.HashMap;
import java.util.Map;
public class Operations {
    public static Polynomial addition(Polynomial a, Polynomial b) {
        Polynomial resultPolynomial = new Polynomial("");
        a.getPolynomialMap().forEach((power, coeff) ->
                resultPolynomial.getPolynomialMap().merge(power, coeff, Float::sum));
        b.getPolynomialMap().forEach((power, coeff) ->
                resultPolynomial.getPolynomialMap().merge(power, coeff, Float::sum));
        return resultPolynomial;
    }

    public static Polynomial substraction(Polynomial a, Polynomial b) { //performs a-b
        Polynomial resultPolynomial = new Polynomial("");
        a.getPolynomialMap().forEach((power, coeff) ->
                resultPolynomial.getPolynomialMap().merge(power, coeff, Float::sum));
        b.getPolynomialMap().forEach((power, coeff) ->
                resultPolynomial.getPolynomialMap().merge(power, -1 * coeff, Float::sum));
        return resultPolynomial;
    }

    public static Polynomial multiplication(Polynomial a, Polynomial b) {
        Polynomial resultPolynomial = new Polynomial("");

        for (Map.Entry<Integer, Float> aEntry : a.getPolynomialMap().entrySet()) {//going over each tern of the first polynomial and multiplying with each of teh second
            int aPower = aEntry.getKey();
            float aCoeff = aEntry.getValue();
            for (Map.Entry<Integer, Float> bEntry : b.getPolynomialMap().entrySet()) {//each in the second
                int bPower = bEntry.getKey();
                float bCoeff = bEntry.getValue();
                float resultCoeff = aCoeff * bCoeff;
                int resultPower = aPower + bPower;
                resultPolynomial.getPolynomialMap().merge(resultPower, resultCoeff, Float::sum);//updating teh result
            }
        }
        return resultPolynomial;
    }
    public static Polynomial derivative(Polynomial a)
    {
        Polynomial derivativePoly=new Polynomial("");
        a.getPolynomialMap().forEach((power, coeff)->
        {
            if(power>0)//not adding to the derivative the constant terms at all, just skipping over them
            {
                int derivPower=power-1;
                float derivCoeff=coeff*power;
                derivativePoly.getPolynomialMap().put(derivPower, derivCoeff);
            }
        });
        return derivativePoly;
    }
    public static Polynomial integration(Polynomial a) {
        Polynomial integratedPoly = new Polynomial("");
        a.getPolynomialMap().forEach((power, coeff) ->
        {
            int intPow = power + 1;
            float intCoeff = coeff / intPow;
            integratedPoly.getPolynomialMap().put(intPow, intCoeff);
        });
        return integratedPoly;
    }
    public static Polynomial[] division(Polynomial a, Polynomial b) {

        if (b.ifPolyIsZero())
        {
            throw new IllegalArgumentException("The divisor polynomial cannot be zero.");
            //resultDisplay.setText("The divisor polynomial cannot be zero.");
        }
        Polynomial quotient = new Polynomial("");
        Polynomial remainder = new Polynomial("");
        remainder.getPolynomialMap().putAll(a.getPolynomialMap()); // Initially, the remainder is the dividend

        int powerA = remainder.getLargestPowFromPolynomial();
        int powerB = b.getLargestPowFromPolynomial();
        if (powerA < powerB)
        {
            throw new IllegalArgumentException("Cannot divide");
        }
        while (!remainder.getPolynomialMap().isEmpty() && remainder.getLargestPowFromPolynomial() >= b.getLargestPowFromPolynomial()) {
            powerA = remainder.getLargestPowFromPolynomial();
            powerB = b.getLargestPowFromPolynomial();
            float coeffA = remainder.getPolynomialMap().get(powerA);
            float coeffB = b.getPolynomialMap().get(powerB);

            if (powerA < powerB) {
                break;
            }

            int powerDifference = powerA - powerB;
            float coefficientQuotient = coeffA / coeffB;

            Polynomial term = new Polynomial("");
            term.getPolynomialMap().put(powerDifference, coefficientQuotient);

            quotient = addition(quotient, term);
            Polynomial product = multiplication(b, term);
            remainder = substraction(remainder, product);

            // Cleaning up small coefficients resulting from floating-point arithmetic
            remainder.getPolynomialMap().entrySet().removeIf(entry -> Math.abs(entry.getValue()) < 1E-5);
        }

        // Returning both quotient and remainder
        return new Polynomial[]{quotient, remainder};
    }

}
