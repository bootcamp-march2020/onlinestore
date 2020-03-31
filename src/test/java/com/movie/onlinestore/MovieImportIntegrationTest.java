package com.movie.onlinestore;

import com.movie.onlinestore.model.MovieInventory;
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
        String[] record = {"tt0371746","Default","5"};
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
    public void testSaveMovieInventoryShouldAddTheStockCountWhenSameMovieCodeGivenAgain(){
        String[] record = {"tt0120611","Clasic","5"};
        List<String> errorList = new ArrayList<>();
        movieImportService.saveMovieInventory(record,1,errorList);
        long beforeMovieCount = movieRepository.count();
        long beforeMovieInventoryCount = movieInventoryRepository.count();
        Optional<MovieInventory> movieInventoryRecord = movieInventoryRepository.findByImdbId("tt0120611");
        MovieInventory movieInventoryBefore = movieInventoryRecord.get();
        movieImportService.saveMovieInventory(record,1,errorList);
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
        String[] record = {"tt03717146","Default","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 Unable to get Movie Details From API",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseAndAlsoAddErrorInErrorListWhenInvalidRecordGiven(){
        String[] record = {"tt0120611","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 has data mismatch",errorList.get(0));
    }

    @Test
    public void testSaveMovieInventoryShouldReturnFalseAndAlsoAddErrorInErrorListWhenInvalidPricingCategoryGiven(){
        String[] record = {"tt0112553","Unknown","5"};
        List<String> errorList = new ArrayList<>();
        Boolean status = movieImportService.saveMovieInventory(record,1,errorList);
        assertFalse(status);
        assertEquals(1,errorList.size());
        assertEquals("Line 1 has invalid pricing type",errorList.get(0));
    }

}
