package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements View

        .OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        setUpButtons();
    }

    private void setUpButtons(){
        int[] buttonIDs = {R.id.how_to,R.id.play,R.id.custom_char};
        Button howToButton = findViewById(R.id.how_to);
        howToButton.setText("Instructions");
        howToButton.setOnClickListener(this);

        Button playButton = findViewById(R.id.play);
        playButton.setText("Play");
        playButton.setOnClickListener(this);

        Button customCharButton = findViewById(R.id.custom_char);
        customCharButton.setText("Custom Character");
        customCharButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button buttonClicked = (Button) view;
        if (buttonClicked.getText().equals("Instructions")){
           Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
           startActivity(intent);
        } else  if (buttonClicked.getText().equals("Play")) {
            Intent intent = new Intent(MainActivity.this, SelectCharacterActivity.class);
            startActivity(intent);
        } else if (buttonClicked.getText().equals("Custom Character")) {
            Intent intent = new Intent(MainActivity.this, CharacterActivity.class);
            startActivity(intent);
        }
    }

}