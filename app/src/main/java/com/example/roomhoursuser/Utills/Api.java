package com.example.roomhoursuser.Utills;

import com.example.roomhoursuser.AllImageView.AllRoomImage.AllImageModel;
import com.example.roomhoursuser.BankScreen.BankModel;
import com.example.roomhoursuser.CheckInScreen.HomeDetailsDataModel;
import com.example.roomhoursuser.CheckInScreen.HomeDetailsModel;
import com.example.roomhoursuser.FavFragmen.ApiModel.GetFavModel;
import com.example.roomhoursuser.GetChat;
import com.example.roomhoursuser.HomeFragment.HomeModel;
import com.example.roomhoursuser.LoginScreen.LoginModel;
import com.example.roomhoursuser.MapScreen.MapModel;
import com.example.roomhoursuser.MessageFragment.ChatConversation;
import com.example.roomhoursuser.PaymentMwthod.BookingModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    String signUp ="signUp";
    String add_payment ="add_payment";
    String login ="login";
    String SocialloginApi ="social_login";
    String forgotPassword ="forgetPassword";
    String logout ="logout";
    String getProfile ="get_profile";
    String get_BankDetails ="get_BankDetails";
    String get_room_image ="get_all_room_image";
    String getState = "get_state";
    String addVideo = "addVideo";
    String notification = "getNotification";
    String updateProfilePicture = "uploadProfilePicture";
    String changePassword = "changePassword";
    String updateAddress = "Create_address";
    String addLike = "addLikes";
    String dislike = "unLikeById";
    String get_room_details = "get_room_details";
    String room_filter = "room_filter";
    String best_match_room = "best_match_room";
    String nearByRoom = "nearByRoom";
    String add_fav_room = "add_fav_room";
    String add_ChnagePassword = "change_password";
    String get_favorite = "get_favorite";
    String get_room_details_new = "get_room_details_new";
    String get_city = "get_city";
    String getALLfriend = "getALLfriend";
    String getBuyingFriend = "getBuyingFriend";
    String getSellingFriend = "getSellingFriend";
    String addInterest = "addInterest";
    String getHome = "getHome";
    String room_booking = "room_booking";
    String add_to_photo = "add_photo_room";

    String get_conversation_detail ="get_conversation_detail";
    String get_chat ="get_chat";
    String insert_chat ="insert_chat";


    @Multipart
    @POST(add_to_photo)
    Call<ResponseBody>add_to_photo(
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part part
    );

    @FormUrlEncoded
    @POST(signUp)
    Call<LoginModel>signupApi(
            @Field("first_name") String first_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("register_id") String register_id,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(room_booking)
    Call<BookingModel>room_booking(
            @Field("user_id") String user_id,
            @Field("room_id") String room_id,
            @Field("price") String price,
            @Field("check_in_date") String check_in_date,
            @Field("check_out_date") String check_out_date,
            @Field("hours") String hours,
            @Field("payment_type") String payment_type
    );


    @FormUrlEncoded
    @POST(add_payment)
    Call<ResponseBody>add_payment(
            @Field("user_id") String user_id,
            @Field("CardholderName") String CardholderName,
            @Field("cardnumber") String cardnumber,
            @Field("expirydate") String expirydate
    );

    @FormUrlEncoded
    @POST(login)
    Call<ResponseBody>loginApi(
            @Field("email") String email,
            @Field("password") String password,
            @Field("register_id") String register_id,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("type") String type

    );

    @FormUrlEncoded
    @POST(SocialloginApi)
    Call<ResponseBody>SocialloginApi(
            @Field("first_name") String first_name,
            @Field("email") String email,
            @Field("register_id") String register_id,
            @Field("social_id") String social_id,
            @Field("lat") String lat,
            @Field("lon") String lon

    );

    @FormUrlEncoded
    @POST(getProfile)
    Call<LoginModel>getProfile(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_BankDetails)
    Call<BankModel>get_BankDetails(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_room_image)
    Call<AllImageModel>get_room_image(
            @Field("room_id") String room_id
    );

    @FormUrlEncoded
    @POST(forgotPassword)
    Call<ResponseBody> forgotPasswordApi(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST(logout)
    Call<ResponseBody>logoutApi(
            @Field("user_id") String userID
    );



    @Multipart
    @POST(updateProfilePicture)
    Call<ResponseBody>updateProfileImageApi(
            @Part("user_id") RequestBody id,
            @Part MultipartBody.Part part
    );

    @FormUrlEncoded
    @POST(changePassword)
    Call<ResponseBody>changePasswordApi(
            @Field("user_id") String userID,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword
    );

    @FormUrlEncoded
    @POST(updateAddress)
    Call<ResponseBody>updateAddressApi(
            @Field("user_id") String userID,
            @Field("local_address") String localAddress,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country
    );

    @FormUrlEncoded
    @POST(addLike)
    Call<ResponseBody>addLikeApi(
            @Field("user_id") String userID,
            @Field("video_id") String videoId
    );

    @FormUrlEncoded
    @POST(dislike)
    Call<ResponseBody>dislikeApi(
            @Field("user_id") String userID,
            @Field("video_id") String videoID
    );


    @FormUrlEncoded
    @POST(get_room_details)
    Call<HomeModel>get_room_details(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(room_filter)
    Call<HomeModel>room_filter(
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("less_price") String less_price,
            @Field("private_room") String private_room,
            @Field("air_room") String air_room,
            @Field("heating") String heating
    );

    @FormUrlEncoded
    @POST(best_match_room)
    Call<HomeModel>best_match_room(
            @Field("lat") String lat,
            @Field("lon") String lon
    );

    @FormUrlEncoded
    @POST(nearByRoom)
    Call<MapModel>nearByRoom(
            @Field("lat") String lat,
            @Field("lon") String lon
    );


    @FormUrlEncoded
    @POST(add_fav_room)
    Call<ResponseBody>add_fav_room(
            @Field("user_id") String user_id,
            @Field("room_id") String room_id
    );

    @FormUrlEncoded
    @POST(add_ChnagePassword)
    Call<ResponseBody>add_ChnagePassword(
            @Field("user_id") String user_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(get_favorite)
    Call<GetFavModel>get_favorite(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(get_room_details_new)
    Call<HomeDetailsModel>get_room_details_new(
            @Field("room_id") String room_id
    );

    @FormUrlEncoded
    @POST(get_conversation_detail)
    Call<ChatConversation>get_conversation_detail(
            @Field("receiver_id") String receiver_id
    );

    @FormUrlEncoded
    @POST(get_chat)
    Call<GetChat>get_chat(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id
    );

    @FormUrlEncoded
    @POST(insert_chat)
    Call<ResponseBody>insert_chat(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id,
            @Field("chat_message") String chat_message
    );



}
