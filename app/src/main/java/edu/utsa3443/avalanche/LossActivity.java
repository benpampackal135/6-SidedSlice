package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class LossActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);

        ImageView defeatImage = findViewById(R.id.defeatImage);
        Button tryAgainButton = findViewById(R.id.tryAgainButton);
        Button backToMenuButton = findViewById(R.id.backToMenuButton);

        Animation defeatAnim = AnimationUtils.loadAnimation(this, R.anim.defeat_animations);
        defeatImage.startAnimation(defeatAnim);
        tryAgainButton.startAnimation(defeatAnim);
        backToMenuButton.startAnimation(defeatAnim);

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
