package edu.utsa3443.avalanche;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import edu.utsa3443.avalanche.model.Character;

/**
 * The type Character activity.
 */
public class CharacterActivity extends AppCompatActivity {

    private TextView nameInput;
    private Button saveButton, backButton;
    private RecyclerView presetRecyclerView;
    private List<Character> presets;
    private int selectedPresetIndex = -1; // To track the selected preset

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        nameInput = findViewById(R.id.nameInput);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        presetRecyclerView = findViewById(R.id.presetRecyclerView);

        // Load preset characters
        presets = Character.getPresetCharacters();

        // Set up RecyclerView
        presetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        presetRecyclerView.setAdapter(new PresetRecyclerViewAdapter());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveCharacter());

        // Back button click listener
        backButton.setOnClickListener(v -> finish());
    }

    private void saveCharacter() {
        String name = nameInput.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name for your character!", Toast.LENGTH_SHORT).show();
            return;
        }

        Character characterToSave;
        if (selectedPresetIndex == -1) {
            //characterToSave = generateRandomCharacter(name);
            Toast.makeText(this, "Please select a preset!", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            characterToSave = presets.get(selectedPresetIndex);
        }

        // Write the character to a file
        String characterData = name + "," + characterToSave.health + "," +
                characterToSave.atk + "," + characterToSave.def + "," + characterToSave.evd;

        try (FileOutputStream fos = openFileOutput(name + ".txt", MODE_PRIVATE)) {
            fos.write(characterData.getBytes());
            Toast.makeText(this, "Character saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Return to the previous screen
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save character!", Toast.LENGTH_SHORT).show();
        }
    }

    /*private Character generateRandomCharacter(String name) {
        //generate random character stats
       int health = (int)(Math.random() * 100 + 1);
       int atk = (int)(Math.random() * 50 + 1);
       int def = (int)(Math.random() * 50 + 1);
       int evd = (int)(Math.random() * 50 + 1);
       return new Character(name,health, atk, def, evd);
    }*/

    /**
     * Load character character.
     *
     * @param context the context
     * @param name    the name
     * @return the character
     */
//method to load custom character from file
    //we can use this method to load saved character
    //and stats and load them onto the game
    public static Character loadCharacter(Context context, String name){
        try (FileInputStream fis = context.openFileInput(name + ".txt")) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String characterData = new String(buffer);

            // Split the string to get the character details
            String[] data = characterData.split(",");
            String characterName = data[0];
            int health = Integer.parseInt(data[1]);
            int atk = Integer.parseInt(data[2]);
            int def = Integer.parseInt(data[3]);
            int evd = Integer.parseInt(data[4]);

            return new Character(characterName, health, atk, def, evd);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if there was an error
        }
    }

    private class PresetRecyclerViewAdapter extends RecyclerView.Adapter<PresetRecyclerViewAdapter.PresetViewHolder> {

        @NonNull
        @Override
        public PresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.preset_item, parent, false);
            return new PresetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PresetViewHolder holder, int position) {
            Character preset = presets.get(position);
            holder.presetText.setText("Health: " + preset.health +
                    ", ATK: " + preset.atk +
                    ", DEF: " + preset.def +
                    ", EVD: " + preset.evd);
            holder.radioButton.setChecked(position == selectedPresetIndex);

            holder.radioButton.setOnClickListener(v -> {
                selectedPresetIndex = position;
                notifyDataSetChanged(); // Update the selection state
            });
        }

        @Override
        public int getItemCount() {
            return presets.size();
        }

        private class PresetViewHolder extends RecyclerView.ViewHolder {
            private TextView presetText;
            private RadioButton radioButton;

            /**
             * Instantiates a new Preset view holder.
             *
             * @param itemView the item view
             */
            public PresetViewHolder(@NonNull View itemView) {
                super(itemView);
                presetText = itemView.findViewById(R.id.presetText);
                radioButton = itemView.findViewById(R.id.radioButton);
            }
        }
    }
}