package com.glaucustask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glaucustask.Adapter.Adapter;
import com.glaucustask.utility.SSlUtilsw;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    RecyclerView mailRecyclerView;
    FloatingActionButton addMail;

    private ArrayList<Data> mailListView;
    private Adapter adapter;
    private Api service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMail = findViewById(R.id.btn_add_mail);

        mailRecyclerView = findViewById(R.id.mail_recyclerView);
        mailListView = new ArrayList<>();
        mailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(mailListView, this);
        adapter.setOnItemClickListener(this);
        mailRecyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(SSlUtilsw.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Api.class);

        addMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMail();
            }
        });

        getMailList();
    }

    private void getMailList() {
        Call<List<Data>> response = service.getMailList();
        response.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                List<Data> list = response.body();
                mailListView.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.e("Error in getMailList()", "Error -> " + t.getMessage());
            }
        });
    }

    private void addMail() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final View dialogView = getLayoutInflater().inflate(R.layout.mail_add_alert, null, false);
        alertDialog.setView(dialogView);

        final EditText enterMail = dialogView.findViewById(R.id.edit_enter_mail);
        Button saveMail = dialogView.findViewById(R.id.btn_mail_save);
        saveMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = enterMail.getText().toString();
                boolean res = validateEmailAddress(mail);

                if (res) {
                    Data addMail = new Data(res, mail);
                    onSaveClick(addMail);
                    alertDialog.dismiss();
                } else
                    Toast.makeText(getApplicationContext(), "Check E-mail again", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    public void onSaveClick(Data data) {
        Call<Data> response = service.createMail(data);
        response.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() != null) {
                    mailListView.add(response.body());
                    adapter.notifyItemInserted(mailListView.size() - 1);
                } else
                    Toast.makeText(MainActivity.this, "Error in saving email", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("Error in onSaveClick()", "Error -> " + t.getMessage());
            }
        });
    }

    @Override
    public void onDeleteClick(int id, final int index) {
        Call<Boolean> response = service.deleteId(id);
        response.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d("Response Code", String.valueOf(response.code()));
                boolean deleted = response.body();
                if (deleted) {
                    mailListView.remove(index);
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(MainActivity.this, "Error in deleting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Error in deleteMail()", "Error -> " + t.getMessage());
            }
        });
    }

    @Override
    public void onEditClick(final Data data) {
        Call<Data> response = service.updateMail(data.getIdtableEmail(), data);
        response.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.d("Response Code", String.valueOf(response.code()));
                if (response.body() != null) {
                    int index = getIndexById(mailListView, data.getIdtableEmail());
                    mailListView.get(index).setTableEmailEmailAddress(data.getTableEmailEmailAddress());
                    mailListView.get(index).setTableEmailValidate(data.getTableEmailValidate());
                    adapter.notifyDataSetChanged();
                } else
                    Toast.makeText(MainActivity.this, "Error in Updating", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("Error in onEditClick()", "Error -> " + t.getMessage());
            }
        });
    }

    boolean validateEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    int getIndexById(List<Data> mailListView, int id) {
        int index = -1;
        for (int i = 0; i < mailListView.size(); i++) {
            if (mailListView.get(i).getIdtableEmail() == id) {
                index = i;
                break;
            }
        }

        return index;
    }
}
