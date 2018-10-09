public class Sea implements Comparable<Sea>{
    private int powerLimit;
    Wave wave;

    Sea(){}
    public Sea(int powLim, int waveN, int wavePower, String waveLocation){
        powerLimit = powLim;
        wave = new Wave(waveN,wavePower,waveLocation);
    }

    public String toCsv(){
        return Integer.toString(powerLimit) + "," + wave.toCsv();
    }

    @Override
    public int compareTo(Sea o){
        return powerLimit - o.powerLimit;
    }
}
