package com.movie.onlinestore;

import com.movie.onlinestore.model.Movie;
import com.movie.onlinestore.model.MovieInventory;
import com.movie.onlinestore.model.PricingCategory;
import com.movie.onlinestore.repository.MovieInventoryRepository;
import com.movie.onlinestore.repository.MovieRepository;
import com.movie.onlinestore.repository.PricingCategoryRepository;
import com.movie.onlinestore.service.MovieImportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest
public class MovieImportIntegrationTest {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieInventoryRepository movieInventoryRepository;
    @Autowired
    MovieImportService movieImportService;
    @Autowired
    PricingCategoryRepository pricingCategoryRepository;

    @Test
    public void testSaveMovieInventoryShouldSaveMovieAndMovieInventoryWhenValidRecordIsGiven(){
        String[] record = {"InsertMovie","tt0371746","Default","5"};
        List<String> errorList = new ArrayList<>();
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        long afterMovieCount = movieRepository.count();
        long afterMovieInventoryCount = movieInventoryRepository.count();
        assertTrue(status);
        assertEquals(beforeMovieCount+1,afterMovieCount);
        assertEquals(beforeMovieInventoryCount+1,afterMovieInventoryCount);
    }

    @Test
    public void testSaveMovieInventoryShouldAddStockWhenRecordActionIsAddStock(){
        String[] record = {"InsertMovie","tt0120611","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        movieImportService.saveMovieInventory(record,1,errorList);
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Optional<MovieInventory> movieInventoryRecord = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryBefore = movieInventoryRecord.get();
        String[] record1 = {"AddStock","tt0120611","Clasic","5"};
        movieImportService.saveMovieInventory(record1,1,errorList);
        Optional<MovieInventory> movieInventoryRecordAfter = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryAfter = movieInventoryRecordAfter.get();
        long afterMovieCount = movieRepository.count();
        long afterMovieInventoryCount = movieInventoryRepository.count();
        assertEquals(beforeMovieCount,afterMovieCount);
        assertEquals(beforeMovieInventoryCount,afterMovieInventoryCount);
        assertEquals(movieInventoryBefore.getId(),movieInventoryAfter.getId());
        Integer expectedTotalCount = movieInventoryBefore.getTotalCount()+5;
        Integer expectedAvailableCount = movieInventoryBefore.getAvailableCount()+5;
        assertEquals(expectedTotalCount,movieInventoryAfter.getTotalCount());
        assertEquals(expectedAvailableCount,movieInventoryAfter.getAvailableCount());
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseAndAlsoAddApiErrorInErrorListWhenInvalidImdbIdGiven(){
        String[] record = {"InsertMovie","tt03717146","Default","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Insert) Unable to get Movie Details From API.",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseAndAlsoAddErrorInErrorListWhenInvalidRecordGiven(){
        String[] record = {"InsertMovie","tt0120611","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 Column count mismatch.",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseAndAlsoAddErrorInErrorListWhenInvalidPricingCategoryGiven(){
        String[] record = {"InsertMovie","tt0112553","Unknown","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Insert) has invalid pricing type.",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseWhenTryToInsertExistingMovie(){
        String[] record = {"InsertMovie","tt0114709","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        movieImportService.saveMovieInventory(record,1,errorList);
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        long afterMovieCount = movieRepository.count();
        long afterMovieInventoryCount = movieInventoryRepository.count();
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Insert) Movie already exists.",errorList.get(0));
        assertEquals(beforeMovieCount,afterMovieCount);
        assertEquals(beforeMovieInventoryCount,afterMovieInventoryCount);;
    }

    @Test
    public void testSaveMovieInventoryShouldSubtractStockCountWhenRecordActionIsRemoveStock(){
        String[] record = {"InsertMovie","tt0120611","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        movieImportService.saveMovieInventory(record,1,errorList);
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Optional<MovieInventory> movieInventoryRecord = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryBefore = movieInventoryRecord.get();
        String[] record1 = {"RemoveStock","tt0120611","Clasic","2"};
        movieImportService.saveMovieInventory(record1,1,errorList);
        Optional<MovieInventory> movieInventoryRecordAfter = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryAfter = movieInventoryRecordAfter.get();
        long afterMovieCount = movieRepository.count();
        long afterMovieInventoryCount = movieInventoryRepository.count();
        assertEquals(beforeMovieCount,afterMovieCount);
        assertEquals(beforeMovieInventoryCount,afterMovieInventoryCount);
        assertEquals(movieInventoryBefore.getId(),movieInventoryAfter.getId());
        Integer expectedTotalCount = movieInventoryBefore.getTotalCount()-2;
        Integer expectedAvailableCount = movieInventoryBefore.getAvailableCount()-2;
        assertEquals(expectedTotalCount,movieInventoryAfter.getTotalCount());
        assertEquals(expectedAvailableCount,movieInventoryAfter.getAvailableCount());
    }


    @Test
    public void testSaveMovieInventoryShouldNotSubtractStockCountWhenRecordActionIsRemoveStockAndGivenCountIsMoreThanAvailableCount(){
        String[] record = {"InsertMovie","tt1201607","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        movieImportService.saveMovieInventory(record,1,errorList);
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Optional<MovieInventory> movieInventoryRecord = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryBefore = movieInventoryRecord.get();
        String[] record1 = {"RemoveStock","tt1201607","Clasic","6"};
        Boolean status = movieImportService.saveMovieInventory(record1,1,errorList);
        Optional<MovieInventory> movieInventoryRecordAfter = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryAfter = movieInventoryRecordAfter.get();
        long afterMovieCount = movieRepository.count();
        long afterMovieInventoryCount = movieInventoryRepository.count();
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Remove Stock) Stock count to be removed is more than available stock count.",errorList.get(0));
        assertEquals(beforeMovieCount,afterMovieCount);
        assertEquals(beforeMovieInventoryCount,afterMovieInventoryCount);
        assertEquals(movieInventoryBefore.getId(),movieInventoryAfter.getId());
        Integer expectedTotalCount = movieInventoryBefore.getTotalCount();
        Integer expectedAvailableCount = movieInventoryBefore.getAvailableCount();
        assertEquals(expectedTotalCount,movieInventoryAfter.getTotalCount());
        assertEquals(expectedAvailableCount,movieInventoryAfter.getAvailableCount());
    }

    @Test
    public void testSaveMovieInventoryShouldChangePricingCategoryWhenRecordActionIsUpdatePricing(){
        String[] record = {"InsertMovie","tt0120611","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        movieImportService.saveMovieInventory(record,1,errorList);
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Optional<MovieInventory> movieInventoryRecord = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryBefore = movieInventoryRecord.get();
        String[] record1 = {"UpdatePricing","tt0120611","Default","5"};
        Boolean status = movieImportService.saveMovieInventory(record1,1,errorList);
        Optional<MovieInventory> movieInventoryRecordAfter = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryAfter = movieInventoryRecordAfter.get();
        Optional<Movie> movieRecord = movieRepository.findByImdbId("tt0120611");
        Movie movie = movieRecord.get();
        long afterMovieCount = movieRepository.count();
        long afterMovieInventoryCount = movieInventoryRepository.count();
        assertTrue(status);
        assertEquals(0,errorList.size());
        assertEquals(beforeMovieCount,afterMovieCount);
        assertEquals(beforeMovieInventoryCount,afterMovieInventoryCount);
        assertEquals(movieInventoryBefore.getId(),movieInventoryAfter.getId());
        assertNotEquals("Clasic",movie.getPricingCategory().getName());
        assertEquals("Default",movie.getPricingCategory().getName());
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseWhenAddStockForNotExistingMovie(){
        String[] record = {"AddStock","tt0468569","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Add Stock) Movie not exists.",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseWhenRemoveStockForNotExistingMovie(){
        String[] record = {"RemoveStock","tt0468569","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Remove Stock) Movie not exists.",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseWhenUpdatePricingForNotExistingMovie(){
        String[] record = {"UpdatePricing","tt0468569","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 (Update Pricing) Movie not exists.",errorList.get(0));
    }

}
