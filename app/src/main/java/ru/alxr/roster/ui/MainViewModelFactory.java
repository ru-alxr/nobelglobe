package ru.alxr.roster.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.alxr.roster.repository.ContactsRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainViewModel.class) return (T) new MainViewModel(new ContactsRepository());
        throw new RuntimeException();
    }

}