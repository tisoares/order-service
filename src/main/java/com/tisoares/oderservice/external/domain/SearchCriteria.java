package com.tisoares.oderservice.external.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class SearchCriteria {

    private List<Filter> filters;
    private List<String> expands;

    @Getter
    @Builder(toBuilder = true)
    public static class Filter {
        private String field;
        private QueryOperator operator;
        private String value;

        public enum QueryOperator {
            EQUALS, NOT_EQUALS, LIKE, LESS_THAN, GREATER_THAN
        }
    }

}