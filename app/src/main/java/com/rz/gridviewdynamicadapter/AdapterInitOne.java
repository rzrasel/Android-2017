package com.rz.gridviewdynamicadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Rz Rasel on 2017-08-02.
 */

public class AdapterInitOne<T> extends ArrayAdapter<T> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<T> listAdapterItems; // = new ArrayList<ModelInit>();
    private OnFieldListenerHandler onFieldListenerHandler = null;
    private OnEventsListenerHandler onEventsListenerHandler = null;

    public AdapterInitOne(Context argContext, int argLayoutResourceId, ArrayList<T> argListItems) {
        super(argContext, argLayoutResourceId, argListItems);
        this.context = argContext;
        this.layoutResourceId = argLayoutResourceId;
        this.listAdapterItems = argListItems;
    }

    @Override
    public int getCount() {
        return listAdapterItems.size();
    }

    @Override
    public T getItem(int position) {
        return listAdapterItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //public void onSetListRowViewFields(OnFieldListenerHandler argOnFieldListenerHandler, ArrayList<RowViewHolder> argListRowViewFields) {
    public void onSetListRowViewFields(OnFieldListenerHandler argOnFieldListenerHandler) {
        onFieldListenerHandler = argOnFieldListenerHandler;
    }

    //Product p = getItem(position);
    @Override
    public View getView(int argPosition, View argConvertView, ViewGroup argParent) {
        View rowViewRoot = argConvertView;
        RootRowViewHolder rowViewHolder = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (argConvertView == null) {
            rowViewRoot = layoutInflater.inflate(layoutResourceId, argParent, false);
            rowViewHolder = new RootRowViewHolder(rowViewRoot);
            if (onFieldListenerHandler != null) {
                onFieldListenerHandler.onFieldInitialization();
            }
            //rowViewRoot.setTag(rowViewHolder);
            rowViewRoot.setTag(rowViewHolder);
        } else {
            rowViewHolder = (RootRowViewHolder) rowViewRoot.getTag();
        }
        Object item = getItem(argPosition);
        //if(list.get(argPosition)  instanceof A)
        if (onFieldListenerHandler != null) {
            //onFieldListenerHandler.onSetFieldValue();
            onFieldListenerHandler.onSetFieldValue(rowViewHolder, item);
        }
        return rowViewRoot;
    }

    //TEMP TEST
    public static class RootRowViewHolder {
        public TextView textView;

        RootRowViewHolder(View argRootView) {
            textView = (TextView) argRootView.findViewById(R.id.sysTvRowTitle);
        }
    }

    public interface OnFieldListenerHandler {
        public void onFieldInitialization();

        public void onSetFieldValue(RootRowViewHolder argRootRowViewHolder, Object argObject);
        //Customer cust = (Customer) pObject;
    }

    public interface OnEventsListenerHandler {
    }

    public enum APP_FIELDS {
        TEXT_VIEW("text_view"),
        IMAGE_VIEW("image_view");
        private String fieldType;

        APP_FIELDS(String argFieldType) {
            this.fieldType = argFieldType;
        }

        public String getFieldType() {
            return this.fieldType;
        }
    }

    //private Map<String, Object> mapAppFields = new HashMap<String, Object>();
    public static ArrayList<RowViewHolder> listRowViewFields = new ArrayList<RowViewHolder>();

    static class RowViewHolder {
        public static Object fieldObject;
        public static String fieldResourceId;
        public static String fieldExtra;

        public RowViewHolder() {
            //
        }

        public RowViewHolder(Object argFieldObject, String argFieldResourceId, String argFieldExtra) {
            fieldObject = argFieldObject;
            fieldResourceId = argFieldResourceId;
            fieldExtra = argFieldExtra;
        }

        public static Object getFieldObject() {
            return fieldObject;
        }

        public static void setFieldObject(Objects argFieldObject) {
            fieldObject = argFieldObject;
        }

        public static String getFieldResourceId() {
            return fieldResourceId;
        }

        public static void setFieldResourceId(String argFieldResourceId) {
            fieldResourceId = argFieldResourceId;
        }

        public static String getExtra() {
            return fieldExtra;
        }

        public static void setExtra(String argFieldExtra) {
            fieldExtra = argFieldExtra;
        }
    }
}
/*
https://stackoverflow.com/questions/36139024/arrayadapter-for-more-then-one-class-type
https://stackoverflow.com/questions/7114109/generics-with-arrayadapters
*/