package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class GUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextField polynomialInput1;
    private JTextArea resultDisplay;
    private String selectedOperation;
    private Polynomial currentResult;

    public GUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        // frame
        frame = new JFrame("Polynomial Computer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);

        // main panel setup
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.setBackground(new Color(87, 181, 194));

        // input label
        JLabel inputLabel = new JLabel("Input the polynomial(s) you want to perform operations on:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(Color.BLACK);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(inputLabel, gbc);

        // polynomial input
        polynomialInput1 = new JTextField();
        polynomialInput1.setFont(new Font("Arial", Font.PLAIN, 20));
        polynomialInput1.setForeground(Color.WHITE);
        polynomialInput1.setBackground(new Color(87, 181, 194));
        polynomialInput1.setPreferredSize(new Dimension(800, 50));
        polynomialInput1.setMaximumSize(polynomialInput1.getPreferredSize());
        gbc.gridy++;
        mainPanel.add(polynomialInput1, gbc);


        // buttons for operations
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addOperationButton(buttonPanel, "Add", "Addition");
        addOperationButton(buttonPanel, "Subtract", "Subtraction");
        addOperationButton(buttonPanel, "Multiply", "Multiplication");
        addOperationButton(buttonPanel, "Divide", "Division");
        addOperationButton(buttonPanel, "Differentiate", "Derivative");
        addOperationButton(buttonPanel, "Integrate", "Integration");
        addOperationButton(buttonPanel, "Clear", "Clear");
        buttonPanel.setPreferredSize(new Dimension(800, 85));
        gbc.gridy++;
        gbc.gridwidth = 3;
        mainPanel.add(buttonPanel, gbc);

        // result display

        resultDisplay = new JTextArea(10, 50);
        resultDisplay.setEditable(false);
        resultDisplay.setForeground(Color.WHITE);
        resultDisplay.setBackground(Color.PINK);
        resultDisplay.setLineWrap(true); // enable next line when line full
        resultDisplay.setWrapStyleWord(true); // makes sure my word is not split so it becomes unreadable
        resultDisplay.setFont(new Font("Arial", Font.PLAIN, 24));

        JScrollPane resultScrollPane = new JScrollPane(resultDisplay);
        resultScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // disable horizontal scrollbar
        resultScrollPane.setPreferredSize(new Dimension(800, 200));
        gbc.gridy++;
        gbc.gridwidth = 3;
        mainPanel.add(resultScrollPane, gbc);

        // adding main panel to the frame
        frame.add(mainPanel);
        frame.pack(); // adjusts frame size to fit the components
        frame.setLocationRelativeTo(null); // center the frame on the screen
        frame.setVisible(true);
    }

    private void addOperationButton(JPanel panel, String label, String operation) // method to create the operation buttons
    {
        Color buttonColor = new Color(204, 153, 255);
        RoundedButton button = new RoundedButton(label, 30);
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.addActionListener((ActionEvent e) -> {
            selectedOperation = operation;
            performOperation();
        });
        panel.add(button);
    }

   private void performOperation() {
       String input = polynomialInput1.getText().trim(); // Trim input to remove leading/trailing spaces

       if (selectedOperation.equals("Clear")) {
           currentResult = null;
           resultDisplay.setText("");
           polynomialInput1.setText(""); // clear input field
           return;
       }

       try {
           // Create a new Polynomial from the trimmed input
           Polynomial newPolynomial = new Polynomial(input);
           if (selectedOperation.equals("Division") && currentResult != null) {
               // Handle division separately
                   Polynomial[] divisionResult = Operations.division(currentResult, newPolynomial);

                   // divisionResult[0] is the quotient, divisionResult[1] is the remainder
                   String resultText = "Quotient: " + divisionResult[0].displayPolynomial() +
                           ", Remainder: " + divisionResult[1].displayPolynomial();
                   resultDisplay.setText(resultText);
           }
           if (currentResult == null)
           {
               currentResult = newPolynomial;
               if(selectedOperation.equals("Derivative"))
                   currentResult = Operations.derivative(currentResult);
               if(selectedOperation.equals("Integration"))
                   currentResult = Operations.integration(currentResult);
               if(selectedOperation.equals("Division"))
                   resultDisplay.setText(currentResult.displayPolynomial());

           } else
           {
               switch (selectedOperation) {
                   case "Addition":
                       currentResult = Operations.addition(currentResult, newPolynomial);
                       break;
                   case "Subtraction":
                       currentResult = Operations.substraction(currentResult, newPolynomial);
                       break;
                   case "Multiplication":
                       currentResult = Operations.multiplication(currentResult, newPolynomial);
                       break;
                   case "Derivative":
                       currentResult = Operations.derivative(currentResult);
                       break;
                   case "Integration":
                       currentResult = Operations.integration(currentResult);
                       break;
                   default:
               }
           }
           if (!selectedOperation.equals("Division"))
                resultDisplay.setText(currentResult.displayPolynomial());
       } catch (NumberFormatException e) {
           resultDisplay.setText("Invalid input: Please ensure numbers are correctly formatted.");
       } catch (IllegalArgumentException e) {
           resultDisplay.setText("Invalid polynomial input. Please ensure the polynomial is in the correct format.\nExample of a valid format: 3x^2 - 2x + 7. If the format is valid, then you are trying to divide a polynomial by 0 or by a larger degree polynomial.");
       } catch (Exception e) {
           resultDisplay.setText("Unexpected error occurred: " + e.getMessage());
       }
       polynomialInput1.setText(""); // Clear the input field after processing
   }





}
