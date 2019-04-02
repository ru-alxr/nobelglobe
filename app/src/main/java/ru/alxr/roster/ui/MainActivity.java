package ru.alxr.roster.ui;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.alxr.roster.R;
import ru.alxr.roster.ui.adapter.ContactsAdapter;
import ru.alxr.roster.ui.adapter.GroupObject;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    //private EditText mEditTextView;
    private RecyclerView mRecyclerView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, new MainViewModelFactory()).get(MainViewModel.class);
        //mEditTextView = findViewById(R.id.filter_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressView = findViewById(R.id.progress_bar_view);
        mViewModel.getModel().observe(this, this::onModelChange);
    }

    private void onModelChange(MainViewModel.Model model) {
        handleError(model.isError());
        handleLoading(model.isLoading());
        handleData(model.getContacts());
    }

    private void handleError(boolean error) {
        if (!error) return;
        mViewModel.onErrorHandled();
        new AlertDialog
                .Builder(this)
                .setMessage(R.string.error)
                .setNegativeButton(R.string.cancel, (dialog, which) -> finish())
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialog, which) -> mViewModel.onRetrySelected())
                .show();
    }

    private void handleLoading(boolean isLoading) {
        mProgressView.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    private void handleData(List<GroupObject> data) {
        if (data == null) return;
        ContactsAdapter adapter = new ContactsAdapter(data);
        adapter.expandAll();
        mRecyclerView.setAdapter(adapter);
    }

}