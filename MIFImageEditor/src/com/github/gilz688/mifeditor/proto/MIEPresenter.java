package com.github.gilz688.mifeditor.proto;

public interface MIEPresenter {

	public void onAbout();

	public void onNew();

	public void onOpen();

	public void onSave();

	public void onSaveAs();

	public void onQuit();

	public void onMousePressed(int x, int y);

	public void onZoom(int zoom);

}