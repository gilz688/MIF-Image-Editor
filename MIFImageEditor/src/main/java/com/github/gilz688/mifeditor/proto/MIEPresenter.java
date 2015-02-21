package com.github.gilz688.mifeditor.proto;

public interface MIEPresenter {

	public void onAbout();

	public void onNew();

	public void onOpen();

	public void onSave();

	public void onSaveAs();

	public void onQuit();

	public void onMousePressed(double x, double y);

	public void onZoom(int zoom);

	public void onColorPick(double red, double blue, double green);

	public void onEyedropperToolClick(double x, double y);

}