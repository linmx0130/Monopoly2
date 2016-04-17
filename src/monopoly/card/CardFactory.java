package monopoly.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Mengxiao Lin on 2016/4/13.
 */
public class CardFactory {
    private HashMap<String, Class> cardTypeMap;
    private ArrayList<String> cardTypeList;
    private ArrayList<Double> cardRate;
    public CardFactory(){
        cardTypeList= new ArrayList<>();
        cardTypeMap= new HashMap<>();
        cardRate = new ArrayList<>();
        addCard("TurnOrientationCard", TurnOrientationCard.class, 0.01);
        addCard("LandCard", LandCard.class, 0.01);
        addCard("RoadBlockCard", RoadBlockCard.class, 100);
    }
    public void addCard(String typeName, Class cardType, double rate){
        cardTypeList.add(typeName);
        cardTypeMap.put(typeName, cardType);
        cardRate.add(rate);
    }
    public AbstractCard generateRandomCard(){
        double sum = cardRate.stream().reduce(0.0, (a,b)-> a+b);
        double pRate = new Random().nextDouble();
        pRate *= sum;
        double nowRate = 0;
        String typeName="";
        for (int i=0;i<cardTypeList.size() ;++i){
            typeName = cardTypeList.get(i);
            nowRate+=cardRate.get(i);
            if (nowRate>=pRate) break;
        }
        try {
            return (AbstractCard)cardTypeMap.get(typeName).newInstance();
        } catch (Exception e){
            throw new RuntimeException("Error while creating card: "+typeName);
        }
    }
    public AbstractCard generateCard(String typename){
        try {
            return (AbstractCard)cardTypeMap.get(typename).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating card: "+typename);
        }
    }
    public HashMap<String, Class> getCardTypeMap() {
        return cardTypeMap;
    }

    public ArrayList<String> getCardTypeList() {
        return cardTypeList;
    }
}
