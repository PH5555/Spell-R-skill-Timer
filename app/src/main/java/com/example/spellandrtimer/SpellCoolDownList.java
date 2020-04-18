package com.example.spellandrtimer;

public class SpellCoolDownList {
    int getCool(int s) {
        switch (s) {
            case 21: return 180000; //방어막
            case 1 : return 210000; //정화
            case 14 : return 180000; //점화
//            case 36 : return "";
//            case 35 : return "";
            case 3 : return 210000; //탈질
            case 4 : return 300000; //점멸
            case 6 : return 180000; //유체화
            case 7 : return 240000; //회복
            case 13 : return 240000; //총명
//            case 30 : return "to the king"; //왕을 향해! - 전설의 포로왕
//            case 31 : return "poro toss"; //포로 던지기 - 전설의 포로왕
//            case 33 : return "nexus siege";
//            case 34 : return "nexus siege";
            case 11 : return 90000; //강타 첫번째 15초 두번째 90초 90초에 한개씩 충전
            case 39 : return 48000; //표식 - 칼바람
            case 32 : return 48000; //표식 - 칼바람
            case 12 : return 420000; //순간이동 레벨에 따라 다름?
        }
        return 0;
    }
}
