package com.example.spellandrtimer;

public class SpellList {
    String getSpell(int s) {
        switch (s) {
            case 21: return "barrier";
            case 1 : return "cleanse";
            case 14 : return "ignite";
            case 36 : return "";
            case 35 : return "";
            case 3 : return "exhaust";
            case 4 : return "flash";
            case 6 : return "ghost";
            case 7 : return "heal";
            case 13 : return "clarity";
            case 30 : return "to the king"; //왕을 향해! - 전설의 포로왕
            case 31 : return "poro toss"; //포로 던지기 - 전설의 포로왕
            case 33 : return "nexus siege";
            case 34 : return "nexus siege";
            case 11 : return "smite";
            case 39 : return "ultra"; //표식 - 칼바람
            case 32 : return "mark"; //표식 - 칼바람
            case 12 : return "teleport";
        }
        return "";
    }
}
