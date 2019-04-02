package ru.alxr.roster.repository;

import io.reactivex.Single;
import ru.alxr.roster.repository.pojo.Contacts;

public interface IContactsRepository {

    Single<Contacts> get();

}