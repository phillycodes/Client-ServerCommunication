package application;
// Implements Seriablizable to support object IO  
public class Loan implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
  private double annualInterestRate;
  private int numberOfYears;
  private double loanAmount;
  private java.util.Date loanDate;

  /** Default constructor */
  public Loan() {
    this(2.5, 1, 1000);
  }

  /** Construct a loan with specified annual interest rate,
      number of years and loan amount
     * @param annualInterestRate
     * @param numberOfYears
     * @param loanAmount
    */
  protected Loan(double annualInterestRate, int numberOfYears,
      double loanAmount) {
    this.annualInterestRate = annualInterestRate;
    this.numberOfYears = numberOfYears;
    this.loanAmount = loanAmount;
    loanDate = new java.util.Date();
  }

  /** Return annualInterestRate
     * @return  */
  public double getAnnualInterestRate() {
    return annualInterestRate;
  }

  /** Set a new annualInterestRate
     * @param annualInterestRate */
  public void setAnnualInterestRate(double annualInterestRate) {
    this.annualInterestRate = annualInterestRate;
  }

  /** Return numberOfYears
     * @return  */
  public int getNumberOfYears() {
    return numberOfYears;
  }

  /** Set a new numberOfYears
     * @param numberOfYears */
  public void setNumberOfYears(int numberOfYears) {
    this.numberOfYears = numberOfYears;
  }

  /** Return loanAmount
     * @return  */
  public double getLoanAmount() {
    return loanAmount;
  }

  /** Set a newloanAmount
     * @param loanAmount */
  public void setLoanAmount(double loanAmount) {
    this.loanAmount = loanAmount;
  }

  /** Find monthly payment
     * @return  */
  public double getMonthlyPayment() {
    double monthlyInterestRate = annualInterestRate / 1200;
    double monthlyPayment = loanAmount * monthlyInterestRate / (1 -
      (Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12)));
    System.out.println("annual: " + annualInterestRate);
    System.out.println("monthly: " + monthlyInterestRate);
    System.out.println("loan: " + loanAmount);
    System.out.println("monthly payment: " + monthlyPayment);
    return monthlyPayment;    
  }

  /** Find total payment
     * @return  */
  public double getTotalPayment() {
    double totalPayment = getMonthlyPayment() * numberOfYears * 12;
    System.out.println("years: " + numberOfYears);
    return totalPayment;    
  }

  /** Return loan date
     * @return  */
  public java.util.Date getLoanDate() {
    return loanDate;
  }
}
