package com.movie.onlinestore;

import com.movie.onlinestore.model.PricingCategory;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class PricingCategoryTest {

    @Test
    public void testGetInitialCostStringShouldReturnStringWithInitialCostAndCutDays(){
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3D,5,1D);
        String expectedInitialCostString = "$3 for 5 days";
        assertEquals(expectedInitialCostString,pricingCategory.getInitialCostString());
    }

    @Test
    public void testGetInitialCostStringWithInitalCostHasDecimalValues(){
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3.25D,5,1D);
        String expectedInitialCostString = "$3.25 for 5 days";
        assertEquals(expectedInitialCostString,pricingCategory.getInitialCostString());
    }

    @Test
    public void testGetInitialCostStringWithInitalCostHasOneDecimalValues(){
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3.5D,5,1D);
        String expectedInitialCostString = "$3.5 for 5 days";
        assertEquals(expectedInitialCostString,pricingCategory.getInitialCostString());
    }

    @Test
    public void testGetAdditionalCostStringShouldReturnStringWithAdditionalCost(){
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3D,5,1D);
        String expectedAditionalCostString = "and $1 afterwards";
        assertEquals(expectedAditionalCostString,pricingCategory.getAdditionalCostString());
    }

    @Test
    public void testGetAdditionalCostStringWithAdditionalCostHasOneDecimalValues(){
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3D,5,2.5D);
        String expectedAditionalCostString = "and $2.5 afterwards";
        assertEquals(expectedAditionalCostString,pricingCategory.getAdditionalCostString());
    }

    @Test
    public void testGetAdditionalCostStringWithAdditionalCostHasTwoDecimalValues(){
        PricingCategory pricingCategory = new PricingCategory(1L,"Default",3D,5,1.75D);
        String expectedAditionalCostString = "and $1.75 afterwards";
        assertEquals(expectedAditionalCostString,pricingCategory.getAdditionalCostString());
    }
}
