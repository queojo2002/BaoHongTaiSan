package com.example.baohongtaisan_2.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterACTKhuVucPhong;
import com.example.baohongtaisan_2.Adapter.User.AdapterACTLoaiPhong;
import com.example.baohongtaisan_2.Adapter.User.AdapterPhong;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.KhuVucPhong;
import com.example.baohongtaisan_2.Model.LoaiPhong;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TraCuuFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdapterPhong adapterPhong;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase db;
    private List<Phong> phongList;
    private List<KhuVucPhong> khuVucPhongList;
    private List<LoaiPhong> loaiPhongList;
    private AutoCompleteTextView ACT_khuvucphong;
    private AutoCompleteTextView ACT_loaiphong;
    private AdapterACTKhuVucPhong adapterACTKhuVucPhong;
    private AdapterACTLoaiPhong adapterACTLoaiPhong;
    private Button btnTimKiem, btnTaiLai;

    private String MaLP = "-1", MaKVP = "-1";

    public TraCuuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        phongList = new ArrayList<>();
        khuVucPhongList = new ArrayList<>();
        loaiPhongList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tra_cuu, container, false);
        ACT_khuvucphong = view.findViewById(R.id.txtSearch_KVP);
        ACT_loaiphong = view.findViewById(R.id.txtSearch_LoaiPhong);
        btnTaiLai = view.findViewById(R.id.btnTaiLai);
        btnTimKiem = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recycler_1);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        Show_AllKVPhong();
        Show_AllLP();
        Show_AllPhong();
        InitSearch();

        btnTaiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaLP = "-1";
                MaKVP = "-1";
                Show_AllPhong();
            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MaLP != "-1" || MaKVP != "-1") {

                    Show_AllPhong();
                }
            }
        });

        return view;
    }


    private void InitSearch() {
        ACT_loaiphong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<LoaiPhong> adapter = (ArrayAdapter<LoaiPhong>) ACT_loaiphong.getAdapter();
                LoaiPhong selectedLoaiPhong = adapter.getItem(i);
                MaLP = selectedLoaiPhong.getMaLP() + "";
            }
        });

        ACT_khuvucphong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<KhuVucPhong> adapter = (ArrayAdapter<KhuVucPhong>) ACT_khuvucphong.getAdapter();
                KhuVucPhong khuVucPhong = adapter.getItem(i);
                MaKVP = khuVucPhong.getMaKVP() + "";
            }
        });
    }

    private void Show_AllLP() {
        ApiServices.apiServices.get_list_loaiphong().enqueue(new Callback<List<LoaiPhong>>() {
            @Override
            public void onResponse(Call<List<LoaiPhong>> call, Response<List<LoaiPhong>> response) {
                loaiPhongList.clear();
                loaiPhongList = response.body();
                if (loaiPhongList != null && getContext() != null) {
                    adapterACTLoaiPhong = new AdapterACTLoaiPhong(getContext(), R.layout.autocompletetextview_custom, loaiPhongList);
                    ACT_loaiphong.setAdapter(adapterACTLoaiPhong);
                }

            }

            @Override
            public void onFailure(Call<List<LoaiPhong>> call, Throwable t) {

            }
        });
    }

    public void Show_AllKVPhong() {

        ApiServices.apiServices.get_list_khuvucphong().enqueue(new Callback<List<KhuVucPhong>>() {
            @Override
            public void onResponse(Call<List<KhuVucPhong>> call, Response<List<KhuVucPhong>> response) {
                khuVucPhongList.clear();
                khuVucPhongList = response.body();
                if (khuVucPhongList != null && getContext() != null) {
                    adapterACTKhuVucPhong = new AdapterACTKhuVucPhong(getContext(), R.layout.autocompletetextview_custom, khuVucPhongList);
                    ACT_khuvucphong.setAdapter(adapterACTKhuVucPhong);
                }

            }

            @Override
            public void onFailure(Call<List<KhuVucPhong>> call, Throwable t) {

            }
        });
    }

    public void Show_AllPhong() {


        ApiServices.apiServices.get_list_phong().enqueue(new Callback<List<Phong>>() {
            @Override
            public void onResponse(Call<List<Phong>> call, Response<List<Phong>> response) {
                phongList.clear();
                phongList = response.body();
                if (phongList != null && getContext() != null) {
                    adapterPhong = new AdapterPhong(phongList);
                    recyclerView.setAdapter(adapterPhong);
                }

            }

            @Override
            public void onFailure(Call<List<Phong>> call, Throwable t) {

            }
        });






       /* DatabaseReference phongRef = db.getReference("Phong");
        phongRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phongList.clear();
                for (DataSnapshot phongSnapshot : dataSnapshot.getChildren()) {
                    Phong phong = phongSnapshot.getValue(Phong.class);
                    if (MaLP != "-1" && MaKVP != "-1") {
                        if (phong.getLoaiPhong().getMaLP().equals(MaLP) && phong.getKhuVucPhong().getMaKVP().equals(MaKVP)) {
                            phongList.add(phong);
                        }
                    } else if (MaLP != "-1" && MaKVP == "-1") {
                        if (phong.getLoaiPhong().getMaLP().equals(MaLP)) {
                            phongList.add(phong);
                        }
                    } else if (MaLP == "-1" && MaKVP != "-1") {
                        if (phong.getKhuVucPhong().getMaKVP().equals(MaKVP)) {
                            phongList.add(phong);
                        }
                    } else {
                        phongList.add(phong);
                    }


                }
                adapterPhong.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });*/


    }


}