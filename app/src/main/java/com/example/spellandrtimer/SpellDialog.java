package com.example.spellandrtimer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class SpellDialog {

    private Context context;

    public SpellDialog(Context context) {
        this.context = context;
    }

    public void callDialog() {
        final Dialog dlg = new Dialog(context);
        dlg.setContentView(R.layout.spellchoose_dialog);
        dlg.show();
    }
}
