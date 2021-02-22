package com.example.project_test.model.utils.Network;

import com.example.project_test.model.utils.notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers( {

            "Content-Type:application/json",
            "Authorization:key=AAAA0LGZwHo:APA91bFagUT8sONW2R3UyUqeLaviWMdBeFXglx5myHVIlEtXwUwdbU87c47bUgVmzGn9VZU-3duy3xFHPgES1izslrY246IrleZVnnubWfvYm_HO4nyheuVW4o9xvkT31oRmrWaN671r"

    }

    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
