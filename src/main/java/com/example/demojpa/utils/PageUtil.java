package com.example.demojpa.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * Utility class for paging and sorting
 */
public class PageUtil {

    public static PageRequest getPageRequest(String sortField, Integer offset, Integer limit){
        int defaultOffset = 0;
        int defaultLimit = 10;
        if(Objects.nonNull(offset) && Objects.nonNull(limit)){
            return PageRequest.of(offset,limit,getSort(sortField));
        }
        return PageRequest.of(defaultOffset,defaultLimit,getSort(sortField));
    }

    private static Sort getSort(String sortField){
        if(Strings.isEmpty(sortField)){
            // Default sort by id asc
            return Sort.by(Sort.Direction.ASC,"id");
        }else{
            if(sortField.startsWith("-")){
                sortField = sortField.substring(1);
                return Sort.by(Sort.Direction.DESC,sortField);
            }else{
                return Sort.by(Sort.Direction.ASC,sortField);
            }
        }
    }
}
