package theMoneyExample;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MultiCurrency {

    private Expression fiveBucks;
    private Expression tenFrancs;
    private Bank bank;

    @Before
    public void setUp(){
        fiveBucks = Money.dollar(5);
        tenFrancs = Money.franc(10) ;
        bank = new Bank();
        bank.addRate("CHF","USD",2);
    }

    @Test
    public void testMultiplication(){
        Money five = Money.dollar(5) ;
        Assert.assertEquals(Money.dollar(10),five.times(2));
        Assert.assertEquals(Money.dollar(15),five.times(3));

    }

    @Test
    public void testEquality(){
        Assert.assertEquals(Money.dollar(5), Money.dollar(5));
        Assert.assertNotEquals(Money.dollar(5), Money.dollar(6));
        Assert.assertNotEquals(Money.franc(5), Money.dollar(5));
    }

    @Test
    public void testCurrency(){
        Assert.assertEquals("USD",Money.dollar(5).currency());
        Assert.assertEquals("CHF",Money.franc(5).currency());
    }

    @Test
    public void testSimpleAddition(){
        Money five = Money.dollar(5) ;
        Expression sum = five.plus(five) ;
        Bank bank = new Bank() ;
        Expression reduced = bank.reduce(sum,"USD") ;
        Assert.assertEquals(Money.dollar(10),reduced);
    }

    @Test
    public void testPlusReturnsSum(){
        Money five = Money.dollar(5) ;
        Sum sum = (Sum) five.plus(five);
        Assert.assertEquals(five,sum.augend);
        Assert.assertEquals(five,sum.addend);
    }

    @Test
    public void testReduceSum(){
        Expression sum = new Sum(Money.dollar(4),Money.dollar(3)) ;
        Bank bank = new Bank();
        Assert.assertEquals(Money.dollar(7),bank.reduce(sum,"USD"));
    }

    @Test
    public void testReduceMoney(){
        Bank bank = new Bank() ;
        Money reduced = bank.reduce(Money.dollar(1), "USD");
        Assert.assertEquals(Money.dollar(1), reduced);
    }

    @Test
    public void testReduceMoneyDifferentCurrency(){
        Bank bank = new Bank() ;
        bank.addRate("CHF","USD",2);
        Money reduced = bank.reduce(Money.franc(2),"USD");
        Assert.assertEquals(Money.dollar(1),reduced);
    }

    @Test
    public void testRate(){
        Bank bank = new Bank() ;
        bank.addRate("CHF","USD",2);
        int francToDollar = bank.rate("CHF","USD") ;
        Assert.assertEquals(2,francToDollar);
    }

    @Test
    public void testIdentifyRate(){
        Assert.assertEquals(1,new Bank().rate("USD","USD"));
    }

    @Test
    public void testMixedAdditions(){
        Money result = bank.reduce(fiveBucks.plus(tenFrancs),"USD") ;
        Assert.assertEquals(Money.dollar(10),result);
    }

    @Test
    public void testSumPlusMoney(){
        Money result = bank.reduce(new Sum(fiveBucks,tenFrancs).plus(fiveBucks),"USD") ;
        Assert.assertEquals(Money.dollar(15),result);
    }

    @Test
    public void testSumTimes(){
        Money result = bank.reduce(new Sum(fiveBucks,tenFrancs).times(2),"USD") ;
        Assert.assertEquals(Money.dollar(20),result);
    }


}
