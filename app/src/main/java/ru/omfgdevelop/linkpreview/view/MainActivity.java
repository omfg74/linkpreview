package ru.omfgdevelop.linkpreview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.omfgdevelop.linkpreview.R;
import ru.omfgdevelop.linkpreview.interfaces.MainActivityContract;
import ru.omfgdevelop.linkpreview.presenter.MainAcivityPresenter;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    MainActivityContract.Presenter presenter;
    Button button;
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainAcivityPresenter(this);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        editText.setText("http://ya.ru");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonPressed(editText.getText().toString());
            }
        });
    }

    @Override
    public void showData(PreviewObject previewObject) {
        adapter.appendData(previewObject);
    }
}