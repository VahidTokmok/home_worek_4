import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;  //0    1    2    3    4   5    6     7
    public static int[] heroesHealth = {300, 270, 250, 200, 500, 180, 260, 280};
    public static int[] heroesDamage = {10,  15,  20,  0,   5,   12,  18,  25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Hiller", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;           //0          1           2         3         4       5         6          7
    public static int medikHill = 30;
    public static boolean dodgeChance;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }
    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
    }
    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }
    public static void bossHits() {
        Random random = new Random();
        damageAbsorption();
        dodgeChance = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                    if (dodgeChance = true){
                        heroesHealth[5] = heroesHealth[5] + bossDamage;
                        System.out.println("Lucky - избежал удара");
                        break;
                    }
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        hilling();
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = coefficient * heroesDamage[i];
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }
    public static void damageAbsorption() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[7] > 0 && heroesHealth[i] > 0 && heroesHealth[7] != heroesHealth[i]) {
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[7] -= bossDamage / 5;

            }
        }
    }
    public static void hilling(){
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 ) {
                heroesHealth[i] += 20;
            }
            System.out.println(" Medic healed " + heroesAttackType[i]);
            break;
        }
    }
    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
    public static void printStatistics() {
        System.out.println("########## " + "ROUND " + roundNumber + " ##########");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence != null ? bossDefence : "No defence"));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
