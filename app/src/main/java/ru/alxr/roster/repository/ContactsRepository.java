package ru.alxr.roster.repository;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.alxr.roster.BuildConfig;
import ru.alxr.roster.repository.pojo.Contacts;

public class ContactsRepository implements IContactsRepository {

    public ContactsRepository() {
        // I could implement DI, something like Dagger 2 or Koin (for Kotlin)
        // but I it will take too much time and it is so boring...
    }

    private Contacts cache;
    private Moshi moshi;
    private JsonAdapter<Contacts> adapter;

    private JsonAdapter<Contacts> getAdapter() {
        if (adapter == null) {
            adapter = getMoshi().adapter(Contacts.class);
        }
        return adapter;
    }

    private Moshi getMoshi() {
        if (moshi == null) {
            moshi = new Moshi
                    .Builder()
                    .build();
        }
        return moshi;
    }

    @Override
    public Single<Contacts> get() {
        if (cache == null) {
            return Single
                    .fromCallable(() -> {
                        OkHttpClient client = getClient();
                        Request request = new Request
                                .Builder()
                                .url(BuildConfig.URL)
                                .header("Content-Type", "application/json")
                                .get()
                                //... some headers
                                // and all that stuff
                                .build();
                        Response response = client.newCall(request).execute();
                        ResponseBody body = response.body();
                        if (body == null) throw new NullPointerException("Response has no body");
                        String rawAnswer = body.string();
                        Log.d("DEBUG_LOG", "Response is <" + rawAnswer + ">");
                        cache = getAdapter().fromJson(rawAnswer);
                        return cache;
                    });
        } else {
            return Single.just(cache);
        }
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

}