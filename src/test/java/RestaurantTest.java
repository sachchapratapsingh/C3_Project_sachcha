import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    public void restaurantExample(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurantExample();
        Restaurant restaurantTest= Mockito.spy(restaurant);
        Mockito.when(restaurantTest.getCurrentTime()).thenReturn(LocalTime.parse("10:40:00"));
        assertTrue(restaurantTest.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurantExample();
        Restaurant restaurantTest= Mockito.spy(restaurant);
        Mockito.when(restaurantTest.getCurrentTime()).thenReturn(LocalTime.parse("10:20:00"));
        assertFalse(restaurantTest.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurantExample();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurantExample();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurantExample();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void order_value_should_return_0_when_no_item_is_selected(){
        restaurantExample();
        List<String> itemList=new ArrayList<String>();
        int totalValue=restaurant.orderValue(itemList);
        assertEquals(0,totalValue);
    }

    @Test
    public void order_value_should_return_388_when_Sweet_corn_soup_and_Vegetable_lasagne_are_selected(){
        restaurantExample();
        List<String> itemList=new ArrayList<String>();
        itemList.add("Sweet corn soup");
        itemList.add("Vegetable lasagne");
        int totalValue=restaurant.orderValue(itemList);
        assertEquals(388,totalValue);
    }

    @Test
    public void get_price_should_return_price_of_item_selected(){
        restaurantExample();
        List<Item> menu = restaurant.getMenu();
        for(Item item: menu){
            if(item.getName().equals("Sweet corn soup"))
                assertEquals(119,item.getPrice());
        }
    }
}