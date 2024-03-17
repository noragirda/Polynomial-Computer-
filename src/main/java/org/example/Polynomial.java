package org.example;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polynomial {
    private HashMap<Integer, Float> polynomial = new HashMap<>();

    public Polynomial(String input) {
        if (input.isEmpty()) {
            return;  //creating an empty polynomial for empty input
        }
        if (!isValidPolynomial(input)) {
            throw new IllegalArgumentException("Invalid polynomial format.");
        }
        this.addPolynomial(input);
    }

    private boolean isValidPolynomial(String input) {
        String validPolynomialPattern = "^[-+]?\\s*(\\d*\\.?\\d*x(\\^\\d+)?|[-+]?\\d+\\.?\\d*)(\\s*[-+]\\s*(\\d*\\.?\\d*x(\\^\\d+)?|[-+]?\\d+\\.?\\d*))*$";
        String normalizedInput = input.replaceAll("(\\d)([+-])", "$1 $2").replaceAll("([+-])(\\d)", "$1 $2");
        return normalizedInput.matches(validPolynomialPattern);//checks if the normalized input string matches the valid polynomial pattern and returns true if it does, indicating a valid polynomial
    }

    public void addPolynomial(String input) {
        input = input.replaceAll(" ", "").replaceAll("-", "+-").replaceAll("\\++", "+");
        String[] splittedInput = input.split("\\+");

        for (String split : splittedInput) {
            if (split.isEmpty()) continue;

            float coeff = 1;
            int pow = 0;
            if(split.equals("x"))
            {
                coeff=1;
                pow=1;
            }
            else if (split.contains("x"))
            {
                String[] parts = split.split("x");
                coeff = parts[0].isEmpty() || parts[0].equals("+") || parts[0].equals("-") ? Float.parseFloat(parts[0]+"1") : Float.parseFloat(parts[0]);//LAO Hndles when the coeff is not mentioned
                pow = split.contains("^") ? Integer.parseInt(split.substring(split.indexOf("^") + 1)) : 1;//checks if there s an exponent, if it is arses it else 1
            } else {
                coeff = Float.parseFloat(split);
            }
            polynomial.merge(pow, coeff, Float::sum);//adds to teh map or updates
        }
    }

    public String displayPolynomial() {
        List<Integer> sortedPowers = new ArrayList<>(polynomial.keySet());
        Collections.sort(sortedPowers, Collections.reverseOrder());//creating a list of the exponents in descending order to ensure higher-degreeterms first

        StringBuilder polynomialString = new StringBuilder();
        for (int power : sortedPowers) {
            float coeff = polynomial.get(power);
            if (coeff == 0) continue;//skip

            if (polynomialString.length() > 0 && coeff > 0) {//polynomial string is not empty and the coefficient is positive it adds a +
                polynomialString.append("+");
            }
            if (power == 0) {
                polynomialString.append(String.format("%.1f", coeff));
            } else if (power == 1) {
                polynomialString.append(coeff == 1 ? "x" : (coeff == -1 ? "-x" : String.format("%.1fx", coeff)));
            } else {
                polynomialString.append(coeff == 1 ? "x^" + power : (coeff == -1 ? "-x^" + power : String.format("%.1fx^%d", coeff, power)));
            }
        }
        return polynomialString.length() > 0 ? polynomialString.toString() : "0";//return the poly, if teh coeff are all 0 then 0
    }
    public int getLargestPowFromPolynomial() {
        return polynomial.keySet().stream().max(Integer::compare).orElse(0);
    }

    // Get the coefficient of the largest exponent
    public float getLargestExponentCoefficient() {
        int largestExponent = getLargestPowFromPolynomial();
        return polynomial.getOrDefault(largestExponent, 0f);
    }
    public Boolean ifPolyIsZero()//returns if the poly is 0
    {
        Boolean zero=true;
        for(Float coeff: polynomial.values())
        {
            if(coeff!=0)
                zero=false;
        }
        return zero;
    }
    public HashMap<Integer, Float> getPolynomialMap() {
        return polynomial;
    }
}
