package com.example.baohongtaisan_2.Api;

import com.example.baohongtaisan_2.Model.BaoHong;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.Object_Add;
import com.example.baohongtaisan_2.Model.PhanBo;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.Model.ThongKe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiServices apiServices = new Retrofit.Builder()
            .baseUrl("https://kkts.vanduc.top/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices.class);


    // đơn vị
    @GET("api/load_data_donvi.php")
    Call<List<DonVi>> get_list_donvi();

    @GET("api/edit_data_donvi.php")
    Call<ObjectReponse> edit_donvi(@Query("MaDV")int madv,
                                @Query("TenDV")String tendv,
                                @Query("MoTaDV")String motadv);

    @GET("api/add_data_donvi.php")
    Call<ObjectReponse> add_donvi(@Query("TenDV")String tendv,
                               @Query("MoTaDV")String motadv);

    @GET("api/delete_data_donvi.php")
    Call<ObjectReponse> delete_donvi(@Query("MaDV")int madv);
    // end api dv



    // Chức danh
    @GET("api/load_data_chucdanh.php")
    Call<List<ChucDanh>> get_list_chucdanh();

    @GET("api/edit_data_chucdanh.php")
    Call<ObjectReponse> edit_chucdanh(@Query("MaCD")int macd,
                                   @Query("TenCD")String tencd,
                                   @Query("MoTaCD")String motacd);

    @GET("api/add_data_chucdanh.php")
    Call<ObjectReponse> add_chucdanh(@Query("TenCD")String tencd,
                                  @Query("MoTaCD")String motacd);

    @GET("api/delete_data_chucdanh.php")
    Call<ObjectReponse> delete_chucdanh(@Query("MaCD")int macd);

    // end api chức danh


    // Phòng
    @GET("api/load_data_phong.php")
    Call<List<Phong>> get_list_phong();

    @GET("api/add_data_phong.php")
    Call<ObjectReponse> add_phong(@Query("TenP") String tenp,
                                  @Query("MaKVP") int MaKVP,
                                  @Query("MaLP") int MaLP);

    @GET("api/edit_data_phong.php")
    Call<ObjectReponse> edit_phong(@Query("MaP") int MaP,
                                   @Query("TenP") String tenp,
                                   @Query("MaKVP") int MaKVP,
                                   @Query("MaLP") int MaLP);

    @GET("api/delete_data_phong.php")
    Call<ObjectReponse> delete_phong(@Query("MaP") int MaP);

    // End api phòng


    // Loại phòng
    @GET("api/load_data_loaiphong.php")
    Call<List<LoaiPhong>> get_list_loaiphong();

    @GET("api/edit_data_loaiphong.php")
    Call<ObjectReponse> edit_loaiphong(@Query("MaLP") int malp,
                                       @Query("TenLP") String tenlp);

    @GET("api/add_data_loaiphong.php")
    Call<ObjectReponse> add_loaiphong(@Query("TenLP") String tenlp);

    @GET("api/delete_data_loaiphong.php")
    Call<ObjectReponse> delete_loaiphong(@Query("MaLP") int malp);

    // End api loại phòng


    // Khu vực phòng
    @GET("api/load_data_khuvucphong.php")
    Call<List<KhuVucPhong>> get_list_khuvucphong();

    @GET("api/edit_data_khuvucphong.php")
    Call<ObjectReponse> edit_khuvucphong(@Query("MaKVP") int makvp,
                                         @Query("TenKVP") String tenkvp);

    @GET("api/add_data_khuvucphong.php")
    Call<ObjectReponse> add_khuvucphong(@Query("TenKVP") String tenkvp);

    @GET("api/delete_data_khuvucphong.php")
    Call<ObjectReponse> delete_khuvucphong(@Query("MaKVP") int makvp);

    // End api khu vực phòng


    @GET("api/load_data_phanbo_byMaP.php")
    Call<List<PhanBo>> get_list_phanbo_byMaP(@Query("MaP") int MaP);

    @GET("api/load_data_nguoidung_byEmail.php")
    Call<NguoiDung> get_nguoidung_byEmail(@Query("Email") String Email);

    @GET("api/load_data_baoloi.php")
    Call<List<BaoHong>> get_list_baohong();

    @GET("api/load_data_baoloi_byMaND.php")
    Call<List<BaoHong>> get_list_baohong_byMaND(@Query("MaND") int MaND);


    @GET("api/api_thongke.php")
    Call<ThongKe> get_thongke();


    @GET("api/load_data_nguoidung.php")
    Call<List<NguoiDung>> get_list_nguoidung();



    @GET("api/edit_token_uid_nguoidung.php")
    Call<ObjectReponse> edit_token_uid_nguoidung(
            @Query("MaND") int MaND,
            @Query("UID") String UID,
            @Query("TOKEN") String TOKEN);


    @GET("api/add_data_nguoidung.php")
    Call<Object_Add> add_new_nguoidung(
            @Query("HoVaTen") String HoVaTen,
            @Query("Email") String Email,
            @Query("MaPQ") int MaPQ,
            @Query("MaDV") int MaDV,
            @Query("MaCD") int MaCD,
            @Query("UID") String UID,
            @Query("TOKEN") String TOKEN);

    @GET("api/add_data_baoloi.php")
    Call<Object_Add> add_new_baoloi(
            @Query("MaPB") int MaPB,
            @Query("MaND") int MaND,
            @Query("TinhTrang") int TinhTrang,
            @Query("MoTa") String MoTa,
            @Query("HinhAnh") String HinhAnh);

}
