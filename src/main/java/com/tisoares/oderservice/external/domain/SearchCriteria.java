package com.tisoares.oderservice.external.domain;

import com.tisoares.oderservice.internal.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class SearchCriteria<T extends BaseEntity> {

    private Class<T> clazz;
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