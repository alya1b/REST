package com.example.component.column;

import com.example.component.Column;

public class TypeCharInvl extends Column {
    public TypeCharInvl(String name) {
        super(name);
        this.type = TypeColumn.CHARINVL.name();
    }

    @Override
    public boolean validate(String value) {
        String[] buf = value.replace(" ", "").split(",");
        if (buf.length == 2 && buf[0].length() == 1 && buf[1].length() == 1) {
            char a = buf[0].charAt(0);
            char b = buf[1].charAt(0);
            return a < b;
        }
        return false;
    }
}