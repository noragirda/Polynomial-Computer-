import org.example.Operations;
import org.example.Polynomial;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestingOperations
{

    @Test
    public void additionTest(){
        Polynomial p1= new Polynomial("27x^2+3x-7+3x^3-2x^2");
        Polynomial p2=new Polynomial("2x^2+2x^3+2");
        assertEquals(Operations.addition(p1, p2).displayPolynomial(), "5.0x^3+27.0x^2+3.0x-5.0");
        p1= new Polynomial("2x^2-5x+1");
        p2=new Polynomial("-1");
        assertEquals(Operations.addition(p1, p2).displayPolynomial(), "2.0x^2-5.0x");
    }
    @Test
    public void substractionTest(){
        Polynomial p1= new Polynomial("27x^2+3x-7+3x^3-2x^2");
        Polynomial p2=new Polynomial("2x^2+2x^3+2");
        assertEquals(Operations.substraction(p1, p2).displayPolynomial(), "x^3+23.0x^2+3.0x-9.0");
        p1= new Polynomial("2x^2-5x+1");
        p2=new Polynomial("-1");
        assertEquals(Operations.substraction(p1, p2).displayPolynomial(), "2.0x^2-5.0x+2.0");
    }
    @Test
    public void multiplicationTest(){
        Polynomial p1= new Polynomial("x+1");
        Polynomial p2=new Polynomial("x+1");
        assertEquals(Operations.multiplication(p1, p2).displayPolynomial(), "x^2+2.0x+1.0");
        p1= new Polynomial("2x^2-5x+1");
        p2=new Polynomial("0");
        assertEquals(Operations.multiplication(p1, p2).displayPolynomial(), "0");
        p1= new Polynomial("2x^2-4x+2");
        p2=new Polynomial("0.5");
        assertEquals(Operations.multiplication(p1, p2).displayPolynomial(), "x^2-2.0x+1.0");
    }
    @Test
    public void divisionTest(){
        Polynomial p1= new Polynomial("x+1");
        Polynomial p2=new Polynomial("x+1");
        Polynomial[] divisionResult = Operations.division(p1, p2);
        String resultText = "Quotient: " + divisionResult[0].displayPolynomial() +
                ", Remainder: " + divisionResult[1].displayPolynomial();
        assertEquals(resultText, "Quotient: 1.0, Remainder: 0");
    }
    @Test
    public void divisionBy0Test()
    {
        Polynomial p1= new Polynomial("x+1");
        Polynomial p2=new Polynomial("0");
        IllegalArgumentException ex=assertThrows(IllegalArgumentException.class,()->
        {
            Operations.division(p1,p2);
        });
        assertEquals("The divisor polynomial cannot be zero.", ex.getMessage());
    }
    public void divisionByLargerPolynomial()
    {
        Polynomial p1= new Polynomial("x^2+1");
        Polynomial p2=new Polynomial("x^3");
        IllegalArgumentException ex=assertThrows(IllegalArgumentException.class,()->
        {
            Operations.division(p1,p2);
        });
        assertEquals("Cannot divide", ex.getMessage());
    }
    @Test
    public void deriveTest(){
        Polynomial p1= new Polynomial("x+1");
        Polynomial p2=new Polynomial("15x^100+2x^7+9x^2+1");
        assertEquals(Operations.derivative(p2).displayPolynomial(), "1500.0x^99+14.0x^6+18.0x");
        assertEquals(Operations.derivative(p1).displayPolynomial(), "1.0");
    }
    @Test
    public void integrateTest(){
        Polynomial p1= new Polynomial("x+1");
        Polynomial p2=new Polynomial("4x^3+2x+1");
        assertEquals(Operations.integration(p2).displayPolynomial(), "x^4+x^2+x");
        assertEquals(Operations.integration(p1).displayPolynomial(), "0.5x^2+x");
    }
}
