public class InteractManager {
    /*
        Сейчас класс проверяет, являются ли персонаж и предмет в одной локации и
        используется вещь другим персонажем или нет, устал пользователь или нет
        То есть проверяет, может персонаж использовать предмет или нет
        Но в теории сюда можно запихнуть какую-то дополнительную логику, которую
        в сами классы писать не хочется.
     */
    //закрытый
    private InteractManager(){
    }

    public static boolean checkLocations(AliveCreature user, ThingToInteract thing){
        try{
            if(!user.currentLocation.equals(thing.location))
                throw new DifferentLocsException(thing, user);
            return true;
        }catch (DifferentLocsException e){
            System.out.println(e.getMessage());
            System.out.println("Предмет и персонаж в разных локациях:");
            System.out.println(e.getThing().toString());
            System.out.println(e.getUser().getName());
            return false;
        }
    }
    public static boolean userNotTired(AliveCreature user){
        if(user.isTired()){
            System.out.println(user.getName() + " устал");
            return false;
        }else{
            return true;
        }
    }
    public static boolean checkUsingOfThing(AliveCreature user, ThingToInteract thing){
        boolean res = false;
        if(thing.currentUser != null){
            res = thing.currentUser.equals(user);
        }
        if(!res){
            System.out.println(user.getName() + " не может использовать предмет. " +
                    "Кто-то другой уже использует его!");
        }
        return res;
    }

    public static boolean interactionPreparing(AliveCreature user, ThingToInteract thing) {
        System.out.println(user.getName() + " пытается использовать предмет: "
                + thing.getThingName());
        return (checkLocations(user, thing)
                && checkUsingOfThing(user, thing)
                && userNotTired(user));
    }
}