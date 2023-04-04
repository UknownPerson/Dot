package xyz.Dot.setting;

import xyz.Dot.module.Module;

import java.util.ArrayList;

public class Setting {
    private Module module;
    private String name;
    private boolean toggle;
    private double currentValue,minValue,maxValue,incValue;
    private String currentMode;
    private ArrayList<String> Modes;
    private Category category;
    private float settingx = 0;
    private float Red = 0;
    private float Green = 0;
    private float Blue = 0;

    public enum Category{
        BOOLEAN,
        VALUE,
        MODE
    }

    public float getSettingX() {
        return this.settingx;
    }

    public float getRed() {
        return Red;
    }

    public void setRed(float red) {
        Red = red;
    }

    public float getGreen() {
        return Green;
    }

    public void setGreen(float green) {
        Green = green;
    }

    public float getBlue() {
        return Blue;
    }

    public void setBlue(float blue) {
        Blue = blue;
    }

    public void setSettingX(float settingx) {
        this.settingx = settingx;
    }

    public Setting(Module module, String name, boolean toggle) {
        this.module = module;
        this.name = name;
        this.toggle = toggle;
        category = Category.BOOLEAN;
    }

    public Setting(Module module, String name, double currentValue, double minValue, double maxValue, double incValue) {
        this.module = module;
        this.name = name;
        this.currentValue = currentValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.incValue = incValue;
        category = Category.VALUE;
    }

    public Setting(Module module, String name, String currentMode, ArrayList<String> modes) {
        this.module = module;
        this.name = name;
        this.currentMode = currentMode;
        Modes = modes;
        category = Category.MODE;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getIncValue() {
        return incValue;
    }

    public void setIncValue(double incValue) {
        this.incValue = incValue;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public ArrayList<String> getModes() {
        return Modes;
    }

    public void setModes(ArrayList<String> modes) {
        Modes = modes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isBoolean(){
        return this.category.equals(Category.BOOLEAN);
    }

    public boolean isValue(){
        return this.category.equals(Category.VALUE);
    }

    public boolean isMode(){
        return this.category.equals(Category.MODE);
    }

}
