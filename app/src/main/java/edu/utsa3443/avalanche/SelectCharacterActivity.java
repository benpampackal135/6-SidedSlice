package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SelectCharacterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_character);

        Button importCustomButton = findViewById(R.id.importCustomButton);
        Button randomCharacterButton = findViewById(R.id.randomCharacterButton);
        Button backButton = findViewById(R.id.backButton);

        importCustomButton.setOnClickListener(view -> startActivity(new Intent(this, ImportedCharactersActivity.class)));
        randomCharacterButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("character", "random");
            startActivity(intent);
        });
        backButton.setOnClickListener(view -> finish());
    }
}