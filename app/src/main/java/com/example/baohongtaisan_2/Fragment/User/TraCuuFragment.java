package com.example.baohongtaisan_2.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.User.AdapterPhong;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.Phong;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TraCuuFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private AdapterPhong adapterPhong;
    private List<Phong> phongList;
    private androidx.appcompat.widget.SearchView sv;

    public TraCuuFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phongList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tra_cuu, container, false);
        _AnhXa();
        Show_AllPhong();
        _SuKien();


        return view;
    }


    public void _AnhXa() {
        recyclerView = view.findViewById(R.id.recycler_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        sv = view.findViewById(R.id.svPhong_1);
    }

    public void _SuKien() {
        sv.clearFocus();
        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Phong> searchlist = new ArrayList<>();
                for (Phong phong : phongList) {
                    if (phong.getTenP().toLowerCase().contains(s.toLowerCase()) || phong.getTenLP().toLowerCase().contains(s.toLowerCase()) || phong.getTenKVP().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(phong);
                    }
                }
                if (adapterPhong != null) adapterPhong.searchDataList(searchlist);
                return false;
            }
        });

    }

    public void Show_AllPhong() {
        ApiServices.apiServices.get_list_phong().enqueue(new Callback<List<Phong>>() {
            @Override
            public void onResponse(@NonNull Call<List<Phong>> call, @NonNull Response<List<Phong>> response) {
                phongList.clear();
                phongList = response.body();
                if (phongList != null && getContext() != null) {
                    adapterPhong = new AdapterPhong(phongList);
                    recyclerView.setAdapter(adapterPhong);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Phong>> call, @NonNull Throwable t) {

            }
        });


    }


}