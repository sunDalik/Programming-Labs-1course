public abstract class Nature {
    static String season = "лето";
    static int month = 6;
    static int day = 21;
    private static CloudsCondition clouds = CloudsCondition.White;
    private static SunCondition sun = SunCondition.Dawn;
    abstract void goWild();
    abstract void calmDown();


    class NotASky extends Exception{
        NotASky(String msg){
            super(msg);
        }
    }

    static class Describer{
        static String describe_sky(){
            return clouds.getName() + " и " + sun.getName();
        }

        static void describe_season (String location) {
            class NotASeason extends RuntimeException{
                private NotASeason(String msg){
                    super(msg);
                }
            }

            if (season.equals("лето")) {
                System.out.println(location + " во всей своей красе царило " + season + ".");
            } else if (season.equals("осень") || season.equals("зима") || season.equals("весна")) {
                System.out.println(location + " во всей своей красе царила " + season + ".");
            }
            else{
                throw new NotASeason("Времени года " + season + " не существует");
            }
        }
    }

    void changeSky(String cloud, String sunn){
        try {
            switch (cloud) {
                case ("белые облака"):
                    clouds = CloudsCondition.White;
                    break;
                case ("облака пепла"):
                    clouds = CloudsCondition.Ashy;
                    break;
                default:
                    throw new NotASky("Типа облаков " + cloud + " не существует.");
            }
        } catch (NotASky e){
            System.out.println(e.getMessage());
        }
        try {
            switch (sunn) {
                case ("красивые рассветы"):
                    sun = SunCondition.Dawn;
                    break;
                case ("чудесные темно-багровые закаты"):
                    sun = SunCondition.Sunset;
                    break;
                default:
                    throw new NotASky("Состояния солнца " + sunn + " не существует.");
            }
        }catch(NotASky e){
            System.out.println(e.getMessage());
        }
    }

    static void when_summer_solstice(){
        if(day == 21 && month == 6){
            System.out.println("Завтра все уже будет в порядке.");
        }
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Сезон: " + season + ". Месяц: " + month + ". День: " + day + ".";
    }
}
