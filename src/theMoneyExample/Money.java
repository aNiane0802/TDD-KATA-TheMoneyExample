package theMoneyExample;


public class Money implements Expression {
    protected final int amount  ;
    protected final String currency;

    public Money(int amount,String currency) {
        this.amount = amount ;
        this.currency = currency ;
    }

    public static Money dollar(int amount) {
        return new Money(amount,"USD") ;
    }

    public static Money franc(int amount) {
        return new Money(amount,"CHF") ;
    }

    public boolean equals(Object obj) {
        Money money = (Money)obj ;
        return amount == money.amount && currency.equals(money.currency) ;
    }

    @Override
    public String toString() {
        return amount+" "+ currency ;
    }

    public String currency(){
        return currency ;
    }

    public Expression times(int multiplier) {
        return new Money(amount*multiplier,currency);
    }

    public Expression plus(Expression money) {
        return new Sum(this,money) ;
    }

    @Override
    public Money reduce(Bank bank,String to) {
        int rate = bank.rate(currency,to) ;
        return new Money(amount/rate,to) ;
    }
}
