package ru.alxr.roster.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.alxr.roster.ui.adapter.GroupObject;
import ru.alxr.roster.ui.adapter.PersonObject;
import ru.alxr.roster.repository.pojo.Contacts;
import ru.alxr.roster.repository.pojo.Group;
import ru.alxr.roster.repository.pojo.Person;
import ru.alxr.roster.repository.IContactsRepository;
import ru.alxr.roster.utils.CustomDisposableSingleObserver;

class MainViewModel extends ViewModel {

    private final IContactsRepository mContactsRepository;

    private Disposable mDisposable;

    MainViewModel(IContactsRepository repository) {
        mContactsRepository = repository;
        getContacts();
    }

    private MutableLiveData<Model> mLiveData = new MutableLiveData<Model>() {
        {
            setValue(new Model.Builder().get());
        }
    };

    MutableLiveData<Model> getModel() {
        return mLiveData;
    }

    void onErrorHandled() {
        Model model = mLiveData.getValue();
        Model updated = new Model.Builder(model).setIsError(false).get();
        mLiveData.setValue(updated);
    }

    void onRetrySelected() {
        getContacts();
    }

    private void getContacts() {
        disposeIt(mDisposable);
        Model model = mLiveData.getValue();
        Model updated = new Model.Builder(model).setIsLoading(true).get();
        mLiveData.setValue(updated);
        mDisposable = mContactsRepository
                .get()
                .flatMap((Function<Contacts, SingleSource<List<GroupObject>>>) contacts -> {
                    List<GroupObject> list = new ArrayList<>(contacts.getGroups().size());
                    if (contacts.getGroups() == null) return Single.just(Collections.emptyList());
                    for (Group group : contacts.getGroups()) {
                        List<Person> persons = group.getPeople();
                        List<PersonObject> items = new ArrayList<>(persons.size());
                        for (Person p : persons) {
                            items.add(new PersonObject(p));
                        }
                        list.add(new GroupObject(group.getGroupName(), items));
                    }
                    return Single.just(list);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableSingleObserver<List<GroupObject>>(
                                this::onLoadResult,
                                this::onLoadResult
                        )
                );
    }

    private void onLoadResult(List<GroupObject> contacts) {
        Model model = mLiveData.getValue();
        Model updated = new Model.Builder(model).setIsLoading(false).setContacts(contacts).get();
        mLiveData.setValue(updated);
    }

    @SuppressWarnings("unused")
    private void onLoadResult(Throwable throwable) {
        //throwable.printStackTrace();
        Model model = mLiveData.getValue();
        Model updated = new Model.Builder(model).setIsLoading(false).setIsError(true).get();
        mLiveData.setValue(updated);
    }

    private void disposeIt(Disposable disposable) {
        if (disposable == null) return;
        disposable.dispose();
    }

    static class Model {
        private boolean isLoading;
        private List<GroupObject> mContacts;
        private boolean isError;

        boolean isLoading() {
            return isLoading;
        }

        List<GroupObject> getContacts() {
            return mContacts;
        }

        boolean isError() {
            return isError;
        }

        static class Builder {
            Builder() {
                model = new Model();
            }

            Builder(Model source) {
                model = source;
            }

            private Model model;

            Builder setIsLoading(boolean value) {
                model.isLoading = value;
                return this;
            }

            Builder setIsError(boolean value) {
                model.isError = value;
                return this;
            }

            Builder setContacts(List<GroupObject> value) {
                model.mContacts = value;
                return this;
            }

            Model get() {
                return model;
            }
        }
    }
}