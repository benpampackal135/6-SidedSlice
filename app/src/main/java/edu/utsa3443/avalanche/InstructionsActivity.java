package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Instructions activity.
 */
public class InstructionsActivity extends AppCompatActivity {
    private TextView instructionsText;
    private Button prevButton;
    private Button nextButton;
    private Button menuButton;
    private ImageView gameLogo;
    private int currentPage = 0;
    private final String[] pages = {
            "Overview:\n" +
                    "────────────────────────────────\n\n" +
                    "• 6-Sided Slice is a turn-based combat game\n" +
                    "  centered around dice rolls, where you and the\n" +
                    "  opponent will take turns attacking and defending.",

            "Getting Started:\n" +
                    "────────────────────────────────\n\n" +
                    "• Before playing the game, you have the\n" +
                    "  option of using a random character or\n" +
                    "  importing a custom character.\n\n" +
                    "• Custom characters can be created in\n" +
                    "  the Custom Character Creator.\n\n" +
                    "• When creating a custom character, you\n" +
                    "  can choose your character’s stat modifiers.",

            "Stat Modifiers:\n" +
                    "────────────────────────────────\n\n" +
                    "• Stat modifiers affect the dice roll.\n\n" +
                    "• Stats include ATK, DEF, EVD\n\n" +
                    "• ATK affects the attack roll\n\n" +
                    "• DEF affects the defense roll\n\n" +
                    "• EVD affects the evade roll.\n\n" +
                    "• For example, if you roll a 4 for attack\n" +
                    "  and have +1 ATK, you will attack for\n" +
                    "  a total of 5 damage.",

            "The Game:\n" +
                    "────────────────────────────────\n\n" +
                    "• The attacker rolls their dice to\n" +
                    "  attack the other player.\n\n" +
                    "• The defender must choose to\n" +
                    "  defend or evade the attack\n\n" +
                    "• Defending will block incoming damage,\n" +
                    "  but will always take a minimum of 1 damage.\n\n" +
                    "• Evading is a chance to dodge all damage\n" +
                    "  or take full damage.",

            "Defense and Evasion:\n" +
                    "────────────────────────────────\n\n" +
                    "• Defense:\n\n" +
                    "\t\t• If defense < attack,\n" +
                    "\t\t  then damage = attack - defense.\n\n" +
                    "\t\t• If defense >= attack,\n" +
                    "\t\t  then damage = 1\n\n" +
                    "• Evasion:\n\n" +
                    "\t\t• If evasion <= attack,\n" +
                    "\t\t  then damage = attack.\n\n" +
                    "\t\t• If evasion > attack,\n" +
                    "\t\t  then damage = 0",

            "Victory and Defeat:\n" +
                    "────────────────────────────────\n\n" +
                    "• You must deplete your opponent’s\n" +
                    "  health to zero in order to win.\n\n" +
                    "• But if your opponent gets your health\n" +
                    "  to zero first, you will lose."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instructions);

        instructionsText = findViewById(R.id.instructions_text);
        prevButton = findViewById(R.id.prev_button);
        nextButton = findViewById(R.id.next_button);
        menuButton = findViewById(R.id.menu_button);
        gameLogo = findViewById(R.id.game_logo);

        updateContent();

        prevButton.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                updateContent();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentPage < pages.length - 1) {
                currentPage++;
                updateContent();
            } else {
                returnToMenu();
            }
        });

        menuButton.setOnClickListener(v -> returnToMenu());
    }

    private void updateContent() {
        instructionsText.setText(pages[currentPage]);
        prevButton.setVisibility(currentPage == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setText(currentPage == pages.length - 1 ? "Finish" : "Next");
        gameLogo.setVisibility(currentPage == 0 ? View.VISIBLE : View.GONE);
    }

    private void returnToMenu() {
        startActivity(new Intent(InstructionsActivity.this, MainActivity.class));
        finish();
    }
}
