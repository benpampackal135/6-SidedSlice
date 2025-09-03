package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VictoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        ImageView victoryImage = findViewById(R.id.victoryImage);
        TextView wellPlayedText = findViewById(R.id.wellPlayedText);
        Button tryAgainButton = findViewById(R.id.tryAgainButton);
        Button backToMenuButton = findViewById(R.id.backToMenuButton);

        Animation victoryAnim = AnimationUtils.loadAnimation(this, R.anim.victory_animations);
        victoryImage.startAnimation(victoryAnim);
        wellPlayedText.startAnimation(victoryAnim);
        tryAgainButton.startAnimation(victoryAnim);
        backToMenuButton.startAnimation(victoryAnim);

        tryAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SelectCharacterActivity.class);
            startActivity(intent);
        });

        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
