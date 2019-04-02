package ru.alxr.roster.repository.pojo;

import com.squareup.moshi.Json;

public class Person {

    @Json(name = "firstName")
    private String firstName;
    @Json(name = "lastName")
    private String lastName;
    @Json(name = "statusIcon")
    private String statusIcon;
    @Json(name = "statusMessage")
    private String statusMessage;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatusIcon() {
        return statusIcon;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
/*
{
               "firstName":"MARY",
               "lastName":"CAIN",
               "statusIcon":"online",
               "statusMessage":""
            }
 */