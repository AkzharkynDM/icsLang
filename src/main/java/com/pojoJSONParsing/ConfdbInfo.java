package com.pojoJSONParsing;
import java.util.ArrayList;
import java.util.List;

//Todo: OUTDATED
public class ConfdbInfo{
    private String key;
    private Object value;
    private int depth;
    private ConfdbInfo parent;
    private List<ConfdbInfo> children;

    public ConfdbInfo(String text,int depth){
        this.key=text;
        this.value = new ArrayList<ConfdbInfo>();
        this.depth=depth;
        this.parent=null;
    }

    public ConfdbInfo(String text, int depth, ConfdbInfo parent){
        //this.text=text;
        this.depth=depth;
        if (text.contains("=")){
            String[] parts = text.split("=");
            this.key = parts[0];
            this.value = parts[1];
        } else {
            this.key=text;
            this.value = new ArrayList<ConfdbInfo>();
        }
        this.parent=parent;
    }

    public String getText() {
        return key;
    }

    public void setText(String text) {
        this.key = text;
    }

    public void addChild(ConfdbInfo child){
        if (children == null) {
            children = new ArrayList<ConfdbInfo>();
        }
        if (child != null) {
            children.add(child);
        }
    }
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Object getChildren() {
        if (value == null)
        {
            value = new ArrayList<ConfdbInfo>();
        }
        return value;
    }

    public void setChildren(List<ConfdbInfo> newChildren) {
        if (newChildren == null) {
            value = newChildren;
        } else {
            if (value == null) {
                value = new ArrayList<ConfdbInfo>();
            }
            for (ConfdbInfo child : newChildren) {
                this.addChild(child);
            }
        }
    }

    public ConfdbInfo getParent() {
        //if (parent != null)
        return parent;
    }

    public void setParent(ConfdbInfo parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ConfdbInfo{" +
                "key='" + key + '\'' +
                ", depth=" + depth +
                ", value=" + value +
                ", children="+ children +
                ", parent=" + parent +
                '}';
    }
}