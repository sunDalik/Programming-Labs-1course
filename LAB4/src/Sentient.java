public class Sentient {
private Characters character;
private int imagination = 0;
private String name;
private String location = "Неизвестно";
private double x;
private static String sender = "";
private static String message = "";

    public Sentient(Characters c){
        this.character = c;
        switch(character){
            case Snufkin:
                name = "Снусмумрик";
                imagination = 30;
                location = "Прибрежные луга"; //Прибрежные луга с x = [15;70] допустим
                x = 30;
                break;
            case MoominTroll:
                name = "Мумми-тролль";
                imagination = 10;
                location = "Муми-Дол";
                x = 400;
                break;
            case ParkKeeper:
                name = "Сторож";
                imagination = 3;
                x = 130;
                break;
        }
        think();
    }

    public Sentient(String name, int imagination, String location){
        this.name = name;
        this.imagination = imagination;
        this.location = location;
        think();
    }

    public void go(boolean forward){
        if (forward) x+=10;
                else x-=10;
        if(forward && x >= 25 && x <= 75  ||  !forward && x >= 15 && x <= 65){
            System.out.println(name + " продолжал идти по прибрежным лугам.");
        }
        else if(forward && x > 75 && x <= 85  ||  !forward && x >= 5 && x < 15){
            System.out.println(name + " ушел с прибрежных лугов.");
        }
        else if(forward && x >= 15 && x < 25  ||  !forward && x > 65 && x <= 75){
            System.out.println(name + " вошел на прибрежные луга.");
        }
        else System.out.println(name + " идет.");
    }

    public void lookAtSky(){
        imagination +=10;
        System.out.println(name + " смотрел на " + Nature.Describer.describe_sky() + " и любовался ими.");
    }

    public void think(){

        MentalChannel moominMentalChannel = new MentalChannel(){
            @Override
            public void receiveThought(){
                //Потратив 15 воображения можно принять мысль, отправленную по ментальному каналу
                if(imagination >= 15) {
                    if (!message.equals("")) {
                        System.out.println(name + " получил мысль: " + message + " от " + sender);
                        //Если у отправителя хватило воображения отправить конкретное сообщение, то мы его принимаем
                    }
                    else if (name.equals("Снусмумрик")) {
                        if (!sender.equals("Сторож") && !sender.equals("Снусмумрик")) {
                            System.out.println("Снусмумрику пришла в голову мысль, что, наверное, " + sender + " заждался его.");
                        }
                        else if(sender.equals("Сторож")) {
                            System.out.println("Снусмумрик вспомнил, что должен свести счеты со сторожем парка, но это можно было уладить лишь в день летнего солнцестояния.");
                        }
                    }
                    else if (name.equals("Мумми-тролль")) {
                        System.out.println("Мумми-троллю показалось, что " + sender + " зовет его.");
                    }
                    else if (name.equals("Сторож")) {
                        System.out.println("А, что? Хм... Показалось.");
                    }
                    //Если отправитель смог лишь отправить напоминание о себе, то мы вспоминаем его
                    imagination -= 15;
                    sender = "";
                    message = "";
                }
            }

            @Override
            public  void sendThought(String sndr){
                if (imagination >= 3) {
                    imagination -= 3;
                    sender = sndr;
                    //Если не хватило воображения отправить сообщение, то можно просто отправить мысль о себе за 3 воображения
                }
            }

            @Override
            public void sendThought(String msg, String sndr){
                if (imagination >= 25){
                    imagination -= 25;
                    sender = sndr;
                    message = msg;
                    // За 25 воображения можно мысленно передать конкретное сообщение
                }
                else sendThought(sndr);
            }
        };


            if (!sender.equals("")){
                moominMentalChannel.receiveThought();
                //Сначала проверяем не отправил ли кто-то что-нибудь в ментальный канал.
            }

            else if (name.equals("Снусмумрик")){
                if(!location.equals("Муми-Дол") && imagination >= 40){
                    System.out.print(name + " не имел ни малейшего представления о том, что случилось с его друзьями в Муми-Доле");
                    imagination -=5;
                    if (Nature.day == 21 && Nature.month == 6){
                        System.out.println(" и полагал, что они все также мирно сидят на веранде и празднуют день летнего солнцестояния.");
                    }
                    else System.out.println(", но полагал, что у них все хорошо.");
                }
            }
            else if (name.equals("Мумми-тролль")){
                moominMentalChannel.sendThought("Снусмумрик, спаси нас!!", name);
            }
            else if (name.equals("Сторож")) {
                moominMentalChannel.sendThought("Хррррр....", name);
            }
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentient sentient = (Sentient) o;

        if (imagination != sentient.imagination) return false;
        if (character != sentient.character) return false;
        if (name != null ? !name.equals(sentient.name) : sentient.name != null) return false;
        return location != null ? location.equals(sentient.location) : sentient.location == null;
    }

    @Override
    public int hashCode() {
        int result = character != null ? character.hashCode() : 0;
        result = 31 * result + imagination;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Имя: " + name + ". Местонахождение: " + location + ". Воображение: " + imagination + ".";
    }
}
