//package com.example.ex1;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.Map;
//import java.util.UUID;
//
//public class CustomApplicationClass extends Application {
//    private static Context context;
//    private FirebaseFirestore db;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        context = getApplicationContext();
//        db = FirebaseFirestore.getInstance();
//        db.collection("defaults").document("user").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.getResult().exists()){
//                    Intent intent = new Intent(context, MainActivity.class);
//                    DocumentSnapshot document = task.getResult();
//                    Map<String, Object> documentData = document.getData();
//                    String name = (String)documentData.get("name");
//                    intent.putExtra("name","hello" + name +"!");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }else{
//                    Intent intent = new Intent(context, ConfigureNameActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
//        });
//
//
//
//    }
//}
