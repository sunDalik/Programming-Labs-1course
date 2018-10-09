public class Main {
    public static void main (String[] args){
        Sentient snufkin = new Sentient(Characters.Snufkin);
        snufkin.go(true);
        Sea sea = new Sea(50);
        sea.create_waves(9,5,"бухта");
        try {
            Nature.Describer.describe_season("Здесь");
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Вулкан");
        Volcano volcanion = new Volcano(6);
        volcanion.produce(new String[]{"облака пепла", "чудесные темно-багровые закаты"});
        snufkin.lookAtSky();
        snufkin.think();
        new Sentient(Characters.MoominTroll);
        snufkin.think();
        new Sentient(Characters.ParkKeeper);
        snufkin.think();
        Nature.when_summer_solstice();
    }
}

