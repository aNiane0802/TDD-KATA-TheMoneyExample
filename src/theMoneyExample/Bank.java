package theMoneyExample;

import java.util.Hashtable;

class Bank {

    private final Hashtable<Pair,Integer> rates = new Hashtable() ;

    public Money reduce(Expression expression,String to) {
        return expression.reduce(this,to) ;
    }

    public void addRate(String from, String to, int rate) {
        Pair pair = new Pair(from,to) ;
        rates.put(pair,rate) ;
    }

    public int rate(String from, String to) {
        if(from.equals(to))
            return 1 ;
        return rates.get(new Pair(from,to)) ;
    }
}
