package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.entity.QArticle;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class ArticleQuerydsl {
    public static List<OrderSpecifier> getOrderSpecifier(Sort sort, QArticle article) {
        List<OrderSpecifier> orders = new ArrayList<>();
        if(!sort.isEmpty()){
            for (Sort.Order order : sort){
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                String prop = order.getProperty();
                PathBuilder pathBuilder = new PathBuilder(article.getType(), article.getMetadata());
                orders.add(new OrderSpecifier(direction, pathBuilder.get(prop)));
            }
        }
        return orders;
    }

    public static BooleanBuilder createFilterBuilder(String searchType, Category category, String keyword, QArticle article){
        BooleanBuilder filterBuilder = new BooleanBuilder();

        //검색
        addSearchTypeFilters(searchType, keyword, article, filterBuilder);
        //카테고리
        addCategoryTypeFilters(category, article, filterBuilder);

        filterBuilder.and(article.category.ne(Category.CATEGORY_ANNOUNCEMENT));

        return filterBuilder;
    }

    private static void addSearchTypeFilters(String searchType, String keyword, QArticle article, BooleanBuilder filterBuilder){
        if(searchType != null){
            if(searchType.contains("t")){
                filterBuilder.or(article.title.contains(keyword));
            }
            if(searchType.contains("c")){
                filterBuilder.or(article.content.contains(keyword));
            }
            if(searchType.contains("w")){
                filterBuilder.or(article.user.nickName.contains(keyword));
            }
        }
    }

    private static void addCategoryTypeFilters(Category category, QArticle article, BooleanBuilder filterBuilder){
        if(category != null){
            filterBuilder.and(
                    article.category.eq(category)
            );
        }
    }
}

