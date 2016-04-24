package monopoly;

/**
 * Created by Mengxiao Lin on 2016/4/24.
 */
public class Date {
    private int year, month, day;
    private static final int[] MONTH_DAY_COUNT={0, 31, 28, 31,30,31,30,31,31,30,31,30,31};
    private static int getDayCountOfMonth(int year, int month){
        if (month !=2) return MONTH_DAY_COUNT[month];
        boolean isLeap = ((year % 4 ==0) &&(year%100 !=0)) || (year % 400 ==0);
        if (isLeap && month ==2){
            return 29;
        }else return 28;
    }
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        if (month <1 || month >12) throw new RuntimeException("WRONG DATE");
        if (day <1 || day > getDayCountOfMonth(year,month))  throw new RuntimeException("WRONG DATE");
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    public void toNextDay(){
        day ++;
        if (day>getDayCountOfMonth(year,month)){
            month ++;
            day = 1;
        }
        if (month ==13){
            month =1;
            year++;
        }
    }

    @Override
    public String toString() {
        return year +"年"+ month +"月"+ day +"日";
    }
}
