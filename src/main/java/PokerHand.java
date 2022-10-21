import java.util.Arrays;

/**
 * первый символ — это номинал карты. Допустимые значения: 2, 3, 4, 5, 6, 7, 8, 9, T(en),
 * J(ack), Q(ueen), K(ing), A(ce);
 * второй символ — масть. Допустимые значения: S(pades) пики, H(earts) червы, D(iamonds) буби, C(lubs) крести.
 * */
public class PokerHand implements Comparable<PokerHand>{
    private String hand;
    private Integer power;
    private int kicker;
    private int comboPower;

    public PokerHand(String hand) {
        this.hand = hand;
        this.comboPower = 0;
        this.kicker = 0;
        range();
    }
    private void range(){
        String[] cards = hand.split(" ");
        Arrays.sort(cards);
        if (flashRoyal(cards))
            power = 10;
        else if (streetFlash(cards))
            power = 9;
        else if (care(cards))
            power = 8;
        else if (fullHouse(cards))
            power = 7;
        else if (flash(cards))
            power = 6;
        else if (street(cards))
            power = 5;
        else if (trips(cards))
            power = 4;
        else if (twoPairs(cards))
            power = 3;
        else if (pair(cards))
            power = 2;
        else {
            power = 1;
            elderCard(cards);
        }
    }

    private boolean flashRoyal(String[] cards){
        if(cards[0].startsWith("A") && street(cards)){
            return flash(cards);
        }
        return false;
    }
    private boolean streetFlash(String[] cards){
        if(flash(cards))
            if(street(cards)) {
                this.comboPower = Arrays.stream(cardsToPowers(cards)).max().getAsInt();
                return true;
            }
        return false;
    }
    private boolean care(String[] cards){
        int k;
        int kicker;
        int pow;
        if(cards[0].startsWith(cards[1].substring(0, 1))) {
            k = 3;
            kicker = getPowerOfCard(cards[4]);
            pow = getPowerOfCard(cards[0]);
        }else{
            k = 4;
            kicker = getPowerOfCard(cards[0]);
            pow = getPowerOfCard(cards[1]);
        }
        for (int i = 1; i < k; i++) {
            if (!cards[i].startsWith(cards[i + 1].substring(0, 1)))
                return false;
        }
        this.comboPower = pow;
        this.kicker = kicker;
        return true;
    }
    private boolean fullHouse(String[] cards){
        String num3 = cards[2].substring(0, 1);
        if(cards[0].startsWith(num3) && cards[1].startsWith(num3)) {
            if(cards[3].startsWith(cards[4].substring(0, 1))){
                this.comboPower = getPowerOfCard(cards[2]);
                return true;
            }
        }
        else if(cards[0].startsWith(cards[1].substring(0, 1))){
            if(cards[3].startsWith(num3) && cards[4].startsWith(num3)){
                this.comboPower = getPowerOfCard(cards[2]);
                return true;
            }
        }
        return false;
    }
    private boolean flash(String[] cards){
        String m = cards[0].substring(1);
        if(cards[1].endsWith(m) &&
                cards[2].endsWith(m) &&
                cards[3].endsWith(m) &&
                cards[4].endsWith(m)) {
            int[] arr = cardsToPowers(cards);
            Arrays.sort(arr);
            this.comboPower = arr[4];
            return true;
        }
        return false;
    }
    private boolean street(String[] cards){
        int[] arr = cardsToPowers(cards);
        if(arr[0] == arr[1] - 1 && arr[1] == arr[2] - 1 && arr[2] == arr[3] - 1 && arr[3] == arr[4] - 1) {
            this.comboPower = arr[4];
            return true;
        }
        return false;
    }
    private boolean trips(String[] cards){
        String num3 = cards[2].substring(0, 1);
        if(cards[0].startsWith(num3) && cards[1].startsWith(num3)) {
            this.kicker = Math.max(getPowerOfCard(cards[3]), getPowerOfCard(cards[4]));
            this.comboPower = getPowerOfCard(cards[2]);
            return true;
        }
        else if(cards[1].startsWith(num3) && cards[3].startsWith(num3)) {
            this.kicker = Math.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[4]));
            this.comboPower = getPowerOfCard(cards[2]);
            return true;
        }
        else if(cards[3].startsWith(num3) && cards[4].startsWith(num3)){
            this.kicker = Math.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[1]));
            this.comboPower = getPowerOfCard(cards[2]);
            return true;
        }
        return false;
    }
    private boolean twoPairs(String[] cards){
        String num2 = cards[1].substring(0, 1);
        String num4 = cards[3].substring(0, 1);

        if(cards[0].startsWith(num2)){
            if (cards[2].startsWith(num4)) {
                this.kicker = getPowerOfCard(cards[4]);
                this.comboPower = Integer.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[2]));
                return true;
            }
            else if (cards[4].startsWith(num4)){
                this.kicker = getPowerOfCard(cards[2]);
                this.comboPower = Integer.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[4]));
                return true;
            }
        } else if(cards[2].startsWith(num2)){
            if(cards[4].startsWith(num4)){
                this.kicker = getPowerOfCard(cards[2]);
                this.comboPower = Integer.max(getPowerOfCard(cards[2]), getPowerOfCard(cards[4]));
                return true;
            }
        }
        return false;
    }
    private boolean pair(String[] cards){
        String num2 = cards[1].substring(0, 1);
        String num4 = cards[3].substring(0, 1);
        int kicker;
        if(cards[0].startsWith(num2)) {
            kicker = Math.max(getPowerOfCard(cards[2]), getPowerOfCard(cards[3]));
            this.kicker = Math.max(kicker, getPowerOfCard(cards[4]));
            this.comboPower = getPowerOfCard(cards[0]);
            return true;
        }
        if (cards[2].startsWith(num2)) {
            kicker = Math.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[3]));
            this.kicker = Math.max(kicker, getPowerOfCard(cards[4]));
            this.comboPower = getPowerOfCard(cards[2]);
            return true;
        }
        if (cards[2].startsWith(num4)) {
            this.comboPower = getPowerOfCard(cards[2]);
            kicker = Math.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[1]));
            this.kicker = Math.max(kicker, getPowerOfCard(cards[4]));
            return true;
        }
        if (cards[4].startsWith(num4)){
            kicker = Math.max(getPowerOfCard(cards[0]), getPowerOfCard(cards[1]));
            this.kicker = Math.max(kicker, getPowerOfCard(cards[2]));
            this.comboPower = getPowerOfCard(cards[4]);
            return true;
        }
        return false;
    }
    private void elderCard(String[] cards){
        int[] arr = cardsToPowers(cards);
        Arrays.sort(arr);
        this.kicker = arr[3];
        this.comboPower = arr[4];
    }

    private int[] cardsToPowers(String[] cards){
        return Arrays.stream(cards).map(s -> s.substring(0, 1)
                        .replace("T", "10")
                        .replace("J", "11")
                        .replace("Q", "12")
                        .replace("K", "13")
                        .replace("A", "14")
                )
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
    }

    private int getPowerOfCard(String card){
        return Integer.parseInt(card.substring(0, 1)
                .replace("T", "10")
                .replace("J", "11")
                .replace("Q", "12")
                .replace("K", "13")
                .replace("A", "14"));
    }

    @Override
    public int compareTo(PokerHand o) {
        if(o.getPower() == getPower() && o.getComboPower() == getComboPower() && o.getKicker() == getKicker())
            return 0;
        if(o.getPower() > getPower())
            return 1;
        else if(o.getPower() == getPower()) {
            if (o.getComboPower() > getComboPower())
                return 1;
            else if (o.getComboPower() == getComboPower()) {
                if (o.getKicker() > getKicker())
                    return 1;
                else
                    return -1;
            }
        }
        return -1;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
        range();
    }

    public int getPower() {
        return power;
    }

    public int getKicker() {
        return kicker;
    }

    public int getComboPower() {
        return comboPower;
    }

    @Override
    public String toString() {
        return "PokerHand{" +
                "hand='" + getHand() + '\'' +
                ", power=" + getPower() +
                ", kicker=" + getKicker() +
                ", comboPower=" + getComboPower() +
                "}\n";
    }
}
