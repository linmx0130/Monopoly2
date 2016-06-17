package monopoly.cell;

import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2016/4/12.
 */
public class CellFactory {
    public AbstractCell buildCellFromScanner(int id, String cellType, Scanner reader){
        switch (cellType){
            case "BankCell":
                return new BankCell(id);
            case "EmptyCell":
                return new EmptyCell(id);
            case "PropertyCell":
                {
                    String name = reader.next();
                    int streetNumber = reader.nextInt();
                    double basePrice = reader.nextDouble();
                    return new PropertyCell(id,name,name,basePrice,streetNumber);
                }
            case "CardCell":
                return new CardCell(id);
            case "NewsCell":
                return new NewsCell(id);
            case "CouponCell":
                return new CouponCell(id);
            case "CardShopCell":
                return new CardShopCell(id);
            case "LotteryCell":
                return new LotteryCell(id);
            case "HospitalCell":
                return new HospitalCell(id);
            default:

        }
        return null;
    }
}
