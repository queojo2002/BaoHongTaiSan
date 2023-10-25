package com.example.baohongtaisan_2.Fragment.Admin.NguoiDung;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baohongtaisan_2.Adapter.Admin.AdminNguoiDungAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerChucDanhAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerDonViAdapter;
import com.example.baohongtaisan_2.Adapter.Admin.SpinnerAdapter.SpinnerPhanQuyenAdapter;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Interface.RCVClickItem;
import com.example.baohongtaisan_2.Model.ChucDanh;
import com.example.baohongtaisan_2.Model.DonVi;
import com.example.baohongtaisan_2.Model.NguoiDung;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.PhanQuyen;
import com.example.baohongtaisan_2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminDanhSachNguoiDungFragment extends Fragment {

    private SearchView SVnd;
    private RecyclerView rcvND;
    private AdminNguoiDungAdapter adminNguoiDung_adapter;
    private List<NguoiDung> listND;
    private List<DonVi> donViList;
    private List<ChucDanh> chucDanhList;
    private List<PhanQuyen> phanQuyenList;
    private Button btnaddNd;
    private View rootView;
    private int MaDV = -1, MaCD = -1, MaPQ = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_admin_danh_sach_nguoi_dung, container, false);
        _AnhXa();
        _SuKien();

        return rootView;
    }

    private void _AnhXa() {
        rcvND = rootView.findViewById(R.id.rvNguoidung);

        SVnd = rootView.findViewById(R.id.txtSearchND);

        btnaddNd = rootView.findViewById(R.id.btnnd_add);

        LinearLayoutManager linearLayoutManagerND = new LinearLayoutManager(requireContext());

        rcvND.setLayoutManager(linearLayoutManagerND);

        listND = new ArrayList<>();

        donViList = new ArrayList<>();

        chucDanhList = new ArrayList<>();

        phanQuyenList = new ArrayList<>();


        GetListNguoiDung();
    }

    private void _SuKien() {
        SVnd.clearFocus();
        SVnd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<NguoiDung> searchlist = new ArrayList<>();
                for (NguoiDung nguoiDung : listND) {
                    if (nguoiDung.getHoVaTen().toLowerCase().contains(s.toLowerCase())) {
                        searchlist.add(nguoiDung);
                    }
                }
                if (adminNguoiDung_adapter != null)
                {
                    adminNguoiDung_adapter.searchDataList(searchlist);
                }
                return false;
            }
        });

        btnaddNd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getContext(), AddNguoidungActivity.class);
                startActivity(intent);*/
            }
        });


    }



    public void GetListNguoiDung() {
        ApiServices.apiServices.get_list_nguoidung().enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                listND.clear();
                listND = response.body();
                if (listND != null && getContext() != null) {
                    adminNguoiDung_adapter = new AdminNguoiDungAdapter(listND, new RCVClickItem() {
                        @Override
                        public void onClickRCV(Object object, String CURD) {
                            NguoiDung nguoiDung = (NguoiDung) object;
                            if (CURD.equals("EDIT"))
                            {
                                Open_Dialog_Edit(nguoiDung);
                            }
                        }
                    });
                    rcvND.setAdapter(adminNguoiDung_adapter);
                }

            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                Toast.makeText(getContext(), "Lấy dữ liệu thất bại...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void Open_Dialog_Edit(NguoiDung nd) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_nguoidung_add);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtt = window.getAttributes();
        windowAtt.gravity = Gravity.CENTER;
        window.setAttributes(windowAtt);
        dialog.setCancelable(true);

        TextView tv = dialog.findViewById(R.id.tvAdd_TenChucNangEdit);
        EditText txtinput = dialog.findViewById(R.id.txtAdd_Input);
        Button btnhuybo = dialog.findViewById(R.id.btnAdd_HuyBo);
        Button btnthem = dialog.findViewById(R.id.btnAdd);

        Spinner spnDV = dialog.findViewById(R.id.spnAdd_1);
        Spinner spnCD = dialog.findViewById(R.id.spnAdd_2);
        Spinner spnPQ = dialog.findViewById(R.id.spnAdd_3);

        btnthem.setText("Chỉnh sửa");
        tv.setText("Chỉnh sửa thông tin người dùng");

        Load_Data_DonVi(spnDV, nd.getMaDV());
        Load_Data_ChucDanh(spnCD, nd.getMaCD());
        Load_Data_PhanQuyen(spnPQ, nd.getMaPQ());
        txtinput.setText(nd.getHoVaTen());



        spnDV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<DonVi> adapter = (ArrayAdapter<DonVi>) spnDV.getAdapter();
                DonVi selected = adapter.getItem(i);
                if (selected != null) {
                    MaDV = selected.getMaDV();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spnCD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<ChucDanh> adapter = (ArrayAdapter<ChucDanh>) spnCD.getAdapter();
                ChucDanh selected = adapter.getItem(i);
                if (selected != null) {
                    MaCD = selected.getMaCD();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spnPQ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<PhanQuyen> adapter = (ArrayAdapter<PhanQuyen>) spnPQ.getAdapter();
                PhanQuyen selected = adapter.getItem(i);
                if (selected != null) {
                    MaPQ = selected.getMaPQ();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MaCD == -1 || MaDV == -1 || MaPQ == -1 || TextUtils.isEmpty(txtinput.getText().toString()))
                {
                    Toast.makeText(requireContext(), "Dữ liệu sửa người dùng của bạn không đúng !!!", Toast.LENGTH_SHORT).show();
                }else
                {

                    ApiServices.apiServices.edit_data_nguoidung(nd.getMaND(), txtinput.getText().toString(), MaDV, MaCD, MaPQ).enqueue(new Callback<ObjectReponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ObjectReponse> call, @NonNull Response<ObjectReponse> response) {
                            ObjectReponse objectEdit = response.body();
                            if (objectEdit == null) return;
                            if (objectEdit.getCode() == 1) {
                                onResume();
                                Toast.makeText(requireContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(requireContext(), objectEdit.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ObjectReponse> call, @NonNull Throwable t) {
                            Toast.makeText(requireContext(), "Có lỗi khi cập nhật người dùng!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        btnhuybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void Load_Data_DonVi(Spinner spinner, int MaDV) {
        ApiServices.apiServices.get_list_donvi().enqueue(new Callback<List<DonVi>>() {
            @Override
            public void onResponse(@NonNull Call<List<DonVi>> call, @NonNull Response<List<DonVi>> response) {
                if (response.isSuccessful())
                {
                    donViList = response.body();
                    SpinnerDonViAdapter spinnerDonViAdapter = new SpinnerDonViAdapter(requireContext(), R.layout.custom_spinner_selected, donViList);
                    spinner.setAdapter(spinnerDonViAdapter);
                    for (int i = 0; i < donViList.size(); i++) {
                        if (donViList.get(i).getMaDV() == MaDV)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DonVi>> call, @NonNull Throwable t) {

            }
        });
    }

    private void Load_Data_ChucDanh(Spinner spinner, int MaCD) {
        ApiServices.apiServices.get_list_chucdanh().enqueue(new Callback<List<ChucDanh>>() {
            @Override
            public void onResponse(@NonNull Call<List<ChucDanh>> call, @NonNull Response<List<ChucDanh>> response) {
                if (response.isSuccessful())
                {
                    chucDanhList = response.body();
                    SpinnerChucDanhAdapter spinnerChucDanhAdapter = new SpinnerChucDanhAdapter(requireContext(), R.layout.custom_spinner_selected, chucDanhList);
                    spinner.setAdapter(spinnerChucDanhAdapter);
                    for (int i = 0; i < chucDanhList.size(); i++) {
                        if (chucDanhList.get(i).getMaCD() == MaCD)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ChucDanh>> call, @NonNull Throwable t) {

            }
        });
    }

    private void Load_Data_PhanQuyen(Spinner spinner, int MaPQ) {
        ApiServices.apiServices.get_list_phanquyen().enqueue(new Callback<List<PhanQuyen>>() {
            @Override
            public void onResponse(@NonNull Call<List<PhanQuyen>> call, @NonNull Response<List<PhanQuyen>> response) {
                if (response.isSuccessful())
                {
                    phanQuyenList = response.body();
                    SpinnerPhanQuyenAdapter spinnerPhanQuyenAdapter = new SpinnerPhanQuyenAdapter(requireContext(), R.layout.custom_spinner_selected, phanQuyenList);
                    spinner.setAdapter(spinnerPhanQuyenAdapter);
                    for (int i = 0; i < phanQuyenList.size(); i++) {
                        if (phanQuyenList.get(i).getMaPQ() == MaPQ)
                        {
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PhanQuyen>> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        GetListNguoiDung();
    }
}