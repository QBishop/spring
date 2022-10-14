package com.example.firstproject.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {
    //셰프는 식재료 공장을 알고있음
    private IngredientFactory ingredientFactory;

    //셰프가 식재료 공장과 협업하기 위한 DI
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu) {
        //재료 준비  매번 안에 객체를 바꿔주고 beef도 pork로 매번 바꿔야하는 일이 발생한다. 그래서 DI를 이용한다. 재료공장을 만들어서 재료를 준다.
//        Pork pork = new Pork("한돈 등심");
//        Beef beef = new Beef("한우 꽃등심");
        Ingredient ingredient= ingredientFactory.get(menu);
        //요리 반환
        return ingredient.getName() +"으로 만든 " +menu;

    }
}
