package ru.omfgdevelop.linkpreview.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.omfgdevelop.linkpreview.R;
import ru.omfgdevelop.linkpreview.interfaces.AdapterItemNumberCallBack;
import ru.omfgdevelop.linkpreview.interfaces.MainActivityContract;
import ru.omfgdevelop.linkpreview.presenter.MainAcivityPresenter;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View, AdapterItemNumberCallBack {
//This sample app was writen in a day.
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
        adapter = new RecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        presenter.onButtonPressed("http://ya.ru");
        presenter.onButtonPressed("http://google.com");
        presenter.onButtonPressed("http://github.com");
        presenter.onButtonPressed("http://gitlab.com");
        presenter.onButtonPressed("http://mail.ru");
//        presenter.onButtonPressed("http://1с-bitrix.ru");
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

    @Override
    public void changeText() {
        editText.setText("");
    }

    @Override
    public void addData(int itemNumber, PreviewObject previewObject) {
        adapter.addDataToItem(itemNumber,previewObject);
    }

    @Override
    public void removeDataItem(int itemNumber) {
        adapter.removeItem(itemNumber);
    }

    @Override
    public void giveItemNumber(int i, PreviewObject previewObject) {
        presenter.provideNumber(i, previewObject);
    }
}
