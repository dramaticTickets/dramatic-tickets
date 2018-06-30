package com.example.chen.dramatic_tickets.service;

import com.example.chen.dramatic_tickets.model.Cinema;
import com.example.chen.dramatic_tickets.model.Movie;
import com.example.chen.dramatic_tickets.model.MovieSession;
import com.example.chen.dramatic_tickets.model.Seat;
import com.example.chen.dramatic_tickets.model.Ticket;
import com.example.chen.dramatic_tickets.model.User;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by chennan on 2018/6/29.
 */

public interface DramaticService {

    //User
    @GET("/resource/users/account/{account}")
    Observable<User> getUserByAccount(@Path("account") String account);

    @GET("/resource/users/phone/{phone}")
    Observable<User> getUserByPhone(@Path("phone") String account);

    @POST("/resource/users/login")
    Observable<User> login(@Body Map<String, Object> body);

    @POST("/resource/users")
    Observable<User> register(@Body Map<String, Object> body);


    //Cinema
    @GET("/resource/cinema")
    Observable<Cinema[]> getAllCinema();

    @GET("/resource/cinema/{cinemaName}")
    Observable<Cinema> getCinema(@Path("cinemaName") String cinemaName);


    //Movie Session
    @GET("/resource/cinema/{cinemaName}/hall/{hallNum}/{date}/{startTime}")
    Observable<MovieSession> getSessionId(@Path("cinemaName") String cinemaName,
                                          @Path("hallNum") int hallNum,
                                          @Path("date") int date,
                                          @Path("startTime") int startTime);

    @GET("/resource/cinema/{cinemaName}/movie/{movieName}")
    Observable<MovieSession[]> getSessions(@Path("cinemaName") String cinemaName,
                                  @Path("movieName") String movieName);


    //Seat
    @GET("/resource/movie_session/{sessionId}/{seatRow}/{seatCol}")
    Observable<Seat> getSeat(@Path("sessionId") int sessionId,
                             @Path("seatRow") int seatRow,
                             @Path("seatCol") int seatCol);


    //Movie
    @GET("/resource/movie/{movieName}")
    Observable<Movie> getMovie(@Path("movieName") String movieName);

    @GET("/resource/movie")
    Observable<Movie> getAllMovie();

    //Ticket
    @GET("/resource/tickets/{phone}")
    Observable<Ticket[]> getAllTicket(@Path("phone") String phone);

    @POST("/resource/tickets")
    Observable<Ticket> bookTicket(@Body Map<String, Object> body);

}
