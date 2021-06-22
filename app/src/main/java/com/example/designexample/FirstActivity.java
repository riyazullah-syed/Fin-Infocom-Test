package com.example.designexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {
    TextInputLayout emailLayout,numberLayout;
    TextInputEditText emailedt,numberedt;


    private Button addBtn, saveBtn;
    private RecyclerView courseRV;
    private CourseAdapter adapter;
    private ArrayList<Bean> courseModalArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        emailLayout=(TextInputLayout)findViewById(R.id.emailLayout);
        numberLayout=(TextInputLayout)findViewById(R.id.numberLayout);
        emailedt=(TextInputEditText) findViewById(R.id.emailedt);
        numberedt=(TextInputEditText)findViewById(R.id.numberedt);


        addBtn = findViewById(R.id.idBtnAdd);
        saveBtn = findViewById(R.id.idBtnSave);
        courseRV = findViewById(R.id.idRVCourses);

        loadData();

        buildRecyclerView();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidEmail(emailedt.getText().toString())){
                    if(numberedt.getText().toString().length()==10){
                        courseModalArrayList.add(new Bean(emailedt.getText().toString(), numberedt.getText().toString()));
                    }else {
                        Toast.makeText(FirstActivity.this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FirstActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                }

                adapter.notifyItemInserted(courseModalArrayList.size());
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    private void buildRecyclerView() {
        adapter = new CourseAdapter(courseModalArrayList, FirstActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);

        courseRV.setLayoutManager(manager);

        courseRV.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("courses", null);

        Type type = new TypeToken<ArrayList<Bean>>() {}.getType();

        courseModalArrayList = gson.fromJson(json, type);

        if (courseModalArrayList == null) {
            courseModalArrayList = new ArrayList<>();
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(courseModalArrayList);

        editor.putString("courses", json);

        editor.apply();

        Toast.makeText(this, "Saved. ", Toast.LENGTH_SHORT).show();
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}