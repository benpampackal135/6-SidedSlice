# 6-Sided Slice
### An App by Team Avalanche
* Odalys Chacon
* Faizan Haque
* Cody Krawczynski
* Benson Pampackal
* Nicolas Soto

## Description
6-Sided Slice is a turn-based combat game centered around dice rolls, where you and an opponent will take turns attacking and defending.

Our application provides a fun combat game that involves luck and strategy. Test your skills as you face off against your opponent. With the correct combination of attacking, defending, and evading, you can emerge victorious!

**Video demo of the app**: https://drive.google.com/file/d/1JHr8BI6hYud5vhU85xDgfiRKLMMgo7f3/view?usp=drive_link

## Downloading the App
1. **Clone the project into Android Studio**:
   
   - (Git must be installed and set up in Android Studio)
   - Copy the URL below
   ```sh
   https://github.com/UTSA-CS-3443/Fall-2024-004-Avalanche.git
   ```   
   - Open Android Studio
   - Go to File -> New -> Project from Version Control
   - Under Respository URL, select the Version control as Git and paste the copied URL
   - Click Clone
  
1. **Run the app in the Device Emulator**:
   
   - After cloning, wait for the Gradle project sync to finish
   - Once the Gradle project sync has finished, go to Run -> Run 'app' to start the app

## How to Play

**Getting Started:**

* Before playing the game, you have the option of using a random character or importing a custom character.
* Custom characters can be created in the Custom Character Creator.
* When creating a custom character, you can choose your character’s stat modifiers.

**Stat Modifiers:**

* Stat modifiers affect the dice roll.
* Stats include ATK, DEF, EVD.
* ATK affects the attack roll.
* DEF affects the defense roll.
* EVD affects the evade roll.
* For example, if you roll a 4 for attack and have +1 ATK, you will attack for a total of 5 damage.

**The Game:**

* The attacker rolls their dice to attack the other player.
* The defender must choose to defend or evade the attack.
* Defending will block incoming damage, but will always take a minimum of 1 damage. 
* Evading is a chance to dodge all damage or take full damage.

**Defense and Evasion:**

* Defense:
  * If defense < attack, then damage = attack - defense. 
  * If defense >= attack, then damage = 1.
* Evasion:
  * If evasion <= attack, then damage = attack. 
  * If evasion > attack, then damage = 0.

**Victory and Defeat:**

* You must deplete your opponent’s health to zero in order to win.
* But if your opponent gets your health to zero first, you will lose.
