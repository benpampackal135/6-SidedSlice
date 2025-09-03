package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

import edu.utsa3443.avalanche.CharacterActivity;
import edu.utsa3443.avalanche.model.Character;

/**
 * The type Imported characters activity.
 */
public class ImportedCharactersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imported_characters);

        ListView characterListView = findViewById(R.id.characterListView);
        Button backButton = findViewById(R.id.backButton);

        ArrayList<String> characterFiles = loadCharacterFiles();
        if (characterFiles.isEmpty()) {
            Toast.makeText(this, "No custom characters found.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, characterFiles);
            characterListView.setAdapter(adapter);

            characterListView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedCharacter = characterFiles.get(position);
                Character customCharacter = CharacterActivity.loadCharacter(this, selectedCharacter);

                if (customCharacter != null) {
                    Intent intent = new Intent(this, GameActivity.class);
                    intent.putExtra("characterName", customCharacter.name);
                    intent.putExtra("characterHealth", customCharacter.health);
                    intent.putExtra("characterAtk", customCharacter.atk);
                    intent.putExtra("characterDef", customCharacter.def);
                    intent.putExtra("characterEvd", customCharacter.evd);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Failed to load character.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        backButton.setOnClickListener(view -> finish());
    }

    private ArrayList<String> loadCharacterFiles() {
        ArrayList<String> files = new ArrayList<>();
        File dir = getFilesDir();
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".txt")) {
                files.add(file.getName().replace(".txt", ""));
            }
        }
        return files;
    }
}