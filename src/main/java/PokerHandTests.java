import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerHandTests {
    @Test
    public void getHandTest(){
        String str = "2S 3S 4S 5S 6S";
        PokerHand pokerHand = new PokerHand(str);
        Assert.assertEquals(str, pokerHand.getHand());
    }
    @Test
    public void flashRoyalTest(){
        String str = "TS JS QS KS AS";
        PokerHand pokerHand = new PokerHand(str);
        Assert.assertEquals(10, pokerHand.getPower());
        Assert.assertEquals(14, pokerHand.getComboPower());
        Assert.assertEquals(0, pokerHand.getKicker());
    }
    @Test
    public void streetFlashTest(){
        String str = "2S 3S 4S 5S 6S";
        PokerHand pokerHand = new PokerHand(str);
        Assert.assertEquals(9, pokerHand.getPower());
        Assert.assertEquals(6, pokerHand.getComboPower());
        Assert.assertEquals(0, pokerHand.getKicker());
    }
    @Test
    public void careTest(){
        String str = "5S 5C 5D 5H 6S";
        PokerHand pokerHand = new PokerHand(str);
        Assert.assertEquals(8, pokerHand.getPower());
        Assert.assertEquals(5, pokerHand.getComboPower());
        Assert.assertEquals(6, pokerHand.getKicker());
    }
    @Test
    public void FullHouseTest(){
        String str = "2S 2C 4D 4S 4C";
        PokerHand pokerHand1 = new PokerHand(str);
        PokerHand pokerHand2 = new PokerHand("4D 4S 4C 2S 2C");
        Assert.assertEquals(7, pokerHand1.getPower());
        Assert.assertEquals(4, pokerHand1.getComboPower());
        Assert.assertEquals(0, pokerHand1.getKicker());

        Assert.assertEquals(7, pokerHand2.getPower());
        Assert.assertEquals(4, pokerHand2.getComboPower());
        Assert.assertEquals(0, pokerHand2.getKicker());
    }
    @Test
    public void tripsTest(){
        String str = "2S 3C 4D 4S 4C";
        PokerHand pokerHand1 = new PokerHand(str);
        PokerHand pokerHand2 = new PokerHand("4D 4S 4C 3S 2C");
        Assert.assertEquals(4, pokerHand1.getPower());
        Assert.assertEquals(4, pokerHand1.getComboPower());
        Assert.assertEquals(3, pokerHand1.getKicker());

        Assert.assertEquals(4, pokerHand2.getPower());
        Assert.assertEquals(4, pokerHand2.getComboPower());
        Assert.assertEquals(3, pokerHand2.getKicker());
    }
    @Test
    public void twoPairsTest(){
        String str = "2S 2C 4D 5S 4C";
        PokerHand pokerHand1 = new PokerHand(str);
        PokerHand pokerHand2 = new PokerHand("4D 5S 4C 3S 3C");
        Assert.assertEquals(3, pokerHand1.getPower());
        Assert.assertEquals(4, pokerHand1.getComboPower());
        Assert.assertEquals(5, pokerHand1.getKicker());

        Assert.assertEquals(3, pokerHand2.getPower());
        Assert.assertEquals(4, pokerHand2.getComboPower());
        Assert.assertEquals(5, pokerHand2.getKicker());
    }
    @Test
    public void pairTest(){
        String str = "2S 2C 4D 5S 6C";
        PokerHand pokerHand1 = new PokerHand(str);
        PokerHand pokerHand2 = new PokerHand("4D 5S 8C 3S 3C");
        Assert.assertEquals(2, pokerHand1.getPower());
        Assert.assertEquals(2, pokerHand1.getComboPower());
        Assert.assertEquals(6, pokerHand1.getKicker());

        Assert.assertEquals(2, pokerHand2.getPower());
        Assert.assertEquals(3, pokerHand2.getComboPower());
        Assert.assertEquals(8, pokerHand2.getKicker());
    }
    @Test
    public void elderCardTest(){
        String str = "2S 7C 4D 5S 6C";
        PokerHand pokerHand1 = new PokerHand(str);
        PokerHand pokerHand2 = new PokerHand("4D 5S 8C AS 3C");
        Assert.assertEquals(1, pokerHand1.getPower());
        Assert.assertEquals(7, pokerHand1.getComboPower());
        Assert.assertEquals(6, pokerHand1.getKicker());

        Assert.assertEquals(1, pokerHand2.getPower());
        Assert.assertEquals(14, pokerHand2.getComboPower());
        Assert.assertEquals(8, pokerHand2.getKicker());
    }
    @Test
    public void compareTest(){
        PokerHand pokerHand1 = new PokerHand("AS KS QS JS TS");
        PokerHand pokerHand2 = new PokerHand("KS QS JS TS 9S");
        PokerHand pokerHand3 = new PokerHand("2S 2C 4D 4S 6C");
        List<PokerHand> list = new ArrayList<>();
        list.add(pokerHand3);
        list.add(pokerHand2);
        list.add(pokerHand1);
        list.add(new PokerHand("QS JS TS 9S 8S"));
        list.add(new PokerHand("2S 2C 5D 5S 3C"));
        Collections.sort(list);
        Assert.assertEquals(pokerHand1, list.get(0));
        Assert.assertEquals(pokerHand2, list.get(1));
        Assert.assertEquals(pokerHand3, list.get(4));
    }
    @Test
    public void setHandAndCompareTest(){
        PokerHand pokerHand1 = new PokerHand("2S 2C 4D 4S 6C");
        PokerHand pokerHand2 = new PokerHand("KS QS JS TS 9S");
        List<PokerHand> list = new ArrayList<>();
        list.add(pokerHand2);
        list.add(pokerHand1);
        Collections.sort(list);
        Assert.assertEquals(pokerHand1, list.get(1));
        Assert.assertEquals(pokerHand2, list.get(0));
        pokerHand2.setHand("AS KS QS JS TS");
        list.add(pokerHand2);
        list.sort(null);
        Assert.assertEquals(pokerHand1, list.get(2));
        Assert.assertEquals(pokerHand2, list.get(0));
    }
}
