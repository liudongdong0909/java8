package com.donggua.enums;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-12-15 下午 03:22
 */
public enum Meal {

    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;

    Meal(Class<? extends Food> kind) {
        this.values = kind.getEnumConstants();
    }

    public interface Food {

        enum Appetizer implements Food {
            SALD, SOUP, SPRING_ROLLS;
        }

        enum MainCourse implements Food {
            LASAGNE, BURRITO, PAD_THAT,
            LENTILS, HUMMOUS, VINDALOO;
        }

        enum Dessert implements Food {
            TIRAMISU, GELATO, FRUIT;
        }

        enum Coffee implements Food {
            BLACK_COFFEE, TEA, HERB_TEA;
        }
    }
}
