package com.example.personalprofile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity presents personal information, academic details, about me statement,
 * contact info, technical skills, and achievements in a sleek monochrome card layout.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupContactActions();
    }

    /**
     * Sets up click listeners for email, phone, and GitHub contact items.
     */
    private void setupContactActions() {
        View btnEmail = findViewById(R.id.btnEmail);
        View btnPhone = findViewById(R.id.btnPhone);
        View btnGithub = findViewById(R.id.btnGithub);

        if (btnEmail != null) {
            btnEmail.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + getString(R.string.contact_email)));
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email..."));
                } catch (Exception e) {
                    Toast.makeText(this, "Email: " + getString(R.string.contact_email), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnPhone != null) {
            btnPhone.setOnClickListener(v -> {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + getString(R.string.contact_phone)));
                try {
                    startActivity(dialIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "Contact: " + getString(R.string.contact_phone), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnGithub != null) {
            btnGithub.setOnClickListener(v -> {
                String githubUrl = "https://" + getString(R.string.contact_github);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                try {
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(this, "GitHub: " + getString(R.string.contact_github), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
