package com.example.my_schedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int     skdvhks = 1010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent= new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
        }
    }

    public void log(View view) {
        List<AuthUI.IdpConfig> provi = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()

        );
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provi)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setLogo(R.drawable.hello).build();
        startActivityForResult(intent,
                skdvhks);
    }

    @Override
    public void onActivityResult(int requestCode,int resultcode ,@Nullable Intent data) {
        super.onActivityResult(requestCode,resultcode, data);
        if(requestCode == skdvhks){
            if(resultcode == RESULT_OK){
                FirebaseUser user=  FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, "Its working Properly!", Toast.LENGTH_SHORT).show();
                if(user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                    Toast.makeText(this, "Welcome new User", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Welcome back again!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            } else{
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if(response == null){
                    Toast.makeText(this, "The user has cancelled the sign in Request!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "OnActivity Result: ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}