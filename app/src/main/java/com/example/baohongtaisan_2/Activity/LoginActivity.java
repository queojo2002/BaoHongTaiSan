package com.example.baohongtaisan_2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baohongtaisan_2.Activity.Admin.AdminHomeActivity;
import com.example.baohongtaisan_2.Activity.User.HomeActivity;
import com.example.baohongtaisan_2.Api.ApiServices;
import com.example.baohongtaisan_2.Model.ObjectReponse;
import com.example.baohongtaisan_2.Model.Object_Add;
import com.example.baohongtaisan_2.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private GoogleSignInClient client;
    private GoogleSignInAccount account;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        btnLogin = findViewById(R.id.btnLogin);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        client = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, 99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                progressDialog.show();
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            if (account.getEmail().contains("@student.tdmu.edu.vn")) {

                                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        _Them_NguoiDung(account.getDisplayName(), account.getEmail(), 2, 5, 7, FirebaseAuth.getInstance().getCurrentUser().getUid().toString(), task.getResult().toString());
                                    }
                                });



                            } else {
                                Toast.makeText(LoginActivity.this, "Bạn phải đăng nhập bằng email của trường cấp !!!", Toast.LENGTH_SHORT).show();
                                client.signOut();
                                FirebaseAuth.getInstance().signOut();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập không thành công!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }


    public void _Them_NguoiDung(String HoVaTen, String Email, int MaPQ, int MaDV, int MaCD, String UID, String Token) {
        ApiServices.apiServices.add_new_nguoidung(HoVaTen, Email, MaPQ, MaDV, MaCD, UID, Token).enqueue(new Callback<Object_Add>() {
            @Override
            public void onResponse(Call<Object_Add> call, Response<Object_Add> response) {
                Object_Add _object_add = response.body();
                if (_object_add.getCode() == 1) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, "Có lỗi khi đăng nhập !!", Toast.LENGTH_SHORT).show();
                    client.signOut();
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onFailure(Call<Object_Add> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Có lỗi khi đăng nhập !", Toast.LENGTH_SHORT).show();
            }
        });
    }


}