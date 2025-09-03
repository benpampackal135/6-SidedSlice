package edu.utsa3443.avalanche;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

import edu.utsa3443.avalanche.model.Character;

/**
 * The type Game activity.
 */
public class GameActivity extends AppCompatActivity {

    private TextView actionText, playerStats, opponentStats, playerFinalNumberText, opponentFinalNumberText, damageText;
    private ImageView playerDiceImage, opponentDiceImage;
    private Button rollButton, defendButton, evadeButton;

    private Character playerCharacter;
    private Character opponentCharacter;
    private String playerName = "Player";
    private String opponentName = "Opponent";

    private boolean isPlayerTurn = true; // Track turn-based logic
    private Random random = new Random();

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize Views
        actionText = findViewById(R.id.actionText);
        playerStats = findViewById(R.id.playerStats);
        opponentStats = findViewById(R.id.opponentStats);
        playerFinalNumberText = findViewById(R.id.playerFinalNumberText);
        opponentFinalNumberText = findViewById(R.id.opponentFinalNumberText);
        damageText = findViewById(R.id.damageText);
        playerDiceImage = findViewById(R.id.playerDiceImage);
        opponentDiceImage = findViewById(R.id.opponentDiceImage);
        rollButton = findViewById(R.id.rollButton);
        defendButton = findViewById(R.id.defendButton);
        evadeButton = findViewById(R.id.evadeButton);

        // Initialize characters
        initializeCharacters();

        // Update stats UI
        updateStats();

        // Set up roll button for player's turn
        rollButton.setOnClickListener(v -> handleRoll());

        actionText.setText("");
        damageText.setText("");
        playerFinalNumberText.setText("Player:\n");
        opponentFinalNumberText.setText("Opponent:\n");
        playerDiceImage.setImageResource(R.drawable.dice_blank);
        opponentDiceImage.setImageResource(R.drawable.dice_blank);
        rollButton.setText("Roll to Begin");
    }

    private void initializeCharacters() {
        // Get random preset stats for both player and opponent
        List<Character> presets = Character.getPresetCharacters();
        Intent intent = getIntent();
        if (intent.hasExtra("characterName")) {
            String name = intent.getStringExtra("characterName");
            int health = intent.getIntExtra("characterHealth", 10);
            int atk = intent.getIntExtra("characterAtk", 0);
            int def = intent.getIntExtra("characterDef", 0);
            int evd = intent.getIntExtra("characterEvd", 0);
            playerCharacter = new Character(name, health, atk, def, evd);
        } else {
            // Default to a random preset character
            playerCharacter = presets.get(random.nextInt(presets.size()));
        }
        //select another random character but making sure its not the same as playerCharacter
        Character opponent = null;
        do{
            opponent = presets.get(random.nextInt(presets.size()));
        }
        while(opponent == playerCharacter);

        opponentCharacter = opponent;

    }

    private void updateStats() {
        playerStats.setText(playerName + "\n\n" + playerCharacter.toString());
        opponentStats.setText(opponentName + "\n\n" + opponentCharacter.toString());
    }

    private void handleRoll() {
        playerDiceImage.setImageResource(R.drawable.dice_blank);
        opponentDiceImage.setImageResource(R.drawable.dice_blank);
        playerFinalNumberText.setText("Player:\n");
        opponentFinalNumberText.setText("Opponent:\n");
        if (isPlayerTurn) {
            actionText.setText("");
            damageText.setText("");
            rollButton.setVisibility(View.GONE);
            damageText.setText("Rolling...");
            new Handler().postDelayed(() -> {
                int roll = random.nextInt(6) + 1; // Roll a 6-sided die
                showDiceImage(roll, playerDiceImage);
                int attackValue = roll + playerCharacter.atk;
                playerFinalNumberText.setText("Player rolled:\n" + roll + " + ATK(" + playerCharacter.atk + ") = " + attackValue);
                actionText.setText("You attack for " + attackValue + "!");
                damageText.setText("");
                new Handler().postDelayed(() -> {
                    damageText.setText("Opponent is choosing to defend or evade...");
                    //new Handler().postDelayed(() -> {
                        // Opponent chooses to defend or evade randomly
                        boolean opponentEvades = 3 + opponentCharacter.evd > attackValue ;
                        if (opponentEvades) {
                            handleOpponentEvasion(attackValue);
                        } else {
                            handleOpponentDefense(attackValue);
                        }
                    //}, 2000);
                }, 2000);
            }, 2000);

        } else {
            actionText.setText("");
            damageText.setText("");
            rollButton.setVisibility(View.GONE);
            damageText.setText("Opponent is rolling...");
            new Handler().postDelayed(() -> {
                int roll = random.nextInt(6) + 1; // Roll a 6-sided die
                showDiceImage(roll, opponentDiceImage);
                int attackValue = roll + opponentCharacter.atk;
                opponentFinalNumberText.setText("Opponent rolled:\n" + roll + " + ATK(" + opponentCharacter.atk + ") = " + attackValue);
                actionText.setText("Opponent attacks for " + attackValue + "!");
                damageText.setText("Defend or Evade?");

                // Prompt player to defend or evade
                defendButton.setVisibility(View.VISIBLE);
                evadeButton.setVisibility(View.VISIBLE);

                defendButton.setOnClickListener(v -> handlePlayerDefense(attackValue));
                evadeButton.setOnClickListener(v -> handlePlayerEvasion(attackValue));
            }, 2000);
        }
    }

    private void handleOpponentDefense(int attackValue) {
        damageText.setText("Opponent is rolling for defense...");
        new Handler().postDelayed(() -> {
            int roll = random.nextInt(6) + 1;
            int defenseValue = roll + opponentCharacter.def;
            showDiceImage(roll, opponentDiceImage);
            opponentFinalNumberText.setText("Opponent rolled:\n" + roll + " + DEF(" + opponentCharacter.def + ") = " + defenseValue);
            actionText.setText("Opponent defends for " + defenseValue + "!");

            int damage = Math.max(attackValue - defenseValue, 1);
            opponentCharacter.health -= damage;
            damageText.setText("Opponent took " + damage + " damage!");

            rollButton.setVisibility(View.VISIBLE);

            rollButton.setText("End Turn");
            endTurn();
        }, 2000);
    }

    private void handleOpponentEvasion(int attackValue) {
        damageText.setText("Opponent is rolling for evasion...");
        new Handler().postDelayed(() -> {
            int roll = random.nextInt(6) + 1;
            int evadeValue = roll + opponentCharacter.evd;
            showDiceImage(roll, opponentDiceImage);
            opponentFinalNumberText.setText("Opponent rolled:\n" + roll + " + EVD(" + opponentCharacter.evd + ") = " + evadeValue);
            actionText.setText("Opponent evades for " + evadeValue + "!");

            if (attackValue >= evadeValue) {
                opponentCharacter.health -= attackValue;
                damageText.setText("Opponent failed to evade and took " + attackValue + " damage!");
            } else {
                damageText.setText("Opponent successfully evaded!");
            }

            rollButton.setVisibility(View.VISIBLE);

            rollButton.setText("End Turn");
            endTurn();
        }, 2000);
    }

    private void handlePlayerDefense(int attackValue) {
        defendButton.setVisibility(View.GONE);
        evadeButton.setVisibility(View.GONE);
        damageText.setText("Rolling for defense...");
        new Handler().postDelayed(() -> {
            int roll = random.nextInt(6) + 1;
            int defenseValue = roll + playerCharacter.def;
            showDiceImage(roll, playerDiceImage);
            playerFinalNumberText.setText("Player rolled:\n" + roll + " + DEF(" + playerCharacter.def + ") = " + defenseValue);
            actionText.setText("You defend for " + defenseValue + "!");

            int damage = Math.max(attackValue - defenseValue, 1);
            playerCharacter.health -= damage;
            damageText.setText("You took " + damage + " damage!");

            rollButton.setVisibility(View.VISIBLE);

            rollButton.setText("Roll Dice");
            endTurn();
        }, 2000);
    }

    private void handlePlayerEvasion(int attackValue) {
        defendButton.setVisibility(View.GONE);
        evadeButton.setVisibility(View.GONE);
        damageText.setText("Rolling for evasion...");
        new Handler().postDelayed(() -> {
            int roll = random.nextInt(6) + 1;
            int evadeValue = roll + playerCharacter.evd;
            showDiceImage(roll, playerDiceImage);
            playerFinalNumberText.setText("Player rolled:\n" + roll + " + EVD(" + playerCharacter.evd + ") = " + evadeValue);
            actionText.setText("You evade for " + evadeValue + "!");

            if (attackValue >= evadeValue) {
                playerCharacter.health -= attackValue;
                damageText.setText("You failed to evade and took " + attackValue + " damage!");
            } else {
                damageText.setText("You successfully evaded!");
            }

            defendButton.setVisibility(View.GONE);
            evadeButton.setVisibility(View.GONE);
            rollButton.setVisibility(View.VISIBLE);

            rollButton.setText("Roll Dice");
            endTurn();
        }, 2000);
    }

    private void showDiceImage(int roll, ImageView diceImage) {
        int resId = getResources().getIdentifier("dice_" + roll, "drawable", getPackageName());
        diceImage.setImageResource(resId);
    }

    private void endTurn() {
        updateStats();

        if (playerCharacter.health <= 0) {
            rollButton.setVisibility(View.GONE);
            startActivity(new Intent(this, LossActivity.class));
            finish();
        } else if (opponentCharacter.health <= 0) {
            rollButton.setVisibility(View.GONE);
            startActivity(new Intent(this, VictoryActivity.class));
            finish();
        } else {
            isPlayerTurn = !isPlayerTurn;
        }
    }
}