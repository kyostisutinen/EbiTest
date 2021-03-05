package com.example.ebitest.controller;

import com.example.ebitest.AppProperties;
import com.example.ebitest.da.GetEnsembleData;
import com.example.ebitest.dom.EbiTestReturnDom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class EbiTaskController {

    @Autowired
    AppProperties myAppProperties;

    @GetMapping("/gene_suggest")
    public ArrayList<EbiTestReturnDom> greeting(@RequestParam(value = "query") String argGeneName, @RequestParam(value = "species") String argSpecies, @RequestParam(value = "limit") String argLimit) throws Exception {
        try {
            // NOTE: It's unclear for me if we can trim given GENE NAME or SPECIES arguments or can those parameters include spaces.
            // NOTE: Also I was wondering need for encoding and SQL injection prevention but I did not develop those now.

            // Check length of query parameter
            if ((argGeneName.trim().length() < 1))   throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Given 'query' parameter was empty");

            // Check length of species parameter
            if ((argSpecies.trim().length() < 1))   throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Given 'species' parameter was empty");

            // Check length of limit parameter
            if ((String.valueOf(argLimit).trim().length() < 1))   throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Given 'limit' parameter was empty");

            // Parse given LIMIT into short
            short limitAsShort = Short.parseShort(argLimit);

            // Check limit parameter
            if ((limitAsShort < 1) || (limitAsShort > myAppProperties.getMaxRecordLimit()))  throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Limit value must be between 1 and " + String.valueOf(myAppProperties.getMaxRecordLimit()));

            System.out.println("query='" + argGeneName + "'");
            System.out.println("species='" + argSpecies + "'");
            System.out.println("limit='" + limitAsShort + "'");

            // Get data from Ensemble. Pass db url and db username also as parameter
            return new GetEnsembleData(argGeneName, argSpecies, limitAsShort, myAppProperties.getUrl(), myAppProperties.getUsername()).fetchData();

        } catch (ResponseStatusException _e ) {
            throw new ResponseStatusException(_e.getStatus(), _e.getReason());
        } catch (NumberFormatException _e ) {
            throw new NumberFormatException("Given 'limit' value '" + argLimit + "' is not short number");
        } catch (Exception _e ) {
            throw new Exception(_e.getMessage());
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) throws Exception {
        String name = ex.getParameterName();
        String _returnStr = "Exception occurred: '" + name + "' parameter was missing in REST api call!";
        System.out.println(_returnStr);
        throw new Exception(_returnStr);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleInvalidParams(ResponseStatusException _ex) throws Exception {
        String _returnStr = "Exception occurred: " + _ex.getStatus() + ", " + _ex.getReason();
        System.out.println(_returnStr);
        throw new Exception(_returnStr);
    }
}