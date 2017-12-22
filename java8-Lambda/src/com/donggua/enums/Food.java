package com.donggua.enums;

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