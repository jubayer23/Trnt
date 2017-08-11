package com.creative.trnt.appdata;

/**
 * Created by comsol on 10-Aug-17.
 */
public class ApiUrl {

    public static final String URL_BASE = "https://yts.ag/api/v2/list_movies.json";

    public static String getUrlForMovieList(
            String limit,
            String page,
            String quality,
            String minimum_rating,
            String query_term,
            String genre,
            String sort_by,
            String order_buy
    ) {

        String parameter = "?";

        if (limit != null && !limit.isEmpty()  ) {
            parameter = parameter +  "limit=" + limit + "&";
        }
        if (page != null && !page.isEmpty()  ) {
            parameter = parameter + "page="  + page + "&";
        }

        if ( quality != null && !quality.isEmpty() ) {
            parameter = parameter + quality + "&";
        }
        if (minimum_rating != null && !minimum_rating.isEmpty() ) {
            parameter = parameter + minimum_rating + "&";
        }
        if ( query_term != null && !query_term.isEmpty() ) {
            parameter = parameter + query_term + "&";
        }
        if ( genre != null && !genre.isEmpty() ) {
            parameter = parameter + genre + "&";
        }
        if ( sort_by != null && !sort_by.isEmpty() ) {
            parameter = parameter + "sort_by=" + sort_by + "&";
        }

        if ( order_buy != null && !order_buy.isEmpty() ) {
            parameter = parameter + order_buy + "&";
        }

        parameter = parameter.substring(0,parameter.length() - 1);

        return URL_BASE + parameter;

    }
}
