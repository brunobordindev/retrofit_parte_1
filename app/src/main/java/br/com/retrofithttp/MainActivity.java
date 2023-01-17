package br.com.retrofithttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.Bundle;

import br.com.retrofithttp.api.CEPService;
import br.com.retrofithttp.databinding.ActivityMainBinding;
import br.com.retrofithttp.model.CEP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //mexer no manisfet permissao
        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        binding.btn.setOnClickListener(view -> {

            recuperarCepRetrofit();

        });
    }

    private void recuperarCepRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCEP();

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {

                if (response.isSuccessful()){
                    CEP cep = response.body();
                    binding.textResult.setText(cep.getLogradouro());

                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }
}