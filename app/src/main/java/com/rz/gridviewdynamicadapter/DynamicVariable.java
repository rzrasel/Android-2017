package com.rz.gridviewdynamicadapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rz Rasel on 2017-08-01.
 */

public class DynamicVariable {
    private Context context;
    private Map<String, Object> mapVar = new HashMap<String, Object>();

    public void onSet(Context argContext) {
        context = argContext;
        TextView tvViewOne = new TextView(context);
        TextView tvViewTwo = new TextView(context);
        mapVar.put("idTextOne", tvViewOne);
        mapVar.put("idTextTwo", tvViewTwo);
    }

    public void onGet() {
        View view = new View(context);
        for (Map.Entry<String, Object> entry : mapVar.entrySet()) {
            //String key = entry.getKey();
            //entry.getKey()
            //entry.getValue()
            Object obj = entry.getValue();
            System.out.println("‒‒‒‒|‑‑‑‑|――――――――――――|MAP_KEY|‒‒‒‒" + entry.getKey());
            System.out.println("‒‒‒‒|‑‑‑‑|――――――――――――|CLASS|‒‒‒‒" + obj.getClass());
            if (obj instanceof TextView) {
                System.out.println("‒‒‒‒|‑‑‑‑|――――――――――――|TEXT_VIEW|");
                String textViewID = entry.getKey();
                int resID = context.getResources().getIdentifier(textViewID, "id", context.getPackageName());
                TextView tvView = (TextView) view.findViewById(resID);
                mapVar.put(entry.getKey(), tvView);
            }
        }
        //map.get(key)
        //String value = (String) newMap.get("my_code");
    }
}
/*
Button[] buttons;
for(int i=0; i<buttons.length; i++) {
{
   String buttonID = "sound" + (i+1);

   int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
   buttons[i] = ((Button) findViewById(resID));
   buttons[i].setOnClickListener(this);
}
https://stackoverflow.com/questions/12227310/how-can-we-use-a-variable-in-r-id

//-----------------------------------------------------------------------------
DynamicVariable dynamicVariable = new DynamicVariable();
dynamicVariable.onSet(context);
dynamicVariable.onGet();
System.out.println("FRAG---------------------------->");
//-----------------------------------------------------------------------------
map.put(key, map.containsKey(key) ? map.get(key) + 1 : 1);

https://stackoverflow.com/questions/12227310/how-can-we-use-a-variable-in-r-id
https://stackoverflow.com/questions/29934340/how-to-fetch-a-r-id-in-java-using-a-variable-name
*/