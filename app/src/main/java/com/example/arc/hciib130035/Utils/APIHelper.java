package com.example.arc.hciib130035.Utils;

import com.example.arc.hciib130035.models.Category;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.models.UserImage;

import java.util.List;

/**
 * Created by arc on 29/12/16.
 */

public class APIHelper {
    public static APIHelper apiHelper = new APIHelper();

    private static User loggedUser;

    private static List<Category> globalCatList;

    private static UserImage loggedUserImage;








    public static List<Category> getGlobalCatList() {
        return globalCatList;
    }

    public static void setGlobalCatList(List<Category> globalCatList) {
        APIHelper.globalCatList = globalCatList;
    }

    public static UserImage getLoggedUserImage() {
        return loggedUserImage;
    }

    public static void setLoggedUserImage(UserImage loggedUserImage) {
        APIHelper.loggedUserImage = loggedUserImage;
    }

    public User getUser() {
        return loggedUser;
    }

    public void setUser(User user) {
        this.loggedUser = user;
    }

    public int getLoggedUserId() {return this.loggedUser.getUserId(); }


    public static APIHelper getApiHelper() {
        return apiHelper;
    }

    public void setApiHelper(APIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }


}
