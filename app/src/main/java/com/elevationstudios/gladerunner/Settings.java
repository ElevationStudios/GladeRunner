package com.elevationstudios.gladerunner;

import android.util.Log;

import com.elevationstudios.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Settings {
public static boolean soundEnabled = true;

    public static int gold = 500;
    //keeps track of what we just earned
    public static int lastRunGold = 0;
    public static int lastRunDistance = 0;
    public static int[] highscores = new int[] { 250, 200, 150, 100, 50};

    //boughtitems = 5 items, start at 0
    public static int[] boughtItems = new int[] { 0, 0, 0};

    public static int ExtraGold;
    public static int maxHealth;
    public static int ExtraPoints;

    public static void load (FileIO files){
        BufferedReader in = null;
        try {
            Log.d("Settings.java", "Starting load");
            in = new BufferedReader(new InputStreamReader(files.readFile("gladerunner.settings")));

            soundEnabled = Boolean.parseBoolean(in.readLine());

            gold = Integer.parseInt(in.readLine());

            lastRunGold = Integer.parseInt(in.readLine());
            lastRunDistance = Integer.parseInt(in.readLine());

            for (int i = 0; i < highscores.length; i++){
                highscores[i] = Integer.parseInt(in.readLine());
            }

            for (int i = 0; i < boughtItems.length; i++){
                boughtItems[i] = Integer.parseInt(in.readLine());
            }
            Log.d("Settings.java", "Loaded file");
        } catch (IOException e){

            Log.d("Settings.java", "Failed to load file");
        } catch (NumberFormatException e){
        } finally {
            try{
                if(in!=null)
                    in.close();
            }catch (IOException e){}
        }
    } //end of load

    public static void save(FileIO files){
        BufferedWriter out = null;
        try{

            Log.d("Settings.java", "Starting save");
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile("gladerunner.settings")));

            out.write(Boolean.toString(soundEnabled));
            out.write("\n");

            out.write(Integer.toString(gold));
            out.write("\n");

            out.write(Integer.toString(lastRunGold));
            out.write("\n");
            out.write(Integer.toString(lastRunDistance));
            out.write("\n");

            for(int i = 0; i < highscores.length; i++){
                out.write(Integer.toString(highscores[i]));
                out.write("\n");
            }
            for(int i = 0; i < boughtItems.length; i++){
                out.write(Integer.toString(boughtItems[i]));
                out.write("\n");
            }
            Log.d("Settings.java", "Saved file");

        }catch(IOException e){

            Log.d("Settings.java", "Failed to save file");
        }finally {
            try{
                if(out!=null)
                    out.close();
            }catch (IOException e){}
        }
    }

    public static void addDistance(int score){
        for(int i = 0; i < highscores.length; i++){
            if (highscores[i]< score){
                for (int j = highscores.length-1; j > i; j--)
                    highscores[j] = highscores[j-1];
                highscores[i] = score;
                break;
            }
        }
    }

    public static void updateShopMenu(int num[]){
        if(boughtItems.length != num.length) {
            return;
        }
        for(int i = 0; i < boughtItems.length; i++) {
            boughtItems[i] = num[i];
        }
    }

    public static void updateLastRunDistance(int num){
        lastRunDistance = num;
    }

    public static void updateLastRunGold(int num){
        lastRunGold = num;
    }

    public static void setGold(int num){gold = num;}

    public static void addGold(int num){
        gold += num;
    }

    public static int getGold() {return gold;}

    public static void setMaxHealth(int num){ maxHealth = num; }

    public static int getMaxHealth(){return maxHealth;}

    public static void updateMaxHealth(){ setMaxHealth((15 * boughtItems[0]) + 100); }


    public static void setMoneyGain(int num) {ExtraGold = num;}

    public static int getMoneyGain() {return ExtraGold;}

    public static void updateMoneyGain() {setMoneyGain(boughtItems[1]);}

    public static void setExtraPoints(int num) {ExtraPoints = num; }

    public static int getExtraPoints() {return ExtraPoints;}

    public static void updateExtraPoints() {setExtraPoints((boughtItems[2] * 15));}

    public static void setSoundEnabled(boolean b){soundEnabled = b;}
    public static boolean getSoundEnabled(){return soundEnabled;}
}
