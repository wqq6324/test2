import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class W2FC {
    abstract class Drinks{
        protected String Name;
        protected double Cost;//成本
        protected LocalDate PD;//生产日期   produce date
        protected int QGP;//保质期   quality guarantee period
        public Drinks(String Name, double Cost, int Year, int Month, int Day, int Days){
            this.Name = Name;
            this.Cost = Cost;
            this.PD = LocalDate.of(Year, Month, Day);
            this.QGP = Days;
        }
        public boolean overdue(){
            LocalDate Now = LocalDate.now();
            long betweentime = ChronoUnit.DAYS.between(PD, Now);
            if(betweentime > QGP){
                return true;
            }else{
                return false;
            }
        }//true为已过期，false为未过期
        public abstract String toString();
    }
    class Beer extends Drinks{
        protected float Degree;
        public Beer(String Name, double Cost, int Year, int Month, int Day, int Dregrees){
            super(Name, Cost, Year, Month, Day, 30);//啤酒保质期为30天
            this.Degree = Dregrees;
            super.QGP = 30;
        }
        public String toString(){
            String message = "名字: "+this.Name+"成本:"+this.Cost+"生产日期:"+this.PD+"保质期:"+this.QGP;
            return message;
        }

    }
    class Juice extends Drinks{
        public Juice(String Name, double Cost, int Year, int Month, int Day){
            super(Name, Cost, Year, Month, Day, 2);
        }
        public String toString(){
            String message = "名字: "+this.Name+"成本:"+this.Cost+"生产日期:"+this.PD+"保质期:"+this.QGP;
            return message;
        }
    }
    class SetMeal{
        public String MealName;
        public double Price;
        public String NameOfChicken;
        public Drinks Drink;
        public SetMeal(String mealName, double price, String nameOfChicken, Drinks drink) {
            this.MealName = mealName;
            this.Price = price;
            this.NameOfChicken = nameOfChicken;
            this.Drink = drink;
        }
        public String toString(){
            String message = "套餐名:"+MealName+"套餐价格:"+Price+"炸鸡名:"+NameOfChicken+"饮料信息:"+this.Drink.toString();
            return message;
        }
    }
    //抽象接口:
    public interface FriedChickenRestaurant{
        public void Sold();//出售
        public void Stock();//进货
    }

    class West2FriedChickenRestaurant implements FriedChickenRestaurant{
        private double Balance;
        public Map <Beer, Integer> Beers;
        public Map <Juice, Integer> Juices;
        public Map<SetMeal, Integer> SetMeals;
        public West2FriedChickenRestaurant(){}
        public West2FriedChickenRestaurant(double balance, Map<Beer, Integer> beers, Map<Juice, Integer> juices, Map<SetMeal, Integer> setmeals){
            this.Balance = balance;
            this.Beers = beers;
            this.Juices = juices;
            this.SetMeals = setmeals;
        }
        public void Sold(){}
        public void Stock(){}
        public void Sold(SetMeal S/*出售的套餐*/) throws RuntimeException{
            if(S.Drink.QGP == 30/*为啤酒*/){
                if(Beers.get(S.Drink)>0){
                    /*啤酒数量--
                        怎么对啤酒的map里的的数量进行操作还不会，尝试了几次都是编译错误
                    * */
                }else if(Beers.get(S.Drink) == 0){
                    throw new IngredientSortOutException("啤酒售完");
                }
            }else if (S.Drink.QGP == 2){
                if(Juices.get(S.Drink)>0){
                    /*果汁数量--
                    * */
                }else if(Juices.get(S.Drink) == 0){
                    throw new IngredientSortOutException("果汁售完");
                }
            }


        }
        public void Stock(Drinks drinks, int num){
            double total = num * drinks.Cost;
            if(this.Balance < total){
                throw new OverdraftBalanceException("进货余额不足，差"+(total-this.Balance)+"元");
            }else{
                this.Balance -= total;
                /*
                Drinks对应的啤酒还是果汁数量++
                * */
            }
        }
    }



    public class BaseException extends RuntimeException {
        public BaseException(){}
        public BaseException(String message){
            super(message);
        }
    }
    //售完
    public class IngredientSortOutException extends BaseException{
        public IngredientSortOutException(){}
        public IngredientSortOutException(String message){
            super(message);
        }
    }
    //进货余额不足
    public class OverdraftBalanceException extends RuntimeException{
        public OverdraftBalanceException(){}
        public OverdraftBalanceException(String message){
            super(message);
        }
    }
    //看群里main方法可以不交？
    public static void main(String[] args) {

    }
}
