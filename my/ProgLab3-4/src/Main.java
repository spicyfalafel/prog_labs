import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Персонажи
        //1
        Moomin mom = new Moomin("Муми-мама", Gender.FEMALE, null);
        mom.setDescription("Муми-мама — идеальная мама, воплощение доброты, нежности, заботы." +
                " Всегда готова накормить, окружить вниманием и уложить спать.");
        //2
        Moomin snork = new Moomin("Муми-тролль", Gender.MALE, null);
        snork.setDescription("Сказочное существо, похожее на маленького гиппопотама. " +
                "Муми-тролль(Снорк) — общий любимец, подкупающе искренний, отзывчивый, любит родителей и друзей. Он с удовольствием, как и любой другой ребёнок, " +
                "пускается во всевозможные приключения, путешествия, всегда жаждет чего-то нового, невиданного, таинственного и необыкновенного.");

        //3
        Animal sniff = new Animal("Снифф", Gender.MALE, null);
        sniff.setDescription("Снифф — друг Муми-тролля. Маленький длиннохвостый зверёк, наподобие крысёнка." +
                " Является сыном зверька Шнырька и зверюшки Сос. Немного труслив. Склонен к занудству. Очень " +
                "ранимый и милый зверек. Любит всё яркое и блестящее." +
                " Любит существ ещё меньше себя. Любимая фраза: «Это приключение может быть" +
                " опасно для такого маленького зверька, как я».");
        //4
        Hemuli hemuli = new Hemuli("Хемуль", Gender.MALE, null);
        hemuli.setDescription("Типичный хемуль — это лысый здоровяк с огромной, слегка приплюснутой мордой,\n" +
                "одетый в форменный мундир или тётушкино платье с оборками — подобие судейской мантии\n" +
                "или халата учёного. Носит огромные ботинки. Хемули — сквозные персонажи, фигурирующие\n" +
                "в большинстве книг о муми-троллях. Эти существа являются упорядочивающей и организующей\n" +
                "силой в мире, где большинство персонажей легкомысленны и безалаберны. Хемули выполняют\n" +
                "функции контролёров, сторожей, воспитателей, полицейских, играют в духовых оркестрах.\n" +
                "Отсюда и характерный хемульский типаж: солидный, прямолинейный и усердный труженик,\n" +
                "без особой тонкости характера, почти лишённый чувства юмора, вкуса и такта.");

        //5
        Human snusmumriken = new Human("Снусмумрик", Gender.MALE, null);
        snusmumriken.setDescription("Снусмумрик — знаменитый путешественник-одиночка, лучший друг Муми-тролля. " +
                "Туве изображает Снусмумрика в виде человечка с круглым лицом и большими глазами, одетого в зелёный " +
                "дождевик и широкополую зелёную шляпу с пером. Снусмумрик немногословен, невозмутим и во время путешествий почти " +
                "невидим, так как не производит шума и теряется на фоне окружающей местности." +
                "Опытный путешественник, он знает множество секретов бродяжничества, и пережил море опасных " +
                "приключений.");


        // Локации
        //1
        Location crushPoint = new Location("Место крушения кораблей",
                "У берега лежат обломки кораблей");
        ThingToInteract[] b = new ThingToInteract[]{
                new Cloth("Пиратские трусы", "рваные трусы с надписью " +
                        "\"Pirate is free\"", Cloth.ClothType.PANTS),
                new Value("Мешочек золота",
                        "20 монет из чистого золота"),
                new Trash("Ржавый меч", "выглядит бесполезно"),
                new Value("Компас", "старый компас с надписью " +
                        "\"Собственность Капитана Джека Воробья\""),
                new Food("Рыба", "аппетитная рыбка Немо"),
                new Cloth("Тряпка",
                        "бывшая футболка цвета радуги и надписью ITMO",
                        Cloth.ClothType.BODY)};
        ArrayList<ThingToInteract> crushPointStaff =
                new ArrayList<ThingToInteract>(Arrays.asList(b));

        crushPoint.setThingsToInteract(crushPointStaff);

        //2
        Location sandPad = new Location("Песчаная площадка",
                "очень приятное место");
        ThingToInteract[] a = new ThingToInteract[]{
                new Nature("Огромная скала", "Огромная скала." +
                        " Говорят, что она стояла задолго до рождения самых старых муми-троллей."
                ),
                new Nature("Гвоздики", "Голубые морские гвоздики, " +
                        "очень красивые и вкусно пахнут.")
        };
        ArrayList<ThingToInteract> sandPadStaff =
                new ArrayList<ThingToInteract>(Arrays.asList(a));
        sandPad.setThingsToInteract(sandPadStaff);

        //3
        Location peak = new Location("Вершина горы", "С этой горы остров виден от побережья " +
                "до побережья и кажется букетом цветов, плывущим по неспокойному морю.");


        //Начало событий
        mom.goToLocation(crushPoint);
        snork.goToLocation(crushPoint);

        for (ThingToInteract thing :
                crushPoint.getThingsToInteract()) {
            thing.mainInteraction(snork);
        }

        mom.goToLocation(sandPad);
        mom.setCurrentState(AliveCreature.State.TIRED);
        mom.say("Полежу здесь совсем немножко");
        // должна лечь
        mom.interact(sandPad.getThingWithName("Огромная скала"));
        snork.takeOff(Cloth.ClothType.PANTS);
        snork.takeOff(Cloth.ClothType.BODY);
        snork.goToLocation(peak);
        snork.say("смотрю на берег!");
        sniff.goToLocation(crushPoint);
        hemuli.goToLocation(crushPoint);
        snusmumriken.goToLocation(crushPoint);

        // создадим что-нибудь новое
        crushPoint.clearAllThingsInLocation();
        Nature cypripediumCalceolus = new Nature("Венерин башмачок",
                "особая разновидность");
        crushPoint.addThingToInteract(cypripediumCalceolus);
        Cloth kepka = new Cloth("Кепка FBI", "Пахнет морским бризом и горами",
                Cloth.ClothType.HEADDRESS);
        Cloth pidjak = new Cloth("Малиновый пиджак",
                "Пиджак, на нем брошь с рисунком белого медведя", Cloth.ClothType.BODY);
        Trash laboratornaya = new Trash("Лабораторная работа некого Кривоносого", "Выглядит отлично");
        Food shaverma = new Food("Шаверма", "Откуда шаверма на корабле?");
        Value konspekt = new Value("Конспект лекций", "Набирает сообщение...");
        Value metodichka = new Value("Методические указания Полякова", "Кажется, это было на лекциях");
        crushPoint.addThingsToInteract(new ThingToInteract[]{
                kepka,
                pidjak,
                laboratornaya,
                shaverma,
                konspekt,
                metodichka
        });

        sniff.interact(kepka);
        sniff.interact(shaverma);
        cypripediumCalceolus.mainInteraction(hemuli);
        pidjak.mainInteraction(snusmumriken);
        laboratornaya.mainInteraction(hemuli);
        metodichka.mainInteraction(hemuli);

        // показываю, что знаю как делать анонимный класс
        Animal kingKong = new Animal("King-Kong", Gender.MALE, null) {
            @Override
            public AdditionalAnimalInformation getAdditionalInfo() {
                return new AdditionalAnimalInformation("GORILLA", 4, 100, 3000);
            }

            @Override
            public void say(String msg) {
                super.say("RRRRRRRRRRRRRRRRRRRRRRRR");
            }


            @Override
            protected void changeStaminaBy(int i) {
                System.out.println("Энергия данного существа не может измениться...");
            }

            @Override
            public int getMAX_STAMINA() {
                return Integer.MAX_VALUE;
            }
        };
        kingKong.setDescription("King kong king king KONG!!!");
        kingKong.goToLocation(sandPad);
        //рычит вместо слов
        kingKong.say("Энн, я люблю тебя!");

        // просто проверка, работает ли класс AdditionalInfo в Animal
        Nature tree = new Nature("Яблоня", "Яблоки только на высоте 2 метра") {
            @Override
            public void interaction(AliveCreature animal) {
                if (animal instanceof Animal) {
                    if (    ((Animal) animal).getAdditionalInfo().getHeight() >= 200) {
                        animal.saveThing(new Food("Яблоко", ""));
                    }
                }
            }
        };
        sandPad.addThingToInteract(tree);
        //работает
        tree.mainInteraction(kingKong);
        //а он в другой локации, вызывается DifferentLocsException
        tree.mainInteraction(snork);


        /*for (; ; ) {
            try {
                return 1;
            } catch (OutOfMemoryError e) {
                System.out.println("kaput");
            }finally {
                return 0;
            }
        }*/
    }
}